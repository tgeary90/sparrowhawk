package tom.app.engine.service;

import org.elasticsearch.action.delete.DeleteResponse;

import tom.app.engine.model.Subscriber;
import tom.app.engine.model.WebPage;

public interface DocumentDao {
	String index(WebPage page, String s);
	WebPage get(int id, Subscriber s);
	DeleteResponse delete(int id, Subscriber s);
}
