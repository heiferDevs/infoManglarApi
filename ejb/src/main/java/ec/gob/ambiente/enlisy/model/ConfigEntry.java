package ec.gob.ambiente.enlisy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="config_entries", schema="suia_configuration")
public class ConfigEntry implements Serializable {

	private static final long serialVersionUID = -6910615407773396263L;

	@Id	
	@Column(name="coen_key")
	private String key;

	@Column(name="coen_active")
	private boolean active = true;

	@Column(name="coen_value")
	private String value;
	
	public ConfigEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public ConfigEntry() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}