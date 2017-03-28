package tom.app.engine.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import javax.ws.rs.client.ClientBuilder;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tom.app.engine.client.EngineClient;
import tom.app.engine.model.Subscriber;
import tom.app.engine.model.Subscriber.License;
import tom.app.engine.model.WebPage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class WebPageResourceIT {

	@LocalServerPort
	private int port;
	
	private EngineClient client;
	
	private String baseURL;
	
	@Before
	public void setUp() throws Exception {
		baseURL = "http://localhost";
		client = new EngineClient(ClientBuilder.newClient(), baseURL, String.valueOf(port));
	}
	
	@Test
	public void shouldIndexWebpage() {
		Subscriber sub = new Subscriber(UUID.randomUUID(), "test_index", License.CUSTOMER);
		WebPage webPage = new WebPage("www.test1.local", "<html>pass</html>");
		String docId = client.post(webPage, sub);
		
		assertNotNull(docId);
	}
	
	@Ignore
	public void shouldRetrieveWebPage() {
		
	}
	
	@Ignore
	public void shouldPassHealthCheck() {
		String body = client.get("healthcheck");
		assertThat(body).isEqualTo("sparrowhawk operational");
	}
}
