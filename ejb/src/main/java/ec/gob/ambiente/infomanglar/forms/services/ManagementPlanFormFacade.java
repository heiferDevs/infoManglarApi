package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;

@Stateless
public class ManagementPlanFormFacade extends AbstractFacade<ManagementPlanForm, Integer> {

	public ManagementPlanFormFacade() {
		super(ManagementPlanForm.class, Integer.class);		
	}
	
	public boolean save(ManagementPlanForm form)
	{
		try
		{						
			if (form.getManagementPlanFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				ManagementPlanForm old = find(form.getManagementPlanFormId());
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
	public List<ManagementPlanForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<ManagementPlanForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ManagementPlanForm>) query.getResultList();
		}
		
		return result;
	}

	public ManagementPlanForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from ManagementPlanForm m where m.organizationManglarId = :orgId ORDER BY m.managementPlanFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (ManagementPlanForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ManagementPlanForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<ManagementPlanForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ManagementPlanForm>) query.getResultList();
		}
		
		return result;
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
