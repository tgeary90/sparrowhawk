package tom.app.engine.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tom.app.engine.model.Subscriber;

@Component
public class SubscriberService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberService.class);
	
	private DocumentDao documentDao;
	
	
	public SubscriberService(DocumentDao documentDao) {
		super();
		this.documentDao = documentDao;
	}

	public String register(Subscriber sub) {
		try {
			return documentDao.prepareIndex(sub.getId().toString(), "webpage");
		} catch (IOException e) {
			LOGGER.warn("Unable to register subscriber {}", sub.getName(), e);
		}
		return "";
	}

}
