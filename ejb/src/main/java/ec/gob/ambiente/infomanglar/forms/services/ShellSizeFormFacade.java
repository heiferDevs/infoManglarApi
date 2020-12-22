package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.util.Utility;

@Stateless
public class ShellSizeFormFacade extends AbstractFacade<ShellSizeForm, Integer> {

	public ShellSizeFormFacade() {
		super(ShellSizeForm.class, Integer.class);		
	}
	
	public boolean save(ShellSizeForm form)
	{
		try
		{						
			if (form.getShellSizeFormId() == null) {
				form.setCreatedAt(Utility.getDate(form.getSamplingDate()));
				form.setFormStatus(true);
				create(form);
			} else {
				ShellSizeForm old = find(form.getShellSizeFormId());
				form.setCreatedAt(Utility.getDate(form.getSamplingDate()));
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
	public List<ShellSizeForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<ShellSizeForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ShellSizeForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShellSizeForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<ShellSizeForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ShellSizeForm>) query.getResultList();
		}
		
		return result;
	}
	
	public ShellSizeForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from ShellSizeForm m where m.organizationManglarId = :orgId ORDER BY m.shellSizeFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (ShellSizeForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ShellSizeForm> getByOrgAndDates(Integer orgId, Date start, Date end) {
		Query query = super.getEntityManager().createQuery("select m from ShellSizeForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.shellSizeFormId DESC");
		query.setParameter("orgId", orgId);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<ShellSizeForm>) query.getResultList();
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
