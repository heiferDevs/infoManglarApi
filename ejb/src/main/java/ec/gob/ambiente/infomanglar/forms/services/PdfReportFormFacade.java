package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.PdfReportForm;

@Stateless
public class PdfReportFormFacade extends AbstractFacade<PdfReportForm, Integer> {

	public PdfReportFormFacade() {
		super(PdfReportForm.class, Integer.class);		
	}
	
	public boolean save(PdfReportForm form)
	{
		try
		{						
			if (form.getPdfReportFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				PdfReportForm old = find(form.getPdfReportFormId());
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
	public List<PdfReportForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from PdfReportForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<PdfReportForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PdfReportForm>) query.getResultList();
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PdfReportForm> getByOrg(Integer orgId) {
		
		Query query = super.getEntityManager().createQuery("select o from PdfReportForm o where o.organizationManglarId = :orgId ORDER BY o.pdfReportFormId ASC");
		query.setParameter("orgId", orgId);
		
		List<PdfReportForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PdfReportForm>) query.getResultList();
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PdfReportForm> getByOrgPublished(Integer orgId) {
		
		Query query = super.getEntityManager().createQuery("select o from PdfReportForm o where o.organizationManglarId = :orgId and o.isPublished = true ORDER BY o.pdfReportFormId ASC");
		query.setParameter("orgId", orgId);
		
		List<PdfReportForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PdfReportForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PdfReportForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from PdfReportForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<PdfReportForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<PdfReportForm>) query.getResultList();
		}
		
		return result;
	}

	public PdfReportForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from PdfReportForm m where m.organizationManglarId = :orgId ORDER BY m.pdfReportFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (PdfReportForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PdfReportForm> getByOrgAndDates(Integer orgId, Date start, Date end) {
		Query query = super.getEntityManager().createQuery("select m from PdfReportForm m where m.organizationManglarId = :orgId AND m.createdAt BETWEEN :start AND :end ORDER BY m.pdfReportFormId DESC");
		query.setParameter("orgId", orgId);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<PdfReportForm>) query.getResultList();
	}

	/**
	 * group by type count
	 * @param organizationManglarId
	 * @return
	 */
	public List<?> summary() {
		Query query = super.getEntityManager().createQuery("select o.formType, count(*) from PdfReportForm o group by o.formType");
		return query.getResultList();
	}

}
