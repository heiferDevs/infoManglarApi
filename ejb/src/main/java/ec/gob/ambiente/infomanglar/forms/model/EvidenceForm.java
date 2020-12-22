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
import javax.persistence.JoinColumn;
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

import ec.gob.ambiente.infomanglar.model.EvidenceActivity;
import ec.gob.ambiente.infomanglar.model.PlannedActivity;

@Entity
@Table(name = "evidence_forms", schema = "info_manglar")
@NamedQuery(name = "EvidenceForm.findAll", query = "SELECT o FROM EvidenceForm o")
public class EvidenceForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "EVIDENCE_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_evidence_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVIDENCE_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "evidence_form_id")
	private Integer evidenceFormId;

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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "evidenceForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<EvidenceActivity> evidenceActivities;

	  
	  @Getter
	  @Setter
	  @Column(name="activity")
	  private String activity;
	  
	  @Getter
	  @Setter
	  @Column(name="activity_date")
	  private String activityDate;
	  
	  @Getter
	  @Setter
	  @Column(name="activity_desc")
	  private String activityDesc;
	  
	  @Getter
	  @Setter
	  @Column(name="activity_results")
	  private String activityResults;

}
