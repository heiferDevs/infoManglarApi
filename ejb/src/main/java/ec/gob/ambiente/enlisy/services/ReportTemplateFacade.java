package ec.gob.ambiente.enlisy.services;

import java.io.Serializable;

import javax.ejb.Stateless;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.enlisy.model.ReportTemplate;

@Stateless
public class ReportTemplateFacade extends AbstractFacade<ReportTemplate, Integer> implements Serializable{

	private static final long serialVersionUID = 1164242243166182670L;
	
	public ReportTemplateFacade() {
		super(ReportTemplate.class, Integer.class);
	}



}
