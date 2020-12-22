package ec.gob.ambiente.enlisy.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.ContactsForm;
import ec.gob.ambiente.exceptions.ServiceException;

@Stateless
public class ContactsFormFacade extends AbstractFacade<ContactsForm, Integer> {

	public ContactsFormFacade() {
		super(ContactsForm.class, Integer.class);		
	}
	
	/**
	 * Buscar por ID
	 * @param name
	 * @return
	 */
	public ContactsForm findById(Integer cofoId) {
		
		try
		{
			Query query = super.getEntityManager()
					.createQuery(
							"SELECT o FROM ContactsForm o WHERE o.cofoStatus = true and o.cofoId=:cofoId")
					.setParameter("cofoId", cofoId);
			return (ContactsForm) query.getSingleResult();
		}catch(NoResultException e)
		{
			return null;
		}
		
		
	}

	/**
	 * Buscar por nombre
	 * @param name
	 * @return
	 */
	public ContactsForm findByName(String name) {
		Query query = super.getEntityManager()
				.createQuery(
						"SELECT o FROM ContactsForm o WHERE o.cofoName =:name AND o.cofoStatus = true ORDER BY o.cofoOrder")
				.setParameter("name", name);
		List<ContactsForm> contactsForms = (List<ContactsForm>) query.getResultList();
		ContactsForm contactForm = new ContactsForm();
		
		if(contactsForms.size() > 0)
		{
			contactForm = contactsForms.get(0);
		}
		
		return contactForm;
	}
	
	/**
	 * Buscar por estado
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<ContactsForm> findByStatus(boolean status)
			throws ServiceException {
		List<ContactsForm> contactsForms = null;
		try {
			
			Query query = super.getEntityManager().createQuery("select o from ContactsForm o where o.cofoStatus = :status order by o.cofoOrder");
			query.setParameter("status", status);
			contactsForms = (List<ContactsForm>) query.getResultList();
			
		} catch (RuntimeException e) {
			throw new ServiceException(e);
		}
		
		return contactsForms;
	}
}