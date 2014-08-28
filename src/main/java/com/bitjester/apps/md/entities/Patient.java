package com.bitjester.apps.md.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bitjester.apps.common.BaseEntity;
import com.bitjester.apps.md.entities.enums.EnumSex;

@Entity
@Table(name = "pacients")
public class Patient extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@OneToOne
	@PrimaryKeyJoinColumn(name = "biopsy")
	private Biopsy biopsy;
	private String fname;
	private String lname;
	private Integer age;
	@Enumerated(EnumType.STRING)
	private EnumSex sex = EnumSex.MALE;

	@Transient
	public String getFullName() {
		return fname + " " + lname;
	}

	// --- Getters & Setters

	public Biopsy getBiopsy() {
		return biopsy;
	}

	public void setBiopsy(Biopsy biopsy) {
		this.biopsy = biopsy;
	}

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public EnumSex getSex() {
		return sex;
	}

	public void setSex(EnumSex sex) {
		this.sex = sex;
	}
}
