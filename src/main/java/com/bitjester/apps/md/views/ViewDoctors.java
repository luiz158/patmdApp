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
import com.bitjester.apps.common.utils.FacesUtil;
import com.bitjester.apps.md.entities.Doctor;

@Named
@ViewScoped
public class ViewDoctors implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private BookKeeper bk;

	@Inject
	private EntityManager em;

	private Doctor managedDoctor;

	// ================================
	// ======= Doctor Methods =========
	// ================================

	@Named
	@Produces
	@RequestScoped
	public List<String> getDoctorStartLetters() throws Exception {
		String query = "SELECT DISTINCT SUBSTRING(lname,1,1) AS letter FROM Doctor";
		query += " ORDER BY letter";
		return em.createQuery(query, String.class).getResultList();
	}

	@RequestScoped
	public List<Doctor> doctorsForLetter(String letter) {
		String query = "FROM Doctor WHERE lname LIKE '" + letter + "%'";
		query += " ORDER BY lname, fname";
		return em.createQuery(query, Doctor.class).getResultList();
	}

	@Named
	@Produces
	public List<Doctor> getDoctors() {
		String query = "FROM Doctor ORDER BY lname, fname";
		return em.createQuery(query, Doctor.class).getResultList();
	}

	@Named
	@Produces
	public Doctor getManagedDoctor() {
		return managedDoctor;
	}

	public void setManagedDoctor(Doctor managedDoctor) {
		this.managedDoctor = managedDoctor;
	}

	public void load(Long id) {
		managedDoctor = em.find(Doctor.class, id);
	}

	public void add() {
		managedDoctor = new Doctor();
	}

	public void refresh() {
		managedDoctor = null;
	}

	public void remove(Long id) {
		try{
			bk.remove(em.find(Doctor.class, id));
		} catch (Exception e) {
			FacesUtil.addMessage("Error ocurred, please reload page and try again.");
			e.printStackTrace();
		}
	}

	public void store() throws Exception {
		bk.store(managedDoctor);
		managedDoctor = null;
	}
}
