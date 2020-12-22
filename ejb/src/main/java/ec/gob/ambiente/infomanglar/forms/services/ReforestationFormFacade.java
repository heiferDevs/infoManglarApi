package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.ControlForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.model.ReforestationForm;

@Stateless
public class ReforestationFormFacade extends AbstractFacade<ReforestationForm, Integer> {

	public ReforestationFormFacade() {
		super(ReforestationForm.class, Integer.class);		
	}
	
	public boolean save(ReforestationForm form)
	{
		try
		{						
			if (form.getReforestationFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				ReforestationForm old = find(form.getReforestationFormId());
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
	public List<ReforestationForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<ReforestationForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ReforestationForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReforestationForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<ReforestationForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ReforestationForm>) query.getResultList();
		}
		
		return result;
	}
	
	public ReforestationForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from ReforestationForm m where m.organizationManglarId = :orgId ORDER BY m.reforestationFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (ReforestationForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ReforestationForm> getByOrgAndDates(Integer orgId, Date start, Date end) {
		Query query = super.getEntityManager().createQuery("select m from ReforestationForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.reforestationFormId DESC");
		query.setParameter("orgId", orgId);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<ReforestationForm>) query.getResultList();
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
