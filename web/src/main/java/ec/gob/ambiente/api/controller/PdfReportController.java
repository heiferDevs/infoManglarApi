package ec.gob.ambiente.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.services.DescProjectsFormFacade;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;


public class PdfReportController {

	private UserFacade userFacade;
	private DescProjectsFormFacade anomalyFormFacade;
	private OrganizationsUserFacade organizationsUserFacade;
/*
	public PdfReportController(DescProjectsFormFacade anomalyFormFacade, UserFacade userFacade, OrganizationsUserFacade organizationsUserFacade) {
		super();
		this.anomalyFormFacade = anomalyFormFacade;
		this.userFacade = userFacade;
		this.organizationsUserFacade = organizationsUserFacade;
	}

	public File getFormFile(Integer id){
		DescProjectsForm anomalyForm = (DescProjectsForm) anomalyFormFacade.find(id);		
		String cadenaHtml = getHtml(anomalyForm); 
		String nombreReporte = "form_" + id;
		File file = generarFichero(cadenaHtml,nombreReporte);
		return file;
	}

	private String getHtml(DescProjectsForm anomalyForm){
		User user = (User) userFacade.find(anomalyForm.getUserId());
		String userName = user == null ? "NO_USER_NAME" : user.getPeople().getPeopName();
		String userPin = user == null ? "NO_USER_PIN" : user.getUserName();
		String userEmail = user == null ? "NO_USER_EMAIL" : user.getPeople().getEmail();
		String userPhone = user == null ? "NO_USER_Phone" : user.getPeople().getPhone();
		List<OrganizationManglar> organizationsManglar = organizationsUserFacade.findByUserId(user.getUserId()); 
		String organization = "NO_USER_ORGANIZATION";
		if (organizationsManglar.size() > 0) {
			organization = organizationsManglar.get(0).getOrganizationManglarName();
		}
		String created = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(anomalyForm.getCreatedAt());

		StringBuilder mensaje = new StringBuilder();

		mensaje.append("<p style='text-align:center'><b>Tipo de alerta: </b>").append(OfficialDocsFormController.getTypeName(anomalyForm.getAnomalyFormType())).append("</p>");
		mensaje.append("<p style='text-align:center'><b>Id: </b>").append(anomalyForm.getAnomalyFormId()).append("</p>");
		mensaje.append("<p style='text-align:center'><b>Estado: </b><span style='color:#008a8a'>").append(anomalyForm.getAnomalyFormState()).append("</span></p>");
		mensaje.append("<br/>");
		
		mensaje.append("<p style='color:#008a8a'>Información Socio</p>");
		mensaje.append("<p><b>Nombre: </b>").append(userName).append("</p>");
		mensaje.append("<p><b>Cédula: </b>").append(userPin).append("</p>");
		mensaje.append("<p><b>Organización: </b>").append(organization).append("</p>");
		mensaje.append("<p><b>Correo: </b>").append(userEmail).append("</p>");
		mensaje.append("<p><b>Teléfono: </b>").append(userPhone).append("</p>");
		mensaje.append("<br/>");

		mensaje.append("<p style='color:#008a8a'>Información Reporte</p>");
		mensaje.append("<p><b>Fecha: </b>").append(created).append("</p>");
		mensaje.append("<p><b>Descripción: </b>").append(anomalyForm.getDescription()).append("</p>");
		mensaje.append("<p><b>Área de custodia: </b>").append(anomalyForm.getCustody()).append("</p>");
		mensaje.append("<p><b>Área protegida: </b>").append(anomalyForm.getProtectedArea()).append("</p>");
		mensaje.append("<p><b>Ocultar identidad: </b>").append(anomalyForm.getHideIdentity() ? "SI" : "NO").append("</p>");
		mensaje.append("<p><b>Sientes peligro: </b>").append(anomalyForm.getFeelDanger() ? "SI" : "NO").append("</p>");
		if (anomalyForm.getType() != null) mensaje.append("<p><b>Causa tipo: </b>").append(anomalyForm.getType()).append("</p>");
		if (anomalyForm.getArea() != null) mensaje.append("<p><b>Superficie: </b>").append(anomalyForm.getArea()).append("</p>");
		if (anomalyForm.getAnomalyFormSubtype() != null) mensaje.append("<p><b>Sub-tipo: </b>").append(anomalyForm.getAnomalyFormSubtype()).append("</p>");
		if (anomalyForm.getAddress() != null) mensaje.append("<p><b>Dirección: </b>").append(anomalyForm.getAddress()).append("</p>");
		if (anomalyForm.getAnimalsStrandedCount() != null) mensaje.append("<p><b>Animales atrapados: </b>").append(anomalyForm.getAnimalsStrandedCount()).append("</p>");
		if (anomalyForm.getAnimalsStrandedSize() != null) mensaje.append("<p><b>Tamaño animales atrapados: </b>").append(anomalyForm.getAnimalsStrandedSize()).append("</p>");
		if (anomalyForm.getArtesanalType() != null) mensaje.append("<p><b>Tipo artesania: </b>").append(anomalyForm.getArtesanalType()).append("</p>");
		if (anomalyForm.getBeachingReason() != null) mensaje.append("<p><b>Razón varamiento: </b>").append(anomalyForm.getBeachingReason()).append("</p>");
		if (anomalyForm.getFishingType() != null) mensaje.append("<p><b>Tipo pesca: </b>").append(anomalyForm.getFishingType()).append("</p>");
		if (anomalyForm.getFishingTypeOthers() != null) mensaje.append("<p><b>Otro tipo de pesca: </b>").append(anomalyForm.getFishingTypeOthers()).append("</p>");
		if (anomalyForm.getIndividuals() != null) mensaje.append("<p><b>Individuos: </b>").append(anomalyForm.getIndividuals()).append("</p>");
		if (anomalyForm.getIndividualsRequisitioned() != null) mensaje.append("<p><b>Individuos requisados: </b>").append(anomalyForm.getIndividualsRequisitioned()).append("</p>");
		if (anomalyForm.getIndividualsRequisitionedInfo() != null) mensaje.append("<p><b>Individuos requisados info: </b>").append(anomalyForm.getIndividualsRequisitionedInfo()).append("</p>");
		if (anomalyForm.getIndustrialType() != null) mensaje.append("<p><b>Tipo industrial: </b>").append(anomalyForm.getIndustrialType()).append("</p>");
		if (anomalyForm.getSeaConditions() != null) mensaje.append("<p><b>Condiciones del mar: </b>").append(anomalyForm.getSeaConditions()).append("</p>");
		mensaje.append("<br/>");

		mensaje.append("<p style='color:#008a8a'>Ubicación</p>");
		mensaje.append("<p><b>Provincia: </b>").append(anomalyForm.getProvince()).append("</p>");
		mensaje.append("<p><b>Cantón: </b>").append(anomalyForm.getCanton()).append("</p>");
		if (anomalyForm.getLatlong() != null) mensaje.append("<p><b>Latlong: </b>").append(anomalyForm.getLatlong()).append("</p>");
		if (anomalyForm.getEstuary() != null) mensaje.append("<p><b>Estuario: </b>").append(anomalyForm.getEstuary()).append("</p>");
		if (anomalyForm.getCommunity() != null) mensaje.append("<p><b>Comunidad: </b>").append(anomalyForm.getCommunity()).append("</p>");
		mensaje.append("<br/>");

		mensaje.append("<p style='color:#008a8a; text-align:center'>Presuntos infractores</p>");
		if ( anomalyForm.getAnomaliesOffenders().size() == 0 ) {
			mensaje.append("<p style='text-align:center'>Lista de infractores vacia</p>");
		} else {
			for (AnomalyOffender anomalyOffender : anomalyForm.getAnomaliesOffenders()){
				if (anomalyOffender.getAnomalyOffenderName() != null) mensaje.append("<p style='text-align:center'><b>Nombre: </b>").append(anomalyOffender.getAnomalyOffenderName()).append("</p>");
				if (anomalyOffender.getAnomalyOffenderPin() != null) mensaje.append("<p style='text-align:center'><b>Cédula: </b>").append(anomalyOffender.getAnomalyOffenderPin()).append("</p>");
				if (anomalyOffender.getAnomalyOffenderPhone() != null) mensaje.append("<p style='text-align:center'><b>Teléfono: </b>").append(anomalyOffender.getAnomalyOffenderPhone()).append("</p>");
				if (anomalyOffender.getAnomalyOffenderAddress() != null) mensaje.append("<p style='text-align:center'><b>Dirección: </b>").append(anomalyOffender.getAnomalyOffenderAddress()).append("</p>");
				if (anomalyOffender.getAnomalyOffenderAdditionalInformation() != null) mensaje.append("<p style='text-align:center'><b>Info adicional: </b>").append(anomalyOffender.getAnomalyOffenderAdditionalInformation()).append("</p>");
				mensaje.append("<br/>");
			}
		}
		mensaje.append("<br/>");

		mensaje.append("<p style='color:#008a8a; text-align:center'>Presuntos testigos</p>");
		if ( anomalyForm.getAnomaliesWitnesses().size() == 0 ) {
			mensaje.append("<p style='text-align:center'>Lista de testigos vacia</p>");
		} else {
			for (AnomalyWitness anomalyWitness : anomalyForm.getAnomaliesWitnesses()){
				if (anomalyWitness.getAnomalyWitnessName() != null) mensaje.append("<p style='text-align:center'><b>Nombre: </b>").append(anomalyWitness.getAnomalyWitnessName()).append("</p>");
				if (anomalyWitness.getAnomalyWitnessPhone() != null) mensaje.append("<p style='text-align:center'><b>Teléfono: </b>").append(anomalyWitness.getAnomalyWitnessPhone()).append("</p>");
				mensaje.append("<br/>");
			}
		}
		mensaje.append("<br/>");

		mensaje.append("<p style='color:#008a8a; text-align:center'>Fotos/Videos</p>");
		if ( anomalyForm.getAnomaliesEvidences().size() == 0 ) {
			mensaje.append("<p style='text-align:center'>Lista de fotos/videos vacia</p>");
		} else {
			for (FileForm anomalyEvidence : anomalyForm.getAnomaliesEvidences()){
				mensaje.append("<br/>");
				if ("photo".equals(anomalyEvidence.getAnomalyEvidenceType()) )
				mensaje.append("<p style='text-align:center'><img style='height: 200px' src='" + anomalyEvidence.getAnomalyEvidenceUrl() + "'></img></p>");
				mensaje.append("<p style='text-align:center'><b>Nombre: </b>").append(anomalyEvidence.getAnomalyEvidenceNameFile()).append("</p>");
				mensaje.append("<p style='text-align:center'><b>Descripción: </b>").append(anomalyEvidence.getAnomalyEvidenceDescription()).append("</p>");
				mensaje.append("<p style='text-align:center'><b>Tipo: </b>").append(anomalyEvidence.getAnomalyEvidenceType()).append("</p>");
				mensaje.append("<p style='text-align:center'><b>Url: </b>").append(anomalyEvidence.getAnomalyEvidenceUrl()).append("</p>");
			}
		}
		mensaje.append("<br/>");

		return mensaje.toString();
	}

	private File generarFichero(String cadenaHtml, final String nombreReporte) {

		Document document = null;
		PdfWriter writer = null;
		OutputStream fileOutputStream = null;
		File file = null;
		try {
			String buf = cadenaHtml;
			file = File.createTempFile(nombreReporte, ".pdf");

			document = new Document(PageSize.A4, 36, 36, 50, 70);

			fileOutputStream = new FileOutputStream(file);
			writer = PdfWriter.getInstance(document, fileOutputStream);
			writer.createXmpMetadata();

			document.open();			
			
			//Image imagen = Image.getInstance("http://registroconsultores.ambiente.gob.ec:8099/encuestas-consultores-web/resources/images/logo-mae.png");

//			imagen.setAbsolutePosition(95f, 200f);
//			imagen.scalePercent(45);
//			document.add(imagen);			
			
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
			CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(
					cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser p = new XMLParser(worker);
			Reader reader = new StringReader(buf);
			p.parse(reader);

		} catch (Exception e) {
			System.out.println("Error al generarFichero:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (document != null && document.isOpen()) {
				document.close();
			}

			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				System.out.println("Dont create file IOException:"
						+ e.getMessage());
			}
			if (writer != null && !writer.isCloseStream()) {
				writer.close();
			}

		}
		return file;
	}
*/
}