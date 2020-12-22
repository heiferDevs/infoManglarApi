package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.LimitsForm;

@Stateless
public class LimitsFormFacade extends AbstractFacade<LimitsForm, Integer> {

	public LimitsFormFacade() {
		super(LimitsForm.class, Integer.class);		
	}
	
	public boolean save(LimitsForm form)
	{
		try
		{						
			if (form.getLimitsFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				LimitsForm old = find(form.getLimitsFormId());
				form.setCreatedAt(old.getCreatedAt());
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
	public List<LimitsForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<LimitsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<LimitsForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LimitsForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<LimitsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<LimitsForm>) query.getResultList();
		}
		
		return result;
	}
	
	public LimitsForm getLast() {
		try {
			Query query = super.getEntityManager().createQuery("select m from LimitsForm m ORDER BY m.limitsFormId DESC");
			query.setMaxResults(1);
			return (LimitsForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public LimitsForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from LimitsForm m where m.organizationManglarId = :orgId ORDER BY m.limitsFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (LimitsForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
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
