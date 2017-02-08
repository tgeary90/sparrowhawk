package tom.app.engine.model;

/**
 * Model class represents a web page
 * @author tomg
 *
 */
public class WebPage {
	private String url;
	private String htmlText;

	public WebPage(String url, String htmlText) {
		super();
		this.url = url;
		this.htmlText = htmlText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHtmlText() {
		return htmlText;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
}
