package ec.gob.ambiente.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Encapsula comportamiento para acceder a las propiedades en los ficheros resources.
 * @author miguelbaldeon
 *
 */
public class ViewResources implements Serializable {

	private static final long serialVersionUID = 4601039155551543349L;

	/**
	 * Acceso a las properties de la aplicación.
	 */
	static Properties properties = new Properties();	

	static final String messagesFile = "messages.properties";
	static final String propertiesFile = "mae.properties";
	
	/**
	 * Obtiene el valor de la propiedad pasada por parámetro, si la propiedad no existe, <code>null</code> es devuelto.
	 * @param name El nombre de la propiedad a consultar.
	 * @param type El tipo de propiedad a consultar según los enumerados en <code>HydrocarbonsPropertyType</code> 
	 * @return El valor de la propiedad, si la propiedad no existe, <code>null</code> es devuelto.
	 * @throws Throwable
	 */
	@Deprecated
	public static String getProperty(String name, PropertyType type) {
		StringBuilder resp = new StringBuilder();
	 
		InputStream inputStream = null;
				
		try {
			StringBuilder tmpFile = new StringBuilder();
			
			
			if (type == PropertyType.MESSAGE)
				tmpFile.append(messagesFile);
			else if (type == PropertyType.PROPERTY)
				tmpFile.append(propertiesFile);

			inputStream = 
					  ViewResources.class.getClassLoader().getResourceAsStream(tmpFile.toString());
				

		  if (inputStream == null){
			  throw new  MaeException("Error al traer properties del fichero, ¿no se lo encontró?:. " + messagesFile);			  
		  }		  
		  
		  properties.load(inputStream);
		  
		  resp.append(properties.getProperty(name));
	 
		} catch (RuntimeException | IOException e) {
			//TODO ingreso a log de errores.
			throw new MaeException("Error al traer properties del fichero:. " + messagesFile, e);
		}finally{
			if (inputStream != null){
				try{
					inputStream.close();
				}catch(IOException e){
					throw new MaeException("Error al traer properties, IoException! " + e);
				}
			}
		}
	 
		return resp.toString();
	}
		
	public static void main(String[] args) throws Throwable {
		System.out.println(ViewResources.getProperty("phase_id_hydrocarbons", PropertyType.PROPERTY));
	}
	
}
