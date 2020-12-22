package ec.gob.ambiente.infomanglar.forms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ec.gob.ambiente.infomanglar.model.Agreement;
import ec.gob.ambiente.infomanglar.model.DocumentProject;
import ec.gob.ambiente.infomanglar.model.EvidenceActivity;
import ec.gob.ambiente.infomanglar.model.OrgMapping;
import ec.gob.ambiente.infomanglar.model.PlannedActivity;
import ec.gob.ambiente.infomanglar.model.PriceDaily;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="files_forms", schema="info_manglar")
@NamedQuery(name="FileForm.findAll", query="SELECT o FROM FileForm o")
public class FileForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FILE_FORM_GENERATOR", initialValue = 1, sequenceName = "seq_file_form", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_FORM_GENERATOR")
	@Getter
	@Setter
	@Column(name="file_form_id")
	private Integer id;

	@Getter
	@Setter
	@Column(name="file_form_status")
	private Boolean status;

	@Setter
	@ManyToOne
	@JoinColumn(name="official_docs_form_id")
	private OfficialDocsForm officialDocsForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="semi_annual_report_form_id")
	private SemiAnnualReportForm semiAnnualReportForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="technical_report_form_id")
	private TechnicalReportForm technicalReportForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="control_form_id")
	private ControlForm controlForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="organization_info_form_id")
	private OrganizationInfoForm organizationInfoForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="price_daily_id")
	private PriceDaily priceDaily;

	@Setter
	@ManyToOne
	@JoinColumn(name="org_mapping_id")
	private OrgMapping orgMapping;

	@Setter
	@ManyToOne
	@JoinColumn(name="document_project_id")
	private DocumentProject documentProject;

	@Setter
	@ManyToOne
	@JoinColumn(name="agreement_id")
	private Agreement agreement;

	@Setter
	@ManyToOne
	@JoinColumn(name="pdf_report_form_id")
	private PdfReportForm pdfReportForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="evidence_activity_id")
	private EvidenceActivity evidenceActivity;

	@Getter
	@Setter
	@Column(name="file_form_id_option")
	private String idOption;

	@Getter
	@Setter
	@Column(name="file_form_name_file")
	private String name;

	@Getter
	@Setter
	@Column(name="file_form_type")
	private String type;

	@Getter
	@Setter
	@Column(name="file_form_url")
	private String url;

	@Getter
	@Setter
	@Column(name="alfreco_code")
	private String alfrescoCode;

	@Getter
	@JsonIgnore
	@Transient
	private String pathOrigin;

}
