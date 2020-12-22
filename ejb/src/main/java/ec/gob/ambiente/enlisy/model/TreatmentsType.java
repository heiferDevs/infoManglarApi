package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the treatments_types database table.
 * 
 */
@Entity
@Table(name="treatments_types", schema="public")
@NamedQuery(name="TreatmentsType.findAll", query="SELECT t FROM TreatmentsType t")
public class TreatmentsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TREATMENTS_TYPES_GENERATOR", initialValue = 1, sequenceName = "seq_trty_id", schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TREATMENTS_TYPES_GENERATOR")
	@Column(name="trty_id")
	private Integer trtyId;

	@Column(name="trty_name")
	private String trtyName;

	@Column(name="trty_status")
	private Boolean trtyStatus;

	//bi-directional many-to-one association to People
	@OneToMany(mappedBy="treatmentsType")
	private List<People> peoples;

	public TreatmentsType() {
	}
	
	public TreatmentsType(Integer trtyId) {
		this.trtyId = trtyId;
	}

	public Integer getTrtyId() {
		return this.trtyId;
	}

	public void setTrtyId(Integer trtyId) {
		this.trtyId = trtyId;
	}

	public String getTrtyName() {
		return this.trtyName;
	}

	public void setTrtyName(String trtyName) {
		this.trtyName = trtyName;
	}

	public Boolean getTrtyStatus() {
		return this.trtyStatus;
	}

	public void setTrtyStatus(Boolean trtyStatus) {
		this.trtyStatus = trtyStatus;
	}

	public List<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(List<People> peoples) {
		this.peoples = peoples;
	}

	public People addPeople(People people) {
		getPeoples().add(people);
		people.setTreatmentsType(this);

		return people;
	}

	public People removePeople(People people) {
		getPeoples().remove(people);
		people.setTreatmentsType(null);

		return people;
	}

}