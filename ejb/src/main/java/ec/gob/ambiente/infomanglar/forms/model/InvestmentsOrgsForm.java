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
@Table(name = "investments_orgs_forms", schema = "info_manglar")
@NamedQuery(name = "InvestmentsOrgsForm.findAll", query = "SELECT o FROM InvestmentsOrgsForm o")
public class InvestmentsOrgsForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "INVESTMENTS_ORGS_FORM_GENERATOR", initialValue = 1, sequenceName = "seq_investments_orgs_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVESTMENTS_ORGS_FORM_GENERATOR")
	@Getter
	@Setter
	@Column(name = "investments_orgs_form_id")
	private Integer investmentsOrgsFormId;

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
	@Column(name = "surveillance_control")
	private Double surveillanceControl;

	@Getter
	@Setter
	@Column(name = "sustainable_use")
	private Double sustainableUse;

	@Getter
	@Setter
	@Column(name = "administratives_expenses")
	private Double administrativesExpenses;

	@Getter
	@Setter
	@Column(name = "social_activities")
	private Double socialActivities;

	@Getter
	@Setter
	@Column(name = "monitoring")
	private Double monitoring;

	@Getter
	@Setter
	@Column(name = "capacitation")
	private Double capacitation;

	@Getter
	@Setter
	@Column(name = "reforestation")
	private Double reforestation;

	@Getter
	@Setter
	@Column(name = "infrastructure")
	private Double infrastructure;

}
