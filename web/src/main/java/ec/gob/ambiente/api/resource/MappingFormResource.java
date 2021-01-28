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
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.model.MappingForm;
import ec.gob.ambiente.infomanglar.forms.services.MappingFormFacade;
import ec.gob.ambiente.infomanglar.model.OrgMapping;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

@Path("/mapping-form")
public class MappingFormResource {

	@EJB
	private MappingFormFacade mappingFormFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(MappingForm mappingForm) {
		// update Data before save
		for( OrgMapping orgMapping : mappingForm.getOrgsMapping() ) {
			Integer organizationId = orgMapping.getOrganizationId();
			if (organizationId == null){
				OrganizationManglar organizationManglar = organizationManglarFacade.findByName(orgMapping.getOrganizationName());
				if (organizationManglar != null) organizationId = organizationManglar.getOrganizationManglarId();
			}
			orgMapping.setOrganizationId(organizationId);
			orgMapping.setMappingForm(mappingForm);
			for( FileForm fileForm : orgMapping.getFileForms()) {
				fileForm.setStatus(true);
				fileForm.setOrgMapping(orgMapping);
			}
		}
		String changeType = mappingForm.getMappingFormId() == null ? "created" : "edited";
		boolean success = mappingFormFacade.save(mappingForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, mappingForm.getUserId(),
					mappingForm.getMappingFormId(), mappingForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MappingForm> get() {
		return mappingFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public MappingForm getById(@PathParam("form-id") Integer formId) {
		return mappingFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public MappingForm getLast(@PathParam("org-id") Integer orgId) {
		MappingForm form = mappingFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new MappingForm();
	}

}
