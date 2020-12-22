package ec.gob.ambiente.infomanglar.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.enlisy.model.User;


@Entity
@Table(name="organizations_users", schema="info_manglar")
@NamedQuery(name="OrganizationsUser.findAll", query="SELECT r FROM OrganizationsUser r")
public class OrganizationsUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ORUS_ID_GENERATOR", initialValue = 1, sequenceName = "seq_orus_id", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORUS_ID_GENERATOR")
	@Column(name="orus_id")
	private Integer orusId;

	@Getter
	@Setter
	@Column(name="orus_status")
	private Boolean orusStatus;

	//bi-directional many-to-one association to Role
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="organization_manglar_id")
	private OrganizationManglar organizationManglar;
	
	//bi-directional many-to-one association to Role
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}
