package ec.gob.ambiente.infomanglar.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.model.OrganizationsUser;

@Stateless
public class OrganizationsUserFacade extends AbstractFacade<OrganizationsUser, Integer> implements Serializable {

	private static final long serialVersionUID = 5978766190183771960L;

	public OrganizationsUserFacade() {
		super(OrganizationsUser.class, Integer.class);		
	}
		
	public void save(User user, OrganizationManglar organizationManglar)
	{
		OrganizationsUser ou = new OrganizationsUser();
		ou.setOrusStatus(true);
		ou.setUser(user);
		ou.setOrganizationManglar(organizationManglar);		
		create(ou);
	}

//	public List<RolesUser> findByUserNameAndRoleName(String userName,String roleName)
//	{
//		try {
//			TypedQuery<RolesUser> query = super.getEntityManager().createQuery(""
//					+ "select o from RolesUser o where o.user.userStatus=true and o.user.userName = :userName and o.role.roleName like :role and o.rousStatus = true ", RolesUser.class);
//			query.setParameter("userName", userName);
//			query.setParameter("role", roleName);		
//			List<RolesUser> result= (List<RolesUser>) query.getResultList();
//			if(result.size()>0)
//				return result;			
//		} catch (NoResultException e) {
//			return null;
//		}
//		
//		return null;
//	}
//	
	/**
	 * Buscar OrganizacionesManglar por usuario
	 * @param userId
	 * @return Devuelve vacio si no encuntra ningun registro
	 */
	public List<OrganizationManglar> findByUserId(Integer userId)
	{
		List<OrganizationManglar> result = new ArrayList<>();
		try {
			TypedQuery<OrganizationManglar> query = super.getEntityManager().createQuery(""
					+ "select o from OrganizationsUser ou, OrganizationManglar o where ou.user.userId = :userId and ou.organizationManglar.organizationManglarId = o.organizationManglarId and ou.orusStatus = true", OrganizationManglar.class);		
			query.setParameter("userId", userId);		
			result = (List<OrganizationManglar>) query.getResultList();
		} catch (NoResultException e) {			
			e.printStackTrace();
		}
		return result;
	}

	public List<User> findByOrgId(Integer orgId)
	{
		List<User> result = new ArrayList<>();
		try {
			TypedQuery<User> query = super.getEntityManager().createQuery(""
					+ "select u from OrganizationsUser ou, User u where ou.user.userId = u.userId and ou.organizationManglar.organizationManglarId = :orgId and ou.orusStatus = true", User.class);		
			query.setParameter("orgId", orgId);		
			result = (List<User>) query.getResultList();
		} catch (NoResultException e) {			
			e.printStackTrace();
		}
		return result;
	}

	public List<User> findByOrgIdAndRole(Integer orgId, String role)
	{
		List<User> result = new ArrayList<>();
		try {
			TypedQuery<User> query = super.getEntityManager().createQuery(""
					+ "select u from OrganizationsUser ou, User u, RolesUser ru where ou.user.userId = u.userId and ru.user.userId = u.userId and ou.organizationManglar.organizationManglarId = :orgId and ou.orusStatus = true and ru.role.roleName like :role", User.class);		
			query.setParameter("orgId", orgId);
			query.setParameter("role", role);
			result = (List<User>) query.getResultList();
		} catch (NoResultException e) {			
			e.printStackTrace();
		}
		return result;
	}

	public OrganizationManglar findFirstByUserId(Integer userId)
	{
		List<OrganizationManglar> results = new ArrayList<>();
		try {
			TypedQuery<OrganizationManglar> query = super.getEntityManager().createQuery(""
					+ "select o from OrganizationsUser ou, OrganizationManglar o where ou.user.userId = :userId and ou.organizationManglar.organizationManglarId = o.organizationManglarId and ou.orusStatus = true", OrganizationManglar.class);		
			query.setParameter("userId", userId);		
			results = (List<OrganizationManglar>) query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}	
	
//	
//	public User findUniqueByRoleName(String roleName)
//	{
//		List<User> listUsers=findByRoleName(roleName);
//		if(listUsers!=null)
//			return listUsers.get(0);
//		return null;
//	}
}