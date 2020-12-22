package ec.gob.ambiente.enlisy.services;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.Role;

@Stateless
public class RoleFacade extends AbstractFacade<Role, Integer> implements Serializable{

	private static final long serialVersionUID = 2650804737317304970L;

	public RoleFacade() {
		super(Role.class, Integer.class);		
	}
	
	public Role findByName(String rolName) {
		try {
			 Query query = getEntityManager().createQuery("Select o from Role o where o.roleStatus =true and o.roleName =:rolName");
			    query.setParameter("rolName", rolName);    
			    return (Role) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	   
	}
}