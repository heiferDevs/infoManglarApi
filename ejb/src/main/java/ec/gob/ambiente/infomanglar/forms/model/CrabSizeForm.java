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

@Entity
@Table(name = "crab_size_forms", schema = "info_manglar")
@NamedQuery(name = "CrabSizeForm.findAll", query = "SELECT o FROM CrabSizeForm o")
public class CrabSizeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CRAB_SIZE_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_crab_size_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRAB_SIZE_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "crab_size_form_id")
	private Integer crabSizeFormId;

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

	@Getter
	@Setter
	@Column(name="sector_name")
	private String sectorName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "crabSizeForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<SizeForm> sizeForms = new ArrayList<>();

	@Getter
	@Setter
	@Column(name = "cangrejero_name")
	private String cangrejeroName;

	@Getter
	@Setter
	@Column(name = "sampling_date")
	private String samplingDate;

	@Getter
	@Setter
	@Column(name = "crab_count")
	private Integer crabCount;


}
