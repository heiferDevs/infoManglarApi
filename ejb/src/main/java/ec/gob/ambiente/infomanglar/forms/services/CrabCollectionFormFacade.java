package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;
import ec.gob.ambiente.util.Utility;

@Stateless
public class CrabCollectionFormFacade extends AbstractFacade<CrabCollectionForm, Integer> {

	public CrabCollectionFormFacade() {
		super(CrabCollectionForm.class, Integer.class);		
	}
	
	public boolean save(CrabCollectionForm form)
	{
		try
		{						
			if (form.getCrabCollectionFormId() == null) {
				form.setCreatedAt(Utility.getDate(form.getCollectorDate()));
				form.setFormStatus(true);
				create(form);
			} else {
				CrabCollectionForm old = find(form.getCrabCollectionFormId());
				form.setCreatedAt(Utility.getDate(form.getCollectorDate()));
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
	public List<CrabCollectionForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<CrabCollectionForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<CrabCollectionForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CrabCollectionForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<CrabCollectionForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<CrabCollectionForm>) query.getResultList();
		}
		
		return result;
	}
	public CrabCollectionForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from CrabCollectionForm m where m.organizationManglarId = :orgId ORDER BY m.crabCollectionFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (CrabCollectionForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public CrabCollectionForm getLastByOrgAndUser(Integer orgId, Integer userId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from CrabCollectionForm m where m.organizationManglarId = :orgId and m.userId = :userId ORDER BY m.crabCollectionFormId DESC");
			query.setParameter("orgId", orgId);
			query.setParameter("userId", userId);
			query.setMaxResults(1);
			return (CrabCollectionForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CrabCollectionForm> getByOrgAndDates(Integer orgId, Date start, Date end) {
		Query query = super.getEntityManager().createQuery("select m from CrabCollectionForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.crabCollectionFormId DESC");
		query.setParameter("orgId", orgId);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<CrabCollectionForm>) query.getResultList();
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
