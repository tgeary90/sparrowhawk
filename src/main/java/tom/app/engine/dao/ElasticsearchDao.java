package tom.app.engine.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tom.app.engine.model.Subscriber;
import tom.app.engine.model.WebPage;
import tom.app.engine.service.DocumentDao;

@Component
public class ElasticsearchDao implements DocumentDao {

	private TransportClient client;
	
	public ElasticsearchDao() {
		super();
		
		Settings settings = Settings.builder().put(
				"cluster.name", "sparrowhawk").build();
		try {
			client = new PreBuiltTransportClient(settings);	
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("es"), 9300));
		} 
		catch (UnknownHostException uhe) {
			System.err.println("Is elasticsearch running?");
			uhe.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Indexes a document into ES under the Subscribers
	 * ID
	 */
	@Override
	public String index(WebPage page, String s)  {
		ObjectMapper mapper = new ObjectMapper();
		byte[] rawJson = null;
		try {
			 rawJson = mapper.writeValueAsBytes(page);
			
		}
		catch (JsonProcessingException je) {
			throw new RuntimeException("could not create json", je);
		}
		IndexResponse resp = client.prepareIndex("sub_" + s, "webpage").setSource(rawJson).get();
		return resp.getId();
	}

	@Override
	public WebPage get(int id, Subscriber s) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Deletes a document out of ES under the Subscribers
	 * ID
	 */
	@Override
	public DeleteResponse delete(int docId, Subscriber s) {
		return client.prepareDelete("sub_" + s.getId(), "url", String.valueOf(docId)).get();
	}


}
