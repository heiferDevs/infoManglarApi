package ec.gob.ambiente.infomanglar.forms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "reforestation_forms", schema = "info_manglar")
@NamedQuery(name = "ReforestationForm.findAll", query = "SELECT o FROM ReforestationForm o")
public class ReforestationForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "REFORESTATION_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_reforestation_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFORESTATION_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "reforestation_form_id")
	private Integer reforestationFormId;

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
	@Column(name = "reforested_area")
	private Double reforestedArea;

	@Getter
	@Setter
	@Column(name = "mangle_sembrado_count")
	private Integer mangleSembradoCount;

	@Getter
	@Setter
	@Column(name = "planting_date")
	private String plantingDate;

	@Getter
	@Setter
	@Column(name = "planting_state")
	private String plantingState;

	@Getter
	@Setter
	@Column(name = "average_state")
	private Double averageState;

	@Getter
	@Setter
	@ElementCollection
	@CollectionTable(name="info_manglar.reforestation_forms_entities", joinColumns=@JoinColumn(name="reforestation_form_id"))
	@JsonProperty
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> entities = new ArrayList<String>();

	@Getter
	@Setter
	@ElementCollection
	@CollectionTable(name="info_manglar.reforestation_forms_vertices", joinColumns=@JoinColumn(name="reforestation_form_id"))
	@JsonProperty
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> vertices = new ArrayList<String>();

}
