package ec.gob.ambiente.api.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.model.UserInfoManglarModel;
import ec.gob.ambiente.enlisy.model.Contact;
import ec.gob.ambiente.enlisy.model.ContactsForm;
import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.model.Nationality;
import ec.gob.ambiente.enlisy.model.Organization;
import ec.gob.ambiente.enlisy.model.OrganizationsType;
import ec.gob.ambiente.enlisy.model.People;
import ec.gob.ambiente.enlisy.model.TreatmentsType;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.ContactFacade;
import ec.gob.ambiente.enlisy.services.ContactsFormFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.enums.TipoMensajeMailEnum;
import ec.gob.ambiente.suia.utils.JsfUtil;
import ec.gob.ambiente.util.Email;
import ec.gob.ambiente.vo.NotificacionesMails;
import ec.gob.registrocivil.consultacedula.Cedula;

@ManagedBean
@ViewScoped
public class UserInfoManglarController implements Serializable {
	
	public static final String ROL_SOCIO_INFO_MANGLAR = "Socio-InfoManglar";
	public static final String ROL_ORG_INFO_MANGLAR = "Org-InfoManglar";
	public static final String ROL_MAE_INFO_MANGLAR = "Mae-InfoManglar";
	public static final String ROL_INP_INFO_MANGLAR = "Inp-InfoManglar";
	public static final String ROL_SUPER_ADMIN_INFO_MANGLAR = "SuperAdmin-InfoManglar";
	
	private static final long serialVersionUID = -8083094528006538436L;
	private static final String SOLO_NUMEROS = "return numbersonly(this, event);";
	private static final String TAMANIO_250 = "250";
	private static final String TAMANIO_10 = "10";
	private static final String PERSONA_NATURAL = "N";
	private static final String GENERO_MASCULINO = "HOMBRE";
	private static final String ID_EMPRESA_MIXTA = "8";
	
	@Getter
	@Setter
	private UserFacade userfacade;
	
	@Getter
	@Setter
	private ContactFacade contactFacade;

	@Getter
	@Setter
	private ContactsFormFacade contactsFormFacade;

	@Getter
	@Setter
	private UserInfoManglarModel socioManglarBean;
	

	@PostConstruct
	public void inicio() {
		socioManglarBean = new UserInfoManglarModel();
		cambioTipoPersona();
		socioManglarBean.setTipoPersona(PERSONA_NATURAL);        
	}

	public void cambioTipoPersona() {
		socioManglarBean.setScriptTamanio(TAMANIO_250);
		socioManglarBean.setScriptTamanioDocumento(TAMANIO_10);
		socioManglarBean.setScriptNumerosDocumento(SOLO_NUMEROS);
		socioManglarBean.setUsuario(new User());
		socioManglarBean.setPersona(new People());
		socioManglarBean.setOrganizacion(new Organization());
		socioManglarBean.setContacto(new Contact());
		socioManglarBean.setListaContactoObligatorios(new ArrayList<Contact>());
		socioManglarBean.setListaContactoOpcionales(new ArrayList<Contact>());
		socioManglarBean.getPersona().setPeopTitle("Proponente");
		socioManglarBean.getUsuario().setUserStatus(true);
		socioManglarBean.getPersona().setPeopStatus(true);
		socioManglarBean.getContacto().setStatus(true);
		socioManglarBean.getOrganizacion().setOrgaStatus(true);
	}

	public void cargarDatosWsCedula(Cedula cedula) {
		if (cedula != null
		&& cedula.getError().equals("NO ERROR")) {
			socioManglarBean.getPersona().setPeopPin(cedula.getCedula());
			socioManglarBean.getPersona().setPeopName(cedula.getNombre());
			socioManglarBean.getPersona().setPeopGenre(
			cedula.getGenero().equals(GENERO_MASCULINO) ? "HOMBRE"
			: "MUJER");
			cargarTratamiento(cedula);
			socioManglarBean.setWsEncontrado(true);
		} else {
			socioManglarBean.setWsEncontrado(false);
		}
	}

