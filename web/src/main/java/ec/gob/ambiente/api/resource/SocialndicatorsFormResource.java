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
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.SocialIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.services.SocialIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/social-indicators-form")
public class SocialndicatorsFormResource {

	@EJB
	private SocialIndicatorsFormFacade socialIndicatorsFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(SocialIndicatorsForm socialIndicatorsForm) {
		String changeType = socialIndicatorsForm.getSocialIndicatorsFormId() == null ? "created" : "edited";
		boolean success = socialIndicatorsFormFacade.save(socialIndicatorsForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, socialIndicatorsForm.getUserId(),
					socialIndicatorsForm.getSocialIndicatorsFormId(), socialIndicatorsForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SocialIndicatorsForm> get() {
		return socialIndicatorsFormFacade.findAll();
	}

	@GET
	@Path("/get-last/{org-id}/{user-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SocialIndicatorsForm getLast(@PathParam("org-id") Integer orgId, @PathParam("user-id") Integer userId) {
		SocialIndicatorsForm form = socialIndicatorsFormFacade.getLastByOrgAndUser(orgId, userId);
		if (form != null) return form;
		return new SocialIndicatorsForm();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SocialIndicatorsForm getById(@PathParam("form-id") Integer formId) {
		return socialIndicatorsFormFacade.find(formId);
	}

}


