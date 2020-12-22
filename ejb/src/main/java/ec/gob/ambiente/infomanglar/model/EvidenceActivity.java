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

import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="evidence_activities", schema="info_manglar")
@NamedQuery(name="EvidenceActivity.findAll", query="SELECT o FROM EvidenceActivity o")
public class EvidenceActivity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "EVIDENCE_ACTIVITY_GENERATOR", initialValue = 1, sequenceName = "seq_evidence_activities", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVIDENCE_ACTIVITY_GENERATOR")
	@Getter
	@Setter
	@Column(name="evidence_activity_id")
	private Integer id;

	@Setter
	@ManyToOne
	@JoinColumn(name="evidence_form_id")
	private EvidenceForm evidenceForm;

	@Getter
	@Setter
	@Column(name = "evidence_type")
	private String evidenceType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "evidenceActivity", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

}
