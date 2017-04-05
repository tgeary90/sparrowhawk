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
	
	public String index(WebPage webPage, String subId) {
		// TODO validate against subIdscriber
		return documentDao.index(webPage, subId);
	}

	public WebPage get(String subId, String docId) {
		return documentDao.get(docId, subId);
	}

	public String search(String subId, WebPage webPage) {
		return documentDao.search(subId, webPage.getUrl(), "webpage", "url");
	}
	
	

}
