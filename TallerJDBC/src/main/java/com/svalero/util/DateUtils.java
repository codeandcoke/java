package com.svalero.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;

public class DateUtils {

    // Convierte de LocalDate a java.sql.Date
    public static Date toDate(LocalDate localDate) {
        java.util.Date date = java.util.Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        return new Date(date.getTime());
    }

    // Convierte de java.sql.Date a LocalDate
    public static LocalDate toLocalDate(Date date) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        return utilDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
