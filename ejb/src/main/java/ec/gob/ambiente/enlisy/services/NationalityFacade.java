package ec.gob.ambiente.enlisy.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.Nationality;
import ec.gob.ambiente.exceptions.ServiceException;

@Stateless
public class NationalityFacade extends AbstractFacade<Nationality, Integer> 
{
	
	public NationalityFacade() {
		super(Nationality.class, Integer.class);		
	}

	/**
	 * Buscar por Id
	 * @param natiId
	 * @return
	 */
	public Nationality findById(Integer natiId)
	{
		Query query = super.getEntityManager().createQuery("select o from Nationality o where o.natiId = :natiId and o.natiStatus = true");
		query.setParameter("natiId", natiId);
		
		Nationality nationality = new Nationality();
		
		if(query.getResultList().size() > 0)
		{
			nationality = (Nationality) query.getResultList().get(0);
		}
		
		return nationality;
	}
	
	/**
	 * Buscar Nacionalidades (Activas)
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Nationality> findByStatus(boolean status)
			throws ServiceException {
		List<Nationality> nationalities = null;
		try {
			Query query = super.getEntityManager().createQuery("select o from Nationality o where o.natiStatus = :status order by o.natiDescription");
			query.setParameter("status", status);
			
			nationalities = (List<Nationality>) query.getResultList();

		} catch (RuntimeException e) {
			throw new ServiceException(e);
		}
		return nationalities;
	}
	
	/**
	 * Buscar Nacionalidad por Descripcion
	 * @param description
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Nationality findByDescription(String description)throws ServiceException {
		description=description.toUpperCase();
		try {
			Query query = super.getEntityManager().createQuery("select o from Nationality o where o.natiStatus = true and upper(o.natiDescription) =:description");
			query.setParameter("description", description);
			
			return (Nationality)query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}		
	}
}