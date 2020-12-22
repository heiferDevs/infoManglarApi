package ec.gob.ambiente.infomanglar.forms.model;

import java.io.Serializable;
import java.util.ArrayList;
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

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.ambiente.infomanglar.model.Agreement;

@Entity
@Table(name = "officials_docs_forms", schema = "info_manglar")
@NamedQuery(name = "OfficialDocsForm.findAll", query = "SELECT o FROM OfficialDocsForm o")
public class OfficialDocsForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "OFFICIAL_DOCS_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_official_docs", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFFICIAL_DOCS_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "official_docs_form_id")
	private Integer officialDocsFormId;

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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "officialDocsForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

	@Getter
	@Setter
	@Column(name = "organization_name")
	private String organizationName;

	@Getter
	@Setter
	@Column(name = "summary")
	private String summary;

	@Getter
	@Setter
	@Column(name = "organization_type")
	private String organizationType;

	@Getter
	@Setter
	@Column(name = "organization_location")
	private String organizationLocation;

	@Getter
	@Setter
	@Column(name = "custody_area")
	private Double custodyArea;

	@Getter
	@Setter
	@Column(name = "year_creation")
	private Integer yearCreation;

	@Getter
	@Setter
	@Column(name = "seps_register")
	private String sepsRegister;

	@Getter
	@Setter
	@Column(name = "president_name")
	private String presidentName;

	@Getter
	@Setter
	@Column(name = "pin")
	private String pin;

	@Getter
	@Setter
	@Column(name = "phone")
	private String phone;

	@Getter
	@Setter
	@Column(name = "email")
	private String email;

	@Getter
	@Setter
	@Column(name = "partners_list_date")
	private String partnersListDate;
	
	@Getter
	@Setter
	@Column(name = "internal_regulation_date")
	private String internalRegulationDate;

	@Getter
	@Setter
	@Column(name = "directors_register_date")
	private String directorsRegisterDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "officialDocsForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<Agreement> agreements;

}
