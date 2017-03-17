package com.wiwi.jsoil.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MapUtil
{
  public static String getValue(Map map, String key)
  {
    if (map == null) return null;
    if (key == null) return null;
    if (map.containsKey(key))
      return map.get(key).toString();

    return null;
  }

  public static String getValue(Map map, String key, String defaultValue) {
    String value = getValue(map, key);
    return ((value == null) ? defaultValue : value);
  }

  public static Long getLongValue(Map map, String key, long defaultValue)
  {
    Long value = getLongValue(map, key);
    return Long.valueOf(value.longValue());
  }

  public static Long getLongValue(Map map, String key) {
    if (map == null) return null;
    if (key == null) return null;
    if (map.containsKey(key))
      return Long.valueOf(Long.parseLong(map.get(key).toString()));

    return null;
  }

  public static Integer getIntValue(Map map, String key)
  {
    if (map == null) return null;
    if (key == null) return null;
    if (map.containsKey(key))
      return Integer.valueOf(Integer.parseInt(map.get(key).toString()));

    return null;
  }

  public static Integer getIntValue(Map map, String key, int defaultValue)
  {
    Integer value = getIntValue(map, key);
    return Integer.valueOf(value.intValue());
  }

  public static Double getDoubleValue(Map map, String key)
  {
    if (map == null) return null;
    if (key == null) return null;
    if (map.containsKey(key))
      return Double.valueOf(Double.parseDouble(map.get(key).toString()));

    return null;
  }

  public static Double getDoubleValue(Map map, String key, double defaultValue)
  {
    Double value = getDoubleValue(map, key);
    return Double.valueOf(value.doubleValue());
  }

  public static Date getDateValue(Map map, String key)
  {
    if (map == null) return null;
    if (key == null) return null;
    if (map.containsKey(key)) {
      Object obj = map.get(key);
      if (obj instanceof Date) {
        return ((Date)obj);
      }

      if (obj instanceof Timestamp) {
        return new Date(((Timestamp)obj).getTime());
      }

      if (obj instanceof Date) {
        return new Date(((Date)obj).getTime()); }

      if (obj instanceof String);
    }
    try {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get(key).toString());
    } catch (ParseException e) {
      try {
        return new SimpleDateFormat("yyyy-MM-dd").parse(map.get(key).toString());
      } catch (ParseException e1) {
        e1.printStackTrace();

        return null; } }
  }

  public static String getStringForDateValue(Map map, String key) {
    return getStringForDateValue(map, key, null);
  }

  public static String getStringForDateValue(Map map, String key, String defaultValue) {
    return getStringForDateValue(map, key, null, defaultValue);
  }

  public static String getStringForDateValue(Map map, String key, String formater, String defaultValue) {
    if ((formater == null) || (formater.trim().length() < 1))
      formater = "yyyy-MM-dd HH:mm:ss";

    if (map == null) return "";
    if (key == null) return "";
    if (map.containsKey(key)) {
      Object obj = map.get(key);
      if (obj instanceof Date) {
        return new SimpleDateFormat(formater).format((Date)obj);
      }

      if (obj instanceof Timestamp) {
        return new SimpleDateFormat(formater).format(new Date(((Timestamp)obj).getTime()));
      }

      if (obj instanceof Date) {
        return new SimpleDateFormat(formater).format(new Date(((Date)obj).getTime()));
      }

      if (obj instanceof String)
        return obj.toString();
    }

    return defaultValue;
  }
}