package ec.gob.ambiente.configuration.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

import ec.gob.ambiente.enlisy.services.ConfigurationFacade;
import ec.gob.ambiente.enlisy.model.ConfigEntry;

@ApplicationScoped
public class ConfigurationBean {

	public static final String KEY_VALUES_SEPARATOR = ";";
	public static final String KEY_OVERRIDE_INDICATOR = "OVERRIDE.";

	private Map<String, String> configurations;

	@EJB
	private ConfigurationFacade configurationDao;

	public String getConfigurationValue(String key) {
		Map<String, String> config = loadConfigurations();
		if (config != null && config.containsKey(key))
			return config.get(key);
		return null;
	}

	public boolean containsIntegerConfigurationValue(String key, Integer value) {
		return false;
	}

	/**
	 * Reseatea las configuraciones del sistema
	 */
	public void resetConfigurations() {
		configurations = null;
		loadConfigurations();
	}

	/**
	 * Carga las configuraciones para el funcionamiento del aplicativo
	 * @return Map<String, String>
	 */
	private Map<String, String> loadConfigurations() {
		if (configurations == null) {
			configurations = new HashMap<String, String>();
			try {
				List<ConfigEntry> entries = configurationDao.getConfigEntries();
				for (ConfigEntry configEntry : entries) {
					configurations.put(configEntry.getKey(), configEntry.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return configurations;
	}
}
