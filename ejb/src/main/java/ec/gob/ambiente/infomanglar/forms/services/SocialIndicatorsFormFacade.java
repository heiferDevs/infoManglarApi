package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.SocialIndicatorsForm;

@Stateless
public class SocialIndicatorsFormFacade extends AbstractFacade<SocialIndicatorsForm, Integer> {

	public SocialIndicatorsFormFacade() {
		super(SocialIndicatorsForm.class, Integer.class);		
	}
	
	public boolean save(SocialIndicatorsForm form)
	{
		try
		{						
			if (form.getSocialIndicatorsFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				SocialIndicatorsForm old = find(form.getSocialIndicatorsFormId());
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
	public List<SocialIndicatorsForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<SocialIndicatorsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<SocialIndicatorsForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SocialIndicatorsForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<SocialIndicatorsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<SocialIndicatorsForm>) query.getResultList();
		}
		
		return result;
	}
	
	public SocialIndicatorsForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from SocialIndicatorsForm m where m.organizationManglarId = :orgId ORDER BY m.socialIndicatorsFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (SocialIndicatorsForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public SocialIndicatorsForm getLastByOrgAndUser(Integer orgId, Integer userId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from SocialIndicatorsForm m where m.organizationManglarId = :orgId and m.userId = :userId ORDER BY m.socialIndicatorsFormId DESC");
			query.setParameter("orgId", orgId);
			query.setParameter("userId", userId);
			query.setMaxResults(1);
			return (SocialIndicatorsForm) query.getSingleResult();
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
