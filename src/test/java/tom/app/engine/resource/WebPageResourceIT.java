package tom.app.engine.resource;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import javax.ws.rs.client.ClientBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import tom.app.engine.client.EngineClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebPageResourceIT {

	@LocalServerPort
	private int port;
	
	//@Autowired
	//private TestRestTemplate httpClient;
	
	private EngineClient client;
	
	private String baseURL;
	
	@Before
	public void setUp() throws Exception {
		baseURL = "http://localhost";
		client = new EngineClient(ClientBuilder.newClient(), baseURL, "8080");
	}
	
	@Test
	public void shouldIndexWebpage() {
		// TODO spring boot IT setup
		// TODO bring up elasticsearch in docker container.
		
		
	}
	
	@Test
	public void shouldRetrieveWebPage() {
		
	}
	
	@Test
	public void shouldPassHealthCheck() {
		//String body = httpClient.getForObject("/healthcheck", String.class);
		String body = client.get("healthcheck");
		assertThat(body).isEqualTo("sparrowhawk operational");
	}
}
