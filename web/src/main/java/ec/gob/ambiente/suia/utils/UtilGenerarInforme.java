package ec.gob.ambiente.suia.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
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


public class UtilGenerarInforme {

	/*
	 * private static final Logger LOG = Logger
	 * .getLogger(UtilGenerarInforme.class);
	 */
	/**
	 * Nombre:SUIA Descripción: Para obterner el áres responsable para el logo
	 * del documentos. ParametrosIngreso: PArametrosSalida: Fecha:16/08/2015
	 */

	static String areaRes = null;

	/**
	 * FIN Para obterner el áres responsable para el logo del documentos.
	 */

	private static String generarHtml(final String cadenaHtml,
			final Object entityInforme, String textoNull) {
		try {
			String buf = cadenaHtml;
			List<String> listaTags = new ArrayList<>();
			Pattern pa = Pattern.compile("\\$F[{]\\w+[}$]");
			Matcher mat = pa.matcher(buf);
			while (mat.find()) {
				listaTags.add(mat.group());
			}

			Map<String, Object> mapa = new HashMap<>();
			Class<?> clase = entityInforme.getClass();
			Method[] campos = clase.getMethods();
			for (Method f : campos) {
				if (f.getName().startsWith("get")) {
					String metodo = "$F{" + f.getName().replace("get", "")
							+ "}";
					for (String s : listaTags) {
						if (s.equalsIgnoreCase(metodo)) {
							mapa.put(s, f.invoke(entityInforme, null));
							break;
						}
					}
				}
			}

			for (Map.Entry<String, Object> m : mapa.entrySet()) {
				buf = buf
						.replace(
								m.getKey(),
								m.getValue() == null ? "<span style='color:red'>INGRESAR</span>"
										: m.getValue().toString());
				// .getValue().toString().replace("\n", "<br />"));
			}

			return buf;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}

	}

