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
@Table(name = "pdf_report_forms", schema = "info_manglar")
@NamedQuery(name = "PdfReportForm.findAll", query = "SELECT o FROM PdfReportForm o")
public class PdfReportForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PDF_REPORT_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_pdf_report_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PDF_REPORT_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "pdf_report_form_id")
	private Integer pdfReportFormId;

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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pdfReportForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

	@Getter
	@Setter
	@Column(name = "start_date")
	private String startDate;

	@Getter
	@Setter
	@Column(name = "end_date")
	private String endDate;

	@Getter
	@Setter
	@Column(name = "published_date")
	private String publishedDate;

	@Getter
	@Setter
	@Column(name = "is_published")
	private Boolean isPublished;

	  
	@Getter
	@Setter
	@Column(name = "is_approved")
	private Boolean isApproved;

	@Getter
	@Setter
	@Column(name = "is_with_observations")
	private Boolean isWithObservations;

	@Getter
	@Setter
	@Column(name = "approved_date")
	private String approvedDate;

	@Getter
	@Setter
	@Column(name = "observation_date")
	private String observationDate;

	@Getter
	@Setter
	@Column(name = "approved_id")
	private Integer approvedId;

	@Getter
	@Setter
	@Column(name = "observation_id")
	private Integer observationId;

}
