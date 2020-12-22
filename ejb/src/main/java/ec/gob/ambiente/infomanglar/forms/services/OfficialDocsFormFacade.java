package ec.gob.ambiente.infomanglar.forms.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;

@Stateless
public class OfficialDocsFormFacade extends AbstractFacade<OfficialDocsForm, Integer> {

	public OfficialDocsFormFacade() {
		super(OfficialDocsForm.class, Integer.class);		
	}
	
	public boolean save(OfficialDocsForm form)
	{
		try
		{						
			if (form.getOfficialDocsFormId() == null) {
				form.setCreatedAt(new Date());
				form.setFormStatus(true);
				create(form);
			} else {
				OfficialDocsForm old = find(form.getOfficialDocsFormId());
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
	public List<OfficialDocsForm> getByUserId(Integer userId) {
		
		Query query = super.getEntityManager().createQuery("select o from AnomalyForm o where o.userId = :userId");
		query.setParameter("userId", userId);
		
		List<OfficialDocsForm> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<OfficialDocsForm>) query.getResultList();
		}
		
		return result;
	}

	public OfficialDocsForm getLastByOrg(Integer orgId) {
		try {
			Query query = super.getEntityManager().createQuery("select m from OfficialDocsForm m where m.organizationManglarId = :orgId ORDER BY m.officialDocsFormId DESC");
			query.setParameter("orgId", orgId);
			query.setMaxResults(1);
			return (OfficialDocsForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
