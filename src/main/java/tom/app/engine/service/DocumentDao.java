package tom.app.engine.service;

import tom.app.engine.model.WebPage;

public interface DocumentDao {
	void index(WebPage page);
	WebPage get(int id);
	void delete(int id);
}
