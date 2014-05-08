package com.bitjester.apps.md.views;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.utils.BookKeeper;
import com.bitjester.apps.common.utils.FacesUtil;
import com.bitjester.apps.md.entities.Biopsy;
import com.bitjester.apps.md.entities.Image;

@Named
@ViewScoped
public class ViewBiopsy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private BookKeeper bk;

	@Inject
	private EntityManager em;

	private Biopsy managedBiopsy;
	private Image managedImage;

	// ================================
	// ======= Image Methods ==========
	// ================================

	public Image getManagedImage() {
		return managedImage;
	}

	public void setManagedImage(Image managedImage) {
		this.managedImage = managedImage;
	}

	public void loadImage(Image bi) {
		managedImage = bi;
	}

	public void refreshImage() {
		managedImage = null;
	}

	public void removeImage(Image bi) {
		managedBiopsy.getImages().remove(bi);
	}

	public void storeImage() {
		int i = managedBiopsy.getImages().indexOf(managedImage);
		if (-1 == i)
			managedBiopsy.getImages().add(managedImage);
		else
			managedBiopsy.getImages().set(i, managedImage);
		managedImage.setBiopsy(managedBiopsy);
		managedImage = null;
	}

	public void uploadImage() {

	}

	// ================================
	// ======= Biopsy Methods =========
	// ================================

	@Named
	@Produces
	public Biopsy getManagedBiopsy() {
		return managedBiopsy;
	}

	public void setManagedBiopsy(Biopsy managedBiopsy) {
		this.managedBiopsy = managedBiopsy;
	}

	public void load(Long id) {
		managedBiopsy = em.find(Biopsy.class, id);
	}

	public void refresh() {
		managedBiopsy = null;
	}

	public void remove(Long id) throws Exception {
		Biopsy biopsy = em.find(Biopsy.class, id);
		bk.remove(biopsy);
		FacesUtil.navTo("/forms/biopsies.xhtml");
	}

	public void store() throws Exception {
		bk.store(managedBiopsy);
		managedBiopsy = null;
	}
}
