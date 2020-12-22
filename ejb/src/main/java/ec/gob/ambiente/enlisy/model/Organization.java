package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the organizations database table.
 * 
 */
@Entity
@Table(name="organizations", schema="public")
@NamedQuery(name="Organization.findAll", query="SELECT o FROM Organization o")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ORGANIZATIONS_GENERATOR", initialValue = 1, sequenceName = "seq_orga_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATIONS_GENERATOR")
	@Column(name="orga_id")
	private Integer orgaId;

	@Column(name="gelo_id")
	private Integer geloId;

	@Column(name="orga_charge_legal_representative")
	private String orgaChargeLegalRepresentative;

	@Column(name="orga_name_organization")
	private String orgaNameOrganization;

	@Column(name="orga_ruc")
	private String orgaRuc;

	@Column(name="orga_state_participation")
	private String orgaStateParticipation;

	@Column(name="orga_status")
	private Boolean orgaStatus;

	//bi-directional many-to-one association to People
	@ManyToOne
	@JoinColumn(name="peop_id")
	private People people;

	//bi-directional many-to-one association to Contact	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "organization")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Contact> contacts;
	
	//bi-directional many-to-one association to OrganizationsType
	@ManyToOne
	@JoinColumn(name="tyor_id")
	private OrganizationsType organizationsType;
	
	@Getter
	@Setter
	@Column(name="orga_activity")
	private String orgaActivity;

	public Organization() {
	}

	public Integer getOrgaId() {
		return this.orgaId;
	}

	public void setOrgaId(Integer orgaId) {
		this.orgaId = orgaId;
	}

	public Integer getGeloId() {
		return this.geloId;
	}

	public void setGeloId(Integer geloId) {
		this.geloId = geloId;
	}

	public String getOrgaChargeLegalRepresentative() {
		return this.orgaChargeLegalRepresentative;
	}

	public void setOrgaChargeLegalRepresentative(String orgaChargeLegalRepresentative) {
		this.orgaChargeLegalRepresentative = orgaChargeLegalRepresentative;
	}

	public String getOrgaNameOrganization() {
		return this.orgaNameOrganization;
	}

	public void setOrgaNameOrganization(String orgaNameOrganization) {
		this.orgaNameOrganization = orgaNameOrganization;
	}

	public String getOrgaRuc() {
		return this.orgaRuc;
	}

	public void setOrgaRuc(String orgaRuc) {
		this.orgaRuc = orgaRuc;
	}

	public String getOrgaStateParticipation() {
		return this.orgaStateParticipation;
	}

	public void setOrgaStateParticipation(String orgaStateParticipation) {
		this.orgaStateParticipation = orgaStateParticipation;
	}

	public Boolean getOrgaStatus() {
		return this.orgaStatus;
	}

	public void setOrgaStatus(Boolean orgaStatus) {
		this.orgaStatus = orgaStatus;
	}

	public People getPeople() {
		return this.people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setOrganization(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setOrganization(null);

		return contact;
	}

	public OrganizationsType getOrganizationsType() {
		return organizationsType;
	}

	public void setOrganizationsType(OrganizationsType organizationsType) {
		this.organizationsType = organizationsType;
	}
	
	
}