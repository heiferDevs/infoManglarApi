package ec.gob.ambiente.infomanglar.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.model.AllowedUser;

@Stateless
public class AllowedUserFacade extends AbstractFacade<AllowedUser, Integer> {

	public AllowedUserFacade() {
		super(AllowedUser.class, Integer.class);		
	}
	
	public boolean save(AllowedUser allowedUser)
	{
		try
		{						
			if (allowedUser.getAllowedUserId() == null) {
				create(allowedUser);
			} else {
				edit(allowedUser);
			}
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	/**
	 * Buscar por Id
	 * @param allowedUserId
	 * @return
	 */
	public AllowedUser findById(Integer allowedUserId)
	{
		Query query = super.getEntityManager().createQuery("select o from AllowedUser o where o.allowedUserId = :allowedUserId and o.allowedUserStatus = true");
		query.setParameter("allowedUserId", allowedUserId);
		
		AllowedUser allowedUser = null;
		
		if(query.getResultList().size() > 0)
		{
			allowedUser = (AllowedUser) query.getResultList().get(0);
		}
		
		return allowedUser;
	}

	/**
	 * Buscar por Pin
	 * @param allowedUserPin
	 * @return
	 */
	public AllowedUser findByUserPin(String allowedUserPin)
	{
		Query query = super.getEntityManager().createQuery("select o from AllowedUser o where o.allowedUserPin = :allowedUserPin and o.allowedUserStatus = true");
		query.setParameter("allowedUserPin", allowedUserPin);
		
		AllowedUser allowedUser = null;
		
		if(query.getResultList().size() > 0)
		{
			allowedUser = (AllowedUser) query.getResultList().get(0);
		}
		
		return allowedUser;
	}


	/**
	 * Buscar por Role
	 * @param roleName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AllowedUser> findByRoleName(String roleName)
	{
		Query query = super.getEntityManager().createQuery("select a from AllowedUser a, Role r where a.role.roleId = r.roleId and r.roleName = :roleName and a.allowedUserStatus = true");
		query.setParameter("roleName", roleName);		
		return (List<AllowedUser>) query.getResultList();
	}
}
