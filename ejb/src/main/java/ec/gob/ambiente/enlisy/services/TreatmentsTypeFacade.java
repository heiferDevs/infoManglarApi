package ec.gob.ambiente.enlisy.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.TreatmentsType;
import ec.gob.ambiente.exceptions.ServiceException;

@Stateless
public class TreatmentsTypeFacade extends AbstractFacade<TreatmentsType, Integer>{

	public TreatmentsTypeFacade() {
		super(TreatmentsType.class, Integer.class);		
	}
	
	/**
	 * Buscar por status 
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
    public List<TreatmentsType> findByStatus(boolean status) throws ServiceException {
        List<TreatmentsType> treatmentsTypes = null;
        try {
            Query query = super.getEntityManager().createQuery("select o from TreatmentsType o where o.trtyStatus = :status ");
            query.setParameter("status", status);
            
            treatmentsTypes = (List<TreatmentsType>) query.getResultList();        
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
        return treatmentsTypes;
    }

}
