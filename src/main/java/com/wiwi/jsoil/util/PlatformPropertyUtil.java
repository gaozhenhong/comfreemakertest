package com.wiwi.jsoil.util;

import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.PlatformProperty;
import com.wiwi.jsoil.sys.service.PlatformPropertyService;

public class PlatformPropertyUtil
{
  public static String getValue(String propertyCode)
  {
    PlatformPropertyService service = new PlatformPropertyService();
    PlatformProperty p = null;
    try
    {
      p = service.getByCode(propertyCode);
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    return p == null ? null : p.getValue();
  }
  
  public static Long getLongValue(String propertyCode)
  {
    String value = getValue(propertyCode);
    return value == null ? null : Long.valueOf(Long.parseLong(value));
  }
  
  public static Integer getIntegerValue(String propertyCode)
  {
    String value = getValue(propertyCode);
    return value == null ? null : Integer.valueOf(Integer.parseInt(value));
  }
  
  public static Double getDoubleValue(String propertyCode)
  {
    String value = getValue(propertyCode);
    return value == null ? null : Double.valueOf(Double.parseDouble(value));
  }
}
