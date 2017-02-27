package tom.app.engine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class represents a web page
 * @author tomg
 *
 */
public class WebPage {
	private String url;
	private String htmlText;
	private String tag;

	@JsonCreator
	public WebPage(
			@JsonProperty("url") String url, 
			@JsonProperty("html") String htmlText,
			@JsonProperty("tag") String tag) {
		super();
		this.url = url;
		this.htmlText = htmlText;
		this.tag = tag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonGetter("html")
	public String getHtmlText() {
		return htmlText;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
