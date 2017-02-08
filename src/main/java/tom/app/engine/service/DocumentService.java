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
	
	public void index(WebPage webPage) {
		documentDao.index(webPage);
	}

}
