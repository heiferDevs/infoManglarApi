package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.util.Utility;

@Stateless
public class CrabSizeFormFacade extends AbstractFacade<CrabSizeForm, Integer> {

	public CrabSizeFormFacade() {
		super(CrabSizeForm.class, Integer.class);		
	}
	
	public boolean save(CrabSizeForm form)
	{
		try
		{						
			if (form.getCrabSizeFormId() == null) {
				form.setCreatedAt(Utility.getDate(form.getSamplingDate()));
				form.setFormStatus(true);
				create(form);
			} else {
				CrabSizeForm old = find(form.getCrabSizeFormId());
				form.setCreatedAt(Utility.getDate(form.getSamplingDate()));
				form.setFormStatus(old.getFormStatus());
				edit(form);
			}
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	/**
	 * Buscar por userId
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CrabSizeForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<CrabSizeForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<CrabSizeForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CrabSizeForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<CrabSizeForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<CrabSizeForm>) query.getResultList();
		}
		
		return result;
	}
	
	public CrabSizeForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from CrabSizeForm m where m.organizationManglarId = :orgId ORDER BY m.crabSizeFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (CrabSizeForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CrabSizeForm> getByOrgAndDates(Integer orgId, Date start, Date end) {
		Query query = super.getEntityManager().createQuery("select m from CrabSizeForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.crabSizeFormId DESC");
		query.setParameter("orgId", orgId);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<CrabSizeForm>) query.getResultList();
	}
	
	/**
	 * group by type count
	 * @param organizationManglarId
	 * @return
	 */
	public List<?> summary() {
		Query query = super.getEntityManager().createQuery("select o.formType, count(*) from AnomalyForm o group by o.formType");
		return query.getResultList();
	}

}
