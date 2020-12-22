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

import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="plans_info", schema="info_manglar")
@NamedQuery(name="PlanInfo.findAll", query="SELECT o FROM PlanInfo o")
public class PlanInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PLAN_INFO_GENERATOR", initialValue = 1, sequenceName = "seq_plans_info", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAN_INFO_GENERATOR")
	@Getter
	@Setter
	@Column(name="plan_info_id")
	private Integer id;

	@Setter
	@ManyToOne
	@JoinColumn(name="management_plan_form_id")
	private ManagementPlanForm managementPlanForm;

	@Getter
	@Setter
	@Column(name = "info")
	private String info;

	@Getter
	@Setter
	@Column(name = "type_info")
	private String typeInfo;

	@Setter
	@ManyToOne
	@JoinColumn(name="plan_info_parent_id")
	private PlanInfo planInfoParent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planInfoParent", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<PlanInfo> plansInfo = new ArrayList<>();

}
