package com.bitjester.apps.md.views;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.utils.BookKeeper;
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
	public List<Doctor> getDoctors() {
		String query = "FROM Doctor d ORDER BY d.lname, d.fname";
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

	public void remove(Long id) throws Exception {
		Doctor doctor = em.find(Doctor.class, id);
		bk.remove(doctor);

	}

	public void store() throws Exception {
		bk.store(managedDoctor);
		managedDoctor = null;
	}
}
