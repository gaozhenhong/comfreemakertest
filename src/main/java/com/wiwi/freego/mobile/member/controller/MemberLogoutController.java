package com.wiwi.freego.mobile.member.controller;

import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.jsoil.base.BaseController;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberLogoutController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(MemberLogoutController.class);

  @RequestMapping({"/site/mobile/member/logoutAction"})
  public String logoutAction(Model model)
    throws Exception
  {
    String siteCode = getCurrentHotel().getCode();
    HttpSession session = getSession();
    session.removeAttribute("MeMbErLoGiNsEsSiOnKeY");

    session.invalidate();
    return "redirect:/site/mobile/web/" + siteCode + "/index";
  }
}