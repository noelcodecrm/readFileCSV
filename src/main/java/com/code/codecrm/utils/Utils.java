package com.code.codecrm.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Utils {

	@Value("${proc.date.parseDefaulting}")
	public Long parseDefaulting;

	public Utils() {
		super();
	}

	public DateTimeFormatter getDateValue(Long parseDefaultingArg) {
		DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder().parseCaseInsensitive().parseLenient()
				.parseDefaulting(ChronoField.YEAR_OF_ERA, parseDefaultingArg).appendPattern("[yyyy-MM-dd]")
				.appendPattern("[M/dd/yyyy]").appendPattern("[M/d/yyyy]").appendPattern("[MM/dd/yyyy]")
				.appendPattern("[MMM dd yyyy]").appendPattern("[dd/MM/yyyy]");

		return builder.toFormatter(Locale.ENGLISH);
	}

}
