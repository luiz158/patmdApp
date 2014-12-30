package com.bitjester.apps.md.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.utils.BookKeeper;
import com.bitjester.apps.common.utils.CodeUtil;
import com.bitjester.apps.common.utils.FacesUtil;
import com.bitjester.apps.jcr.JCRUtil;
import com.bitjester.apps.md.entities.Biopsy;
import com.bitjester.apps.md.entities.Image;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@Named
@ViewScoped
public class ViewBiopsy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private BookKeeper bk;

	@Inject
	private EntityManager em;

	@Inject
	private JCRUtil jcrUtil;

	private Biopsy managedBiopsy;
	private Image managedImage;

	@PostConstruct
	private void init() {
		// Check for biopsy parameter in request - if not found its a request for a new biopsy.
		Long biopsy = (Long) FacesUtil.getFlash().get("biopsy");
		if (null == biopsy)
			managedBiopsy = new Biopsy();
		else
			load(biopsy);
	}

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

	public void storeImage(FileUploadEvent event) {
		String path;

		// Sanity check
		if(null == managedBiopsy)
			return;

		// Setting up the biopsy repository.
		path = managedBiopsy.getCode();
		jcrUtil.createBiopsyRepo(path);

		// Uploading image data
		UploadedFile file = event.getUploadedFile();
		path += "/" + file.getName();
		jcrUtil.storeFile(path,file.getData());
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

	public void remove(Long id) throws Exception {
		Biopsy biopsy = em.find(Biopsy.class, id);
		bk.remove(biopsy);
		FacesUtil.navTo("forms/biopsies.xhtml");
	}

	public void store() throws Exception {
		// If the object is not persisted
		if (managedBiopsy.isNew()) {
			// Change the code until it is not present in the DB.
			while (checkCode(managedBiopsy.getCode()))
				managedBiopsy.setCode(CodeUtil.generateCode('U'));
		}
		bk.store(managedBiopsy);
		// managedBiopsy = null;
		FacesUtil.navTo("forms/biopsies.xhtml");
	}

	// Returns true if the code is present in the database.
	private Boolean checkCode(String code) {
		String query = "FROM Biopsy WHERE code = ?1";
		return em.createQuery(query).setParameter(1, code).getResultList().size() > 0;
	}
}
