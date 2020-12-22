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

import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="prices_dailies", schema="info_manglar")
@NamedQuery(name="PriceDaily.findAll", query="SELECT o FROM PriceDaily o")
public class PriceDaily implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PRICE_DAILY_GENERATOR", initialValue = 1, sequenceName = "seq_prices_dailies", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRICE_DAILY_GENERATOR")
	@Getter
	@Setter
	@Column(name="price_daily_id")
	private Integer id;

	@Setter
	@ManyToOne
	@JoinColumn(name="prices_form_id")
	private PricesForm pricesForm;

	@Getter
	@Setter
	@Column(name = "product_type")
	private String productType;

	@Getter
	@Setter
	@Column(name = "bio_aquatic_type")
	private String bioAquaticType;

	@Getter
	@Setter
	@Column(name = "shell_quality")
	private String shellQuality;

	@Getter
	@Setter
	@Column(name = "bio_aquatic_price")
	private Double bioAquaticPrice;

	@Getter
	@Setter
	@Column(name = "other_products_price")
	private Double otherProductsPrice;

	@Getter
	@Setter
	@Column(name = "shell_count")
	private Integer shellCount;

	@Getter
	@Setter
	@Column(name = "sarts_count")
	private Integer sartsCount;

	@Getter
	@Setter
	@Column(name = "miel_mangle_count")
	private Integer mielMangleCount;

	@Getter
	@Setter
	@Column(name = "craft_count")
	private Integer craftCount;

	@Getter
	@Setter
	@Column(name = "plantas_mangle_count")
	private Integer plantasMangleCount;


	@Getter
	@Setter
	@Column(name = "shell_pulp_pounds")
	private Double shellPulpPounds;

	@Getter
	@Setter
	@Column(name = "crab_pulp_pounds")
	private Double crabPulpPounds;


	@Getter
	@Setter
	@Column(name = "crab_quality")
	private String crabQuality;

	@Getter
	@Setter
	@Column(name = "crab_observations")
	private String crabObservations;

	@Getter
	@Setter
	@Column(name = "other_products")
	private String otherProducts;

	@Getter
	@Setter
	@Column(name = "craft_name")
	private String craftName;


	@Getter
	@Setter
	@Column(name = "service_type")
	private String serviceType;

	@Getter
	@Setter
	@Column(name = "bioemprendimiento_name")
	private String bioemprendimientoName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "priceDaily", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

}
