package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.sys.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController
{
  UserService userService;

  public LogoutController()
  {
    this.userService = new UserService();
  }

  @RequestMapping({"/sys/logoutAction.do"})
  public String logoutAction(Model model, HttpSession session) {
    session.removeAttribute("LoGiNsEsSiOnKeY");
    session.invalidate();
    return "redirect:/";
  }
}