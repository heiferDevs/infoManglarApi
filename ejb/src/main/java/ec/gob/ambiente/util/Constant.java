package ec.gob.ambiente.util;

import org.apache.log4j.Logger;

import ec.gob.ambiente.configuration.bean.ConfigurationBean;

public class Constant {
	
	// LOOKUP	
	public static final String ALFRESCO_SERVICE_BEAN = "alfresco-service";	
	public static final String JBPM_EJB_PROCESS_BEAN = "jbpm proceesss";
	public static final String JBPM_EJB_TASK_BEAN = "jbpm ejb";
	
	// NOMBRES PROCESOS
	public static final String NOMBRE_PROCESO_CERTIFICACION_CONSULTORES_AMBIENTALES = "Consultores.CertificacionConsultoresAmbientales";	
		
	// VARIABLES PROCESOS
	public static final String VARIABLE_TRAMITE_NO_PROYECTO = "No asociado";
	public static final String VARIABLE_PROCESO_TRAMITE_RESOLVER = "procedureResolverClass";
	public static final String VARIABLE_SESION_PROCESS_ID = "VARIABLE_SESION_PROCESS_ID";
	public static final String CODIGO_PROYECTO = "codigoProyecto";
	public static final String ID_PROYECTO = "idProyecto";
	public static final String USUARIO_VISTA_MIS_PROCESOS = "usuarioVistaMisProcesos";
	public static final String USUARIO_FUNCIONARIO_VISTA_MIS_PROCESOS = "usuarioFuncionarioVistaMisProcesos";
	public static final String VARIABLE_PROCESO_TRAMITE = "tramite";
	
	// UTILES
	public static final String ESTADO_PROCESO_NO_INICIADO = "No iniciado";
	public static final String ESTADO_PROCESO_ABORTADO = "Abortado";
	public static final String ESTADO_TAREA_INICIADA = "En curso";
	public static final String ESTADO_TAREA_COMPLETADA = "Completada";
	public static final String ESTADO_EVENTO_TAREA_COMPLETADA = "COMPLETED";	
	
	private static final Logger LOG = Logger.getLogger(Constant.class);			
	
	public static Integer getRemoteApiTimeout() {
		return getPropertyAsInteger("server.bpms.remote.api.timeout");		
	}
	
	public static int getPropertyAsInteger(String name) {
		return Integer.parseInt(getPropertyAsString(name));
	}
	
	public static String getPropertyAsString(String name) {
		return loadFromAppProperties(name);
	}
	
	private static String loadFromAppProperties(String key) {
		String result = null;
		if (result == null)
			result = BeanLocator.getInstance(ConfigurationBean.class).getConfigurationValue(key);
		return result;		
	}
	
	public static String getDeploymentId() {		
		return getPropertyAsString("server.bpms.deploymentId.consultores");
	}
	
	public static String getUrlBusinessCentral() {		
		return getPropertyAsString("server.bpms.aplicativos");
	}
	
	public static String getNotificationService() {
		return getPropertyAsString("app.service.notifications");
	}
	
	public static String getUrlWsRegistroCivilSri() {
		return getPropertyAsString("app.service.bus.snap");
	}
	
	public static Boolean getPermitirRUCPasivo() {
		return existsProperty("app.ruc.pasivo") ? getPropertyAsBoolean("app.ruc.pasivo") : false;
	}
	
	public static boolean existsProperty(String name) {
		return getPropertyAsString(name) != null;
	}
	
	public static boolean getPropertyAsBoolean(String name) {
		return Boolean.parseBoolean(getPropertyAsString(name));
	}
	
	public static String getHttpSuiaImagenFooterMail() {
		return getPropertyAsString("path.http.footermail");
	}

	public static String getHttpSuiaImagenInfoMail() {
		return getPropertyAsString("path.http.infomail");
	}
	
	public static String getLinkMaeTransparente() {
		return getPropertyAsString("link.maetransparente");
	}
	
	//Metodo utilizado en administrador de procesos
	//busca la url del servicio de 'Definiciones de Procesos del JBPM'
	public static String getUrlSuiaDefinicionesProcesos() {
		//return getPropertyAsString("app.service.listadefinicionesprocesos");
		return reemplazarServidorRestServices(getPropertyAsString("app.service.listadefinicionesprocesos"));
	}
	
	//Metodo utilizado en notificaciones
	public static String getUrlSuiaEventosTarea() {
		return reemplazarServidorRestServices(getPropertyAsString("app.service.listaeventostarea"));
	}
	
	//Metodo utilizado en notificaciones
	public static String getUrlSuiaResumenTarea() {
		//return getPropertyAsString("app.service.listaresumentarea");
		return reemplazarServidorRestServices(getPropertyAsString("app.service.listaresumentarea"));
	}
	
	//Metodo utilizado en notificaciones
	public static String getUrlJbpmRestService() {
		return getPropertyAsString("app.service.jbpmrest");
	}
	
	//Metodo utilizado en notificaciones
	public static String getUrlNodeInstanceLog() {
		return getUrlJbpmRestService() + "nodeinstancelog/";
	}
	
	public static String getFirmaDigital() {	
		return getPropertyAsString("app.digital.sign");
	}	

	public static String getRootId() {
		return getPropertyAsString("app.alfresco.root.id.consultores");
	}

	public static String getRootStaticDocumentsId() {
		return getPropertyAsString("app.alfresco.root.static.documents.id");
	}
	
	public static String getServicePayAddres() {
		return getPropertyAsString("app.service.suiaPay");		
	}
	
	public static String getBancoCuentaMae() {	
		return getPropertyAsString("cuenta.mae.oficial");		
	}
	
	private static String reemplazarServidorRestServices(String url)
	{		
		String urlBussiness=getUrlBusinessCentral();		
		int indexUrl=url.indexOf("jbpm-rest-services");		
		int indexServerBpm=urlBussiness.indexOf("business-central");
		
		//Reemplaza servidor por el que se utiliza en la bandeja de tareas "business-central"
		urlBussiness=urlBussiness.substring(0,indexServerBpm);		
		url=urlBussiness+url.substring(indexUrl,url.length());
		
		return url;
	}

}