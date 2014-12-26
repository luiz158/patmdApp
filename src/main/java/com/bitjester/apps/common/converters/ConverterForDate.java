package com.bitjester.apps.common.converters;

import javax.enterprise.context.RequestScoped;
import javax.faces.convert.DateTimeConverter;
import javax.inject.Named;
import java.util.TimeZone;

@Named
@RequestScoped
public class ConverterForDate extends DateTimeConverter {
	public ConverterForDate() {
		setPattern("yyyy/MM/dd HH:mm");
		setTimeZone(TimeZone.getTimeZone("America/El_Salvador"));
	}
}
