package ec.gob.ambiente.suia.utils;

/**
 * Diferentes enumerados para representar estados de la aplicación, en este caso, 
 * si el fichero de resources es un resources normal o un tipo de dato.
 *
 */
public enum PropertyType {
	/**
	 * Indica que la búsqueda se hará en un fichero que guarda recursos "tipo de dato".
	 */
	DATATYPE,	
	/**
	 * Indica que la búsqueda se hará en un fichero que guardar recursos "normales".
	 */
	RESOURCE  
}