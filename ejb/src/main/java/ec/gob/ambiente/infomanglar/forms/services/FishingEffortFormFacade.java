package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.FishingEffortForm;

@Stateless
public class FishingEffortFormFacade extends AbstractFacade<FishingEffortForm, Integer> {

	public FishingEffortFormFacade() {
		super(FishingEffortForm.class, Integer.class);		
	}
	
	public boolean save(FishingEffortForm form)
	{
		try
		{						
			if (form.getFishingEffortFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				FishingEffortForm old = find(form.getFishingEffortFormId());
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
	public List<FishingEffortForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<FishingEffortForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<FishingEffortForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FishingEffortForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<FishingEffortForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<FishingEffortForm>) query.getResultList();
		}
		
		return result;
	}

	public FishingEffortForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from FishingEffortForm m where m.organizationManglarId = :orgId ORDER BY m.fishingEffortFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (FishingEffortForm) query.getSingleResult();
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
