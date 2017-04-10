package tom.app.engine.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

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
	public void shouldIndexAPage() {
		Subscriber sub = new Subscriber(UUID.randomUUID(), "indexPage", License.CUSTOMER);
		client.registerSubscriber(sub);
		WebPage webPage = new WebPage("www.dexapage.local", "<html>pass</html>");
		
		String docId = client.indexAPage(webPage, sub);
		
		assertNotNull(docId);
		WebPage actual = client.getPage(docId, sub);
		assertThat(actual.getHtmlText()).isEqualTo("<html>pass</html>");
		assertThat(actual.getUrl()).isEqualTo("www.dexapage.local");
	}
	
	@Test
	public void shouldPassHealthCheck() {
		String body = client.get("healthcheck");
		assertThat(body).isEqualTo("sparrowhawk operational");
	}
	
	@Test
	public void shouldFindAPage() {
		Subscriber sub = new Subscriber(UUID.randomUUID(), "indexPage", License.CUSTOMER);
		client.registerSubscriber(sub);
		WebPage webPage = new WebPage("www.findapage.local", "<html>passTestFind</html>");
		String docId = client.indexAPage(webPage, sub);
		assertNotNull(docId);
		
		String id = client.searchFor(webPage, sub);
		
		WebPage actual = client.getPage(docId, sub);
		assertThat(actual.getHtmlText()).isEqualTo("<html>passTestFind</html>");
		assertThat(actual.getUrl()).isEqualTo("www.findapage.local");
	}
	
	@Test
	public void shouldAllowARequest() {
		Subscriber sub = new Subscriber(UUID.randomUUID(), "indexPage", License.CUSTOMER);
		client.registerSubscriber(sub);
		sleep(2);
		WebPage webPage = new WebPage("www.allowapage.local", "<html>passTestAllow</html>");
		client.indexAPage(webPage, sub);
		sleep(2);
		String id = client.searchFor(webPage, sub);
		sleep(1);
		WebPage actual = client.getPage(id, sub);
		sleep(1);
		assertThat(actual.getHtmlText()).isEqualTo("<html>passTestAllow</html>");
		assertThat(actual.getUrl()).isEqualTo("www.allowapage.local");
		
		String result = client.filter(webPage, sub);
		
		assertThat(result).isEqualTo("ALLOW");
	}
	
	@Test
	public void shouldBlockARequest() {
		Subscriber sub = new Subscriber(UUID.randomUUID(), "indexPage", License.CUSTOMER);
		client.registerSubscriber(sub);
		sleep(2);
		WebPage webPage = new WebPage("www.blockapage.local", "<html>revolver</html>");
		client.indexAPage(webPage, sub);
		sleep(2);
		String id = client.searchFor(webPage, sub);
		sleep(1);
		WebPage actual = client.getPage(id, sub);
		sleep(1);
		assertThat(actual.getHtmlText()).isEqualTo("<html>revolver</html>");
		assertThat(actual.getUrl()).isEqualTo("www.blockapage.local");
		
		String result = client.filter(webPage, sub);
		
		assertThat(result).isEqualTo("BLOCK");
	}
	
	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException ie) {}
	}
}
