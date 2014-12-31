package com.bitjester.apps.md.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.bitjester.apps.common.BaseEntity;

@Entity
@Table(name = "images")
@SecondaryTable(name = "imageContent", pkJoinColumns = @PrimaryKeyJoinColumn(name = "image", referencedColumnName = "id"))
public class Image extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "biopsy")
	private Biopsy biopsy;
	private int index;
	private String name;
	private String type;
	private Date uploadDate;
	private String displayName;
	private String description;
	private Boolean active;
	private Long fileSize;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(table = "imageContent")
	private byte[] preview;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(table = "imageContent")
	private byte[] data;

	// --- Getters & Setters
	// --- Accessor methods

	public Biopsy getBiopsy() {
		return biopsy;
	}

	public void setBiopsy(Biopsy biopsy) {
		this.biopsy = biopsy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getPreview() {
		return preview;
	}

	public void setPreview(byte[] preview) {
		this.preview = preview;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
