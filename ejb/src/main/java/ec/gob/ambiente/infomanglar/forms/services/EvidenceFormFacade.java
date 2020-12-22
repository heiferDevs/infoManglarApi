package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;

@Stateless
public class EvidenceFormFacade extends AbstractFacade<EvidenceForm, Integer> {

	public EvidenceFormFacade() {
		super(EvidenceForm.class, Integer.class);		
	}
	
	public boolean save(EvidenceForm form)
	{
		try
		{						
			if (form.getEvidenceFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				EvidenceForm old = find(form.getEvidenceFormId());
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
	public List<EvidenceForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<EvidenceForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<EvidenceForm>) query.getResultList();
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<EvidenceForm> getByOrgAndDates(Integer orgId, Date start, Date end) {
		Query query = super.getEntityManager().createQuery("select m from EvidenceForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.evidenceFormId DESC");
		query.setParameter("orgId", orgId);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<EvidenceForm>) query.getResultList();
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EvidenceForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<EvidenceForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<EvidenceForm>) query.getResultList();
		}
		
		return result;
	}

	public EvidenceForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from EvidenceForm m where m.organizationManglarId = :orgId ORDER BY m.evidenceFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (EvidenceForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public EvidenceForm getLastByOrgAndUser(Integer orgId, Integer userId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from EvidenceForm m where m.organizationManglarId = :orgId and m.userId = :userId ORDER BY m.evidenceFormId DESC");
			query.setParameter("orgId", orgId);
			query.setParameter("userId", userId);
			query.setMaxResults(1);
			return (EvidenceForm) query.getSingleResult();
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
