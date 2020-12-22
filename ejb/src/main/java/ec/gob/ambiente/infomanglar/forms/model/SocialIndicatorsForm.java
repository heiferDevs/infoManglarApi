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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "social_indicators_forms", schema = "info_manglar")
@NamedQuery(name = "SocialIndicatorsForm.findAll", query = "SELECT o FROM SocialIndicatorsForm o")
public class SocialIndicatorsForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SOCIAL_INDICATORS_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_social_indicators_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOCIAL_INDICATORS_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "social_indicators_form_id")
	private Integer socialIndicatorsFormId;

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
	@Column(name = "count_family_members")
	private Integer countFamilyMembers;

	@Getter
	@Setter
	@Column(name = "count_manglar_dependents")
	private Integer countManglarDependents;

	@Getter
	@Setter
	@Column(name = "income_percentage")
	private Double incomePercentage;

	@Getter
	@Setter
	@Column(name = "nivel")
	private String nivel;

}
