package com.bitjester.apps.md.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bitjester.apps.common.BaseEntity;

@Cacheable
@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String fname;
	private String lname;
	private String specialty;

	@Transient
	public String getFullName() {
		return fname + " " + lname;
	}

	// --- Getters & Setters
	// --- Accessor methods

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
}
