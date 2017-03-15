package tom.app.engine.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tom.app.engine.model.WebPage;
import tom.app.engine.service.DocumentDao;

@Component
public class ElasticsearchDao implements DocumentDao {

	private final static Logger LOGGER = Logger.getLogger(ElasticsearchDao.class);
	
	private TransportClient client;
	private String clusterName;
	private String elasticsearchHostname;
	
	@Autowired
	public ElasticsearchDao(@Value("${es.cluster.name}") String clusterName,  @Value("${es.host.name}") String elasticsearchHostname) {
		super();
		this.clusterName = clusterName;
		this.elasticsearchHostname = elasticsearchHostname;
		
		Settings settings = Settings.builder().put(
				"cluster.name", this.clusterName).build();
		try {
			client = new PreBuiltTransportClient(settings);	
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(this.elasticsearchHostname), 9300));
			LOGGER.info("Connected to "+elasticsearchHostname+" for cluster "+clusterName);
		} 
		catch (UnknownHostException e) {
			LOGGER.error("Is elasticsearch running?", e);
			System.exit(1);
		}
	}
	
	/**
	 * Indexes a document into ES under the Subscribers
	 * ID
	 */
	@Override
	public String index(WebPage page, String s)  {
		
		boolean isIndexExists = IndexCheck(s);
		if ( ! isIndexExists) {
			try {
				prepareIndex(s, "webpage");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		byte[] rawJson = null;
		try {
			rawJson = mapper.writeValueAsBytes(page);
			
		}
		catch (JsonProcessingException je) {
			throw new RuntimeException("could not create json", je);
		}
		IndexResponse resp = client.prepareIndex(s, "webpage").setSource(rawJson).get();
		return resp.getId();
	}

	@Override
	public WebPage get(String docId, String s) {
		GetResponse resp = client.prepareGet(s, "webpage", String.valueOf(docId)).get();
		byte[] respJson = resp.getSourceAsBytes();
		WebPage page = null;
		ObjectMapper mapper = new ObjectMapper();
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
	
	private void prepareIndex(String indexName, String type) throws IOException {
		
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
					.startObject("mappings")
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
					.endObject()
				.endObject().string();
		
		client.admin().indices().prepareCreate(indexName)
			.setSettings(Settings.builder().loadFromSource(settingsJson))
			.addMapping(type, mappingJson)
			.get();
			
	}

	private boolean IndexCheck(String s) {
		GetSettingsResponse resp = client.admin().indices()
				.prepareGetSettings(s).get();
		ImmutableOpenMap<String, Settings> cursor = resp.getIndexToSettings();
		return (cursor == null) || cursor.containsKey(s);
	}
}
