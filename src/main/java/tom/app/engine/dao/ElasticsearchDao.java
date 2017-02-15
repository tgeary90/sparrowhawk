package tom.app.engine.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

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

	@Override
	public void index(WebPage page) {
		// TODO Auto-generated method stub

	}

	@Override
	public WebPage get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
