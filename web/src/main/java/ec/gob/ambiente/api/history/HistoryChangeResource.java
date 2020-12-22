package ec.gob.ambiente.api.history;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.infomanglar.model.HistoryChange;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/history-changes")
public class HistoryChangeResource {

	@EJB
	private HistoryChangeFacade historyChangeFacade;
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<HistoryChange> get() {
		return historyChangeFacade.findAll();
	}

	@GET
	@Path("/filter/{form-type}/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<HistoryChange> filter(@PathParam("form-type") String formType, @PathParam("form-id") Integer formId) {
		return historyChangeFacade.findByForm(formType, formId);
	}
	
}
