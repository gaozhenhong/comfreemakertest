package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.sys.service.LoginLogService;
import com.wiwi.jsoil.sys.service.LoginMonitorService;
import java.util.Date;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

public class LoginLog
  implements HttpSessionBindingListener
{
  private static final Logger log = LoggerFactory.getLogger(LoginLog.class);
  private String id;
  private User user;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date loginTime;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date logoutTime;
  private String clientIp;
  private String clientHost;
  
  public String getClientHost()
  {
    return this.clientHost;
  }
  
  public void setClientHost(String clientHost)
  {
    this.clientHost = clientHost;
  }
  
  public String getClientIp()
  {
    return this.clientIp;
  }
  
  public void setClientIp(String clientIp)
  {
    this.clientIp = clientIp;
  }
  
  public Date getLoginTime()
  {
    return this.loginTime;
  }
  
  public void setLoginTime(Date loginTime)
  {
    this.loginTime = loginTime;
  }
  
  public Date getLogoutTime()
  {
    return this.logoutTime;
  }
  
  public void setLogoutTime(Date logoutTime)
  {
    this.logoutTime = logoutTime;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void valueBound(HttpSessionBindingEvent sbe)
  {
    try
    {
      HttpSession session = sbe.getSession();
      
      Object attObj = sbe.getValue();
      if ((attObj != null) && ((attObj instanceof LoginLog)))
      {
        LoginMonitorService loginMonitorService = LoginMonitorService.getInstance();
        
        LoginLog loginInfo = (LoginLog)attObj;
        loginMonitorService.put(loginInfo.getUser().getId()+"", session);
        
        String message = new StringBuilder(String.valueOf(loginInfo.getUser().getName())).append("成功登录，目前共有 (").toString() + new StringBuilder().append(loginMonitorService.getOnlineCount()).toString() + ") 个在线人员.";
        log.info("valueBound:" + message);
      }
    }
    catch (Throwable throwable)
    {
      log.error("异常: ", throwable);
    }
  }
  
  public void valueUnbound(HttpSessionBindingEvent sbe)
  {
    try
    {
      Object attObj = sbe.getValue();
      if ((attObj != null) && ((attObj instanceof LoginLog)))
      {
        LoginMonitorService loginMonitorService = LoginMonitorService.getInstance();
        LoginLog loginInfo = (LoginLog)attObj;
        loginMonitorService.remove(loginInfo.getUser().getId()+"");
        
        String message = new StringBuilder(String.valueOf(loginInfo.getUser().getName())).append("成功退出，目前还剩 (").toString() + new StringBuilder().append(loginMonitorService.getOnlineCount()).toString() + ") 个在线人员.";
        log.info("valueUnbound:" + message);
        
        loginInfo.setLogoutTime(new Date());
        LoginLogService loginInfoService = new LoginLogService();
        loginInfoService.add(loginInfo);
      }
    }
    catch (Throwable throwable)
    {
      log.error("异常: ", throwable);
    }
  }
}
