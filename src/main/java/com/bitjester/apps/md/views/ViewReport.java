package com.bitjester.apps.md.views;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.utils.BookKeeper;

@Named
@ViewScoped
public class ViewReport {
	@Inject
	private BookKeeper bk;

	@Inject
	private EntityManager em;
}
