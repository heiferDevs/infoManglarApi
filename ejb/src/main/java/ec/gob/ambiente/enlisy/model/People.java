package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;





/**
 * The persistent class for the people database table.
 * 
 */
@Entity
@Table(name="people", schema="public")
@NamedQuery(name="People.findAll", query="SELECT p FROM People p")
public class People implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PEOPLE_GENERATOR", initialValue = 1, sequenceName = "seq_peop_id", schema = "public", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEOPLE_GENERATOR")
	@Column(name="peop_id")
	private Integer peopId;

	@Column(name="peop_genre")
	private String peopGenre;

	@Column(name="peop_id_document")
	private String peopIdDocument;

	@Column(name="peop_name")
	private String peopName;

	@Column(name="peop_pin")
	private String peopPin;

	@Column(name="peop_position")
	private String peopPosition;

	@Column(name="peop_status")
	private Boolean peopStatus;

	@Column(name="peop_title")
	private String peopTitle;

	//bi-directional many-to-one association to Organization
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "people")
	private List<Organization> organizations;

	//bi-directional many-to-one association to GeographicalLocation
	@ManyToOne
	@JoinColumn(name="gelo_id")
	private GeographicalLocation geographicalLocation;

	//bi-directional many-to-one association to Nationality
	@ManyToOne
	@JoinColumn(name="nati_id")
	private Nationality nationality;

	//bi-directional many-to-one association to TreatmentsType
	@ManyToOne
	@JoinColumn(name="trty_id")
	private TreatmentsType treatmentsType;

	//bi-directional many-to-one association to User
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="people")
	private List<User> users;

	//bi-directional many-to-one association to Contact
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "people")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Contact> contacts;

	//bi-directional many-to-one association to UserType
	/*@OneToMany(mappedBy = "people")	
	private List<ResearchApplication> applications;*/

	public People() {
	}

	public Integer getPeopId() {
		return this.peopId;
	}

	public void setPeopId(Integer peopId) {
		this.peopId = peopId;
	}

	public String getPeopGenre() {
		return this.peopGenre;
	}

	public void setPeopGenre(String peopGenre) {
		this.peopGenre = peopGenre;
	}

	public String getPeopIdDocument() {
		return this.peopIdDocument;
	}

	public void setPeopIdDocument(String peopIdDocument) {
		this.peopIdDocument = peopIdDocument;
	}

	public String getPeopName() {
		return this.peopName;
	}

	public void setPeopName(String peopName) {
		this.peopName = peopName;
	}

	public String getPeopPin() {
		return this.peopPin;
	}

	public void setPeopPin(String peopPin) {
		this.peopPin = peopPin;
	}

	public String getPeopPosition() {
		return this.peopPosition;
	}

	public void setPeopPosition(String peopPosition) {
		this.peopPosition = peopPosition;
	}

	public Boolean getPeopStatus() {
		return this.peopStatus;
	}

	public void setPeopStatus(Boolean peopStatus) {
		this.peopStatus = peopStatus;
	}

	public String getPeopTitle() {
		return this.peopTitle;
	}

	public void setPeopTitle(String peopTitle) {
		this.peopTitle = peopTitle;
	}

	public List<Organization> getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public Organization addOrganization(Organization organization) {
		getOrganizations().add(organization);
		organization.setPeople(this);

		return organization;
	}

	public Organization removeOrganization(Organization organization) {
		getOrganizations().remove(organization);
		organization.setPeople(null);

		return organization;
	}

	public GeographicalLocation getGeographicalLocation() {
		return this.geographicalLocation;
	}

	public void setGeographicalLocation(GeographicalLocation geographicalLocation) {
		this.geographicalLocation = geographicalLocation;
	}

	public Nationality getNationality() {
		return this.nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public TreatmentsType getTreatmentsType() {
		return this.treatmentsType;
	}

	public void setTreatmentsType(TreatmentsType treatmentsType) {
		this.treatmentsType = treatmentsType;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setPeople(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setPeople(null);

		return user;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setPeople(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setPeople(null);

		return contact;
	}
	
	public String getEmail()
	{	
		try {
			for (Contact contact : getContacts()) {
				if(contact.getContactsForm().getCofoId()==ContactsForm.EMAIL && !contact.getValue().isEmpty())					
					return contact.getValue();
			}
		
		} catch (Exception e) {
			System.out.println("No se encontro email de "+peopName);
		}
		return null;
	}

	public String getPhone()
	{	
		try {
			for (Contact contact : getContacts()) {
				if(contact.getContactsForm().getCofoId()==ContactsForm.CELULAR && !contact.getValue().isEmpty())					
					return contact.getValue();
			}
		
		} catch (Exception e) {
			System.out.println("No se encontro email de "+peopName);
		}
		return null;
	}

	/*public List<ResearchApplication> getApplications() {
		return applications;
	}

	public void setApplications(List<ResearchApplication> applications) {
		this.applications = applications;
	}
	
	public ResearchApplication addApplication(ResearchApplication application) {
		getApplications().add(application);
		application.setPeopleTutor(this);

		return application;
	}

	public ResearchApplication removeApplication(ResearchApplication application) {
		getApplications().remove(application);
		application.setPeopleTutor(null);

		return application;
	}*/
}