	private void cargarTratamiento(Cedula cedula) {
		if (cedula.getGenero().equals(GENERO_MASCULINO)) {
			socioManglarBean.setIdTipoTrato("1");
		} else if (("CASADO").equals(cedula.getEstadoCivil())) {
			socioManglarBean.setIdTipoTrato("2");
		} else {
			socioManglarBean.setIdTipoTrato("3");
		}
	}

	public void addContacts() {
		socioManglarBean.setListaContactoObligatorios(new ArrayList<Contact>());
		ContactsForm fcTelf = new ContactsForm(ContactsForm.TELEFONO);
		Contact cTelf = new Contact();
		cTelf.setContactsForm(fcTelf);
		cTelf.setStatus(true);
		cTelf.setValue(socioManglarBean.getTelefono());
		ContactsForm fcCel = new ContactsForm(ContactsForm.CELULAR);
		Contact cCel = new Contact();
		cCel.setContactsForm(fcCel);
		cCel.setStatus(true);
		cCel.setValue(socioManglarBean.getCelular());
		ContactsForm fcDir = new ContactsForm(ContactsForm.DIRECCION);
		Contact cDir = new Contact();
		cDir.setContactsForm(fcDir);
		cDir.setStatus(true);
		cDir.setValue(socioManglarBean.getDireccion());
		ContactsForm fcEma = new ContactsForm(ContactsForm.EMAIL);
		Contact cEma = new Contact();
		cEma.setContactsForm(fcEma);
		cEma.setStatus(true);
		cEma.setValue(socioManglarBean.getEmail());
		if (socioManglarBean.getTipoPersona().equals(PERSONA_NATURAL)) {
			cTelf.setPeople(socioManglarBean.getPersona());
			cCel.setPeople(socioManglarBean.getPersona());
			cDir.setPeople(socioManglarBean.getPersona());
			cEma.setPeople(socioManglarBean.getPersona());
		} else {
			cTelf.setOrganization(socioManglarBean.getOrganizacion());
			cCel.setOrganization(socioManglarBean.getOrganizacion());
			cDir.setOrganization(socioManglarBean.getOrganizacion());
			cEma.setOrganization(socioManglarBean.getOrganizacion());
		}
		socioManglarBean.getListaContactoObligatorios().add(cTelf);
		socioManglarBean.getListaContactoObligatorios().add(cCel);
		socioManglarBean.getListaContactoObligatorios().add(cDir);
		socioManglarBean.getListaContactoObligatorios().add(cEma);
	}

