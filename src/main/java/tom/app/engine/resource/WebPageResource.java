package tom.app.engine.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tom.app.engine.model.WebPage;
import tom.app.engine.service.DocumentService;

/**
 * Resource to provide index/get/delete operations on simple page
 * objects.
 * @author tomg
 *
 */
@Component
@Path("pages")
public class WebPageResource {
	
	private DocumentService documentService;

	@Autowired
	public WebPageResource(DocumentService documentService) {
		super();
		this.documentService = documentService;
	}

	@POST
	@Path("{subscriber}/index")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String indexWebPage(@PathParam("subscriber") String sub, WebPage webPage) {
		return documentService.index(webPage, sub);
	}
	
	
}
