package com.app.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class DoubleSerializer extends JsonSerializer<Double> {

	private static final Logger LOG = LoggerFactory.getLogger(DoubleSerializer.class);

	@Override
	public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) {
		try {
			if (value == null) {
				jgen.writeNull();
			} else {
				DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
				formatSymbols.setDecimalSeparator('.');
				DecimalFormat formatter = new DecimalFormat("#.##", formatSymbols);
				jgen.writeNumber(formatter.format(value));
			}
		} catch (IOException e) {
			LOG.error("Value could not be formatted", e);
		}
	}
}
