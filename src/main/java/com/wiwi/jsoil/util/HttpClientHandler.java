package com.wiwi.jsoil.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpClientHandler
{
  public static String post(String url, List<NameValuePair> params)
  {
    String result = "";
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    
    HttpPost httppost = new HttpPost(url);
    try
    {
      if (((params != null ? 1 : 0) & (params.size() > 0 ? 1 : 0)) != 0) {
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
      }
      CloseableHttpResponse res = httpclient.execute(httppost);
      if (res.getStatusLine().getStatusCode() == 200)
      {
        HttpEntity entity = res.getEntity();
        result = EntityUtils.toString(entity, "utf-8");
      }
      return result;
    }
    catch (ClientProtocolException e)
    {
      e.printStackTrace();
      return result;
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
      return result;
    }
    catch (IOException e)
    {
      String str1;
      e.printStackTrace();
      return result;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static String postJson(String url, JSONObject parmJson)
  {
    String result = "";
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    
    HttpPost httppost = new HttpPost(url);
    try
    {
      if (parmJson != null)
      {
        StringEntity parmEn = new StringEntity(parmJson.toString(), "UTF-8");
        
        httppost.setEntity(parmEn);
      }
      CloseableHttpResponse res = httpclient.execute(httppost);
      if (res.getStatusLine().getStatusCode() == 200)
      {
        HttpEntity entity = res.getEntity();
        result = EntityUtils.toString(entity, "utf-8");
      }
      return result;
    }
    catch (ClientProtocolException e)
    {
      e.printStackTrace();
      return result;
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
      return result;
    }
    catch (IOException e)
    {
      String str1;
      e.printStackTrace();
      return result;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static String postString(String url, String parmStr)
  {
    String result = "";
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    
    HttpPost httppost = new HttpPost(url);
    try
    {
      if (parmStr != null)
      {
        StringEntity parmEn = new StringEntity(parmStr, "UTF-8");
        
        httppost.setEntity(parmEn);
      }
      CloseableHttpResponse res = httpclient.execute(httppost);
      if (res.getStatusLine().getStatusCode() == 200)
      {
        HttpEntity entity = res.getEntity();
        result = EntityUtils.toString(entity, "utf-8");
      }
      return result;
    }
    catch (ClientProtocolException e)
    {
      e.printStackTrace();
      return result;
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
      return result;
    }
    catch (IOException e)
    {
      String str1;
      e.printStackTrace();
      return result;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static String get(String url, List<NameValuePair> params)
  {
    String result = "";
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    if ((params != null) && (params.size() > 0))
    {
      String paraStr = "";
      for (NameValuePair obj : params) {
        paraStr = paraStr + "&" + obj.getName() + "=" + obj.getValue();
      }
      if ((paraStr != "") && (paraStr.length() > 0)) {
        paraStr = paraStr.substring(1);
      }
      url = url + "?" + paraStr;
    }
    HttpGet httpget = new HttpGet(url);
    try
    {
      CloseableHttpResponse res = httpclient.execute(httpget);
      if (res.getStatusLine().getStatusCode() == 200)
      {
        HttpEntity entity = res.getEntity();
        result = EntityUtils.toString(entity, "utf-8");
      }
      return result;
    }
    catch (ClientProtocolException e)
    {
      e.printStackTrace();
      return result;
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
      return result;
    }
    catch (IOException e)
    {
      String str1;
      e.printStackTrace();
      return result;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}
