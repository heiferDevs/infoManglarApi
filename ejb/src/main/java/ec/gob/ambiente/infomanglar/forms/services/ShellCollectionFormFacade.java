package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.ReforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.util.Utility;

@Stateless
public class ShellCollectionFormFacade extends AbstractFacade<ShellCollectionForm, Integer> {

	public ShellCollectionFormFacade() {
		super(ShellCollectionForm.class, Integer.class);		
	}
	
	public boolean save(ShellCollectionForm form)
	{
		try
		{						
			if (form.getShellCollectionFormId() == null) {
				form.setCreatedAt(Utility.getDate(form.getCollectorDate()));
				form.setFormStatus(true);
				create(form);
			} else {
				ShellCollectionForm old = find(form.getShellCollectionFormId());
				form.setCreatedAt(Utility.getDate(form.getCollectorDate()));
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
	public List<ShellCollectionForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<ShellCollectionForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ShellCollectionForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShellCollectionForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<ShellCollectionForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<ShellCollectionForm>) query.getResultList();
		}
		
		return result;
	}

	public ShellCollectionForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from ShellCollectionForm m where m.organizationManglarId = :orgId ORDER BY m.shellCollectionFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (ShellCollectionForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public ShellCollectionForm getLastByOrgAndUser(Integer orgId, Integer userId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from ShellCollectionForm m where m.organizationManglarId = :orgId and m.userId = :userId ORDER BY m.shellCollectionFormId DESC");
			query.setParameter("orgId", orgId);
			query.setParameter("userId", userId);
			query.setMaxResults(1);
			return (ShellCollectionForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ShellCollectionForm> getByOrgAndDates(Integer orgId, Date start, Date end) {
		Query query = super.getEntityManager().createQuery("select m from ShellCollectionForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.shellCollectionFormId DESC");
		query.setParameter("orgId", orgId);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<ShellCollectionForm>) query.getResultList();
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
