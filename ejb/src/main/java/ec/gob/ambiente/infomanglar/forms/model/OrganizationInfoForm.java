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
@Table(name = "organization_info_forms", schema = "info_manglar")
@NamedQuery(name = "OrganizationInfoForm.findAll", query = "SELECT o FROM OrganizationInfoForm o")
public class OrganizationInfoForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ORGANIZATION_INFO_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_organization_info_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_INFO_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "organization_info_form_id")
	private Integer organizationInfoFormId;

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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationInfoForm", orphanRemoval=true)
	@OrderBy("id")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter
	@Setter
	@JsonProperty
	private List<FileForm> fileForms = new ArrayList<>();

	@Getter
	@Setter
	@Column(name = "cumple_regla_no_enajenar")
	private Boolean cumpleReglaNoEnajenar;

	@Getter
	@Setter
	@Column(name = "cumple_regla_no_ampliar")
	private Boolean cumpleReglaNoAmpliar;

	@Getter
	@Setter
	@Column(name = "cumple_regla_buena_vecindad")
	private Boolean cumpleReglaBuenaVecindad;

	@Getter
	@Setter
	@Column(name = "pescadores_independientes")
	private Boolean pescadoresIndependientes;

	@Getter
	@Setter
	@Column(name = "pescadores_de_otras_orgs")
	private Boolean pescadoresDeOtrasOrgs;

	@Getter
	@Setter
	@Column(name = "denuncio_pescadores_no_autorizados")
	private Boolean denuncioPescadoresNoAutorizados;

	@Getter
	@Setter
	@Column(name = "todos_recuros_bioacuaticos")
	private Boolean todosRecurosBioacuaticos;

	@Getter
	@Setter
	@Column(name = "pesca_fuera_del_manglar")
	private Boolean pescaFueraDelManglar;

	@Getter
	@Setter
	@Column(name = "actividades_de_cria")
	private Boolean actividadesDeCria;

	@Getter
	@Setter
	@Column(name = "cumple_normas_cria")
	private Boolean cumpleNormasCria;

	@Getter
	@Setter
	@Column(name = "devuelve_individuos_decomisados")
	private Boolean devuelveIndividuosDecomisados;

	@Getter
	@Setter
	@Column(name = "actividades_turismo")
	private Boolean actividadesTurismo;

	@Getter
	@Setter
	@Column(name = "normativa_turistica")
	private Boolean normativaTuristica;

	@Getter
	@Setter
	@Column(name = "cuenta_socio_manglar")
	private Boolean cuentaSocioManglar;

	@Getter
	@Setter
	@Column(name = "actividades_educacion_ambiental")
	private Boolean actividadesEducacionAmbiental;

	@Getter
	@Setter
	@Column(name = "actividades_investigacion")
	private Boolean actividadesInvestigacion;

	@Getter
	@Setter
	@Column(name = "cuenta_con_permisos")
	private Boolean cuentaConPermisos;

	@Getter
	@Setter
	@Column(name = "evidencio_muerte_arboles")
	private Boolean evidencioMuerteArboles;

	@Getter
	@Setter
	@Column(name = "evidencio_muerte_bioacuaticos")
	private Boolean evidencioMuerteBioacuaticos;

	@Getter
	@Setter
	@Column(name = "evidencio_muerte_flora")
	private Boolean evidencioMuerteFlora;

	@Getter
	@Setter
	@Column(name = "evidencio_muerte_fauna")
	private Boolean evidencioMuerteFauna;

	@Getter
	@Setter
	@Column(name = "cambios_en_agua")
	private Boolean cambiosEnAgua;

	@Getter
	@Setter
	@Column(name = "cambios_suelo")
	private Boolean cambiosSuelo;

	@Getter
	@Setter
	@Column(name = "cambios_clima")
	private Boolean cambiosClima;

	@Getter
	@Setter
	@Column(name = "cambios_temperatura")
	private Boolean cambiosTemperatura;
	
	  @Getter
	  @Setter
	  @Column(name = "cumple_regla_no_enajenar_desc")
	  private String cumpleReglaNoEnajenarDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cumple_regla_no_ampliar_desc")
	  private String cumpleReglaNoAmpliarDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cumple_regla_buena_vecindad_desc")
	  private String cumpleReglaBuenaVecindadDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "pescadores_independientes_desc")
	  private String pescadoresIndependientesDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "pescadores_de_otras_orgs_desc")
	  private String pescadoresDeOtrasOrgsDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "denuncio_pescadores_no_autorizados_desc")
	  private String denuncioPescadoresNoAutorizadosDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "todos_recuros_bioacuaticos_desc")
	  private String todosRecurosBioacuaticosDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "pesca_fuera_del_manglar_desc")
	  private String pescaFueraDelManglarDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "actividades_de_cria_desc")
	  private String actividadesDeCriaDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cumple_normas_cria_desc")
	  private String cumpleNormasCriaDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "devuelve_individuos_decomisados_desc")
	  private String devuelveIndividuosDecomisadosDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "actividades_turismo_desc")
	  private String actividadesTurismoDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "normativa_turistica_desc")
	  private String normativaTuristicaDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cuenta_socio_manglar_desc")
	  private String cuentaSocioManglarDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "actividades_educacion_ambiental_desc")
	  private String actividadesEducacionAmbientalDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "actividades_investigacion_desc")
	  private String actividadesInvestigacionDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cuenta_con_permisos_desc")
	  private String cuentaConPermisosDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "evidencio_muerte_arboles_desc")
	  private String evidencioMuerteArbolesDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "evidencio_muerte_bioacuaticos_desc")
	  private String evidencioMuerteBioacuaticosDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "evidencio_muerte_flora_desc")
	  private String evidencioMuerteFloraDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "evidencio_muerte_fauna_desc")
	  private String evidencioMuerteFaunaDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cambios_en_agua_desc")
	  private String cambiosEnAguaDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cambios_suelo_desc")
	  private String cambiosSueloDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cambios_clima_desc")
	  private String cambiosClimaDesc;
	  
	  @Getter
	  @Setter
	  @Column(name = "cambios_temperatura_desc")
	  private String cambiosTemperaturaDesc;

}
