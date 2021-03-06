package tom.app.engine.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public Response indexWebPage(@PathParam("subscriberId") String subId, WebPage webPage) {
		String docId = documentService.index(webPage, subId);
		 return Response.status(Status.CREATED).entity(docId).build();
	}
	
	@GET
	@Path("{subscriberId}/index/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWebPage(@PathParam("subscriberId") String subId, @PathParam("id") String docId) {
		WebPage page = documentService.get(subId, docId);
		return Response.ok().entity(page).build();
	}
	
	@POST
	@Path("{subscriberId}/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response searchWebPage(@PathParam("subscriberId") String subId, WebPage webPage) {
		String docId = documentService.search(subId, webPage);
		return Response.ok().entity(docId).build();
	}
	
	@POST
	@Path("{subscriberId}/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response filterWebPage(@PathParam("subscriberId") String subId, WebPage webPage) {
		String decision = documentService.filter(subId, webPage).toString();
		return Response.ok().entity(decision).build();
	}
}
