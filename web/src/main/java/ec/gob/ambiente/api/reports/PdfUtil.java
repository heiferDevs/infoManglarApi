package ec.gob.ambiente.api.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
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

public class PdfUtil {

	public File generarFichero(String cadenaHtml, final String nombreReporte, boolean landscape) {

		Document document = null;
		PdfWriter writer = null;
		OutputStream fileOutputStream = null;
		File file = null;
		try {
			String buf = cadenaHtml;
			file = File.createTempFile(nombreReporte, ".pdf");
			Rectangle pageRectangle = landscape ? PageSize.A4.rotate() : PageSize.A4;
			document = new Document(pageRectangle, 36, 36, 50, 70);

			fileOutputStream = new FileOutputStream(file);
			writer = PdfWriter.getInstance(document, fileOutputStream);
			writer.createXmpMetadata();

			document.open();			
			
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
			CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
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

}