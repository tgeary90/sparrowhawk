package tom.app.engine.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

@Component
@Path("healthcheck")
public class HealthCheck {

	private String ok = "sparrowhawk operational";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String healthCheck() {
		return ok;
	}
}
