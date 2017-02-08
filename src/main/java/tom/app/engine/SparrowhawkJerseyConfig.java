package tom.app.engine;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class SparrowhawkJerseyConfig extends ResourceConfig {

	public SparrowhawkJerseyConfig() {
		register(WebPageResource.class);
	}
}
