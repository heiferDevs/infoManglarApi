package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;

@Stateless
public class EconomicIndicatorsFormFacade extends AbstractFacade<EconomicIndicatorsForm, Integer> {

	public EconomicIndicatorsFormFacade() {
		super(EconomicIndicatorsForm.class, Integer.class);		
	}
	
	public boolean save(EconomicIndicatorsForm form)
	{
		try
		{						
			if (form.getEconomicIndicatorsFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				EconomicIndicatorsForm old = find(form.getEconomicIndicatorsFormId());
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
	public List<EconomicIndicatorsForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<EconomicIndicatorsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<EconomicIndicatorsForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EconomicIndicatorsForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<EconomicIndicatorsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<EconomicIndicatorsForm>) query.getResultList();
		}
		
		return result;
	}
	
	public EconomicIndicatorsForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from EconomicIndicatorsForm m where m.organizationManglarId = :orgId ORDER BY m.economicIndicatorsFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (EconomicIndicatorsForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public EconomicIndicatorsForm getLastByOrg(Integer orgId, Date start, Date end) {
		try {
			Query query = super.getEntityManager().createQuery("select m from EconomicIndicatorsForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.economicIndicatorsFormId DESC");
			query.setParameter("orgId", orgId);
			query.setParameter("start", start);
			query.setParameter("end", end);
			query.setMaxResults(1);
			return (EconomicIndicatorsForm) query.getSingleResult();
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
