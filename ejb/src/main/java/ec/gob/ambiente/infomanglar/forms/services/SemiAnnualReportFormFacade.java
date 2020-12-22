package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.SemiAnnualReportForm;

@Stateless
public class SemiAnnualReportFormFacade extends AbstractFacade<SemiAnnualReportForm, Integer> {

	public SemiAnnualReportFormFacade() {
		super(SemiAnnualReportForm.class, Integer.class);		
	}
	
	public boolean save(SemiAnnualReportForm form)
	{
		try
		{						
			if (form.getSemiAnnualReportFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				SemiAnnualReportForm old = find(form.getSemiAnnualReportFormId());
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
	public List<SemiAnnualReportForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<SemiAnnualReportForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<SemiAnnualReportForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SemiAnnualReportForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<SemiAnnualReportForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<SemiAnnualReportForm>) query.getResultList();
		}
		
		return result;
	}

	public SemiAnnualReportForm getLastByOrg(Integer orgName) {
		try {
			Query query = super.getEntityManager().createQuery("select m from SemiAnnualReportForm m where m.organizationManglarId = :orgName ORDER BY m.semiAnnualReportFormId DESC");
			query.setParameter("orgName", orgName);
			query.setMaxResults(1);
			return (SemiAnnualReportForm) query.getSingleResult();
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

	public List<SemiAnnualReportForm> findByCustom(String form, String currentForm, String all, String formType, String userId, String orgName, String startTs, String endTs) {
		if (!all.equals(formType) && !currentForm.equals(formType)){
			return new ArrayList<SemiAnnualReportForm>();
		}
		return findByCustom(form, all, userId, orgName, startTs, endTs);
	}

	@SuppressWarnings("unchecked")
	public List<SemiAnnualReportForm> findByCustom(String form, String all, String userId, String orgName, String startTs, String endTs) {
		
		if (all.equals(userId) && all.equals(orgName) && all.equals(startTs) && all.equals(endTs)) {
			return findAll();
		}
		String q = "select f from " + form + " f where ";
		List<String> queries = new ArrayList<>();
		if (!all.equals(userId)) queries.add("f.userId = :userId");
		if (!all.equals(orgName)) queries.add("f.organizationName = :orgName");
		if (!all.equals(startTs)) {
			queries.add("f.createdAt BETWEEN :startDate AND :endDate");
		}		
		q += AbstractFacade.join(queries, " AND ");
		try {
			Query query = getEntityManager().createQuery(q);
			if (!all.equals(userId))query.setParameter("userId", new Integer(userId));
			if (!all.equals(orgName)) query.setParameter("orgName", orgName);
			if (!all.equals(startTs)) {
				Date startDate = new Date(new Long(startTs));
				Date endDate = new Date();
				if (!all.equals(endTs)) {
					endDate = new Date(new Long(endTs));
				}
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
			}
			return (List<SemiAnnualReportForm>) query.getResultList();
		} catch (Exception e) {
			// PREVENT ANY ERROR ON CAST TO INTEGER
		}
		return new ArrayList<SemiAnnualReportForm>();
	}

}
