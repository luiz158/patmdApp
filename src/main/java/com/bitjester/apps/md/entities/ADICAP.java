package com.bitjester.apps.md.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bitjester.apps.common.BaseEntity;

@Cacheable
@Entity
@Table(name = "adicaps", uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
public class ADICAP extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String code;
	private String description;

	// --- Getters & Setters
	// --- Accessor methods

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
