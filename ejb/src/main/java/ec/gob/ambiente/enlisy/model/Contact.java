package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the contacts database table.
 * 
 */
@Entity
@Table(name="contacts", schema="public")
@NamedQuery(name="Contact.findAll", query="SELECT c FROM Contact c")
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTACTS_GENERATOR", initialValue = 1, sequenceName = "seq_cont_id", schema = "public", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACTS_GENERATOR")
	@Column(name="cont_id")
	private Long contId;

	@Column(name="cont_status")
	private Boolean status;

	@Column(name="cont_value")
	private String value;

	//bi-directional many-to-one association to ContactsForm
	@ManyToOne
	@JoinColumn(name="cofo_id")
	private ContactsForm contactsForm;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="orga_id")
	private Organization organization;

	//bi-directional many-to-one association to People	
	@ManyToOne
	@JoinColumn(name = "pers_id", referencedColumnName = "peop_id")
	private People people;

	public Contact() {		
	}
	
	public Contact(ContactsForm contactsForm) {
		setContactsForm(contactsForm);
		setStatus(true);
	}

	public Long getContId() {
		return this.contId;
	}

	public void setContId(Long contId) {
		this.contId = contId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ContactsForm getContactsForm() {
		return this.contactsForm;
	}

	public void setContactsForm(ContactsForm contactsForm) {
		this.contactsForm = contactsForm;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public People getPeople() {
		return this.people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

}