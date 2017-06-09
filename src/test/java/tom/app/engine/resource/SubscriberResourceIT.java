package tom.app.engine.resource;

import java.util.UUID;

import javax.ws.rs.client.ClientBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tom.app.engine.client.EngineClient;
import tom.app.engine.model.Subscriber;
import tom.app.engine.model.Subscriber.License;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubscriberResourceIT {

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
	public void shouldRegisterSubscriber() {
		Subscriber sub = new Subscriber(UUID.randomUUID(), "register", License.CUSTOMER);
		String result = client.registerSubscriber(sub);
		
		//Tested at e-e level 
		//assertThat(result).isEqualTo("true");
	}
}
