package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the organizations_types database table.
 * 
 */
@Entity
@Table(name="organizations_types", schema="public")
@NamedQuery(name="OrganizationsType.findAll", query="SELECT o FROM OrganizationsType o")
public class OrganizationsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="orty_id")
	private Integer ortyId;

	@Column(name="orty_description")
	private String ortyDescription;

	@Column(name="orty_name")
	private String ortyName;

	@Column(name="orty_status")
	private Boolean ortyStatus;

	//bi-directional many-to-one association to Organization
	@OneToMany(mappedBy="organizationsType")
	private List<Organization> organizations;

	//bi-directional many-to-one association to OrganizationsType
	@ManyToOne
	@JoinColumn(name="orty_parent_id")
	private OrganizationsType organizationsType;

	//bi-directional many-to-one association to OrganizationsType
	@OneToMany(mappedBy="organizationsType")
	private List<OrganizationsType> organizationsTypes;

	public OrganizationsType() {
	}
	
	public OrganizationsType(Integer ortyId) {
		this.ortyId = ortyId;
	}

	public Integer getOrtyId() {
		return this.ortyId;
	}

	public void setOrtyId(Integer ortyId) {
		this.ortyId = ortyId;
	}

	public String getOrtyDescription() {
		return this.ortyDescription;
	}

	public void setOrtyDescription(String ortyDescription) {
		this.ortyDescription = ortyDescription;
	}

	public String getOrtyName() {
		return this.ortyName;
	}

	public void setOrtyName(String ortyName) {
		this.ortyName = ortyName;
	}

	public Boolean getOrtyStatus() {
		return this.ortyStatus;
	}

	public void setOrtyStatus(Boolean ortyStatus) {
		this.ortyStatus = ortyStatus;
	}

	public List<Organization> getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public Organization addOrganization(Organization organization) {
		getOrganizations().add(organization);
		organization.setOrganizationsType(this);

		return organization;
	}

	public Organization removeOrganization(Organization organization) {
		getOrganizations().remove(organization);
		organization.setOrganizationsType(null);

		return organization;
	}

	public OrganizationsType getOrganizationsType() {
		return this.organizationsType;
	}

	public void setOrganizationsType(OrganizationsType organizationsType) {
		this.organizationsType = organizationsType;
	}

	public List<OrganizationsType> getOrganizationsTypes() {
		return this.organizationsTypes;
	}

	public void setOrganizationsTypes(List<OrganizationsType> organizationsTypes) {
		this.organizationsTypes = organizationsTypes;
	}

	public OrganizationsType addOrganizationsType(OrganizationsType organizationsType) {
		getOrganizationsTypes().add(organizationsType);
		organizationsType.setOrganizationsType(this);

		return organizationsType;
	}

	public OrganizationsType removeOrganizationsType(OrganizationsType organizationsType) {
		getOrganizationsTypes().remove(organizationsType);
		organizationsType.setOrganizationsType(null);

		return organizationsType;
	}

}