package com.svalero.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {

    public static Date getDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public static LocalDate getLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
}
