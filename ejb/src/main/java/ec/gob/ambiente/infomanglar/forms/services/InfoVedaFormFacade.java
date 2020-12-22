package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.InfoVedaForm;
import ec.gob.ambiente.util.Utility;

@Stateless
public class InfoVedaFormFacade extends AbstractFacade<InfoVedaForm, Integer> {

	public InfoVedaFormFacade() {
		super(InfoVedaForm.class, Integer.class);		
	}
	
	public boolean save(InfoVedaForm form)
	{
		try
		{						
			if (form.getInfoVedaFormId() == null) {
				form.setCreatedAt(Utility.getDate(form.getInformationGathering()));
				form.setFormStatus(true);
				create(form);
			} else {
				InfoVedaForm old = find(form.getInfoVedaFormId());
				form.setCreatedAt(Utility.getDate(form.getInformationGathering()));
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
	public List<InfoVedaForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<InfoVedaForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<InfoVedaForm>) query.getResultList();
		}
		
		return result;
	}

	/**
	 * Buscar por type
	 * @param organizationManglarId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InfoVedaForm> findByType(String type) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.formType = :type");
		query.setParameter("type", type);
		
		List<InfoVedaForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<InfoVedaForm>) query.getResultList();
		}
		
		return result;
	}
	

	public InfoVedaForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from InfoVedaForm m where m.organizationManglarId = :orgId ORDER BY m.infoVedaFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (InfoVedaForm) query.getSingleResult();
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
