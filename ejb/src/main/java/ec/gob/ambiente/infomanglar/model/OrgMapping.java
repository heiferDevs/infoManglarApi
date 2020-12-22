package ec.gob.ambiente.infomanglar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.LimitsForm;
import ec.gob.ambiente.infomanglar.forms.model.MappingForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orgs_mapping", schema="info_manglar")
@NamedQuery(name="OrgMapping.findAll", query="SELECT o FROM OrgMapping o")
public class OrgMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ORG_MAPPING_GENERATOR", initialValue = 1, sequenceName = "seq_orgs_mapping", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_MAPPING_GENERATOR")
	@Getter
	@Setter
	@Column(name="org_mapping_id")
	private Integer id;

	@Getter
	@Setter
	@Column(name = "organization_id")
	private Integer organizationId;

	@Getter
	@Setter
	@Column(name = "organization_name")
	private String organizationName;

	@Getter
	@Setter
	@Column(name = "mapping_date")
	private String mappingDate;

	@Setter
	@ManyToOne
	@JoinColumn(name="mapping_form_id")
	private MappingForm mappingForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="limits_form_id")
	private LimitsForm limitsForm;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orgMapping", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

}
