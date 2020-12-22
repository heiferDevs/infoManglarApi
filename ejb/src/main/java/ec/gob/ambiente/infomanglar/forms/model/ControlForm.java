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
@Table(name = "control_forms", schema = "info_manglar")
@NamedQuery(name = "ControlForm.findAll", query = "SELECT o FROM ControlForm o")
public class ControlForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTROL_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_control_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTROL_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "control_form_id")
	private Integer controlFormId;

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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "controlForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

	@Getter
	@Setter
	@Column(name = "responsible_patrullaje")
	private String responsiblePatrullaje;

	@Getter
	@Setter
	@Column(name = "sector")
	private String sector;

	@Getter
	@Setter
	@Column(name = "start_site")
	private String startSite;

	@Getter
	@Setter
	@Column(name = "end_site")
	private String endSite;

	@Getter
	@Setter
	@Column(name = "register_details")
	private String registerDetails;

	@Getter
	@Setter
	@Column(name = "type")
	private String type;

	@Getter
	@Setter
	@Column(name = "verification")
	private String verification;

	@Getter
	@Setter
	@Column(name = "event_exists")
	private Boolean eventExists;

	@Getter
	@Setter
	@Column(name = "route_duration")
	private Integer routeDuration;

	@Getter
	@Setter
	@Column(name = "route_date")
	private String routeDate;

	@Getter
	@Setter
	@Column(name = "route_localities")
	private String routeLocalities;

	@Getter
	@Setter
	@Column(name = "internal_auditor")
	private String internalAuditor;

}
