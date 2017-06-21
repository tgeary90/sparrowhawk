package tom.app.engine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class WebPageMixIn {

	@JsonCreator
	public WebPageMixIn(
			@JsonProperty("url") String url,
			@JsonProperty("html") String htmlText) {
	}
		
	@JsonGetter("html")
	public abstract String getHtmlText();	

	
}
