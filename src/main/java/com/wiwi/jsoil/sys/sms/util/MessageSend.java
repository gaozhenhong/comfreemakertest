package com.wiwi.jsoil.sys.sms.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wiwi.jsoil.util.AppConstants;
import com.wiwi.jsoil.util.JSONResult;

public class MessageSend
{
  private static Log log = LogFactory.getLog(MessageSend.class);
  public static final String MESSAGE_TYPE_REGISTER = "REGISTER_";
  public static final String MESSAGE_TYPE_PASSWORD = "PASSWORD_";
  public static final String ACOUNT_SID = AppConstants.getProperty("ACOUNT_SID");
  public static final String AUTH_TOKEN = AppConstants.getProperty("AUTH_TOKEN");
  public static final String APP_ID = AppConstants.getProperty("APP_ID");
  public static final String TEMPLET_CODE = "1";
  public static final String TEST_PATH = "sandboxapp.cloopen.com";
  public static final String PATH = "app.cloopen.com";
  public static final String PORT = "8883";
  public static final String SUCCESS_STATUS = "000000";
  
  public static JSONResult sendCode(String phone, String templetCode, String[] datas)
  {
    String message = "";
//    try
//    {
//      HashMap<String, Object> result = new HashMap();
//      boolean isTest = AppConstants.DEBUG_MODE;
//      if (templetCode == null) {
//        templetCode = "1";
//      }
//      log.info("现在的环境是：" + (isTest ? "测试环境" : "生产环境") + ";messageTempletCode=" + templetCode);
//      
//      CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//      String sdkPath = isTest ? "sandboxapp.cloopen.com" : "app.cloopen.com";
//      restAPI.init(sdkPath, "8883");
//      restAPI.setAccount(ACOUNT_SID, AUTH_TOKEN);
//      restAPI.setAppId(APP_ID);
//      result = restAPI.sendTemplateSMS(phone, templetCode, datas);
//      if ("000000".equals(result.get("statusCode")))
//      {
//        JSONResult json = new JSONResult();
//        json.setResult(true);
//        return json;
//      }
//      message = "错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg");
//      log.error(message);
//      System.err.println(result);
//    }
//    catch (Exception e)
//    {
//      message = e.getMessage();
//      e.printStackTrace();
//      log.error(message);
//      System.err.println("验证码获取异常");
//    }
    JSONResult json = new JSONResult();
    json.setResult(false);
    json.setMsg(message);
    return json;
  }
}
