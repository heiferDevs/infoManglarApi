package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users", schema= "public")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@Column(name="user_id")
	@SequenceGenerator(name = "USERS_GENERATOR", initialValue = 1, sequenceName = "seq_user_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_GENERATOR")
	private Integer userId;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="area_id")	
	private Area area;

	@Column(name="justification_access")
	private String justificationAccess;

	@Column(name="temp_password")
	private String tempPassword;

	@Column(name="user_creation_date")
	private Date userCreationDate;

	@Column(name="user_creation_user")
	private String userCreationUser;

	@Column(name="user_creator_user")
	private String userCreatorUser;

	@Column(name="user_data_complete")
	private Boolean userDataComplete;

	@Column(name="user_date_expiration")
	private Date userDateExpiration;

	@Column(name="user_date_update")
	private Date userDateUpdate;

	@Column(name="user_docu_id")
	private String userDocuId;

	@Column(name="user_edif_id")
	private Integer userEdifId;

	@Column(name="user_functionary")
	private Boolean userFunctionary;

	@Column(name="user_justification_access")
	private String userJustificationAccess;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_observations")
	private String userObservations;

	@Column(name="user_password")
	private String userPassword;

	@Column(name="user_pin")
	private String userPin;

	@Column(name="user_status")
	private Boolean userStatus;

	@Column(name="user_subrogante")
	private Boolean userSubrogante;

	@Column(name="user_temp_password")
	private String userTempPassword;

	@Column(name="user_token")
	private Boolean userToken;

	@Column(name="user_user_update")
	private String userUserUpdate;
	
	//bi-directional many-to-one association to Catalog
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="peop_id")
	private People people;

	//bi-directional many-to-one association to RolesUser
	@OneToMany(mappedBy="user")
	private List<RolesUser> rolesUsers;

	public User() {
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}	

	public String getJustificationAccess() {
		return this.justificationAccess;
	}

	public void setJustificationAccess(String justificationAccess) {
		this.justificationAccess = justificationAccess;
	}

	public String getTempPassword() {
		return this.tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public Date getUserCreationDate() {
		return this.userCreationDate;
	}

	public void setUserCreationDate(Date userCreationDate) {
		this.userCreationDate = userCreationDate;
	}

	public String getUserCreationUser() {
		return this.userCreationUser;
	}

	public void setUserCreationUser(String userCreationUser) {
		this.userCreationUser = userCreationUser;
	}

	public String getUserCreatorUser() {
		return this.userCreatorUser;
	}

	public void setUserCreatorUser(String userCreatorUser) {
		this.userCreatorUser = userCreatorUser;
	}

	public Boolean getUserDataComplete() {
		return this.userDataComplete;
	}

	public void setUserDataComplete(Boolean userDataComplete) {
		this.userDataComplete = userDataComplete;
	}

	public Date getUserDateExpiration() {
		return this.userDateExpiration;
	}

	public void setUserDateExpiration(Date userDateExpiration) {
		this.userDateExpiration = userDateExpiration;
	}

	public Date getUserDateUpdate() {
		return this.userDateUpdate;
	}

	public void setUserDateUpdate(Date userDateUpdate) {
		this.userDateUpdate = userDateUpdate;
	}

	public String getUserDocuId() {
		return this.userDocuId;
	}

	public void setUserDocuId(String userDocuId) {
		this.userDocuId = userDocuId;
	}

	public Integer getUserEdifId() {
		return this.userEdifId;
	}

	public void setUserEdifId(Integer userEdifId) {
		this.userEdifId = userEdifId;
	}

	public Boolean getUserFunctionary() {
		return this.userFunctionary;
	}

	public void setUserFunctionary(Boolean userFunctionary) {
		this.userFunctionary = userFunctionary;
	}

	public String getUserJustificationAccess() {
		return this.userJustificationAccess;
	}

	public void setUserJustificationAccess(String userJustificationAccess) {
		this.userJustificationAccess = userJustificationAccess;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserObservations() {
		return this.userObservations;
	}

	public void setUserObservations(String userObservations) {
		this.userObservations = userObservations;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPin() {
		return this.userPin;
	}

	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	public Boolean getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public Boolean getUserSubrogante() {
		return this.userSubrogante;
	}

	public void setUserSubrogante(Boolean userSubrogante) {
		this.userSubrogante = userSubrogante;
	}

	public String getUserTempPassword() {
		return this.userTempPassword;
	}

	public void setUserTempPassword(String userTempPassword) {
		this.userTempPassword = userTempPassword;
	}

	public Boolean getUserToken() {
		return this.userToken;
	}

	public void setUserToken(Boolean userToken) {
		this.userToken = userToken;
	}

	public String getUserUserUpdate() {
		return this.userUserUpdate;
	}

	public void setUserUserUpdate(String userUserUpdate) {
		this.userUserUpdate = userUserUpdate;
	}	

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public List<RolesUser> getRolesUsers() {
		return rolesUsers;
	}

	public void setRolesUsers(List<RolesUser> rolesUsers) {
		this.rolesUsers = rolesUsers;
	}
	
	public RolesUser addRolesUser(RolesUser rolesUser) {
		getRolesUsers().add(rolesUser);
		rolesUser.setUser(this);

		return rolesUser;
	}

	public RolesUser removeRolesUser(RolesUser rolesUser) {
		getRolesUsers().remove(rolesUser);
		rolesUser.setUser(null);

		return rolesUser;
	}
}