package com.wiwi.jsoil.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil
{
  public static JSONObject getJSONObject(JSONArray jsonArray, String keyName, String keyValue)
  {
    if ((jsonArray == null) || (keyName == null) || (keyValue == null)) return null;
    JSONObject obj = null;
    for (int i = 0; i < jsonArray.length(); ++i) {
      obj = (JSONObject)jsonArray.get(i);
      if (obj.getString(keyName).equals(keyValue))
        return obj;
    }

    return null;
  }

  public static String getValue(JSONArray jsonArray, String keyName, String keyValue, String propertyName)
  {
    if (propertyName == null) return null;
    JSONObject obj = getJSONObject(jsonArray, keyName, keyValue);
    if (obj == null) return null;
    return obj.get(propertyName).toString();
  }

  public static String getValue(JSONObject jsonObj, String name)
  {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name))
      return jsonObj.getString(name);

    return null;
  }

  public static String getValue(JSONObject jsonObj, String name, String defaultValue) {
    String value = getValue(jsonObj, name);
    return ((value == null) ? defaultValue : value);
  }

  public static Long getLongValue(JSONObject jsonObj, String name, long defaultValue)
  {
    Long value = getLongValue(jsonObj, name);
    return Long.valueOf(value.longValue());
  }

  public static Long getLongValue(JSONObject jsonObj, String name) {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name))
      return Long.valueOf(jsonObj.getLong(name));

    return null;
  }

  public static Integer getIntValue(JSONObject jsonObj, String name)
  {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name))
      return Integer.valueOf(jsonObj.getInt(name));

    return null;
  }

  public static Integer getIntValue(JSONObject jsonObj, String name, int defaultValue)
  {
    Integer value = getIntValue(jsonObj, name);
    return Integer.valueOf(value.intValue());
  }

  public static Double getDoubleValue(JSONObject jsonObj, String name)
  {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name))
      return Double.valueOf(jsonObj.getDouble(name));

    return null;
  }

  public static Enum getEnumValue(JSONObject jsonObj, String name, Class clazz)
  {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name))
      return jsonObj.getEnum(clazz, name);

    return null;
  }

  public static Double getDoubleValue(JSONObject jsonObj, String name, double defaultValue)
  {
    Double value = getDoubleValue(jsonObj, name);
    return Double.valueOf(value.doubleValue());
  }

  public static JSONObject getJSONObjectValue(JSONObject jsonObj, String name) {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name))
      return jsonObj.getJSONObject(name);

    return null;
  }

  public static JSONArray getJSONArrayValue(JSONObject jsonObj, String name) {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name))
      return jsonObj.getJSONArray(name);

    return null;
  }

  public static Date getDateValue(JSONObject jsonObj, String name)
  {
    if (jsonObj == null) return null;
    if (name == null) return null;
    if (jsonObj.has(name)) {
      Object obj = jsonObj.get(name);
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
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObj.getString(name));
    } catch (ParseException e) {
      try {
        return new SimpleDateFormat("yyyy-MM-dd").parse(jsonObj.getString(name));
      } catch (ParseException e1) {
        e1.printStackTrace();

        return null; } }
  }

  public static String getStringForDateValue(JSONObject jsonObj, String name) {
    return getStringForDateValue(jsonObj, name, null);
  }

  public static String getDateStringForDateValue(JSONObject jsonObj, String name) {
    return getStringForDateValue(jsonObj, name, "yyyy-MM-dd", null);
  }

  public static String getStringForDateValue(JSONObject jsonObj, String name, String defaultValue)
  {
    return getStringForDateValue(jsonObj, name, null, defaultValue);
  }

  public static String getStringForDateValue(JSONObject jsonObj, String name, String formater, String defaultValue) {
    if ((formater == null) || (formater.trim().length() < 1))
      formater = "yyyy-MM-dd HH:mm:ss";

    if (jsonObj == null) return "";
    if (name == null) return "";
    if (jsonObj.has(name)) {
      Object obj = jsonObj.get(name);
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