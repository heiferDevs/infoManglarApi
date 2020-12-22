package ec.gob.ambiente.infomanglar.forms.services;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.ConfigForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;

@Stateless
public class ConfigFormFacade extends AbstractFacade<ConfigForm, Integer> {

	public ConfigFormFacade() {
		super(ConfigForm.class, Integer.class);		
	}
	
	public boolean save(ConfigForm form)
	{
		try
		{						
			if (form.getConfigFormId() == null) {
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

	public ConfigForm getLast() {
		try {
			Query query = super.getEntityManager().createQuery("select m from ConfigForm m");
			query.setMaxResults(1);
			return (ConfigForm) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
