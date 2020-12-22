package ec.gob.ambiente.infomanglar.model;

import java.io.Serializable;

import javax.persistence.*;

import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.model.Role;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="allowed_users", schema="info_manglar")
@NamedQuery(name="AllowedUser.findAll", query="SELECT o FROM AllowedUser o")
public class AllowedUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ALLOWED_USERS_GENERATOR", initialValue = 1, sequenceName = "seq_allowed_user_id", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALLOWED_USERS_GENERATOR")
	@Getter
	@Setter
	@Column(name="allowed_user_id")
	private Integer allowedUserId;

	@Getter
	@Setter
	@Column(name="allowed_user_status")
	private Boolean allowedUserStatus;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="organization_manglar_id")
	private OrganizationManglar organizationManglar;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="gelo_id")
	private GeographicalLocation geographicalLocation;

	@Getter
	@Setter
	@Column(name="allowed_user_name")
	private String allowedUserName;
	
	@Getter
	@Setter
	@Column(name="allowed_user_pin")
	private String allowedUserPin;
	
}
