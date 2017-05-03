package com.wiwi.jsoil.base;

import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.NoCurrentHotelException;
import com.wiwi.jsoil.exception.NoLoginException;
import com.wiwi.jsoil.exception.NoMemberLoginException;
import com.wiwi.jsoil.exception.NoSiteCodeException;
import com.wiwi.jsoil.exception.ReadCustomSQLException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.member.model.Member;
import com.wiwi.jsoil.sys.model.LoginLog;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.OrganizationService;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
  OrganizationService orgService;

  public BaseController()
  {
    this.orgService = new OrganizationService(); }

  @ExceptionHandler
  public String exp(HttpServletRequest request, Exception ex) {
    ex.printStackTrace();

    logger.error(ex.getMessage());
    request.setAttribute("ex", ex);

    if (ex instanceof ReadCustomSQLException)
      return "/sys/errors/error";
    if (ex instanceof DaoException)
      return "/sys/errors/error";

    if (ex instanceof RenderException)
      return "/sys/errors/error";

    if (ex instanceof NoLoginException) {
      setOperationMessage("登录失效，没访问权限，请重新登录！");
      return "redirect:/login"; }
    if (ex instanceof NoMemberLoginException) {
      setOperationMessage("会员登录失效，没访问权限，请重新登录！");
      return "redirect:/mobile/member/login";
    }
    return "/sys/errors/error";
  }

  protected static HttpSession getSession()
  {
    HttpSession session = null;
    try {
      session = getRequest().getSession();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("获取Session：{}", e);
    }
    return session;
  }

  protected static User getUser() throws NoLoginException {
    User user = null;
    try {
      LoginLog loginLog = (LoginLog)getSession().getAttribute("LoGiNsEsSiOnKeY");
      user = loginLog.getUser();
    } catch (Exception e) {
      e.printStackTrace();
      getRequest().getSession().setAttribute("BeFoReLoGiNuRl", getRequest().getRequestURL().toString());
      getRequest().setAttribute("operationMessage", "没访问权限！");
      throw new NoLoginException("");
    }
    return user; 
    } 

  protected com.wiwi.jsoil.sys.model.Organization getCompany() throws NoLoginException { 
	 try{
       User user = this.getUser();
       int orgId = user.getOrg().getId();
       Organization org = orgService.getCompany(orgId);
       return org;
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	 return null;
  }
  
  protected static Member getMember() throws NoMemberLoginException {
	  Member member = null;
    try {
      member = (Member)getSession().getAttribute("MeMbErLoGiNsEsSiOnKeY");
      if (member == null) {
        String requestUrl = getRequest().getRequestURL().toString();
        String queryStr = getRequest().getQueryString();
        if (queryStr == null)
          queryStr = "";
        if (!(queryStr.equalsIgnoreCase("")))
          requestUrl = requestUrl + "?" + queryStr;
        getRequest().getSession().setAttribute("BeFoReLoGiNuRl", requestUrl);
        getRequest().setAttribute("operationMessage", "没访问权限！");
        throw new NoMemberLoginException("没访问权限！");
      }
    } catch (Exception e) {
      String requestUrl = getRequest().getRequestURL().toString();
      String queryStr = getRequest().getQueryString();
      if (queryStr == null)
        queryStr = "";
      if (!(queryStr.equalsIgnoreCase("")))
        requestUrl = requestUrl + "?" + queryStr;
      getRequest().getSession().setAttribute("BeFoReLoGiNuRl", requestUrl);
      getRequest().setAttribute("operationMessage", "没访问权限！");
      throw new NoMemberLoginException("没访问权限！");
     }
    return member;
  }

  protected static String getSiteCode() throws NoSiteCodeException {
    String siteCode = "";
    try {
      siteCode = getSession().getAttribute("SiTeCoDe").toString();
    } catch (Exception e) {
      getRequest().getSession().setAttribute("BeFoReLoGiNuRl", getRequest().getRequestURL().toString());
      getRequest().setAttribute("operationMessage", "没访问权限！");
      throw new NoSiteCodeException("没访问权限！");
    }
    return siteCode;
  }

  protected static Hotel getCurrentHotel() throws NoCurrentHotelException {
    Hotel curHotel = new Hotel();
    try {
      curHotel = (Hotel)getSession().getAttribute("CuRrEnThOtEl");
    } catch (Exception e) {
      getRequest().getSession().setAttribute("BeFoReLoGiNuRl", getRequest().getRequestURL().toString());
      getRequest().setAttribute("operationMessage", "没访问权限！");
      throw new NoCurrentHotelException("没访问权限！");
    }
    return curHotel;
  }

  protected static HttpServletRequest getRequest() {
    ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    return attrs.getRequest();
  }

  protected static ServletContext getServletContext() {
    ServletContext application = getSession().getServletContext();
    return application;
  }

  protected void setOperationMessage(String operationMessage) {
    getSession().setAttribute("operationMessage", operationMessage);
  }

  protected void processOperationMessage(Model model)
  {
    String operationMessage = (String)getSession().getAttribute("operationMessage");
    getSession().removeAttribute("operationMessage");
    model.addAttribute("operationMessage", operationMessage);
  }
}