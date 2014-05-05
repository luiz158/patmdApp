package com.bitjester.apps.md.utils;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.md.entities.Image;

@Named
public class ImageUtils {
	@Inject
	private EntityManager em;

	public Image getFile(Long id) throws Exception {
		return em.find(Image.class, id);
	}

	public void downloadFile(Long id) throws Exception {
		FacesContext.getCurrentInstance().getExternalContext().getResponse();
		// Put Bytes onto Response.
	}

	public void previewFile(Long id) throws Exception {
		FacesContext.getCurrentInstance().getExternalContext().getResponse();
		// Put Bytes onto Response.
	}
}
