package ec.gob.ambiente.enlisy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the contacts database table.
 * 
 */
@Entity
@Table(name = "areas", schema = "public")
public class Area{
	

	@Getter
	@Setter
	@Column(name = "area_id")
	@Id
	@SequenceGenerator(name = "AREAS_GENERATOR", initialValue = 1, sequenceName = "seq_area_id", schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREAS_GENERATOR")
	private Integer id;

	@Getter
	@Setter
	@Column(name = "area_abbreviation")
	private String areaAbbreviation;

	@Getter
	@Setter
	@Column(name = "area_email")
	private String areaEmail;

	@Getter
	@Setter
	@Column(name = "area_name")
	private String areaName;

	@Getter
	@Setter
	@Column(name = "area_ente_identification")
	private String identificacionEnte;

	@Getter
	@Setter
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private List<User> usersList;

	@Getter
	@Setter
	@JoinColumn(name = "gelo_id", referencedColumnName = "gelo_id")
	@ManyToOne	
	private GeographicalLocation ubicacionesGeografica;

	/*@Getter
	@Setter
	@JoinColumn(name = "arty_id", referencedColumnName = "arty_id")
	@ManyToOne	
	private TipoArea tipoArea;*/

	@Getter
	@Setter
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private List<Area> areaList;

	@Getter
	@Setter
	@JoinColumn(name = "area_parent_id", referencedColumnName = "area_id")
	@ManyToOne(fetch = FetchType.LAZY)	
	private Area area;

	/*@Getter
	@Setter
	@OneToMany(mappedBy = "areaResponsable", fetch = FetchType.LAZY)
	private List<AprobacionRequisitosTecnicos> requisitosTecnicos;*/

	@Getter
	@Setter
	@Column(name = "entity_type")
	private String tipoEnteAcreditado;
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Area)) {
			return false;
		}
		Area other = (Area) obj;
		if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getAreaName();
	}

}