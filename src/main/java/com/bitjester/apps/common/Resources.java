package com.bitjester.apps.common;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

public class Resources {
	// Defines the application name.
	@Named
	@Produces
	private String appName = "mdApp";

	// Defines login attempts limit
	@Named
	@Produces
	private Integer login_limit = 5;

	// Expose an entity manager using the resource producer pattern
	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	@Produces
	public Logger getLogger(InjectionPoint ip) {
		String category = ip.getMember().getDeclaringClass().getName();
		return Logger.getLogger(category);
	}
}
