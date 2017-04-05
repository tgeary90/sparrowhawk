package tom.app.engine.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import tom.app.engine.model.Subscriber;

@Component
public class SubscriberService {

	private DocumentDao documentDao;
	
	
	public SubscriberService(DocumentDao documentDao) {
		super();
		this.documentDao = documentDao;
	}

	public String register(Subscriber sub) {
		try {
			return documentDao.prepareIndex(sub.getId().toString(), "webpage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
