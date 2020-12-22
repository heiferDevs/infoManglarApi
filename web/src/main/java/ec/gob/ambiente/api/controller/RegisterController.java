package ec.gob.ambiente.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.model.UserInfoManglarModel;
import ec.gob.ambiente.enlisy.model.Contact;
import ec.gob.ambiente.enlisy.model.People;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.ContactFacade;
import ec.gob.ambiente.enlisy.services.ContactsFormFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.model.AllowedUser;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.AllowedUserFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;
import ec.gob.ambiente.suia.utils.JsfUtil;
import ec.gob.ambiente.util.Email;
import ec.gob.registrocivil.consultacedula.Cedula;

public class RegisterController {
	
	@Getter
	@Setter
	private String msg;
	
	@Getter
	@Setter
	private User user;
	
	@Getter
	@Setter
	private boolean isSuperAdmin;
	
	private UserFacade userFacade;
	private ContactFacade contactFacade;
	private AllowedUserFacade allowedUserFacade;
	private ContactsFormFacade contactsFormFacade;
	private OrganizationsUserFacade organizationsUserFacade;
	private OrganizationManglarFacade organizationManglarFacade;

	public RegisterController(UserFacade userFacade,
			ContactFacade contactFacade, AllowedUserFacade allowedUserFacade,
			ContactsFormFacade contactsFormFacade,
			OrganizationsUserFacade organizationsUserFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		this.userFacade = userFacade;
		this.contactFacade = contactFacade;
		this.allowedUserFacade = allowedUserFacade;
		this.contactsFormFacade = contactsFormFacade;
		this.organizationsUserFacade = organizationsUserFacade;
		this.organizationManglarFacade = organizationManglarFacade;
	}

	public boolean save(String name, String pin, String gender,
		String civilStatus, String treatmentId,
		String nationalityId, String phone, String mobile,
		String email, String address, String parroquiaId,
		Integer organizationManglarId){
		
		AllowedUser allowedUser = allowedUserFacade.findByUserPin(pin);
		if (allowedUser == null){
			msg = "El usuario no es socio de organización";
			return false;
		}
		if ( !validarRegistro(pin, email, mobile, phone) ){
			return false;
		}
		String roleName = allowedUser.getRole().getRoleName();
		String peopTitle = roleName; // TODO Ask for correct value
		
		UserInfoManglarController socio = new UserInfoManglarController();
		socio.inicio();
		socio.setUserfacade(userFacade);
		socio.setContactFacade(contactFacade);
		socio.setContactsFormFacade(contactsFormFacade);
		
		Cedula cedula = new Cedula();
		cedula.setError("NO ERROR");
		cedula.setNombre(name);
		cedula.setCedula(pin);
		cedula.setGenero(gender);
		cedula.setEstadoCivil(civilStatus);
		socio.cargarDatosWsCedula(cedula);
		
		UserInfoManglarModel socioBean = socio.getSocioManglarBean();
		user = socioBean.getUsuario();
		People persona = socioBean.getPersona();
		
		// Update user values
		user.setUserDocuId("Cédula");
		user.setUserPin(pin);
		
		// Update person values
		persona.setPeopName(name);
		persona.setPeopTitle(peopTitle);
		
		// Update bean values
		socioBean.setIdTipoTrato(treatmentId);
		socioBean.setIdNacionalidad(nationalityId);
		socioBean.setAceptaTerminos(true);
		socioBean.setTelefono(phone);
		socioBean.setCelular(mobile);
		socioBean.setEmail(email);
		socioBean.setDireccion(address);
		socioBean.setIdParroquia(parroquiaId);
		
		socio.addContacts();
		
		boolean success = socio.guardar(roleName);
		
		if (!success) {
			msg = "Registro fallido";
			return false;
		}
		
		OrganizationManglar organizationManglar = organizationManglarFacade
		.findById(organizationManglarId);
		if (organizationManglar == null) {
			msg = "Registro fallido, no existe la organización";
			return false;
		}
		organizationsUserFacade.save(user, organizationManglar);
		return true;
	}
	
