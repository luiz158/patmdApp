package com.bitjester.apps.md.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bitjester.apps.common.BaseEntity;
import com.bitjester.apps.common.utils.HashUtil;

@Entity
@Table(name = "biopsies", uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
@SecondaryTable(name = "clinical_data", pkJoinColumns = @PrimaryKeyJoinColumn(name = "biopsy", referencedColumnName = "id"))
public class Biopsy extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Date reception;
	private String code;
	@Column(columnDefinition = "text")
	private String sample;

	@Column(table = "clinical_data")
	private Date examDate;
	@Column(table = "clinical_data", columnDefinition = "text")
	private String macroExam;
	@Column(table = "clinical_data", columnDefinition = "text")
	private String microExam;
	@Column(table = "clinical_data", columnDefinition = "text")
	private String diagnose;

	@ManyToOne
	// @JoinColumn(name="adicap")
	private ADICAP adicap;
	@ManyToOne
	// @JoinColumn(name="doctor")
	private Doctor doctor;
	@OneToOne(mappedBy = "biopsy", cascade = CascadeType.ALL)
	private Patient pacient;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "biopsy", orphanRemoval = true)
	@OrderColumn(name = "index")
	private List<Image> images;

	// --- Constructor

	public Biopsy() {
		super();
		examDate = new Date();
		pacient = new Patient();
		images = new ArrayList<Image>(0);

	}

	public void generateCode() throws Exception {
		if (null == code && null != examDate)
			code = HashUtil.calc_HashSHA(id.toString() + examDate.toString());
	}

	// --- Getters & Setters
	// --- Accessor methods

	public Date getReception() {
		return reception;
	}

	public void setReception(Date reception) {
		this.reception = reception;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getMacroExam() {
		return macroExam;
	}

	public void setMacroExam(String macroExam) {
		this.macroExam = macroExam;
	}

	public String getMicroExam() {
		return microExam;
	}

	public void setMicroExam(String microExam) {
		this.microExam = microExam;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public ADICAP getAdicap() {
		return adicap;
	}

	public void setAdicap(ADICAP adicap) {
		this.adicap = adicap;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPacient() {
		return pacient;
	}

	public void setPacient(Patient pacient) {
		this.pacient = pacient;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
}
