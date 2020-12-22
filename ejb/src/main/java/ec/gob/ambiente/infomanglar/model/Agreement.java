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

import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="agreements", schema="info_manglar")
@NamedQuery(name="Agreement.findAll", query="SELECT o FROM Agreement o")
public class Agreement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AGREEMENT_GENERATOR", initialValue = 1, sequenceName = "seq_agreements", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGREEMENT_GENERATOR")
	@Getter
	@Setter
	@Column(name="agreement_id")
	private Integer id;

	@Setter
	@ManyToOne
	@JoinColumn(name="official_docs_form_id")
	private OfficialDocsForm officialDocsForm;

	@Getter
	@Setter
	@Column(name = "agreement_type")
	private String agreementType;

	@Getter
	@Setter
	@Column(name = "start_agreement_date")
	private String startAgreementDate;

	@Getter
	@Setter
	@Column(name = "end_agreement_date")
	private String endAgreementDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "agreement", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

}
