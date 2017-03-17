package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.sys.model.LoginLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class LoginMonitorService
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static Logger log = Logger.getLogger(LoginMonitorService.class);
  private static LoginMonitorService loginMonitorService = null;
  private static HashMap<String, HttpSession> onlineMap = null;
  
  private LoginMonitorService()
  {
    onlineMap = new HashMap();
  }
  
  public static LoginMonitorService getInstance()
  {
    if (loginMonitorService == null) {
      loginMonitorService = new LoginMonitorService();
    }
    return loginMonitorService;
  }
  
  public synchronized void put(String userId, HttpSession session)
  {
    try
    {
      synchronized (onlineMap)
      {
        onlineMap.put(userId, session);
      }
    }
    catch (Throwable throwable)
    {
      log.error("异 常:", throwable);
    }
  }
  
  public synchronized void remove(String userId)
  {
    try
    {
      if (onlineMap.containsKey(userId)) {
        onlineMap.remove(userId);
      }
    }
    catch (Throwable throwable)
    {
      log.error("异 常:", throwable);
    }
  }
  
  public synchronized boolean isLogin(String userId)
  {
    boolean flag = false;
    if ((userId != null) && (!userId.equals(""))) {
      flag = onlineMap.containsKey(userId);
    }
    return flag;
  }
  
  public synchronized LoginLog getLoginInfo(long userId)
  {
    LoginLog loginInfo = null;
    if (onlineMap.containsKey(Long.valueOf(userId)))
    {
      HttpSession session = (HttpSession)onlineMap.get(Long.valueOf(userId));
      loginInfo = (LoginLog)session
        .getAttribute("LoGiNsEsSiOnKeY");
    }
    return loginInfo;
  }
  
  public synchronized List getOnlineUserList()
  {
    Set sessionSet = onlineMap.keySet();
    Iterator it = null;
    it = sessionSet.iterator();
    ArrayList<LoginLog> onlineUserList = new ArrayList();
    Object attobj = null;
    HttpSession session = null;
    Object key = null;
    while (it.hasNext())
    {
      key = it.next();
      session = (HttpSession)onlineMap.get(key);
      if ((session == null) && (onlineMap.containsKey(key)))
      {
        onlineMap.remove(key);
        return getOnlineUserList();
      }
      try
      {
        attobj = session.getAttribute("LoGiNsEsSiOnKeY");
      }
      catch (Exception e)
      {
        log.error("session 过期：" + e);
        if (!onlineMap.containsKey(key)) {
          continue;
        }
      }
      onlineMap.remove(key);
      if (attobj != null)
      {
        LoginLog loginInfo = (LoginLog)attobj;
        onlineUserList.add(loginInfo);
      }
    }
    return onlineUserList;
  }
  
  public synchronized int getOnlineCount()
  {
    return onlineMap.size();
  }
}
