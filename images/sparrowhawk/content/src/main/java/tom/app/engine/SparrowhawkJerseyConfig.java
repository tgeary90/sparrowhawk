package tom.app.engine;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import tom.app.engine.resource.HealthCheck;
import tom.app.engine.resource.SubscriberResource;
import tom.app.engine.resource.WebPageResource;

@Component
public class SparrowhawkJerseyConfig extends ResourceConfig {

	public SparrowhawkJerseyConfig() {
		register(WebPageResource.class);
		register(HealthCheck.class);
		register(SubscriberResource.class);
	}
}
