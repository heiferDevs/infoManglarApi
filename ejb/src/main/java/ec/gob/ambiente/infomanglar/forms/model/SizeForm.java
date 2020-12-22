package ec.gob.ambiente.infomanglar.forms.model;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="size_forms", schema="info_manglar")
@NamedQuery(name="SizeForm.findAll", query="SELECT o FROM SizeForm o")
public class SizeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SIZE_FORM_GENERATOR", initialValue = 1, sequenceName = "seq_size_forms", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIZE_FORM_GENERATOR")
	@Getter
	@Setter
	@Column(name="size_form_id")
	private Integer id;

	@Getter
	@Setter
	@Column(name="size_form_status")
	private Boolean status;

	@Setter
	@ManyToOne
	@JoinColumn(name="crab_size_form_id")
	private CrabSizeForm crabSizeForm;

	@Setter
	@ManyToOne
	@JoinColumn(name="shell_size_form_id")
	private ShellSizeForm shellSizeForm;

	@Getter
	@Setter
	@Column(name="size_form_id_option")
	private String idOption;

	@Getter
	@Setter
	@Column(name="size_form_type")
	private String type;

	@Getter
	@Setter
	@Column(name="size_form_sex")
	private String sex;

	@Getter
	@Setter
	@Column(name="size_form_width")
	private Integer width;

	@Getter
	@Setter
	@Column(name="size_form_length")
	private Integer length;

}
