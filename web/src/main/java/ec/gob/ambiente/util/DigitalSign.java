package ec.gob.ambiente.util;

public class DigitalSign {

	public static String sign(String urlAlfresco, String cedula) {
		String urlGlobal = null;
		try {
			String tiketStr = "alf_ticket=";
			if (!urlAlfresco.endsWith(tiketStr)) {
				int pos = urlAlfresco.lastIndexOf(tiketStr) + tiketStr.length();
				urlAlfresco = urlAlfresco.substring(0, pos);
			}
			urlGlobal = Constant.getFirmaDigital()
					+ "index.jsf?cedulaUser="
					+ cedula
					+ "&urlWsdl="
					+ Constant.getFirmaDigital()
					+ "digitalSign/DigitalSignServices&system=SUIA&urlAlfrescoFile="
					+ urlAlfresco;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return urlGlobal;

	}
	
}
