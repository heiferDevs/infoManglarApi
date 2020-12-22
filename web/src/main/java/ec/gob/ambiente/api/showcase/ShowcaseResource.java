package ec.gob.ambiente.api.showcase;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.services.EconomicIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.OfficialDocsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PricesFormFacade;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;

@Path("/showcase")
public class ShowcaseResource {

	@EJB
	private PricesFormFacade pricesFormFacade;

	@EJB
	private EconomicIndicatorsFormFacade economicIndicatorsFormFacade;

	@EJB
	private OfficialDocsFormFacade officialDocsFormFacade;
	
	@EJB
	private OrganizationsUserFacade organizationsUserFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@GET
	@Path("/orgs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Org> getOrgs() {
		List<OrganizationManglar> organizationsManglar= organizationManglarFacade.findAll();
		List<Org> orgs = new ArrayList<>();
		for (OrganizationManglar organizationManglar : organizationsManglar) {
			Integer orgId = organizationManglar.getOrganizationManglarId();
			PricesForm pricesForm = pricesFormFacade.getLastByOrg(orgId);
			EconomicIndicatorsForm economicIndicatorsForm = economicIndicatorsFormFacade.getLastByOrg(orgId);
			OfficialDocsForm officialDocsForm = officialDocsFormFacade.getLastByOrg(orgId);
			if (pricesForm != null && officialDocsForm != null) {
				Org org = new ShowcaseUtil().getOrg(pricesForm, officialDocsForm, economicIndicatorsForm, organizationsUserFacade);
				orgs.add(org);
			}
		}
		return orgs;
	}

}
