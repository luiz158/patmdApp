package com.bitjester.apps.md.utils;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.md.entities.ADICAP;

@Named
public class AdicapUtils implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Named
	@Produces
	@RequestScoped
	public List<ADICAP> getAdicaps() throws Exception {
		String query = "FROM ADICAP a ORDER BY a.description";
		return em.createQuery(query, ADICAP.class).getResultList();
	}
}
