package tom.app.engine.service;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.index.query.QueryBuilder;

import tom.app.engine.model.WebPage;

public interface DocumentDao {
	String index(WebPage page, String s);
	WebPage get(String id, String s);
	DeleteResponse delete(int id, String s);
	String prepareIndex(String indexName, String type) throws IOException;
	String getDocId(String subId, String value, String type, String field);
	WebPage filter(String subId, String type, QueryBuilder qb);
}
