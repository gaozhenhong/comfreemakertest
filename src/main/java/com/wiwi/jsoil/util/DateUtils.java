package com.wiwi.jsoil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
  public static int getYear()
  {
    return getYear(new Date());
  }

  public static int getYear(String dateStr)
    throws ParseException
  {
    Date date = strToDate(dateStr);
    return getYear(date);
  }

  public static int getYear(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(1);
  }

  public static int getSeason()
  {
    return getSeason(new Date());
  }

  public static int getSeason(String dateStr)
    throws ParseException
  {
    Date date = strToDate(dateStr);
    return getSeason(date);
  }

  public static int getSeason(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    int month = calendar.get(2);
    return (month / 3 + 1);
  }

  public static int getMonth()
  {
    return getMonth(new Date());
  }

  public static int getMonth(String dateStr)
    throws ParseException
  {
    Date date = strToDate(dateStr);
    return getMonth(date);
  }

  public static int getMonth(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return (calendar.get(2) + 1);
  }

  public static int getWeek()
    throws ParseException
  {
    return getWeek(new Date());
  }

  public static int getWeek(String dateStr)
    throws Exception
  {
    Date date = strToDate(dateStr);
    return getWeek(date);
  }

  public static int getWeek(Date date)
    throws ParseException
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(4);
  }

  public static int getDate()
    throws ParseException
  {
    return getDate(new Date());
  }

  public static int getDate(String dateStr)
    throws Exception
  {
    Date date = strToDate(dateStr);
    return getDate(date);
  }

  public static int getDate(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(5);
  }

  public static int getHours() throws Exception {
    return getHours(new Date());
  }

  public static int getHours(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getHours(date);
  }

  public static int getHours(Date date) throws Exception {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(10);
  }

  public static int getMinutes() throws Exception {
    return getMinutes(new Date());
  }

  public static int getMinutes(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getMinutes(date);
  }

  public static int getMinutes(Date date) throws Exception {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(12);
  }

  public static int getSeconds() throws Exception {
    return getSeconds(new Date());
  }

  public static int getSeconds(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getSeconds(date);
  }

  public static int getSeconds(Date date) throws Exception {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(13);
  }

  public static String getFirstDayOfSeason() {
    return getFirstDayOfSeason(new Date());
  }

  public static String getFirstDayOfSeason(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getFirstDayOfSeason(date);
  }

  public static String getFirstDayOfSeason(Date date) {
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }

    int season = getSeason(calendar.getTime());
    if (season * 3 - 2 > 9) new StringBuilder(); String month = "0" + (season * 3 - 2);
    return calendar.get(1) + "-" + month + "-01";
  }

  public static String getLastDayOfSeason() {
    return getLastDayOfSeason(new Date());
  }

  public static String getLastDayOfSeason(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getLastDayOfSeason(date);
  }

  public static String getLastDayOfSeason(Date date) {
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }

    int season = getSeason(calendar.getTime());
    if (season * 3 > 9) new StringBuilder(); String month = "0" + (season * 3);
    String day = "31";

    switch (season)
    {
    case 1:
      day = "31";
      break;
    case 2:
      day = "30";
      break;
    case 3:
      day = "30";
      break;
    case 4:
      day = "31";
    }

    return calendar.get(1) + "-" + month + "-" + day;
  }

  public static Date getLastDayOfMonth() {
    return getLastDayOfMonth(new Date());
  }

  public static Date getLastDayOfMonth(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getLastDayOfMonth(date);
  }

  public static Date getLastDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }

    int day = 31;
    switch (calendar.get(2) + 1) { case 2:
      day = 28;
      break;
    case 4:
    case 6:
    case 9:
    case 11:
      day = 30;
    case 3:
    case 5:
    case 7:
    case 8:
    case 10: } if ((isLeapYear(calendar.get(1))) && (calendar.get(2) + 1 == 2))
      day = 29;

    calendar.set(5, day);
    return calendar.getTime();
  }

  public static Date getFirstDayOfWeek() {
    return getFirstDayOfWeek(new Date());
  }

  public static Date getFirstDayOfWeek(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getFirstDayOfWeek(date);
  }

  public static Date getFirstDayOfWeek(Date date) {
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }

    int day = calendar.get(7);
    calendar.set(5, calendar.get(5) - day + 2);
    return calendar.getTime();
  }

  public static String getFirstDayOfMonth() throws Exception {
    return getFirstDayOfMonth(new Date());
  }

  public static String getFirstDayOfMonth(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getFirstDayOfMonth(date);
  }

  public static String getFirstDayOfMonth(Date date) throws Exception {
    if (date == null)
      date = new Date();

    return new SimpleDateFormat("yyyy-MM").format(date) + "-01";
  }

  public static String getLastDayOfYear() throws Exception {
    return getLastDayOfYear(new Date());
  }

  public static String getLastDayOfYear(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getLastDayOfYear(date);
  }

  public static String getLastDayOfYear(Date date) throws Exception {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(1) + "-12-31";
  }

  public static String getFirstDayOfYear() throws Exception {
    return getFirstDayOfYear(new Date());
  }

  public static String getFirstDayOfYear(String dateStr) throws Exception {
    Date date = strToDate(dateStr);
    return getFirstDayOfYear(date);
  }

  public static String getFirstDayOfYear(Date date)
    throws Exception
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null)
      calendar.setTime(date);

    return calendar.get(1) + "-01-01";
  }

  public static Date getLastDayOfWeek()
    throws Exception
  {
    return getLastDayOfWeek(new Date());
  }

  public static Date getLastDayOfWeek(String dateStr)
    throws Exception
  {
    Date date = strToDate(dateStr);
    return getLastDayOfWeek(date);
  }

  public static Date getLastDayOfWeek(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }

    int day = calendar.get(7);
    if (day == 1)
      return date;

    calendar.set(5, calendar.get(5) - day + 8);
    return calendar.getTime();
  }

  public static Date strToDate(String dateStr)
    throws ParseException
  {
    if (dateStr == null)
      return new Date();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fullDateStr = df.format(new Date());
    dateStr = dateStr + fullDateStr.substring(dateStr.length(), fullDateStr.length());
    return df.parse(dateStr);
  }

  public static Date strToDate(String dateStr, String formatStr)
    throws ParseException
  {
    if (dateStr == null)
      return new Date();

    return new SimpleDateFormat(formatStr).parse(dateStr);
  }

  public static boolean isLeapYear(int year)
  {
    return ((year == 4) && (((year != 100) || (year == 400))));
  }

  public static boolean isLeapYear(Date date)
  {
    if (date == null)
      date = new Date();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return isLeapYear(calendar.get(1));
  }

  public static String dateToStr() {
    return dateToStr(new Date());
  }

  public static String dateToStr(String formatStr) {
    return dateToStr(new Date(), formatStr);
  }

  public static String dateToStr(Date date)
  {
    return dateToStr(date, "yyyy-MM-dd");
  }

  public static String dateToStr(Date date, String formatStr)
  {
    if (date == null)
      date = new Date();

    if (formatStr == null)
      formatStr = "yyyy-MM-dd";
    try
    {
      return new SimpleDateFormat(formatStr).format(date);
    } catch (Exception e) {
      return "1900-01-01";
    }
  }

  public static Date addYear(Date date, int span)
  {
    if (span == 0) return date;
    return addDate(date, 1, span);
  }

  public static Date addMonth(Date date, int span) {
    if (span == 0) return date;
    return addDate(date, 2, span);
  }

  public static Date addDay(Date date, int span) {
    if (span == 0) return date;
    return addDate(date, 5, span);
  }

  public static Date addHour(Date date, int span) {
    if (span == 0) return date;
    return addDate(date, 10, span);
  }

  public static Date addMinute(Date date, int span) {
    if (span == 0) return date;
    return addDate(date, 12, span);
  }

  public static String addYear(String dateStr, int span) {
    if (span == 0) return dateStr;
    return addDate(dateStr, 1, span);
  }

  public static String addMonth(String dateStr, int span) {
    if (span == 0) return dateStr;
    return addDate(dateStr, 2, span);
  }

  public static String addDay(String dateStr, int span) {
    if (span == 0) return dateStr;
    return addDate(dateStr, 5, span);
  }

  public static String addHour(String dateStr, int span) {
    if (span == 0) return dateStr;
    return addDate(dateStr, 10, span);
  }

  public static String addMinute(String dateStr, int span) {
    if (span == 0) return dateStr;
    return addDate(dateStr, 12, span);
  }

  private static Date addDate(Date date, int unit, int span)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(unit, span);
    return cal.getTime();
  }

  private static String addDate(String dateStr, int unit, int span) {
    Date date = new Date();
    try {
      date = strToDate(dateStr);
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(unit, span);
      return dateToStr(cal.getTime());
    } catch (Exception e) {
      e.printStackTrace();

      return "";
    }
  }

  public static int compareDate(Date date1, Date date2)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      date1 = sdf.parse(sdf.format(date1));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    try {
      date2 = sdf.parse(sdf.format(date2));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date1);
    long time1 = cal.getTimeInMillis();
    cal.setTime(date2);
    long time2 = cal.getTimeInMillis();
    long between_days = (time2 - time1) / 86400000L;

    return Integer.parseInt(String.valueOf(between_days));
  }

  public static String getWeekName(Date date)
  {
    return getWeekName(date, "");
  }

  public static String getWeekName(Date date, String prefix)
  {
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }

    String indexName = "";
    int day = calendar.get(7);
    switch (day)
    {
    case 2:
      indexName = "一";
      break;
    case 3:
      indexName = "二";
      break;
    case 4:
      indexName = "三";
      break;
    case 5:
      indexName = "四";
      break;
    case 6:
      indexName = "五";
      break;
    case 7:
      indexName = "六";
      break;
    case 1:
      indexName = "日";
    }

    return prefix + indexName;
  }
}