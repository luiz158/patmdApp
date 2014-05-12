package com.bitjester.apps.md.converters;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.BaseEntity;
import com.bitjester.apps.md.entities.ADICAP;

@Named
@RequestScoped
public class ConverterForADICAP implements Converter {
	@Inject
	private EntityManager em;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (null != arg2 && !arg2.contains("---"))
			return em.find(ADICAP.class, Long.valueOf(arg2));
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (null != arg2 && arg2 instanceof BaseEntity)
			return ((BaseEntity) arg2).getId().toString();
		return null;
	}

}
