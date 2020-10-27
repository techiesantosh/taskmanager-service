package com.web.taskmanager.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to format the date
 */
public class DateUtil {

    /**
     * @param localDate
     * @return date in  yyyy-MM-dd format
     */
    public static String formatDate(LocalDate localDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return (localDate).format(formatter);
    }

}
