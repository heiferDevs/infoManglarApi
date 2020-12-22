package ec.gob.ambiente.infomanglar.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.model.EmailNotification;

@Stateless
public class EmailNotificationFacade extends AbstractFacade<EmailNotification, Integer> {

	public EmailNotificationFacade() {
		super(EmailNotification.class, Integer.class);		
	}
	
	public boolean save(EmailNotification emailNotification)
	{
		try
		{						
			if (emailNotification.getEmailNotificationId() == null) {
				create(emailNotification);
			} else {
				edit(emailNotification);
			}
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}


	/**
	 * Buscar por province and anomalyType
	 * @param province
	 * @param anomalyType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EmailNotification> findByProvinceAndAnomalyForm(String province, String anomalyType) {
		
		String q = "select o from EmailNotification o where "
				+ "(o.emailNotificationProvinces LIKE :province or o.emailNotificationProvinces = 'ALL') and "
				+ "(o.emailNotificationAnomaliesTypes LIKE :anomalyType or o.emailNotificationAnomaliesTypes = 'ALL')";

		Query query = super.getEntityManager().createQuery(q);
		query.setParameter("province", "%"+province+"%");
		query.setParameter("anomalyType", "%"+anomalyType+"%");

		List<EmailNotification> result = new ArrayList<>();
		
		if(query.getResultList().size() > 0)
		{
			result = (List<EmailNotification>) query.getResultList();
		}
		
		return result;
	}

}
