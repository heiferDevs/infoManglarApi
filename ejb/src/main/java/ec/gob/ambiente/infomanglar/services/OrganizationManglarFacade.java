package ec.gob.ambiente.infomanglar.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;

@Stateless
public class OrganizationManglarFacade extends AbstractFacade<OrganizationManglar, Integer> {

	public OrganizationManglarFacade() {
		super(OrganizationManglar.class, Integer.class);		
	}
	
	public boolean save(OrganizationManglar organizationManglar)
	{
		try
		{						
			if (organizationManglar.getOrganizationManglarId() == null) {
				create(organizationManglar);
			} else {
				edit(organizationManglar);
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
	 * @param organizationManglarId
	 * @return
	 */
	public OrganizationManglar findById(Integer organizationManglarId)
	{
		Query query = super.getEntityManager().createQuery("select o from OrganizationManglar o where o.organizationManglarId = :organizationManglarId and o.organizationManglarStatus = true");
		query.setParameter("organizationManglarId", organizationManglarId);
		
		OrganizationManglar organizationManglar = null;
		
		if(query.getResultList().size() > 0)
		{
			organizationManglar = (OrganizationManglar) query.getResultList().get(0);
		}
		
		return organizationManglar;
	}

	public OrganizationManglar findByName(String name) {
		try {
			 Query query = getEntityManager().createQuery("Select o from OrganizationManglar o where o.organizationManglarStatus = true and o.organizationManglarName =:name");
			    query.setParameter("name", name);    
			    return (OrganizationManglar) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	   
	}

	@SuppressWarnings("unchecked")
	public List<OrganizationManglar> findByType(String type) {
		Query query = getEntityManager().createQuery("Select o from OrganizationManglar o where o.organizationManglarStatus = true and o.organizationManglarType =:type");
		query.setParameter("type", type);
	    List<OrganizationManglar> result = new ArrayList<>();

		if(query.getResultList().size() > 0)
		{
			result = (List<OrganizationManglar>) query.getResultList();
		}
		return result;
	}

}
