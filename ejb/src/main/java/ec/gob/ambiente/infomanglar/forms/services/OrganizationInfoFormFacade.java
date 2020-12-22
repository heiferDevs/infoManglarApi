package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.OrganizationInfoForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;

@Stateless
public class OrganizationInfoFormFacade extends AbstractFacade<OrganizationInfoForm, Integer> {

	public OrganizationInfoFormFacade() {
		super(OrganizationInfoForm.class, Integer.class);		
	}
	
	public boolean save(OrganizationInfoForm form)
	{
		try
		{						
			if (form.getOrganizationInfoFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				OrganizationInfoForm old = find(form.getOrganizationInfoFormId());
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
	public List<OrganizationInfoForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from OrganizationInfoForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<OrganizationInfoForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<OrganizationInfoForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationInfoForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from OrganizationInfoForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<OrganizationInfoForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<OrganizationInfoForm>) query.getResultList();
		}
		
		return result;
	}

	public OrganizationInfoForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from OrganizationInfoForm m where m.organizationManglarId = :orgId ORDER BY m.organizationInfoFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (OrganizationInfoForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public OrganizationInfoForm getLastByOrg(Integer orgId, Date start, Date end) {
		try {
			Query query = super.getEntityManager().createQuery("select m from OrganizationInfoForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.organizationInfoFormId DESC");
			query.setParameter("orgId", orgId);
			query.setParameter("start", start);
			query.setParameter("end", end);
			query.setMaxResults(1);
			return (OrganizationInfoForm) query.getSingleResult();
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
		Query query = super.getEntityManager().createQuery("select o.formType, count(*) from OrganizationInfoForm o group by o.formType");
		return query.getResultList();
	}

}
