package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import ec.gob.ambiente.infomanglar.forms.model.MappingForm;

@Stateless
public class MappingFormFacade extends AbstractFacade<MappingForm, Integer> {

	public MappingFormFacade() {
		super(MappingForm.class, Integer.class);		
	}
	
	public boolean save(MappingForm form)
	{
		try
		{						
			if (form.getMappingFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				MappingForm old = find(form.getMappingFormId());
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
	public List<MappingForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<MappingForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<MappingForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MappingForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<MappingForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<MappingForm>) query.getResultList();
		}
		
		return result;
	}
	
	public MappingForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from MappingForm m where m.organizationManglarId = :orgId ORDER BY m.mappingFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (MappingForm) query.getSingleResult();
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
