package tom.app.engine;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import tom.app.engine.resource.WebPageResource;

@Component
public class SparrowhawkJerseyConfig extends ResourceConfig {

	public SparrowhawkJerseyConfig() {
		register(WebPageResource.class);
	}
}
