package ec.gob.ambiente.api.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.model.EmailNotification;
import ec.gob.ambiente.infomanglar.services.EmailNotificationFacade;

@Path("/email-notification")
public class EmailNotificationResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private EmailNotificationFacade emailNotificationFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(EmailNotification emailNotification) {
		boolean success = emailNotificationFacade.save(emailNotification);
		if (success) {
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmailNotification> get() {
		return emailNotificationFacade.findAll();
	}

	@POST
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DataResponse remove(DataRemove dataRemove) {
		EmailNotification emailNotification = emailNotificationFacade.find(dataRemove.getId());
		emailNotificationFacade.remove(emailNotification);
		return new DataResponse(DataResponse.SUCCESS_STATE);
	}

}