	public boolean guardar(String role) {
		try {
			socioManglarBean.getUsuario().setUserTempPassword("abc12345"/*JsfUtil.generatePassword()*/);
			socioManglarBean.getUsuario()
				.setUserPassword(JsfUtil.claveEncriptadaSHA1(socioManglarBean.getUsuario()
				.getUserTempPassword()));
			socioManglarBean.getUsuario().setUserCreationDate(new Date());
			socioManglarBean.getPersona()
				.setGeographicalLocation(new GeographicalLocation(Integer.valueOf(socioManglarBean.getIdParroquia())));           
			
			if (socioManglarBean.getTipoPersona().equals(PERSONA_NATURAL)) {
				socioManglarBean.getPersona().setTreatmentsType(new TreatmentsType(Integer.valueOf(socioManglarBean.getIdTipoTrato())));
				socioManglarBean.getPersona().setNationality(new Nationality(Integer.valueOf(socioManglarBean.getIdNacionalidad())));
				socioManglarBean.getUsuario().setUserName(
				socioManglarBean.getUsuario().getUserPin());
				socioManglarBean.getUsuario().setPeople(socioManglarBean.getPersona());
				socioManglarBean.getPersona().setContacts(
				socioManglarBean.getListaContactoObligatorios());
				for (Contact c : socioManglarBean.getListaContactoOpcionales()) {
					socioManglarBean.getPersona().getContacts().add(c);
				}            
				
				NotificacionesMails mail = cargarMail(socioManglarBean.getPersona().getPeopName(),
				socioManglarBean.getUsuario().getUserName(),
				socioManglarBean.getUsuario().getUserTempPassword(),
				socioManglarBean.getEmail());
				
				userfacade.save(socioManglarBean.getUsuario(), mail, true, role);
				
				if(socioManglarBean.getUsuario().getUserId() != null) {
					NotificacionesMails nm = cargarMail(socioManglarBean.getPersona().getPeopName(),
					socioManglarBean.getUsuario().getUserName(),
					socioManglarBean.getUsuario().getUserTempPassword(),
					socioManglarBean.getEmail());
					Email.sendEmail(socioManglarBean.getEmail(), nm.getAsunto(), nm.getContenido());
				}
			}
			else {
				socioManglarBean.getOrganizacion().setContacts(socioManglarBean.getListaContactoObligatorios());
				for (Contact c : socioManglarBean.getListaContactoOpcionales()) {
					c.setOrganization(socioManglarBean.getOrganizacion());
					socioManglarBean.getOrganizacion().getContacts().add(c);
				}
				socioManglarBean.getUsuario().setUserName(
				socioManglarBean.getOrganizacion().getOrgaRuc());
				socioManglarBean.getPersona().setOrganizations(new ArrayList<Organization>());
				socioManglarBean.getPersona().getOrganizations()
					.add(socioManglarBean.getOrganizacion());
				socioManglarBean.getPersona().setTreatmentsType(new TreatmentsType(4));
				socioManglarBean.getUsuario().setPeople(socioManglarBean.getPersona());
				socioManglarBean.getOrganizacion().setOrgaStateParticipation(
				socioManglarBean.getIdTipoOrganizacion().equals(ID_EMPRESA_MIXTA) ? socioManglarBean.getOrganizacion().getOrgaStateParticipation() : null);
				socioManglarBean.getOrganizacion().setOrganizationsType(
				new OrganizationsType(Integer.valueOf(socioManglarBean.getIdTipoOrganizacion())));
				socioManglarBean.getOrganizacion().setGeloId(
				Integer.valueOf(socioManglarBean.getIdParroquia()));
				socioManglarBean.getOrganizacion().setPeople(
				socioManglarBean.getPersona());
				NotificacionesMails mail = cargarMail(socioManglarBean.getPersona().getPeopName(),
						socioManglarBean.getOrganizacion().getOrgaRuc(),
						socioManglarBean.getUsuario().getTempPassword(),
						socioManglarBean.getEmail());

				userfacade.save(socioManglarBean.getUsuario(), mail, true, role);
				
				if(socioManglarBean.getUsuario().getUserId() != null) {
					NotificacionesMails nm = 
					cargarMail(socioManglarBean.getPersona().getPeopName(),
					socioManglarBean.getOrganizacion().getOrgaRuc(),
					socioManglarBean.getUsuario().getUserTempPassword(),
					socioManglarBean.getEmail());
					Email.sendEmail(socioManglarBean.getEmail(), nm.getAsunto(), nm.getContenido());
				}
				
			}			
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();            
		}
		return false;
	}

	private NotificacionesMails cargarMail(final String nombres,
			final String login, final String clave, final String email) {
		NotificacionesMails nm = new NotificacionesMails();
		nm.setAsunto("Aprobación registro de usuario");
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<p>Estimado/a Se&ntilde;or/a ").append(nombres)
		.append("</p>").append("<br/>");
		mensaje.append("<p>Confirmamos que su solicitud de registro de usuario en el sistema SUIA fue aprobada con los siguientes datos: </p>");
		mensaje.append("<ul><li>Nombre de usuario: ").append(login)
		.append("</li>");
		mensaje.append("<li>Contrase&ntilde;a: ").append(clave).append("</li>")
		.append("</ul>");
		mensaje.append(
		"<p>Una vez que haya ingresado al sistema es necesario que realice el cambio de contrase&ntilde;a, accediendo a su perfil de usuario, el mismo se encuentra en la parte izquierda de la segunda barra de la pantalla. (Pulse con el mouse sobre su nombre)")
		.append("</p><br/>");
		mensaje.append("<p>Saludos").append("</p>");
		mensaje.append("<p>Ministerio del Ambiente").append("</p>")
		.append("<br/>");
		nm.setContenido(mensaje.toString());
		nm.setEmail(email);
		nm.setTipoMensaje(TipoMensajeMailEnum.TEXT_HTML.getNemonico());
		return nm;
	}

}
