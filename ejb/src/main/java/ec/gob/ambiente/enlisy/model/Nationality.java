package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;




/**
 * The persistent class for the nationalities database table.
 * 
 */
@Entity
@Table(name="nationalities", schema="public")
@NamedQuery(name="Nationality.findAll", query="SELECT n FROM Nationality n")
public class Nationality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NATIONALITIES_GENERATOR", initialValue = 1, sequenceName = "seq_nati_id", schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NATIONALITIES_GENERATOR")
	@Column(name="nati_id")
	private Integer natiId;

	@Column(name="nati_description")
	private String natiDescription;

	@Column(name="nati_status")
	private Boolean natiStatus;

	//bi-directional many-to-one association to People
	@OneToMany(mappedBy="nationality")
	private List<People> peoples;
	
	public Nationality() {
	}
	
	public Nationality(Integer natiId) {
		this.natiId = natiId;
	}

	public Integer getNatiId() {
		return this.natiId;
	}

	public void setNatiId(Integer natiId) {
		this.natiId = natiId;
	}

	public String getNatiDescription() {
		return this.natiDescription;
	}

	public void setNatiDescription(String natiDescription) {
		this.natiDescription = natiDescription;
	}

	public Boolean getNatiStatus() {
		return this.natiStatus;
	}

	public void setNatiStatus(Boolean natiStatus) {
		this.natiStatus = natiStatus;
	}

	public List<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(List<People> peoples) {
		this.peoples = peoples;
	}

	public People addPeople(People people) {
		getPeoples().add(people);
		people.setNationality(this);

		return people;
	}

	public People removePeople(People people) {
		getPeoples().remove(people);
		people.setNationality(null);

		return people;
	}

}