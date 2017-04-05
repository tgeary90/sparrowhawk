package tom.app.engine.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tom.app.engine.model.Subscriber;
import tom.app.engine.service.SubscriberService;

/**
 * Resource to register Subscribers
 * - create ES index for their webpages.
 * @author tomg
 *
 */
@Component
@Path("subscribers")
public class SubscriberResource {

	private SubscriberService subscriberService;

	
	@Autowired
	public SubscriberResource(SubscriberService subscriberService) {
		super();
		this.subscriberService = subscriberService;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String register(Subscriber sub) {
		return subscriberService.register(sub);
	}
}
