package com.wiwi.jsoil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils
{
  public static String getStyleClass(Date date)
  {
    String style = getWeekDayStyle(date);
    Date now = new Date();
    try {
      now = new SimpleDateFormat("yyyy-MM-dd").parse(DateUtils.dateToStr());
      int days = DateUtils.compareDate(now, date);
      if (days > 0)
        style = style + " fc-future";
      else if (days < 0)
        style = style + " fc-past";
      else
        style = style + " fc-state-highlight";
    }
    catch (ParseException e) {
      e.printStackTrace();
    }

    return style;
  }

  public static String getWeekDayStyle(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return getWeekDayStyle(c.get(7));
  }

  public static String getWeekDayStyle(int weekDay) {
    switch (weekDay)
    {
    case 2:
      return "fc-mon";
    case 3:
      return "fc-tue";
    case 4:
      return "fc-wed";
    case 5:
      return "fc-thu";
    case 6:
      return "fc-fri";
    case 7:
      return "fc-sat";
    case 1:
      return "fc-sun";
    }
    return "";
  }
}