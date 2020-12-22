package ec.gob.ambiente.enlisy.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.GeographicalLocation;

@Stateless
public class GeographicalLocationFacade extends AbstractFacade<GeographicalLocation, Integer> implements Serializable  {
	
	private static final long serialVersionUID = -1153093606324655752L;

	public GeographicalLocationFacade() {
		super(GeographicalLocation.class, Integer.class);		
	}
	
    /**
	 * Buscar por ID
	 * @param cataId id del catalogo que se esta buscando
	 * @return catalogo encontrada.
	 */
	public GeographicalLocation findById(Integer geloId) {
	    Query query = getEntityManager().createQuery("Select o from GeographicalLocation o where o.geloStatus =true and o.geloId =:geloId");
	    query.setParameter("geloId", geloId);	   
	
	    if (query.getResultList().size() == 1) {
	        return (GeographicalLocation) query.getResultList().get(0);
	    } else {
	        return null;
	    }

	}
	
	/**
	 * Buscar por padre ID
	 * @param geloParentId
	 * @return
	 */
	public List<GeographicalLocation> findByParentId(Integer geloParentId) {
		TypedQuery<GeographicalLocation> query = super.getEntityManager().createQuery("SELECT o FROM GeographicalLocation o WHERE o.geographicalLocation.geloId = :geloParentId and o.geloStatus = true order by o.geloName, o.geographicalLocation.geloName", GeographicalLocation.class);
		query.setParameter("geloParentId", geloParentId);
		return query.getResultList();
	}
	

	

}