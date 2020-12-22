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

@Entity
@Table(name = "semi_annual_reports_forms", schema = "info_manglar")
@NamedQuery(name = "SemiAnnualReportForm.findAll", query = "SELECT o FROM SemiAnnualReportForm o")
public class SemiAnnualReportForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEMI_ANNUAL_REPORTS_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_semi_annual_reports_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEMI_ANNUAL_REPORTS_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "semi_annual_report_form_id")
	private Integer semiAnnualReportFormId;

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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "semiAnnualReportForm", orphanRemoval=true)
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
	@Column(name = "semi_Annual")
	private String semiAnnual;

	@Getter
	@Setter
	@Column(name = "year_report")
	private Integer yearReport;

}
