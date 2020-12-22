package ec.gob.ambiente.suia.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements Serializable {
	private static final long serialVersionUID = 2042615127078957486L;
	
//	private static final String USERMAIL = "notificacionesmae.suia@ambiente.gob.ec";
//	private static final String PASSWORD = "5u!AD!5e2014";
//	private static final String SMTPSERVER = "172.16.0.92";
//	private static final String PORTSERVER = "25";
	private String userMail;
	private String userPassword;
	private String smtpServer;
	private String smtpPort;
	
	
	public Email() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().evaluateExpressionGet(context, "#{survey}", ResourceBundle.class);
		userMail = bundle.getString("smtp.user");
		userPassword = bundle.getString("smtp.userpassword");
		smtpServer = bundle.getString("smtp.server");
		smtpPort = bundle.getString("smtp.port");
	}
	

	public boolean sendEmail(String nombreDestino, String emailDestino,
			String asunto, String mensaje) {

		Properties props = new Properties();

		try {
			props.setProperty("mail.smtp.host", smtpServer);
			props.setProperty("mail.smtp.port", smtpPort);
			props.setProperty("mail.smtp.user", userMail);
			props.setProperty("mail.smtp.auth", userPassword);
			props.setProperty("mail.smtp.connectiontimeout", "5000");
			props.setProperty("mail.smtp.timeout", "5000");

			Session session = Session.getInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestino));
			message.setSubject(asunto);
			message.setSentDate(new Date());
			message.setText(asunto);

			// message.setFrom(new InternetAddress(usuario));
			InternetAddress maeAddress = new InternetAddress(userMail);
			maeAddress.setPersonal("Ministerio del Ambiente");
			message.setFrom(maeAddress);

			message.setContent(disenioEmail(nombreDestino, mensaje),
					"text/html; charset=utf-8");
			Transport tr = session.getTransport("smtp");
			tr.connect(userMail, userPassword);
			message.saveChanges();
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	private String disenioEmail(String nameEstimado, String detalleMensaje) {
		String disenio = " <p style=\"font-size: 14px;\">"
				+ "Estimado/a: <b>"
				+ nameEstimado
				+ "</b>, </p>"
				+ " <p style=\"font-size: 14px;\">"
				+ detalleMensaje
				+ " </p>"
				+ "<b>Sitio web</b>: www.ambiente.gob.ec<br/>"
				+ "<b>Facebook</b>: Ministerio Ambiente Ecuador<br/>"
				+ "<b>Twitter</b>: @Ambiente_Ec<br/><br/>"
				+ " <p style=\"font-size: 14px;\">"
				+ " "
				+ " Envíenos sugerencias o dudas a maetransparente@ambiente.gob.ec"
				+ " </p>" + " <div style=\"text-align: left;\">" + " <img"
				+ " src=\"http://web.ambiente.gob.ec/Logoambiente.PNG\""
				+ " width=\"600\" />" + " </div>"
				+ " <div style=\"font-size: 9px; text-align: left;\">SUIA-DCP"
				+ " Ministerio del Ambiente - SUIA - 2016</div>";

		return disenio;
	}

	/* Enviar Correo Fin */

	// método para envío de correos
	public void sendEmailCuentaCoordenadas(String destino, String asunto,
			String mensaje, String convocatoria, String ubicacion,
			String fecha, String horario, String proponente) {

//		String servidorSMTP = "172.16.0.92";
//		String puerto = "25";
//		String usuario = "notificacionesmae.suia@ambiente.gob.ec";
//		String password = "5u!AD!5e2014";
		Properties props = new Properties();
		// props.setProperty("mail.smtp.host", "172.16.0.92");
		props.setProperty("mail.smtp.host", smtpServer);
		// props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.user", userMail);
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.connectiontimeout", "5000");
		props.setProperty("mail.smtp.timeout", "5000");
		Session session = Session.getDefaultInstance(props);
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					destino));
			message.setSubject(asunto);
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(userMail));
			message.setText(mensaje);
			message.setContent(
					enviarMailRegistroConvocatoria(convocatoria, ubicacion,
							fecha, horario, proponente),
					"text/html; charset=utf-8");
			Transport tr = session.getTransport("smtp");
			tr.connect(userMail, userPassword);
			message.saveChanges();
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String enviarMailRegistroConvocatoria(final String convocatoriaM,
			final String ubicacionM, final String fechaM,
			final String horarioM, final String proponenteM) {

		SimpleDateFormat fecha = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",
				new Locale("es"));

		String disenio = "<div class=\"centro\"	>	"
				+ "<img src=\"http://mesadeayuda.ambiente.gob.ec/logomaesuia.png\"/> "
				+ "<center>"
				+ "<span style=\"font-weight: bold; font-size: 16px; text-decoration; underline; \">NOTIFICACI&Oacute;N DE EVALUACIÓN DE CONSULTORES AMBIENTALES </span><br></br>"
				+ ""
				+ "</center>"
				+ "<br /><br />"
				+ "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
				+ "<tbody>"
				+ "<tr>"
				+ "<td> Estimado Consultor Ambiental:"
				+ "<br/>"
				+ "<br/>"
				+ "<td/>"
				+ "<tr/>"
				+ "<tr>"
				+ "<td class=\"der\"> A continuación se detalla el registro para la Evaluación del Consultor "
				+ "<br/>" + "<br/>" + "<tr/>" + "<tr>" + "<td>Convocatoria: "
				+ convocatoriaM
				+ "<td/>"
				+ "<tr/>"
				+ "<tr>"
				+ "<td>Ubicación: "
				+ ubicacionM
				+ "<td/>"
				+ "<tr/>"
				+ "<tr>"
				+ "<td>Fecha: "
				+ fechaM
				+ "<td/>"
				+ "<tr/>"
				+ "<tr>"
				+ "<td>Horario: "
				+ horarioM
				+ "<td/>"
				+ "<tr/>"
				+ "<tr>"
				+ "<td>Postulante: "
				+ proponenteM
				+ "<td/>"
				+ "<tr/>"
				+ "<tr>"
				+ "<td colspan=\"3\">"
				+ "<br/>"
				+ "<br/>"
				+ "<div style=\"padding: 5px; margin: 0 auto; font-size: 10px; align=\"left\">"
				+ "Saludos Cordiales"
				+ "<br/>"
				+ "MINISTERIO DEL AMBIENTE"
				+ "<br/>"
				+ "Teléfono (593 2) 3987600 ext 3002"
				+ "<br/>"
				+ "www.ambiente.gob.ec "
				+ "<br/>"
				+ "Quito - Ecuador"
				+ "<br/>"
				+ "SUIA - @2016 "
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "</tbody>" + "</table>" + "</p>" + "<br />" + "</div>";
		return disenio;
	}

}
