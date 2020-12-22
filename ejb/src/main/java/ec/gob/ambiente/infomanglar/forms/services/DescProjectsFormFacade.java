package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.DeforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;

@Stateless
public class DescProjectsFormFacade extends AbstractFacade<DescProjectsForm, Integer> {

	public DescProjectsFormFacade() {
		super(DescProjectsForm.class, Integer.class);		
	}
	
	public boolean save(DescProjectsForm form)
	{
		try
		{						
			if (form.getDescProjectsFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				DescProjectsForm old = find(form.getDescProjectsFormId());
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
	public List<DescProjectsForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<DescProjectsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<DescProjectsForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DescProjectsForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<DescProjectsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<DescProjectsForm>) query.getResultList();
		}
		
		return result;
	}
	
	public DescProjectsForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from DescProjectsForm m where m.organizationManglarId = :orgId ORDER BY m.descProjectsFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (DescProjectsForm) query.getSingleResult();
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
