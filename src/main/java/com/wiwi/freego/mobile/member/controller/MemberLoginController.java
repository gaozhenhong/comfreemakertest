package com.wiwi.freego.mobile.member.controller;

import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.member.model.Member;
import com.wiwi.jsoil.member.model.MemberQ;
import com.wiwi.jsoil.member.service.MemberService;
import com.wiwi.jsoil.sys.model.LoginLog;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.sms.util.SMSUtil;
import com.wiwi.jsoil.util.AppConstants;
import com.wiwi.jsoil.util.JSONResult;
import com.wiwi.jsoil.util.ResourceUtil;
import com.wiwi.jsoil.util.WxUtil;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberLoginController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(MemberLoginController.class);
  MemberService memberService = new MemberService();

  @RequestMapping(value={"/mobile/member/login"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String login(Locale locale, HttpServletRequest request, Model model, HttpSession session)
    throws Exception
  {
    model.addAttribute("appName", AppConstants.APP_NAME);
    model.addAttribute("companyName", AppConstants.COMPNAY_NAME);
    model.addAttribute("siteCode", getCurrentHotel().getCode());

    model.addAttribute("wxOauth2Url", WxUtil.getOauto2WebUrl(AppConstants.getProperty("WX_OAUTH2_REDIRECT_LOGIN_URI")));
    processOperationMessage(model);
    return "/site/mobile/web/login";
  }

  @RequestMapping(value={"/mobile/member/login/register"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String register(Locale locale, HttpServletRequest request, Model model, HttpSession session)
    throws Exception
  {
    model.addAttribute("appName", AppConstants.APP_NAME);
    model.addAttribute("companyName", AppConstants.COMPNAY_NAME);
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    processOperationMessage(model);
    return "/site/mobile/web/register";
  }

  @RequestMapping(value={"/mobile/member/login/registerAction.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String registerAction(@RequestParam String loginName, @RequestParam String password, Model model, @RequestParam String name, @RequestParam String verificationCode)
    throws Exception
  {
    MemberQ memberQ = new MemberQ();
    memberQ.setTelphone(loginName);
    List memberList = this.memberService.getList(memberQ);
    if ((memberList != null) && (memberList.size() > 0)) {
      model.addAttribute("message", "手机号码重复");
      return "/site/mobile/web/register";
    }

    String validateResult = validateVerificationCode(loginName, verificationCode);
    if ("success".equals(validateResult)) {
      Member member = new Member();
      member.setTelphone(loginName);
      member.setNickName(name);
      member.setName(name);
      this.memberService.insert(member, password);
      model.addAttribute("loginName", loginName);
      model.addAttribute("password", password);
      return "redirect:/mobile/member/loginAction";
    }
    model.addAttribute("message", validateResult);
    model.addAttribute("loginName", loginName);
    model.addAttribute("name", name);
    return "/site/mobile/web/register";
  }

  @RequestMapping({"/mobile/member/loginAction"})
  public String loginAction(@RequestParam String loginName, @RequestParam String password, Model model, HttpSession session)
    throws Exception
  {
    logger.info("网页登录登录.........【{}】", loginName);
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = null;
    try {
      member = this.memberService.checkMemberLogin(loginName, password);
    }
    catch (DaoException e) {
      logger.error("{}登录失败。异常信息：{}", loginName, e);
      e.printStackTrace();
    } catch (RenderException e) {
      logger.error("{}登录失败。异常信息：{}", loginName, e);
      e.printStackTrace();
    }
    if (member != null) {
      User user = new User();
      user.setId(member.getId());
      user.setLoginName(loginName);
      user.setName(member.getName());
      user.setTelephone(member.getTelphone());

      LoginLog loginLog = new LoginLog();
      loginLog.setUser(user);
      loginLog.setLoginTime(new Date());
      loginLog.setClientIp(getRequest().getRemoteAddr());
      loginLog.setClientHost(getRequest().getRemoteHost());
      try
      {
        member.setLastLoginTime(new Date());
        this.memberService.update(member);
      } catch (DaoException e) {
        logger.error("{}修改会员登录时间失败。异常信息：{}", loginName, e);
        e.printStackTrace();
      } catch (RenderException e) {
        logger.error("{}修改会员登录时间失败。异常信息：{}", loginName, e);
        e.printStackTrace();
      }
      session.setAttribute("MeMbErLoGiNsEsSiOnKeY", member);
      model.addAttribute("member", member);
      model.addAttribute("ResourceUtil", ResourceUtil.class);
      String beforeLoginUrl = (String)getRequest().getSession().getAttribute("BeFoReLoGiNuRl");

      logger.info("登录成功后：AppConstants.BEFORE_LOGIN_URL：{}", beforeLoginUrl);

      if ((beforeLoginUrl != null) && (beforeLoginUrl.length() > 0)) {
        getRequest().getSession().setAttribute("BeFoReLoGiNuRl", "");
        processOperationMessage(model);
        return "redirect:" + beforeLoginUrl.toString();
      }
      return "/site/mobile/web/member_index";
    }

    setOperationMessage("用户名或密码错误，请重新登录！");
    return "redirect:/mobile/member/login";
  }

  @RequestMapping(value={"/site/mobile/member/getVerificationCode.do"}, produces={"application/x-www-form-urlencoded; charset=utf-8"})
  @ResponseBody
  public String getVerificationCode(@RequestParam String telphone) {
//    JSONResult json = SMSUtil.sendRegisterCode(telphone);
//    JSONObject jsonObj = json.getJSONObject();
//    if (jsonObj.getBoolean("success")) {
//      return jsonObj.getJSONObject("result").getString("code");
//    }
    return "failed";
  }

  private String validateVerificationCode(@RequestParam String telphone, @RequestParam String code)
  {
//    JSONResult json = SMSUtil.validateCode(telphone, code);
//    JSONObject jsonObj = json.getJSONObject();
//    if (jsonObj.getBoolean("success")) {
//      return "success";
//    }
//    return jsonObj.getJSONObject("result").getString("error");'
	  return "";
  }
}