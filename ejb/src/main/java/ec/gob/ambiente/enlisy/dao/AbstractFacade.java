package ec.gob.ambiente.enlisy.dao;

/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;


/**
 * 
 * @author SUIA
 */
public abstract class AbstractFacade<T, E> {

	private Class<T> entityClass;
	private Class<E> primaryKeyClass;
  
	@PersistenceContext(unitName = "suiaPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public AbstractFacade(Class<T> entityClass, Class<E> primaryKeyClass) {
		this.entityClass = entityClass;
		this.primaryKeyClass = primaryKeyClass;
	}

	public T create(T entity) {
		getEntityManager().persist(entity);
		em.flush();
		return entity;
	}

	public T edit(T entity) {
		getEntityManager().merge(entity);
		em.flush();
		return entity;
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public T find(E id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}
	
	/**
	 * 
	 * <b> Ejecuta un namedQery con los parametros indicados en el mapa, en el que la clave del mapa es el nombre del
	 * parametro, los parametro de limites indican el rango del total de los registros que se necesitan. </b>
	 * 
	 * @author rene
	 * @version Revision: 1.0
	 *          <p>
	 *          [Autor: rene, Fecha: Oct 28, 2014]
	 *          </p>
	 * @param namedQueryName
	 *            nombre del namedQuery
	 * @param parameters
	 *            parametros del query
	 * @param limiteInicio
	 *            rango de inicio
	 * @param limiteFin
	 *            rango de fin
	 * @return resultado de la consulta
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCreateQueryPaginado(final String query_,
			final Map<String, Object> parameters, int limiteInicio, int limiteFin) {
		Query query = getEntityManager().createQuery(query_).setFirstResult(limiteInicio)
				.setMaxResults(limiteFin);
		if (parameters != null) {
			Set<Entry<String, Object>> parameterSet = parameters.entrySet();
			for (Entry<String, Object> entry : parameterSet) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getResultList();
	}
	
	//Obtener la fecha actual en java.util.Date
		public static Date nowDate(){
			
			// 1) create a java calendar instance
			java.util.Calendar calendar = Calendar.getInstance();
			
			// 2) get a java.util.Date from the calendar instance.
			//		    this date will represent the current instant, or "now".
			java.util.Date now = calendar.getTime();

			return now;
		} 
		
		//Obtener la fecha actual en java.sql.Timestamp
		public static Timestamp nowTimespan(){		
			java.util.Date now = nowDate();
			
			// a java current time (now) instance
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());		
			return currentTimestamp;		
		} 

		public static String join(List<?> list, String delim) {
		    int len = list.size();
		    if (len == 0)
		        return "";
		    StringBuilder sb = new StringBuilder(list.get(0).toString());
		    for (int i = 1; i < len; i++) {
		        sb.append(delim);
		        sb.append(list.get(i).toString());
		    }
		    return sb.toString();
		}

		public List<T> findBy(String form, String currentForm, String all, String formType, String userId, String orgId, String startTs, String endTs) {
			if (!all.equals(formType) && !currentForm.equals(formType)){
				return new ArrayList<T>();
			}
			return findBy(form, all, userId, orgId, startTs, endTs);
		}

		@SuppressWarnings("unchecked")
		public List<T> findBy(String form, String all, String userId, String orgId, String startTs, String endTs) {
			
			if (all.equals(userId) && all.equals(orgId) && all.equals(startTs) && all.equals(endTs)) {
				return findAll();
			}
			String q = "select f from " + form + " f where ";
			List<String> queries = new ArrayList<>();
			if (!all.equals(userId)) queries.add("f.userId = :userId");
			if (!all.equals(orgId)) queries.add("f.organizationManglarId = :orgId");
			if (!all.equals(startTs)) {
				queries.add("f.createdAt BETWEEN :startDate AND :endDate");
			}		
			q += AbstractFacade.join(queries, " AND ");
			try {
				Query query = getEntityManager().createQuery(q);
				if (!all.equals(userId))query.setParameter("userId", new Integer(userId));
				if (!all.equals(orgId)) query.setParameter("orgId", new Integer(orgId));
				if (!all.equals(startTs)) {
					Date startDate = new Date(new Long(startTs));
					Date endDate = new Date();
					if (!all.equals(endTs)) {
						endDate = new Date(new Long(endTs));
					}
					query.setParameter("startDate", startDate);
					query.setParameter("endDate", endDate);
				}
				return (List<T>) query.getResultList();
			} catch (Exception e) {
				// PREVENT ANY ERROR ON CAST TO INTEGER
			}
			return new ArrayList<T>();
		}

}	