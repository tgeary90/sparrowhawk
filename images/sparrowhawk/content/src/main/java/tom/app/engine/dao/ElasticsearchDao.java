package tom.app.engine.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tom.app.engine.model.WebPage;
import tom.app.engine.model.WebPageMixIn;
import tom.app.engine.service.DocumentDao;

@Component
public class ElasticsearchDao implements DocumentDao {

	private final static Logger LOGGER = Logger.getLogger(ElasticsearchDao.class);
	
	private TransportClient client;
	private String clusterName;
	private String elasticsearchHostname;
	private String port;
	
	@Autowired
	public ElasticsearchDao(
			@Value("${es.cluster.name}") String clusterName,
			@Value("${es.host.name}") String elasticsearchHostname, 
			@Value("${es.port}") String port) {
		
		this.clusterName = clusterName;
		this.elasticsearchHostname = elasticsearchHostname;
		this.port = port;
		
		Settings settings = Settings.builder()
				.put("client.transport.sniff", false)
				.put("cluster.name", this.clusterName)
				.build();
		try {
			client = new PreBuiltTransportClient(settings);	
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(this.elasticsearchHostname), Integer.valueOf(this.port)));
			LOGGER.info("Connected to "+elasticsearchHostname+" for cluster "+this.clusterName);
		} 
		catch (UnknownHostException e) {
			LOGGER.error("Is elasticsearch running?", e);
			System.exit(1);
		}
		catch (Exception e) {
			LOGGER.error("could not connect to elastic seach with these parameters...");
			LOGGER.error("port: " + port + ", " + " hostname: + " + elasticsearchHostname);
			System.exit(1);;
		}
	}
	
	/**
	 * Indexes a document into ES under the Subscribers
	 * ID
	 */
	@Override
	public String index(WebPage page, String index)  {
		
		boolean isIndexExists = IndexCheck(index);
		if ( ! isIndexExists) {
			return "Request not from Subscriber.";
		}
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(WebPage.class, WebPageMixIn.class);
		byte[] rawJson = null;
		try {
			rawJson = mapper.writeValueAsBytes(page);
			
		}
		catch (JsonProcessingException je) {
			throw new RuntimeException("could not create json", je);
		}
		// TODO check to see if this document already exists 
		// in the index
		//IndexResponse resp = client.prepareIndex(index, "webpage").setSource(rawJson).get();
		IndexResponse resp = client.prepareIndex(index, "webpage").setSource(rawJson).setRefreshPolicy(RefreshPolicy.IMMEDIATE).get();
		LOGGER.info("Indexing page in ES");
		return resp.getId();
	}

	@Override
	public WebPage get(String docId, String index) {
		
		LOGGER.info("Getting page with doc id: " + docId);
		
		GetResponse resp = client.prepareGet(index, "webpage", String.valueOf(docId)).get();
		byte[] respJson = resp.getSourceAsBytes();
		WebPage page = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(WebPage.class, WebPageMixIn.class);
		try {
			page = mapper.readValue(respJson, WebPage.class);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return page;
	}

	/**
	 * Deletes a document out of ES under the Subscribers
	 * ID
	 */
	@Override
	public DeleteResponse delete(int docId, String s) {
		return client.prepareDelete(s, "webpage", String.valueOf(docId)).get();
	}
	
	@Override
	public String prepareIndex(String indexName, String type) throws IOException {
		
		LOGGER.info("Preparing index with name: " + indexName + " and with mapping " + type);
		
		boolean isIndexExists = IndexCheck(indexName);
		if (isIndexExists) {
			return "Index already exists for " + indexName;
		}
		
		String settingsJson = XContentFactory.jsonBuilder()
		.startObject()
			.startObject("analysis")
				.startObject("analyzer")
					.startObject("my_analyzer")
						.field("type", "custom")
						.field("char_filter", "html_strip")
						.field("tokenizer", "standard")
						.field("filter", "lowercase")
					.endObject()
				.endObject()
			.endObject()
		.endObject().string();

		String mappingJson = XContentFactory.jsonBuilder()
				.startObject()
						.startObject(type)
							.startObject("properties")
								.startObject("url")
									.field("type", "string")
									.field("index", "not_analyzed")
								.endObject()
								.startObject("html")
									.field("type", "string")
									.field("index", "analyzed")
									.field("analyzer", "my_analyzer")
								.endObject()
							.endObject()
						.endObject()
				.endObject().string();
		
		CreateIndexResponse resp = client.admin().indices().prepareCreate(indexName)
			.setSettings(Settings.builder().loadFromSource(settingsJson))
			.addMapping(type, mappingJson)
			.get();
		
		LOGGER.info("Refreshing " + indexName);
		client.admin().indices()
        .prepareRefresh(indexName)   
        .get();
			
		return String.valueOf(resp.isAcknowledged());
	}

	@Override
	public String getDocId(String subId, String value, String type, String field) {
		
		LOGGER.info("getting doc id...");
		
		String result = "miss";
		SearchResponse resp = client.prepareSearch(subId)
				.setTypes(type)
				.setQuery(QueryBuilders.termQuery(field, value))
				.setFrom(0).setSize(1).setExplain(true).get();
		long numHits = resp.getHits().getTotalHits();
		if (numHits > 0) {
			SearchHit hit = resp.getHits().getAt(0);
			result = hit.getId();
		}
		return result;
	}
	
	@Override
	public WebPage filter(String subId, String type, QueryBuilder qb) {
		
		LOGGER.info("Filtering page for " + subId);
		
		WebPage page = new WebPage("", "");
		SearchResponse resp = client.prepareSearch(subId)
				.setTypes(type)
				.setQuery(qb)
				.setFrom(0).setSize(1).setExplain(true).get();
		long numHits = resp.getHits().getTotalHits();
		if (numHits > 0) {
			SearchHit hit = resp.getHits().getAt(0);
			Map<String, Object> rawPage = hit.getSource();
			page.setUrl((String) rawPage.get("url"));
			page.setHtmlText((String) rawPage.get("html"));
		}
		return page;
	}
	
	private boolean IndexCheck(String s) {
		GetSettingsResponse resp = null;
		try {
			 resp = client.admin().indices()
				.prepareGetSettings(s).get();
		} 
		catch (Throwable t) {
			return false;
		}
		ImmutableOpenMap<String, Settings> cursor = resp.getIndexToSettings();
		return (cursor == null) || cursor.containsKey(s);
	}

}
