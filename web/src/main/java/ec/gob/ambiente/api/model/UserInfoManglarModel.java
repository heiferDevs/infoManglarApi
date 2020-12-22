package ec.gob.ambiente.api.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import org.primefaces.model.TreeNode;

import ec.gob.ambiente.enlisy.model.Contact;
import ec.gob.ambiente.enlisy.model.Organization;
import ec.gob.ambiente.enlisy.model.People;
import ec.gob.ambiente.enlisy.model.User;

@Data
public class UserInfoManglarModel implements Serializable {

	private static final long serialVersionUID = 7687114018058910027L;

	private User usuario;

	private People persona;

	private Organization organizacion;

	private String tipoPersona;

	private String idNacionalidad;

	private String idTipoOrganizacion;

	private String idFormaContacto;

	private String idTipoTrato;

	private Contact contacto;

	private List<Contact> listaContacto;

	private List<Contact> listaContactoObligatorios;

	private List<Contact> listaContactoOpcionales;

	private List<Contact> listaContactoRemover;

	private String idParroquia;

	private boolean aceptaTerminos;

	private List<String> causas;

	private boolean deshabilitarRegistro;

	private TreeNode root;

	private TreeNode selectedNode;

	private String passwordAnterior;

	private String password;

	private String scriptNumeros;

	private String scriptTamanio;

	private String scriptNumerosDocumento;

	private String scriptTamanioDocumento;

	private boolean wsEncontrado;

	private String telefono;

	private String celular;

	private String email;

	private String direccion;	
	
}
