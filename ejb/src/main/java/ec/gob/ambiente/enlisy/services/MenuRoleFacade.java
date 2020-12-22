package ec.gob.ambiente.enlisy.services;

import java.io.Serializable;

import javax.ejb.Stateless;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.MenuRole;

@Stateless
public class MenuRoleFacade extends AbstractFacade<MenuRole, Integer> implements Serializable {

	private static final long serialVersionUID = -1397835309701774809L;

	public MenuRoleFacade() {
		super(MenuRole.class, Integer.class);		
	}	
}