	/**/
	public static File generarFichero(String cadenaHtml,
			final String nombreReporte, final Boolean mostrarNumeracionPagina,
			final Object entityInforme, String textoNull) {
		/**
		 * Nombre:SUIA Descripción: Pasa las entidades para obtener las áreas
		 * responsables ParametrosIngreso: PArametrosSalida: Fecha:15/08/2015
		 */

		/**
		 * FIN Pasa las entidades para obtener las áreas responsables
		 */
		Document document = null;
		PdfWriter writer = null;
		OutputStream fileOutputStream = null;
		File file = null;
		try {
			cadenaHtml = generarHtml(cadenaHtml, entityInforme, textoNull);
			String buf = cadenaHtml;// .replace("logoMae",JsfUtil.getRequest().getServerName()+":"+JsfUtil.getRequest().getServerPort()+
									// JsfUtil.devolverContexto("/resources/images/logotipo_ministerio_ambiente_ecuador.png"));
			file = File.createTempFile(nombreReporte, ".pdf");
			List<String> listaTags = new ArrayList<String>();
			Pattern pa = Pattern.compile("\\$F[{]\\w+[}$]");
			Matcher mat = pa.matcher(buf);
			while (mat.find()) {
				listaTags.add(mat.group());
			}

			document = new Document(PageSize.A4, 36, 36, 50, 70);

			fileOutputStream = new FileOutputStream(file);
			writer = PdfWriter.getInstance(document, fileOutputStream);
			writer.createXmpMetadata();

			writer.setPageEvent(new HeaderFichaAmbientalMineria(null,mostrarNumeracionPagina));
			document.open();			
			
			//Image imagen = Image.getInstance(getRecursoImage("logo-mae.png"));	
			Image imagen = Image.getInstance("http://registroconsultores.ambiente.gob.ec:8099/encuestas-consultores-web/resources/images/logo-mae.png");

			imagen.setAbsolutePosition(95f, 200f);
			imagen.scalePercent(45);
			document.add(imagen);			
			
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

		
	// *
	public static File generarFichero(String cadenaHtml,
			final String nombreReporte, final Boolean mostrarNumeracionPagina,
			final Object entityInforme) {
		return generarFichero(cadenaHtml, nombreReporte,
				mostrarNumeracionPagina, entityInforme,
				"<span style='color:red'>INGRESAR</span>");
	}

	public static File generarFicheroOficio(String cadenaHtml,
			final String nombreReporte, final Boolean mostrarNumeracionPagina,
			final Object entityInforme, String textoNull) {
		/**
		 * Nombre:SUIA Descripción: Pasa las entidades para obtener las áreas
		 * responsables ParametrosIngreso: PArametrosSalida: Fecha:15/08/2015
		 */

		/**
		 * FIN Pasa las entidades para obtener las áreas responsables
		 */
		Document document = null;
		PdfWriter writer = null;
		OutputStream fileOutputStream = null;
		File file = null;
		try {
			cadenaHtml = generarHtml(cadenaHtml, entityInforme, textoNull);
			String buf = cadenaHtml;// .replace("logoMae",JsfUtil.getRequest().getServerName()+":"+JsfUtil.getRequest().getServerPort()+
									// JsfUtil.devolverContexto("/resources/images/logotipo_ministerio_ambiente_ecuador.png"));
			file = File.createTempFile(nombreReporte, ".pdf");
			List<String> listaTags = new ArrayList<String>();
			Pattern pa = Pattern.compile("\\$F[{]\\w+[}$]");
			Matcher mat = pa.matcher(buf);
			while (mat.find()) {
				listaTags.add(mat.group());
			}

			document = new Document(PageSize.A4, 36, 36, 50, 70);

			fileOutputStream = new FileOutputStream(file);
			writer = PdfWriter.getInstance(document, fileOutputStream);
			writer.createXmpMetadata();

			
			document.open();
			
			//writer.setPageEvent(new HeaderFichaAmbientalMineria(null,mostrarNumeracionPagina));
				
			
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
	
	public static File generarFicheroOficio(String cadenaHtml,
			final String nombreReporte, final Boolean mostrarNumeracionPagina,
			final Object entityInforme) {
		return generarFichero(cadenaHtml, nombreReporte,
				mostrarNumeracionPagina, entityInforme,
				"<span style='color:red'>INGRESAR</span>");
	}
	

	public static String generarFicheroHtml(String cadenaHtml,
			final String nombreReporte, final Boolean mostrarNumeracionPagina,
			final Object entityInforme) {
		Document document = null;
		PdfWriter writer = null;
		OutputStream fileOutputStream = null;
		File file = null;
		try {
			cadenaHtml = generarHtml(cadenaHtml, entityInforme,
					"<span style='color:red'>INGRESAR</span>");
			// System.out.println("html: "+cadenaHtml);

			return cadenaHtml;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cadenaHtml;
	}

	private static URL getRecursoImage(String nombreImagen) {
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		try {
			return servletContext.getResource("/resources/images/"
					+ nombreImagen);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}


	static class HeaderFichaAmbientalMineria extends PdfPageEventHelper {

		String[] params;
		Boolean mostrarNumeracionPagina;
		/**
		 * The template with the total number of pages.
		 */
		PdfTemplate total;

		public HeaderFichaAmbientalMineria() {
		}

		public HeaderFichaAmbientalMineria(String[] params,
				Boolean mostrarNumeracionPagina) {
			this.params = params;
			this.mostrarNumeracionPagina = mostrarNumeracionPagina;
		}

		/**
		 * Creates the PdfTemplate that will hold the total number of pages.
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(com.itextpdf.text.pdf.PdfWriter,
		 *      com.itextpdf.text.Document)
		 */
		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(30, 16);
		}

		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(
					String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
		}

		public void onEndPage(PdfWriter writer, Document document) {
			Image imghead = null;
			try {

				/**
				 * Nombre:SUIA Descripción: Permite obtener logos deacuerdo al
				 * area ParametrosIngreso: PArametrosSalida: Fecha:15/08/2015
				 */
	
				String nombre_logo = null;
			
			//	imghead = Image.getInstance(getRecursoImage("180px-Coat_of_arms_of_Ecuador.svg.png"));
				imghead = Image.getInstance("http://registroconsultores.ambiente.gob.ec:8099/encuestas-consultores-web/resources/images/180px-Coat_of_arms_of_Ecuador.svg.png");

				/**
				 * FIN Permite obtener logos deacuerdo al area
				 */

				imghead.setAbsolutePosition(0,0);
				imghead.setAlignment(Image.ALIGN_CENTER);
				imghead.scalePercent(30f);
				PdfPTable tableHeader = new PdfPTable(4);
				tableHeader.setWidths(new int[] { 14, 10, 10, 14 });
				tableHeader.setTotalWidth(527);
				tableHeader.setLockedWidth(true);
				tableHeader.getDefaultCell().setFixedHeight(20);
				tableHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				tableHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
				PdfPCell cellImagen = new PdfPCell();
				cellImagen.addElement(imghead);
				cellImagen.setBorder(Rectangle.NO_BORDER);
				tableHeader.addCell(cellImagen);
				tableHeader.getDefaultCell().setHorizontalAlignment(
						Element.ALIGN_RIGHT);
				PdfPCell cellVacia = new PdfPCell();
				cellVacia.setBorder(Rectangle.NO_BORDER);
				tableHeader.addCell(cellVacia);
				tableHeader.addCell(cellVacia);
				tableHeader.addCell(cellVacia);
				tableHeader.writeSelectedRows(0, -1, 5, 790,
						writer.getDirectContent());
				if (params != null) {
					int top = 790;
					Font font = new Font(Font.FontFamily.HELVETICA, 8);
					font.setStyle(Font.BOLD);
					for (String s : params) {
						PdfPTable tableHeader1 = new PdfPTable(4);
						Phrase letra = new Phrase(s, font);
						tableHeader1.setWidths(new int[] { 14, 10, 10, 14 });
						tableHeader1.setTotalWidth(527);
						tableHeader1.setLockedWidth(true);
						tableHeader1.getDefaultCell().setFixedHeight(20);
						tableHeader1.getDefaultCell().setBorder(
								Rectangle.NO_BORDER);
						tableHeader1.getDefaultCell().setHorizontalAlignment(
								Element.ALIGN_RIGHT);

						tableHeader1.addCell(cellVacia);
						tableHeader1.addCell(cellVacia);
						tableHeader1.addCell(cellVacia);
						tableHeader1.addCell(letra);
						tableHeader1.writeSelectedRows(0, -1, 36, top,
								writer.getDirectContent());
						top -= 10;
					}
				}

				//PdfPTable tableFooter = crearFoot(writer.getPageNumber(),Image.getInstance(total), this.mostrarNumeracionPagina);
//				tableFooter.setTotalWidth(527);
//				tableFooter.writeSelectedRows(0, -1, 36, 64,
//						writer.getDirectContent());
//				tableFooter.writeSelectedRows(0, -1, 36, 64,
//						writer.getDirectContent());

			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			} catch (MalformedURLException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static PdfPTable crearFoot(int numPaginaActual, Image total,
			Boolean mostrarNumeracionPagina) throws DocumentException,
			MalformedURLException, IOException {
		Image imgfoot = null;
		/**
		 * Nombre:SUIA Descripción: Permite obtener logos deacuerdo al area
		 * ParametrosIngreso: PArametrosSalida: Fecha:15/08/2015
		 */

		//imgfoot = Image.getInstance(getRecursoImage("pie_ci.png"));
		imgfoot = Image.getInstance("http://registroconsultores.ambiente.gob.ec:8099/encuestas-consultores-web/resources/images/pie_ci.png");

		/**
		 * FIN Permite obtener logos deacuerdo al area
		 */
		imgfoot.setAbsolutePosition(0, 0);
		imgfoot.setAlignment(Image.ALIGN_CENTER);
		imgfoot.scalePercent(60f);
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(288 / 5.23f);
		table.setWidths(new int[] { 16, 1, 1 });
		PdfPCell cell;
		cell = new PdfPCell(imgfoot);
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(""));
		cell.setRowspan(2);
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		if (mostrarNumeracionPagina != null && mostrarNumeracionPagina) {
			PdfPCell celdaPageActual = new PdfPCell(new Phrase(
					String.valueOf(numPaginaActual) + " /"));
			celdaPageActual.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celdaPageActual.setBorder(Rectangle.NO_BORDER);
			table.addCell(celdaPageActual);
			PdfPCell cellTotal = new PdfPCell(total);
			cellTotal.setBorder(Rectangle.NO_BORDER);
			cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellTotal);
		}
		return table;
	}

}