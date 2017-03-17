package com.wiwi.jsoil.util;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class AppConstants
{
  private static Logger logger = Logger.getLogger(AppConstants.class);
  public static final String DB_TYPE_MYSQL = "mysql";
  public static final String DB_TYPE_MSSQL = "mssql";
  public static final String DB_TYPE_ORALCE = "oracle";
  public static final String BEFORE_LOGIN_URL = "BeFoReLoGiNuRl";
  public static final String SITE_CODE = "SiTeCoDe";
  public static final String WX_ACCESS_TOKEN = "WxAcCeSsToKeN";
  public static final String CURRENT_HOTEL = "CuRrEnThOtEl";
  public static String APP_NAME = (getProperty("APP_NAME") == null) ? "Jsoil系统" : getProperty("APP_NAME");
  public static String COMPNAY_NAME = (getProperty("COMPNAY_NAME") == null) ? "WIWI" : getProperty("COMPNAY_NAME");
  public static String DB_TYPE = (getProperty("DB_TYPE") == null) ? "mysql" : getProperty("DB_TYPE");
  public static String WX_BASE_TOKEN = (getProperty("WX_BASE_TOKEN") == null) ? "" : getProperty("WX_BASE_TOKEN");
  public static boolean DEBUG_MODE = new Boolean(getProperty("DEBUG_MODE")).booleanValue();
  public static boolean LOG_OPERATE = new Boolean(getProperty("LOG_OPERATE")).booleanValue();
  public static String GUEST_PERMISSION = (getProperty("GUEST_PERMISSION") == null) ? "" : getProperty("GUEST_PERMISSION");
  public static final String LOGIN_SESSION_KEY = "LoGiNsEsSiOnKeY";
  public static final String MEMBER_LOGIN_SESSION_KEY = "MeMbErLoGiNsEsSiOnKeY";
  private static Properties properties = null;

  private static void init()
  {
    Resource resource = new ClassPathResource("/AppConfig.properties");
    try {
      properties = PropertiesLoaderUtils.loadProperties(resource);
    } catch (IOException e) {
      logger.error("读取配置文件时出错！" + e);
      e.printStackTrace();
    }
  }

  public static String getProperty(String name)
  {
    if (properties == null)
      init();

    return properties.getProperty(name);
  }
}