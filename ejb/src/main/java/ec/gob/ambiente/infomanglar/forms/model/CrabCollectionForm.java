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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "crab_collection_forms", schema = "info_manglar")
@NamedQuery(name = "CrabCollectionForm.findAll", query = "SELECT o FROM CrabCollectionForm o")
public class CrabCollectionForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CRAB_COLLECTION_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_crab_collection_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRAB_COLLECTION_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "crab_collection_form_id")
	private Integer crabCollectionFormId;

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
	  @Column(name="crab_period")
	  private String crabPeriod;
	  
	  @Getter
	  @Setter
	  @Column(name="hours_worked")
	  private Integer hoursWorked;
	  
	  @Getter
	  @Setter
	  @Column(name="crab_collected_count")
	  private Integer crabCollectedCount;
	  
	  @Getter
	  @Setter
	  @Column(name="quedados_crabs")
	  private Integer quedadosCrabs;
	  
	  @Getter
	  @Setter
	  @Column(name="females_returned")
	  private Integer femalesReturned;
	  
	  @Getter
	  @Setter
	  @Column(name="collected_greater_count")
	  private Integer collectedGreaterCount;

		@Getter
		@Setter
		@Column(name="sector_name")
		private String sectorName;

		@Getter
		@Setter
		@Column(name="collector_name")
		private String collectorName;

		@Getter
		@Setter
		@Column(name="collector_date")
		private String collectorDate;

}
