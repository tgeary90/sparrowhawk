package tom.app.engine.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tom.app.engine.model.Subscriber;
import tom.app.engine.model.WebPage;

public class EngineClient {

	private final WebTarget target; 
	private final Client client;
	private final String host;
	private final String port;
	
	public EngineClient(Client client, String host, String port) {
		super();
		this.client = client;
		this.host = host;
		this.port = port;
		
		target = client.target(host + ":" + port);
	}

	public String indexAPage(WebPage webPage, Subscriber sub) {
		String endPoint = "pages/" + sub.getId() + "/index";
		Response resp = target.path(endPoint).request(MediaType.TEXT_HTML)
				.post(Entity.entity(webPage,MediaType.APPLICATION_JSON_TYPE));
		
		if (resp.getStatus() != 200) {
			throw new RuntimeException(resp.getStatus() + ": error");
		}
		return resp.readEntity(String.class);
	}

	public String registerSubscriber(Subscriber sub) {
		String endPoint = "subscribers";
		Response resp = target.path(endPoint).request(MediaType.TEXT_HTML)
				.post(Entity.entity(sub, MediaType.APPLICATION_JSON_TYPE));
		
		if (resp.getStatus() != 200) {
			throw new RuntimeException(resp.getStatus() + ": error");
		}
		return resp.readEntity(String.class);
	}
	
	public WebPage getPage(String id, Subscriber sub) {
		String endPoint = "pages/" + sub.getId() + "/index/" + id;
		Response resp = target.path(endPoint).request(
				MediaType.APPLICATION_JSON).get(Response.class);
		
		if (resp.getStatus() != 200) {
			throw new RuntimeException(resp.getStatus() + ": error");
		}
		return resp.readEntity(WebPage.class);
	}
	
	public String get(String path) {
		Response resp = target.path(path).request(MediaType.TEXT_HTML).get(Response.class);
		
		if (resp.getStatus() != 200) {
			throw new RuntimeException(resp.getStatus() + ": error");
		}
		return resp.readEntity(String.class);
	}
	
	public Client getClient() {
		return client;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String searchFor(WebPage webPage, Subscriber sub) {
		String endPoint = "pages/" + sub.getId() + "/search/";
		Response resp = target.path(endPoint).request(MediaType.TEXT_HTML)
				.post(Entity.entity(webPage, MediaType.APPLICATION_JSON_TYPE));
		
		if (resp.getStatus() != 200) {
			throw new RuntimeException(resp.getStatus() + ": error");
		}
		return resp.readEntity(String.class);
	}
}
