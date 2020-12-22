package ec.gob.ambiente.api.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.api.history.HistoryChangeUtil;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.services.CrabCollectionFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class CrabCollectionFormResource {

	@EJB
	private CrabCollectionFormFacade crabCollectionFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/crab-collection-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(CrabCollectionForm crabCollectionForm) {
		String changeType = crabCollectionForm.getCrabCollectionFormId() == null ? "created" : "edited";
		boolean success = crabCollectionFormFacade.save(crabCollectionForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, crabCollectionForm.getUserId(),
					crabCollectionForm.getCrabCollectionFormId(), crabCollectionForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/crab-collection-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CrabCollectionForm> get() {
		return crabCollectionFormFacade.findAll();
	}

	@GET
	@Path("/crab-collection-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CrabCollectionForm getById(@PathParam("form-id") Integer formId) {
		return crabCollectionFormFacade.find(formId);
	}

	@GET
	@Path("/crab-collection-form/get-last/{org-id}/{user-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CrabCollectionForm getLast(@PathParam("org-id") Integer orgId, @PathParam("user-id") Integer userId) {
		CrabCollectionForm form = crabCollectionFormFacade.getLastByOrgAndUser(orgId, userId);
		if (form != null) return form;
		return new CrabCollectionForm();
	}

}
