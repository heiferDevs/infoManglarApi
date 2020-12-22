package ec.gob.ambiente.util;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements Serializable {

	private static final long serialVersionUID = 1685152239605874337L;
	private static final String FROM = "Ministerio del Ambiente";
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	private static final String TRANSPORT ="smtp";
	private static final String SPLIT_SEPARATE =";";
			
	/**
	 * Enviar Correo a Varios Destinos en lista
	 * @param emailsDestino List<String>
	 * @param emailsDestinoCopia List<String>
	 * @param asunto String
	 * @param mensaje String
	 * @return boolean
	 */
	public static boolean sendEmail(List<String> emailsDestino,List<String> emailsDestinoCopia, String asunto, String mensaje) {
		try {
			Properties props = new Properties();
			String prop_fileUrl=ViewResources.getProperty("email.properties.file",PropertyType.PROPERTY);
			String prop_userKey=ViewResources.getProperty("email.key.user",PropertyType.PROPERTY);
			String prop_passwordKey=ViewResources.getProperty("email.key.password",PropertyType.PROPERTY);			
			
			props.load(new FileInputStream(prop_fileUrl));
			String usuario=props.getProperty(prop_userKey);
			String password=props.getProperty(prop_passwordKey);		
			
			Session session = Session.getInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			for (String email : emailsDestino) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			}
			if(emailsDestinoCopia!=null)
			{
				for (String email : emailsDestinoCopia) {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
				}
			}
			
			message.setSubject(asunto);
			message.setSentDate(new Date());
			message.setText(asunto);
						
			InternetAddress maeAddress=new InternetAddress(usuario);
			maeAddress.setPersonal(FROM);			
			message.setFrom(maeAddress);
			
			message.setContent(mensaje,CONTENT_TYPE);
			Transport tr = session.getTransport(TRANSPORT);
			tr.connect(usuario, password);
			message.saveChanges();
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Enviar Correo a Varios Destinos Separados por caracter
	 * @param emailsDestino String
	 * @param emailsDestinoCopia String
	 * @param asunto String
	 * @param mensaje String
	 * @return boolean
	 */
	public static boolean sendEmail(String emailsDestino,String emailsDestinoCopia, String asunto, String mensaje) {		
		List<String> emailsDestinoList=Arrays.asList (emailsDestino.split(SPLIT_SEPARATE));
		List<String> emailsDestinoCopiaList=null;
		if(emailsDestinoCopia!=null&&emailsDestinoCopia.length()>0)
			emailsDestinoCopiaList=Arrays.asList (emailsDestinoCopia.split(SPLIT_SEPARATE));
		return sendEmail(emailsDestinoList, emailsDestinoCopiaList, asunto, mensaje);
	}
	
	/**
	 * Enviar Correo a Un solo destino
	 * @param emailDestino String
	 * @param asunto String
	 * @param mensaje String
	 * @return boolean
	 */
	public static boolean sendEmail(String emailDestino, String asunto, String mensaje) {			
		return sendEmail(emailDestino, null, asunto, mensaje);
	}
	
}