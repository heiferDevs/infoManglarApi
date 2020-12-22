package ec.gob.ambiente.infomanglar.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.model.AllowedUser;
import ec.gob.ambiente.infomanglar.model.HistoryChange;

@Stateless
public class HistoryChangeFacade extends AbstractFacade<HistoryChange, Integer> {

	public HistoryChangeFacade() {
		super(HistoryChange.class, Integer.class);		
	}
	
	public boolean save(HistoryChange form)
	{
		try
		{						
			if (form.getId() == null) {
				form.setDate(new Date());
				form.setHistoryChangeStatus(true);
				create(form);
			} else {
				edit(form);
			}
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<HistoryChange> findByForm(String formType, Integer formId)
	{
		Query query = super.getEntityManager().createQuery("select a from HistoryChange a where a.formType = :formType and a.formId = :formId and a.historyChangeStatus = true");
		query.setParameter("formType", formType);		
		query.setParameter("formId", formId);		
		return (List<HistoryChange>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<HistoryChange> findByForm(String formType, Integer formId, Date start, Date end)
	{
		Query query = super.getEntityManager().createQuery("select a from HistoryChange a where a.formType = :formType and a.formId = :formId and a.historyChangeStatus = true and a.date BETWEEN :start AND :end ");
		query.setParameter("formType", formType);		
		query.setParameter("formId", formId);		
		query.setParameter("start", start);
		query.setParameter("end", end);
		return (List<HistoryChange>) query.getResultList();
	}

}
