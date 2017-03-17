package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.UserUtil;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.GroupService;
import com.wiwi.jsoil.sys.service.MessageService;
import com.wiwi.jsoil.sys.service.ModuleService;
import com.wiwi.jsoil.sys.service.UserService;
import com.wiwi.jsoil.util.AppConstants;
import java.io.PrintStream;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController extends BaseController
{
  UserService userService;
  ModuleService moduleService;

  public HomeController()
  {
    this.userService = new UserService();
    this.moduleService = new ModuleService(); }

  @RequestMapping({"/sys/home.do"})
  public String home(@RequestParam(required=false) String topUrl, Model model, HttpSession session) throws Exception { model.addAttribute("appName", AppConstants.APP_NAME);
    model.addAttribute("debugMode", Boolean.valueOf(AppConstants.DEBUG_MODE));
    model.addAttribute("companyName", AppConstants.COMPNAY_NAME);
    User user = getUser();
    if (user == null)
    {
      model.addAttribute("message", "用户登录已经失效，请重新登录！");
      return "redirect:/";
    }
    model.addAttribute("user", user);
    model.addAttribute("UserUtil", UserUtil.class);
    model.addAttribute("moduleList", this.moduleService.getUserModuleList(user.getId()));
    model.addAttribute("unreadNumber", new MessageService().getUnreadNumber(user.getId()));
    List topWorkbenchList = GroupService.getTopWorkbenchList(user);
    if ((topUrl == null) && (topWorkbenchList != null) && (topWorkbenchList.size() > 0))
      topUrl = (String)topWorkbenchList.get(0);

    System.out.println("topWorkbenchList==" + topWorkbenchList);
    if ((topUrl != null) && (topUrl.equals("config")))
      topUrl = null;

    model.addAttribute("topUrl", topUrl);
    model.addAttribute("topWorkbenchList", topWorkbenchList);
    return "/sys/home";
  }

  @RequestMapping({"/sys/welcome.do"})
  public String welcome(Model model, HttpSession session)
    throws Exception
  {
    User user = getUser();
    List workbenchList = GroupService.getWelocmeWorkbenchList(user);
    if (workbenchList.size() == 1)
      return "redirect:" + ((String)workbenchList.get(0));

    model.addAttribute("workbenchList", workbenchList);
    return "/sys/welcome";
  }
}