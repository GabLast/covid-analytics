package com.myorg.covid_analytics.utils;

import com.myorg.covid_analytics.exceptions.InvalidDataFormat;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class EtlUtilities {

    private static final List<DateTimeFormatter> FORMATTERS =
            List.of(DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                    DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yy"));

    public static String getStringValue(Map<String, Object> row, String column) {
        if (StringUtils.isBlank(column))
            return null;

        Object object = row.get(column);
        return object != null ? (String) row.get(column) : "";
    }

    public static LocalDate getLocalDateValue(Map<String, Object> row, String column) {
        if (StringUtils.isBlank(column))
            return null;

        Object object = row.get(column);

        if (object != null) {
            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    return LocalDate.parse((String) object, formatter);
                } catch (DateTimeParseException ignored) {
                }
            }
        }

        return null;
    }

    public static BigDecimal getBigDecimalValue(Map<String, Object> row, String column) {
        if (StringUtils.isBlank(column))
            return null;

        Object value = row.get(column);

        BigDecimal result = BigDecimal.ZERO;

        switch (value) {
            case null -> {
                return result;
            }
            case String s -> {
                try {
                    result = new BigDecimal(s);
                } catch (NumberFormatException e) {
                    return result;
                }
            }
            case Number number ->
                    result = new BigDecimal(String.valueOf(number.doubleValue()));
            default -> throw new InvalidDataFormat(
                    "Not possible to cast [" + value + "] to BigDecimal");
        }

        return result;
    }

    public static Long getLongValue(Map<String, Object> row, String column) {
        if (StringUtils.isBlank(column))
            return null;

        Object object = row.get(column);
        // return object != null ? Long.parseLong((String) row.get(column)) : 0L;
        if (object instanceof Long) {
            return (Long) object; // Direct cast if it's already a Long
        } else if (object instanceof String) {
            try {
                return Long.valueOf((String) object); // Parse from string
            } catch (NumberFormatException e) {
                // Handle the case where the string is not a valid number
                System.err.println("Invalid number format for String: " + object);
                return -1L;
            }
        } else {
            // Handle other unsupported types
            return -1L;
        }
    }
}
