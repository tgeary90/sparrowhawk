package tom.app.engine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tom.app.engine.model.LexicalExpressionList;
import tom.app.engine.model.TextEntity;
import tom.app.engine.model.WebPage;

@Component
public class DocumentService {

	private DocumentDao documentDao;
	private ListLoaderService loaderService;
	
	@Autowired
	public DocumentService(DocumentDao documentDao, ListLoaderService loaderService) {
		super();
		this.documentDao = documentDao;
		this.loaderService = loaderService;
	}

	
	public String index(WebPage webPage, String subId) {
		// TODO validate against subscriber
		return documentDao.index(webPage, subId);
	}

	public WebPage get(String subId, String docId) {
		return documentDao.get(docId, subId);
	}

	public String search(String subId, WebPage webPage) {
		return documentDao.search(subId, webPage.getUrl(), "webpage", "url");
	}

	public String filter(String subId, WebPage webPage) {
		List<LexicalExpressionList<TextEntity>> lists = loaderService.get();
		
		
		
	}
	
	

}
