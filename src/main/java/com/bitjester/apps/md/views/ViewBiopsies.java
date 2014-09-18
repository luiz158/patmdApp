package com.bitjester.apps.md.views;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.login.AppSession;
import com.bitjester.apps.common.utils.BookKeeper;
import com.bitjester.apps.common.utils.FacesUtil;
import com.bitjester.apps.md.entities.Biopsy;

@Named
@ViewScoped
public class ViewBiopsies implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private BookKeeper bk;

	@Inject
	private AppSession userSession;

	@Inject
	private EntityManager em;

	// ================================
	// ======= Biopsy Methods =========
	// ================================

	@Named
	@Produces
	public List<Biopsy> getBiopsies() {
		String query = "FROM Biopsy b";

		// Return only biopsies for this Doctor/user.
		if (!userSession.getActiveUser().getActiveRole().equals("patho"))
			query += " WHERE b.doctor.id = " + userSession.getActiveUser().getId();

		query += " ORDER BY b.examDate DESC, b.pacient.lname";
		return em.createQuery(query, Biopsy.class).getResultList();
	}

	public void load(Long id) {
		FacesUtil.getFlash().put("biopsy", id);
		FacesUtil.navTo("forms/biopsy.xhtml");
	}

	public void remove(Long id) {
		try {
			bk.remove(em.find(Biopsy.class, id));
		} catch (Exception e) {
			FacesUtil.addMessage("Error ocurred, please reload page and try again.");
			e.printStackTrace();
		}
	}
}
