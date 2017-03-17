package com.wiwi.jsoil.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class WxUtil
{
  public static String byteToStr(byte[] byteArray)
    throws Exception
  {
    String strDigest = "";
    for (int i = 0; i < byteArray.length; i++) {
      strDigest = strDigest + byteToHexStr(byteArray[i]);
    }
    return strDigest;
  }
  
  public static String byteToHexStr(byte mByte)
    throws Exception
  {
    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    char[] tempArr = new char[2];
    tempArr[0] = Digit[(mByte >>> 4 & 0xF)];
    tempArr[1] = Digit[(mByte & 0xF)];
    String s = new String(tempArr);
    return s;
  }
  
  public static String getRandomStr()
  {
    Random random = new Random();
    return MD5Util.string2MD5(String.valueOf(random.nextInt(19999)));
  }
  
  public static String getRandomStr(int rang)
  {
    Random random = new Random();
    return MD5Util.string2MD5(String.valueOf(random.nextInt(rang)));
  }
  
  public static String getMD5SignStrByKey(List<NameValuePair> params, String key)
  {
    ArrayList<String> list = new ArrayList();
    for (NameValuePair nvp : params) {
      if (nvp.getValue() != "") {
        list.add(nvp.getName() + "=" + nvp.getValue() + "&");
      }
    }
    int size = list.size();
    String[] arrayToSort = (String[])list.toArray(new String[size]);
    Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      sb.append(arrayToSort[i]);
    }
    String result = sb.toString();
    result = result + "key=" + key;
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      result = byteToStr(md.digest(result.getBytes("UTF-8")));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    result = result.toUpperCase();
    return result;
  }
  
  public static String getMD5SignStrByKey(JSONObject jsonObj, String key)
  {
    ArrayList<String> list = new ArrayList();
    Iterator<String> keyIt = jsonObj.keys();
    while (keyIt.hasNext())
    {
      String objName = ((String)keyIt.next()).toString();
      String objValue = jsonObj.getString(objName);
      if (objValue != "") {
        list.add(objName + "=" + objValue + "&");
      }
    }
    int size = list.size();
    String[] arrayToSort = (String[])list.toArray(new String[size]);
    Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      sb.append(arrayToSort[i]);
    }
    String result = sb.toString();
    result = result + "key=" + key;
    result = MD5Util.string2MD5(result).toUpperCase();
    return result;
  }
  
  public static String createParamsSignXML(List<NameValuePair> params, String key)
    throws Exception
  {
    StringBuilder cuoXml = new StringBuilder();
    cuoXml.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><xml>");
    for (NameValuePair nvp : params)
    {
      cuoXml.append("<" + nvp.getName() + ">");
      cuoXml.append(nvp.getValue());
      cuoXml.append("</" + nvp.getName() + ">");
    }
    String signStr = getMD5SignStrByKey(params, key);
    cuoXml.append("<sign>");
    cuoXml.append(signStr);
    cuoXml.append("</sign>");
    cuoXml.append("</xml>");
    String cuoXmlStr = cuoXml.toString();
    
    return cuoXmlStr;
  }
  
  public static String createParamsSignXML(JSONObject jsonObj, String key)
    throws Exception
  {
    StringBuilder cuoXml = new StringBuilder();
    cuoXml.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><xml>");
    Iterator<String> keyIt = jsonObj.keys();
    while (keyIt.hasNext())
    {
      String objName = ((String)keyIt.next()).toString();
      String objValue = jsonObj.getString(objName);
      cuoXml.append("<" + objName + ">");
      cuoXml.append(objValue);
      cuoXml.append("</" + objName + ">");
    }
    String signStr = getMD5SignStrByKey(jsonObj, key);
    cuoXml.append("<sign>");
    cuoXml.append(signStr);
    cuoXml.append("</sign>");
    cuoXml.append("</xml>");
    return cuoXml.toString();
  }
  
  public static JSONObject paraXmlToJsonObj(String xml)
    throws Exception
  {
    JSONObject json = new JSONObject();
    try
    {
      ByteArrayInputStream stringStream = null;
      if ((xml != null) && (!xml.trim().equals(""))) {
        stringStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
      }
      SAXReader saxReader = new SAXReader();
      Document document = saxReader.read(stringStream);
      org.dom4j.Element menuRoot = document.getRootElement();
      for (Iterator<org.w3c.dom.Element> menuIt = menuRoot.elementIterator(); menuIt.hasNext();)
      {
        org.dom4j.Element menuEl = (org.dom4j.Element)menuIt.next();
        json.put(menuEl.getName(), menuEl.getText());
      }
    }
    catch (Throwable e)
    {
      e.printStackTrace();
    }
    return json;
  }
  
  public static JSONObject menuXmlToJsonObj(String xmlname)
    throws Exception
  {
    JSONObject menuMainObj = new JSONObject();
    Resource resource = new ClassPathResource(xmlname);
    try
    {
      SAXReader saxReader = new SAXReader();
      InputStream menusInStream = resource.getInputStream();
      Document document = saxReader.read(menusInStream);
      org.dom4j.Element menuRoot = document.getRootElement();
      JSONArray button = new JSONArray();
      for (Iterator<org.w3c.dom.Element> menuIt = menuRoot.elementIterator(); menuIt.hasNext();)
      {
        org.dom4j.Element menuEl = (org.dom4j.Element)menuIt.next();
        JSONObject menuObj = new JSONObject();
        menuObj.put("name", menuEl.attributeValue("name"));
        boolean hasSub = Boolean.parseBoolean(menuEl.attributeValue("have-submenu"));
        if (hasSub)
        {
          JSONArray subMenuArray = new JSONArray();
          for (Iterator<org.w3c.dom.Element> submenuIt = menuEl.elementIterator(); submenuIt.hasNext();)
          {
            org.dom4j.Element submenuEl = (org.dom4j.Element)submenuIt.next();
            JSONObject subMenuObj = new JSONObject();
            for (Iterator<org.w3c.dom.Element> submenuAttrIt = submenuEl.elementIterator(); submenuAttrIt.hasNext();)
            {
              org.dom4j.Element submenuAttrEl = (org.dom4j.Element)submenuAttrIt.next();
              subMenuObj.put(submenuAttrEl.getName(), submenuAttrEl.getText());
            }
            subMenuArray.put(subMenuObj);
          }
          menuObj.put("sub_button", subMenuArray);
        }
        else
        {
          for (Iterator<org.w3c.dom.Element> submenus = menuEl.elementIterator(); submenus.hasNext();)
          {
            org.dom4j.Element submenu = (org.dom4j.Element)submenus.next();
            menuObj.put(submenu.getName(), submenu.getText());
          }
        }
        button.put(menuObj);
      }
      menuMainObj.put("button", button);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return menuMainObj;
  }
  
  public static String getOauto2WebUrl(String redirectUrl)
    throws Exception
  {
    String wxOauth2Url = AppConstants.getProperty("WX_OAUTH2_BASE_URL");
    wxOauth2Url = wxOauth2Url.replace("APPID", AppConstants.getProperty("WX_APPID"));
    wxOauth2Url = wxOauth2Url.replace("REDIRECT_URI", URLEncoder.encode(redirectUrl, "UTF-8"));
    wxOauth2Url = wxOauth2Url.replace("SCOPE", AppConstants.getProperty("WX_OAUTH2_SCOPE"));
    wxOauth2Url = wxOauth2Url.replace("STATE", AppConstants.getProperty("WX_OAUTH2_STATE"));
    return wxOauth2Url;
  }
  
  public static String getOauto2AccessTokenUrl(String code)
    throws Exception
  {
    String wxOauth2Url = AppConstants.getProperty("WX_OAUTH2_ACCESS_TOKEN_URI");
    wxOauth2Url = wxOauth2Url.replace("APPID", AppConstants.getProperty("WX_APPID"));
    wxOauth2Url = wxOauth2Url.replace("SECRET", AppConstants.getProperty("WX_APPSECRET"));
    wxOauth2Url = wxOauth2Url.replace("CODE", code);
    return wxOauth2Url;
  }
  
  public static String getOauto2UserInfoUrl(String accessToken, String openId)
    throws Exception
  {
    String userInfoOauth2Url = AppConstants.getProperty("WX_OAUTH2_USERINFO_URL");
    userInfoOauth2Url = userInfoOauth2Url.replace("ACCESS_TOKEN", accessToken);
    userInfoOauth2Url = userInfoOauth2Url.replace("OPENID", openId);
    return userInfoOauth2Url;
  }
  
  public static String getUnifiedOrderUrl()
    throws Exception
  {
    return AppConstants.getProperty("WX_PAY_UNIFIEDORDER_URL");
  }
  
  public static String getPayRefundUrl()
    throws Exception
  {
    return AppConstants.getProperty("WX_PAY_REFUND_URL");
  }
  
  public static String getAppID()
    throws Exception
  {
    return AppConstants.getProperty("WX_APPID");
  }
  
  public static String getAppSecret()
    throws Exception
  {
    return AppConstants.getProperty("WX_APPSECRET");
  }
  
  public static String getPayMCHID()
    throws Exception
  {
    return AppConstants.getProperty("WX_MCH_ID");
  }
  
  public static String getPayAPIKey()
    throws Exception
  {
    return AppConstants.getProperty("WX_PAY_API_KEY");
  }
  
  public static String getPaySignType()
    throws Exception
  {
    return AppConstants.getProperty("WX_PAY_SIGN_TYPE");
  }
  
  public static String getPayTradeType()
    throws Exception
  {
    return AppConstants.getProperty("WX_PAY_TRADE_TYPE");
  }
  
  public static String getPayNotifyUrl()
    throws Exception
  {
    return AppConstants.getProperty("WX_PAY_NOTIFY_URL");
  }
  
  public static void main(String[] arg)
  {
    try
    {
      JSONObject json1 = menuXmlToJsonObj("wx-menus.xml");
      System.out.println("------------------------------------------------");
      System.out.println(json1.toString());
      String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[NfsMFbUFpdbEhPXP]]></nonce_str><sign><![CDATA[B7274EB9F8925EB93100DD2085FA56C0]]></sign><result_code><![CDATA[SUCCESS]]></result_code><transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id><out_trade_no><![CDATA[1415757673]]></out_trade_no><out_refund_no><![CDATA[1415701182]]></out_refund_no><refund_id><![CDATA[2008450740201411110000174436]]></refund_id><refund_channel><![CDATA[]]></refund_channel><refund_fee>1</refund_fee><coupon_refund_fee>0</coupon_refund_fee></xml>";
      
      JSONObject json = paraXmlToJsonObj(xml);
      System.out.println("------------------------------------------------");
      System.out.println(json.toString());
      System.out.println("------------------------------------------------");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
