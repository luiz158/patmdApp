package com.bitjester.apps.md.views;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.login.AppSession;
import com.bitjester.apps.common.utils.FacesUtil;
import com.bitjester.apps.md.entities.Biopsy;

@Named
@ViewScoped
public class ViewSearch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private AppSession userSession;

	@Inject
	private EntityManager em;

	@Produces
	@RequestScoped
	public List<Biopsy> getBiopsies() {
		String query = "FROM Biopsy b";

		// Return only biopsies for this Doctor/user.
		if (!userSession.getActiveUser().getActiveRole().equals("patho"))
			query += " WHERE b.doctor.id = " + userSession.getActiveUser().getId();

		query += " ORDER BY b.examDate DESC, b.pacient.lname";
		return em.createQuery(query, Biopsy.class).getResultList();
	}

	public String buildFiter() {
		String filter = "";
		
		// Check and build filter

		if (filter.length() > 0) {
			if (userSession.getActiveUser().getActiveRole().equals("patho"))
				filter = "WHERE " + filter;
			else
				filter = "AND " + filter;
		}
		return filter;
	}

	public void load(Long id) {
		FacesUtil.getFlash().put("biopsy", id);
		FacesUtil.navTo("forms/biopsy.xhtml");
	}
}
