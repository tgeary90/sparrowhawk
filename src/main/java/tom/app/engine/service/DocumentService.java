package tom.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tom.app.engine.model.WebPage;

@Component
public class DocumentService {

	private DocumentDao documentDao;
	
	@Autowired
	public DocumentService(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}	
	
	public String index(WebPage webPage, String subscriber) {
		// TODO validate against subscriber
		return documentDao.index(webPage, subscriber);
	}

	public WebPage get(String sub, String docId) {
		return documentDao.get(docId, sub);
	}

	public String search(String sub, WebPage webPage) {
		// TODO request flow logic
		return null;
	}
	
	

}
