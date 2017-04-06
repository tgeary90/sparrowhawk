package tom.app.engine.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tom.app.engine.model.Subscriber;
import tom.app.engine.model.WebPage;
import tom.app.engine.service.DocumentService;

/**
 * Resource to provide CRUD operations for page
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
		this.documentService = documentService;
	}

	@POST
	@Path("{subscriberId}/index")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String indexWebPage(@PathParam("subscriberId") String subId, WebPage webPage) {
		return documentService.index(webPage, subId);
	}
	
	@GET
	@Path("{subscriberId}/index/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WebPage getWebPage(@PathParam("subscriberId") String subId, @PathParam("id") String docId) {
		return documentService.get(subId, docId);
	}
	
	@POST
	@Path("{subscriberId}/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String searchWebPage(@PathParam("subscriberId") String subId, WebPage webPage) {
		return documentService.search(subId, webPage);
	}
	
	@POST
	@Path("{subscriberId}/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String filterWebPage(@PathParam("subscriberId") String subId, WebPage webPage) {
		return documentService.filter(subId, webPage).toString();
	}
}
