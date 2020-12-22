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
@Table(name = "fishing_efforts_forms", schema = "info_manglar")
@NamedQuery(name = "FishingEffortForm.findAll", query = "SELECT o FROM FishingEffortForm o")
public class FishingEffortForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FISHING_EFFORTS_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_fishing_efforts_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FISHING_EFFORTS_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "fishing_effort_form_id")
	private Integer fishingEffortFormId;

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
	@Column(name = "fishing_effort_date")
	private String fishingEffortDate;

	@Getter
	@Setter
	@Column(name = "active_partners")
	private Integer activePartners;

}
