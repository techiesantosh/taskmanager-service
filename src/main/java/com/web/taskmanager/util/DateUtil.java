package com.web.taskmanager.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {


  public static String formatDate(LocalDate localDate){

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String strdate=(localDate).format(formatter);

    return strdate;
  }

}
