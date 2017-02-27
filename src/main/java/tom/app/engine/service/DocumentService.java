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
		// TODO create a subscriber if doesnt exist
		// or validate against if it does.
		return documentDao.index(webPage, subscriber);
	}

}
