package tom.app.engine.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import tom.app.engine.model.WebPage;
import tom.app.engine.service.DocumentDao;

public class ElasticsearchDao implements DocumentDao {

	private TransportClient client;
	
	public ElasticsearchDao() throws UnknownHostException {
		super();
		
		Settings settings = Settings.builder().put(
				"cluster.name", "sparrowhawk").build();
		
		client = new PreBuiltTransportClient(settings);	
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("es"), 9300));
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
