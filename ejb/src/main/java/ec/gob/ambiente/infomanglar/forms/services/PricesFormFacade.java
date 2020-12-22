package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;

@Stateless
public class PricesFormFacade extends AbstractFacade<PricesForm, Integer> {

	public PricesFormFacade() {
		super(PricesForm.class, Integer.class);		
	}
	
	public boolean save(PricesForm form)
	{
		try
		{	
			if (form.getPricesFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				PricesForm old = find(form.getPricesFormId());
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
	public List<PricesForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<PricesForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PricesForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PricesForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<PricesForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PricesForm>) query.getResultList();
		}
		
		return result;
	}
	
	public PricesForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from PricesForm m where m.organizationManglarId = :orgId ORDER BY m.pricesFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (PricesForm) query.getSingleResult();
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
