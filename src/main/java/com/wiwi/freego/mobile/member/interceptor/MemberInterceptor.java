package com.wiwi.freego.mobile.member.interceptor;

import com.wiwi.jsoil.exception.NoMemberLoginException;
import com.wiwi.jsoil.member.model.Member;
import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MemberInterceptor
  implements HandlerInterceptor
{
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
    throws Exception
  {
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
    throws Exception
  {
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
    throws Exception
  {
    System.out.println("----------begin check url-----------------------");
    Member member = (Member)request.getSession().getAttribute("MeMbErLoGiNsEsSiOnKeY");
    if (member != null) {
      return true;
    }

    request.getSession().setAttribute("BeFoReLoGiNuRl", request.getRequestURL().toString());
    request.setAttribute("operationMessage", "没访问权限！");
    throw new NoMemberLoginException("手机端登录，所请求的资源需要登录认证，请先登录！");
  }
}