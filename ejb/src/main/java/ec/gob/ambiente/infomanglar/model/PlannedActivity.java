package ec.gob.ambiente.infomanglar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="planned_activities", schema="info_manglar")
@NamedQuery(name="PlannedActivity.findAll", query="SELECT o FROM PlannedActivity o")
public class PlannedActivity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PLANNED_ACTIVITY_GENERATOR", initialValue = 1, sequenceName = "seq_planned_activities", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANNED_ACTIVITY_GENERATOR")
	@Getter
	@Setter
	@Column(name="planned_activity_id")
	private Integer id;

	@Setter
	@ManyToOne
	@JoinColumn(name="plan_tracking_form_id")
	private PlanTrackingForm planTrackingForm;

	@Getter
	@Setter
	@Column(name = "activity_description")
	private String activityDescription;

	@Getter
	@Setter
	@Column(name = "average_done")
	private Double averageDone;

}
