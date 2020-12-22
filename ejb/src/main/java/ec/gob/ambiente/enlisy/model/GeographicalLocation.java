package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the geographical_locations database table.
 * 
 */
@Entity
@Table(name="geographical_locations", schema="public")
@NamedQuery(name="GeographicalLocation.findAll", query="SELECT g FROM GeographicalLocation g")
public class GeographicalLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "GEOGRAPHICAL_LOCATIONS_GENERATOR", sequenceName = "seq_gelo_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEOGRAPHICAL_LOCATIONS_GENERATOR")
	@Column(name="gelo_id")
	private Integer geloId;

	@Column(name="area_id")
	private Integer areaId;

	@Column(name="gelo_codification_inec")
	private String geloCodificationInec;

	@Column(name="gelo_name")
	private String geloName;

	@Column(name="gelo_region")
	private String geloRegion;

	@Column(name="gelo_status")
	private Boolean geloStatus;

	@Column(name="pglo_id")
	private Integer pgloId;

	@Column(name="zone_id")
	private Integer zoneId;

	//bi-directional many-to-one association to GeographicalLocation
	@ManyToOne
	@JoinColumn(name="gelo_parent_id")
	private GeographicalLocation geographicalLocation;

	//bi-directional many-to-one association to GeographicalLocation
	@OneToMany(mappedBy="geographicalLocation")
	private List<GeographicalLocation> geographicalLocations;

	//bi-directional many-to-one association to People
	@OneToMany(mappedBy="geographicalLocation")
	private List<People> peoples;	

	public GeographicalLocation() {
	}
	
	public GeographicalLocation(Integer geloId) {
		this.geloId = geloId;
	}

	public Integer getGeloId() {
		return this.geloId;
	}

	public void setGeloId(Integer geloId) {
		this.geloId = geloId;
	}

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getGeloCodificationInec() {
		return this.geloCodificationInec;
	}

	public void setGeloCodificationInec(String geloCodificationInec) {
		this.geloCodificationInec = geloCodificationInec;
	}

	public String getGeloName() {
		return this.geloName;
	}

	public void setGeloName(String geloName) {
		this.geloName = geloName;
	}

	public String getGeloRegion() {
		return this.geloRegion;
	}

	public void setGeloRegion(String geloRegion) {
		this.geloRegion = geloRegion;
	}

	public Boolean getGeloStatus() {
		return this.geloStatus;
	}

	public void setGeloStatus(Boolean geloStatus) {
		this.geloStatus = geloStatus;
	}

	public Integer getPgloId() {
		return this.pgloId;
	}

	public void setPgloId(Integer pgloId) {
		this.pgloId = pgloId;
	}

	public Integer getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public GeographicalLocation getGeographicalLocation() {
		return this.geographicalLocation;
	}

	public void setGeographicalLocation(GeographicalLocation geographicalLocation) {
		this.geographicalLocation = geographicalLocation;
	}

	public List<GeographicalLocation> getGeographicalLocations() {
		return this.geographicalLocations;
	}

	public void setGeographicalLocations(List<GeographicalLocation> geographicalLocations) {
		this.geographicalLocations = geographicalLocations;
	}

	public GeographicalLocation addGeographicalLocation(GeographicalLocation geographicalLocation) {
		getGeographicalLocations().add(geographicalLocation);
		geographicalLocation.setGeographicalLocation(this);

		return geographicalLocation;
	}

	public GeographicalLocation removeGeographicalLocation(GeographicalLocation geographicalLocation) {
		getGeographicalLocations().remove(geographicalLocation);
		geographicalLocation.setGeographicalLocation(null);

		return geographicalLocation;
	}

	public List<People> getPeoples() {
		return this.peoples;
	}

	public void setPeoples(List<People> peoples) {
		this.peoples = peoples;
	}

	public People addPeople(People people) {
		getPeoples().add(people);
		people.setGeographicalLocation(this);

		return people;
	}

	public People removePeople(People people) {
		getPeoples().remove(people);
		people.setGeographicalLocation(null);

		return people;
	}

}