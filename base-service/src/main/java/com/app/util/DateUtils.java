package com.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static String nowDate() {
		LocalDate nowDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return nowDate.format(formatter);
	}

	public static String nowTimestamp() {
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
		return nowDate.format(formatter);
	}

	public static String format(String from, String to, String date) {
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat(from);
		SimpleDateFormat dateFormatTo = new SimpleDateFormat(to);

		try {
			return dateFormatTo.format(dateFormatFrom.parse(date));
		} catch (ParseException e) {
			LOG.error("Error formating date", e);
		}

		return date;
	}

	public static LocalDateTime addBusinessDays(LocalDateTime dateTime, int days) {
		LocalDateTime result = dateTime;
		int addedDays = 0;
		while (addedDays < days) {
			result = result.plusDays(1);
			if (result.getDayOfWeek() != DayOfWeek.SUNDAY && result.getDayOfWeek() != DayOfWeek.SATURDAY) {
				++addedDays;
			}
		}
		return result;
	}

	public static String getStringDate(Date date, String defaultTimeZone) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.of(defaultTimeZone)).toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
		return localDate.format(formatter);
	}

}
