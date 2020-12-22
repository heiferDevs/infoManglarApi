package ec.gob.ambiente.enlisy.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.ConfigEntry;

@Stateless
public class ConfigurationFacade extends AbstractFacade<ConfigEntry, Integer> {

	public ConfigurationFacade() {
		super(ConfigEntry.class, Integer.class);		
	}

	/**
	 * Obtiene un listado de configuraciones que pertenecen a recursos genéticos
	 * @return List<ConfigEntry>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ConfigEntry> getConfigEntries() throws Exception {
		
		TypedQuery<ConfigEntry> query = super.getEntityManager().createQuery("select c from ConfigEntry c where c.active = true", ConfigEntry.class);
		
		return (List<ConfigEntry>) query.getResultList();
	}
}