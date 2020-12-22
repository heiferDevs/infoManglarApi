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

import ec.gob.ambiente.infomanglar.model.Agreement;

@Entity
@Table(name = "config_forms", schema = "info_manglar")
@NamedQuery(name = "ConfigForm.findAll", query = "SELECT o FROM ConfigForm o")
public class ConfigForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONFIG_FORMS_GENERATOR", initialValue = 1, sequenceName = "seq_config_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIG_FORMS_GENERATOR")
	@Getter
	@Setter
	@Column(name = "config_form_id")
	private Integer configFormId;

	@Getter
	@Setter
	@Column(name = "version")
	private String version;

}