	/**
	* Recuperar Contraseña
	*/
	public boolean recuperarClave(String userPin)
	{
		try {			
			user = userFacade.findByUserName(userPin);		
			if (user.getUserId() == null) {
				msg = "Usuario no encontrado: " + userPin;
				return false;
			}
			
			String userEmail = user.getPeople().getEmail();
			if (userEmail == null) {
				msg = "Usuario no tiene configurado email de recuperación";
				return false;
			}
			
			String nombreUsuario=user.getUserName();	
			
			//cambiar la clave por una temporal generada automaticamente
			String claveTemporal=JsfUtil.generatePassword();
			user.setUserPassword(JsfUtil.claveEncriptadaSHA1(claveTemporal));
			user.setUserTempPassword(claveTemporal);
			userFacade.updateUser(user);
			
			//Diseniar y enviar el correo de notificacion					
			String mensajeCorreo="Confirmamos que su solicitud de recuperar contrase&ntilde;a  en el sistema SUIA fue aprobada con los siguientes datos: </p>"
			+ "<ul><li>Nombre de usuario:<b>"+nombreUsuario+"</b></li>" 
			+ "<li>Clave:<b>"+claveTemporal+"</b></li></ul>"
			+ "Es necesario cambiar la contraseña al momento de ingresar en el sistema.<br/>";				
			
			if(!Email.sendEmail(userEmail,"Solicitud de cambio de Clave", mensajeCorreo)){
				msg = "No se pudo enviar correo, intente mas tarde nuevamente";
				return false;
			}
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean changePassword(String userName, String userPwd, String userNewPwd, String userNewPwdConfirm){
		
		User user = userFacade.findByUserName(userName);		
		
		if (user.getUserId() == null) {
			msg = "Usuario No encontrado "+userName;
			return false;
		}
		
		if (user.getUserPassword().equals(JsfUtil.claveEncriptadaSHA1(userPwd))||(user.getUserPassword().equals(userPwd) && userPwd.length()==40)) {
			if(userNewPwd==null||userNewPwd.equals("")||userNewPwd==null||userNewPwd.equals("")){
				msg = "Debe ingresar los campos Nueva contraseña y Confirmación de nueva contraseña";
				return false;
			}
			
			if(!userNewPwd.equals(userNewPwdConfirm)){
				msg = "Nueva contraseña y Confirmación de nueva contraseña son diferentes";
				return false;
			}
			
			if(userNewPwd.equals(userPwd)){
				msg = "La nueva contraseña debe ser diferente a la anterior";
				return false;
			}
			
			if(userNewPwd.length()<8){
				msg = "La nueva contraseña debe tener al menos 8 caracteres";
				return false;
			}
			
			try {								
				
				user.setUserPassword(JsfUtil.claveEncriptadaSHA1(userNewPwd));
				userFacade.edit(user);
				enviarEmailConfirmacion(user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}else{
			msg = "Contraseña actual incorrecta";
		}
		return false;
	}
	
	private void enviarEmailConfirmacion(User user){
		try {
			
			Contact emailUsuario = null;
			
			List<Contact> contactosUsuario = contactFacade.findEmailByUser(user);
			if (contactosUsuario.size() > 0)
			emailUsuario = contactosUsuario.get(0);
			
			if(emailUsuario==null)
			contactosUsuario = contactFacade.findEmailByOrganization(user);
			if (contactosUsuario.size() > 0)
			emailUsuario = contactosUsuario.get(0);
			
			if (emailUsuario != null) {
				SimpleDateFormat formateador = new SimpleDateFormat(  "dd 'de' MMMM 'de' yyyy 'a las' HH:mm:ss", new Locale("es", "ES"));
				Date fechaDate = new Date();
				String fechaString = formateador.format(fechaDate);
				
				
				String mensajeCorreo = "Estimado Usuario/a,<br/> Se realizó el cambio de contrase&ntilde;a  en el sistema SUIA para el usuario <b>"	+ user.getUserName() + "</b>.<br/>"
				+ "El día <b>"+fechaString+"</b> </p>";
				Email.sendEmail(emailUsuario.getValue(),"Cambio de Clave", mensajeCorreo);				
			}else{
				System.out.println("No se encontro correo para confirmar");
			}
			
			
		} catch (Exception e) {
			msg = "No se pudo confirmar el cambio de contraseña al correo";
			e.printStackTrace();
		}
	}

	private boolean validarRegistro(String pin, String email, String mobile, String phone) {

/*		  if (!JsfUtil.validarCedulaORUC(pin)) {
		    msg = "El campo cédula no es válido.";
		    return false;
		  }*/

		  if (!JsfUtil.validarMail(email)) {
		    msg = "La dirección de correo ingresada es incorrecta, por favor verifique.";
		    return false;
		  }

		  if (mobile.length() != 10) {
		    msg = "El número celular ingresado no es valido. Ejemplo: 0999123456";
		    return false;
		  }

		  if (phone.length() < 9) {
		    msg = "El número convencional ingresado no es valido. Ejemplo: 072123456";
		    return false;
		  }

		  return true;
	}

}
