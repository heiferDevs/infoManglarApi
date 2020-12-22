package ec.gob.ambiente.infomanglar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="organizations_manglar", schema="info_manglar")
@NamedQuery(name="OrganizationManglar.findAll", query="SELECT o FROM OrganizationManglar o")
public class OrganizationManglar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ORGANIZATION_MANGLAR_GENERATOR", initialValue = 1, sequenceName = "seq_organization_manglar_id", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_MANGLAR_GENERATOR")
	@Getter
	@Setter
	@Column(name="organization_manglar_id")
	private Integer organizationManglarId;

	@Getter
	@Setter
	@Column(name="organization_manglar_status")
	private Boolean organizationManglarStatus;

	@Getter
	@Setter
	@Column(name="organization_manglar_name")
	private String organizationManglarName;

	@Getter
	@Setter
	@Column(name="organization_manglar_complete_name")
	private String organizationManglarCompleteName;

	@Getter
	@Setter
	@Column(name="organization_manglar_type")
	private String organizationManglarType;

}
