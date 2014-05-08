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
import com.bitjester.apps.md.entities.Patient;

@Named
@ViewScoped
public class ViewPacients implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private BookKeeper bk;

	@Inject
	private EntityManager em;

	private Patient managedPatient;

	@Named
	@Produces
	@RequestScoped
	public List<String> getPacientStartLetters() throws Exception {
		String query = "SELECT DISTINCT UPPER(SUBSTRING(p.lname,1,1)) AS letter";
		query += " FROM Patient p ORDER BY letter";
		return em.createQuery(query, String.class).getResultList();
	}

	public List<Patient> getPacients(String letter) {
		String query = "FROM Patient p WHERE SUBSTRING(p.lname,1,1) ='" + letter + "'";
		query += " ORDER BY p.lname, p.fname";
		return em.createQuery(query, Patient.class).getResultList();
	}

	@Named
	@Produces
	public List<Patient> getPacients() {
		String query = "FROM Patient p ORDER BY p.lname, p.fname";
		return em.createQuery(query, Patient.class).getResultList();
	}

	@Named
	@Produces
	public Patient getmanagedPatient() {
		return managedPatient;
	}

	public void setmanagedPatient(Patient managedPatient) {
		this.managedPatient = managedPatient;
	}

	public void load(Long id) {
		managedPatient = em.find(Patient.class, id);
	}

	public void newInstance() {
		managedPatient = new Patient();
	}

	public void refresh() {
		managedPatient = null;
	}

	public void remove(Long id) throws Exception {
		Patient pacient = em.find(Patient.class, id);
		bk.remove(pacient);

	}

	public void store() throws Exception {
		bk.store(managedPatient);
		managedPatient = null;
	}

}
