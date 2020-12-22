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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "deforestation_forms", schema = "info_manglar")
@NamedQuery(name = "DeforestationForm.findAll", query = "SELECT o FROM DeforestationForm o")
public class DeforestationForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DEFORESTATION_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_deforestation_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEFORESTATION_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "deforestation_form_id")
	private Integer deforestationFormId;

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
	@Column( name = "probable_cause")
	private String probableCause;

	@Getter
	@Setter
	@Column( name = "deforested_area")
	private String deforestedArea;

	@Getter
	@Setter
	@Column( name = "custody_area")
	private String custodyArea;

	@Getter
	@Setter
	@Column( name = "protected_area")
	private String protectedArea;

	@Getter
	@Setter
	@Column( name = "location")
	private String location;

	@Getter
	@Setter
	@Column( name = "latlong")
	private String latlong;

	@Getter
	@Setter
	@Column( name = "address")
	private String address;

	@Getter
	@Setter
	@Column( name = "estuary")
	private String estuary;

	@Getter
	@Setter
	@Column( name = "community")
	private String community;

}
