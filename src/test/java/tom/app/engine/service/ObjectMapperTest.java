package tom.app.engine.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import tom.app.engine.model.WebPage;
import tom.app.engine.model.WebPageMixIn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ObjectMapperTest.class)
public class ObjectMapperTest {

	private final String WEB_PAGE_JSON = "{\"url\":\"www.test.com\",\"html\":\"<html>boo</html>\"}";

	@Test
	public void shouldDeserializeWebPageTest() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(WebPage.class, WebPageMixIn.class);
		WebPage page = null;
		
		try {
			page = mapper.readValue(WEB_PAGE_JSON, WebPage.class);
			
		} catch (IOException ioe) {
			fail();
		}
		assertEquals("www.test.com", page.getUrl());
		assertEquals("<html>boo</html>", page.getHtmlText());
	}

	@Test
	public void shouldSerializeWebPageTest() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(WebPage.class, WebPageMixIn.class);
		WebPage page = new WebPage("www.test.com", "<html>boo</html>");
		String serializedWebPage = null;
		
		try {
			serializedWebPage = mapper.writeValueAsString(page);
		} catch (IOException ioe) {
			fail();
		}
		assertEquals(WEB_PAGE_JSON, serializedWebPage);
	}
}
