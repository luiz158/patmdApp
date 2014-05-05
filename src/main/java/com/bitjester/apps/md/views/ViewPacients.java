package com.bitjester.apps.md.views;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.utils.BookKeeper;
import com.bitjester.apps.md.entities.Pacient;

@Named
@ViewScoped
public class ViewPacients implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private BookKeeper bk;

	@Inject
	private EntityManager em;

	@Named
	@Produces
	@RequestScoped
	public List<String> getPacientStartLetters() throws Exception {
		String query = "SELECT DISTINCT UPPER(SUBSTRING(p.lname,1,1)) AS letter";
		query += " FROM Pacient p ORDER BY letter";
		return em.createQuery(query, String.class).getResultList();
	}

	public List<Pacient> getPacients(String letter) {
		String query = "FROM Pacient p WHERE SUBSTRING(p.lname,1,1) ='" + letter + "'";
		query += " ORDER BY p.lname, p.fname";
		return em.createQuery(query, Pacient.class).getResultList();
	}

}
