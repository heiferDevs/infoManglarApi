package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.DeforestationForm;

@Stateless
public class DeforestationFormFacade extends AbstractFacade<DeforestationForm, Integer> {

	public DeforestationFormFacade() {
		super(DeforestationForm.class, Integer.class);		
	}
	
	public boolean save(DeforestationForm form)
	{
		try
		{						
			if (form.getDeforestationFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				DeforestationForm old = find(form.getDeforestationFormId());
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
	public List<DeforestationForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<DeforestationForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<DeforestationForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DeforestationForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<DeforestationForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<DeforestationForm>) query.getResultList();
		}
		
		return result;
	}
	
	
	public DeforestationForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from DeforestationForm m where m.organizationManglarId = :orgId ORDER BY m.deforestationFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (DeforestationForm) query.getSingleResult();
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
