package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.LoginLog;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.UserService;
import com.wiwi.jsoil.util.AppConstants;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
  UserService userService;

  public LoginController()
  {
    this.userService = new UserService();
  }

  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String login(Locale locale, HttpServletRequest request, Model model)
  {
    model.addAttribute("appName", AppConstants.APP_NAME);
    model.addAttribute("companyName", AppConstants.COMPNAY_NAME);
    processOperationMessage(model);
    return "/sys/login/login";
  }

  @RequestMapping({"/sys/loginAction.do"})
  public String loginAction(@RequestParam String loginName, @RequestParam String password, Model model, HttpSession session) throws DaoException, RenderException {
    logger.info("登录.........【{}】", loginName);
    User user = null;
    try {
      user = this.userService.login(loginName, password);
    }
    catch (DaoException e) {
      logger.error("{}登录失败。异常信息：{}", loginName, e);
      e.printStackTrace();
    } catch (RenderException e) {
      logger.error("{}登录失败。异常信息：{}", loginName, e);
      e.printStackTrace();
    }
    if (user != null)
    {
      LoginLog loginLog = new LoginLog();
      loginLog.setUser(user);
      loginLog.setLoginTime(new Date());
      loginLog.setClientIp(getRequest().getRemoteAddr());
      loginLog.setClientHost(getRequest().getRemoteHost());
      session.setAttribute("LoGiNsEsSiOnKeY", loginLog);
      model.addAttribute("user", user);
      String beforeLoginUrl = (String)getRequest().getSession().getAttribute("BeFoReLoGiNuRl");
      logger.info("登录成功后：AppConstants.BEFORE_LOGIN_URL：{}", beforeLoginUrl);
      if ((beforeLoginUrl != null) && (beforeLoginUrl.length() > 0)) {
        getRequest().getSession().setAttribute("BeFoReLoGiNuRl", "");
        processOperationMessage(model);
        return "redirect:" + beforeLoginUrl.toString();
      }
      return "redirect:/sys/home.do";
    }

    setOperationMessage("用户名或密码错误，请重新登录！");
    return "redirect:/login";
  }
}