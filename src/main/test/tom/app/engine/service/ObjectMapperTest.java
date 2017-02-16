package tom.app.engine.service;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import tom.app.engine.model.WebPage;

public class ObjectMapperTest {

	private final String WEB_PAGE_JSON = "{\"url\":\"www.test.com\",\"html\":\"<html>boo</html>\"}";

	@Test
	public void shouldDeserializeWebPage() {
		ObjectMapper mapper = new ObjectMapper();
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
	public void shouldSerializeWebPage() {
		ObjectMapper mapper = new ObjectMapper();
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
