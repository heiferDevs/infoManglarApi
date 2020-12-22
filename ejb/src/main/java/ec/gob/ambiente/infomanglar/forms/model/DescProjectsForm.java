package ec.gob.ambiente.infomanglar.forms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.ambiente.infomanglar.model.DocumentProject;

@Entity
@Table(name = "desc_projects_forms", schema = "info_manglar")
@NamedQuery(name = "DescProjectsForm.findAll", query = "SELECT o FROM DescProjectsForm o")
public class DescProjectsForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DESC_PROJECTS_FORM_GENERATOR", initialValue = 1, sequenceName = "seq_desc_projects_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESC_PROJECTS_FORM_GENERATOR")
	@Getter
	@Setter
	@Column(name = "desc_projects_form_id")
	private Integer descProjectsFormId;

	@Getter
	@Setter
	@Column(name = "user_id")
	private Integer userId;

	@Getter
	@Setter
	@Column(name = "organization_manglar_id")
	private Integer organizationManglarId;

	@Getter
	@Setter
	@Column(name = "form_type")
	private String formType;

	@Getter
	@Setter
	@Column(name = "form_status")
	private Boolean formStatus;

	@Getter
	@Setter
	@Column(name = "created_at")
	private Date createdAt;

	// FORMS VALUES
	@Getter
	@Setter
	@Column(name = "project_type")
	private String projectType;

	@Getter
	@Setter
	@Column(name = "project_name")
	private String projectName;

	@Getter
	@Setter
	@Column(name = "project_objective")
	private String projectObjective;

	@Getter
	@Setter
	@Column(name = "institution_type")
	private String institutionType;

	@Getter
	@Setter
	@Column(name = "institutions_name")
	private String institutionsName;

	@Getter
	@Setter
	@Column(name = "budget")
	private Double budget;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "descProjectsForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<DocumentProject> documentProjects;

}
