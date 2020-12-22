package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the contacts_forms database table.
 * 
 */
@Entity
@Table(name="contacts_forms", schema="public")
@NamedQuery(name="ContactsForm.findAll", query="SELECT c FROM ContactsForm c")
public class ContactsForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int EMAIL = 5;	
	public static final int FAX = 1;
    public static final int DIRECCION = 2;
    public static final int POBOX = 3;
    public static final int CELULAR = 4;    
    public static final int TELEFONO = 6;
    public static final int URL = 7;
    public static final int POSTFIX_ZIP = 8;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="cofo_id")
	private Integer cofoId;

	@Column(name="cofo_name")
	private String cofoName;

	@Column(name="cofo_order")
	private Integer cofoOrder;

	@Column(name="cofo_status")
	private Boolean cofoStatus;

	//bi-directional many-to-one association to Contact
	@OneToMany(mappedBy="contactsForm")
	private List<Contact> contacts;

	public ContactsForm(Integer cofoId) {
        this.cofoId = cofoId;
    }
	
	public ContactsForm() {
	}

	public Integer getCofoId() {
		return this.cofoId;
	}

	public void setCofoId(Integer cofoId) {
		this.cofoId = cofoId;
	}

	public String getCofoName() {
		return this.cofoName;
	}

	public void setCofoName(String cofoName) {
		this.cofoName = cofoName;
	}

	public Integer getCofoOrder() {
		return this.cofoOrder;
	}

	public void setCofoOrder(Integer cofoOrder) {
		this.cofoOrder = cofoOrder;
	}

	public Boolean getCofoStatus() {
		return this.cofoStatus;
	}

	public void setCofoStatus(Boolean cofoStatus) {
		this.cofoStatus = cofoStatus;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setContactsForm(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setContactsForm(null);

		return contact;
	}

}