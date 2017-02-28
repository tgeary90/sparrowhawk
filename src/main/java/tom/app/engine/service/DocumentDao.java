package tom.app.engine.service;

import org.elasticsearch.action.delete.DeleteResponse;

import tom.app.engine.model.WebPage;

public interface DocumentDao {
	String index(WebPage page, String s);
	WebPage get(String id, String s);
	DeleteResponse delete(int id, String s);
}
