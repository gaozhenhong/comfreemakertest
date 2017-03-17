package com.wiwi.jsoil.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil
{
  public static String getParameter(HttpServletRequest request, String name, String defaultValue)
  {
    String value = request.getParameter(name);
    if (value == null)
      return defaultValue;

    return value;
  }

  public static String getParameter(HttpServletRequest request, String name)
  {
    return getParameter(request, name, "");
  }

  public static int getIntegerParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    if (value == null)
      return 0;
    try
    {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return 0;
    }
  }

  public static int[] getIntegerParameters(HttpServletRequest request, String name) {
    String[] values = request.getParameterValues(name);
    if ((values == null) || (values.length == 0))
      return null;

    try
    {
      int[] intValues = new int[values.length];
      for (int i = 0; i < values.length; ++i) {
        intValues[i] = Integer.parseInt(values[i]);
      }

      return intValues;
    } catch (Exception e) {
      return null;
    }
  }

  public static double[] getDoubleParameters(HttpServletRequest request, String name)
  {
    String[] values = request.getParameterValues(name);
    if ((values == null) || (values.length == 0))
      return null;

    try
    {
      double[] doubleValues = new double[values.length];
      for (int i = 0; i < values.length; ++i) {
        doubleValues[i] = Double.parseDouble(values[i]);
      }

      return doubleValues;
    } catch (Exception e) {
      return null;
    }
  }

  public static double getDoubleParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    if (value == null)
      return 0D;
    try
    {
      return Double.parseDouble(value);
    } catch (Exception e) {
      return 0D;
    }
  }

  public static float getFloatParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    if (value == null)
      return 0F;
    try
    {
      return Float.parseFloat(value);
    } catch (Exception e) {
      return 0F;
    }
  }

  public static long getLongParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    if (value == null)
      return 0L;
    try
    {
      return Long.parseLong(value);
    } catch (Exception e) {
      return 0L;
    }
  }

  public static long[] getLongParameters(HttpServletRequest request, String name) {
    String[] values = request.getParameterValues(name);
    if ((values == null) || (values.length == 0))
      return null;

    try
    {
      long[] longValues = new long[values.length];
      for (int i = 0; i < values.length; ++i) {
        longValues[i] = Long.parseLong(values[i]);
      }

      return longValues;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getAttribute(HttpServletRequest request, String name) {
    return getAttribute(request, name, "");
  }

  public static String getAttribute(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getAttribute(name).toString();
    if (value == null)
      return defaultValue;

    return value;
  }
}