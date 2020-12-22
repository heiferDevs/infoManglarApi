package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.MappingForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;

@Stateless
public class PlanTrackingFormFacade extends AbstractFacade<PlanTrackingForm, Integer> {

	public PlanTrackingFormFacade() {
		super(PlanTrackingForm.class, Integer.class);		
	}
	
	public boolean save(PlanTrackingForm form)
	{
		try
		{						
			if (form.getPlanTrackingFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				PlanTrackingForm old = find(form.getPlanTrackingFormId());
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
	public List<PlanTrackingForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<PlanTrackingForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PlanTrackingForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlanTrackingForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<PlanTrackingForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PlanTrackingForm>) query.getResultList();
		}
		
		return result;
	}
	
	public PlanTrackingForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from PlanTrackingForm m where m.organizationManglarId = :orgId ORDER BY m.planTrackingFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (PlanTrackingForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public PlanTrackingForm getLastByOrg(Integer orgId, Date start, Date end) {
		try {
			Query query = super.getEntityManager().createQuery("select m from PlanTrackingForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.planTrackingFormId DESC");
			query.setParameter("orgId", orgId);
			query.setParameter("start", start);
			query.setParameter("end", end);
			query.setMaxResults(1);
			return (PlanTrackingForm) query.getSingleResult();
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
