package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.UserUtil;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.OrganizationQ;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.model.UserQ;
import com.wiwi.jsoil.sys.service.OrganizationService;
import com.wiwi.jsoil.sys.service.UserService;
import com.wiwi.jsoil.util.MD5Util;
import com.wiwi.jsoil.util.RequestUtil;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/companyUser/"})
public class CompanyUserController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(CompanyUserController.class);
  OrganizationService orgService;
  UserService service;

  public CompanyUserController()
  {
    this.orgService = new OrganizationService();
    this.service = new UserService(); }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") UserQ query, Model model, OrganizationService orgService) throws Exception {
    User user = getUser();

    Organization org = orgService.getCompany(user.getOrg().getId().intValue());

    query.setOtherCondition("where orgid in (select id from S_ORGANIZATION where code like '" + org.getCode() + "%')");

    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/user/companyUser/companyUserList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
    throws Exception
  {
    User user = getUser();

    Organization org = this.orgService.getCompany(user.getOrg().getId().intValue());

    OrganizationQ orgQ = new OrganizationQ();
    orgQ.setOtherCondition("where code like '" + org.getCode() + "%'");

    model.addAttribute("instance", new User());
    model.addAttribute("orgList", this.orgService.getList(orgQ));
    return "/sys/user/companyUser/companyUserAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") User instance, Model model, HttpSession session) throws Exception
  {
    instance.setCreateTime(new Date());
    instance.setCreateUserId(getUser().getId());
    String defaultPassword = RequestUtil.getParameter(getRequest(), "loginPswd", "123789");
    this.service.insert(instance, defaultPassword);
    setOperationMessage("添加成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model) throws Exception
  {
    User user = getUser();

    Organization org = this.orgService.getCompany(user.getOrg().getId().intValue());

    OrganizationQ orgQ = new OrganizationQ();
    orgQ.setOtherCondition("where code like '" + org.getCode() + "%'");

    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("orgList", this.orgService.getList(orgQ));

    return "/sys/user/companyUser/companyUserEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") User instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"resetPassword.do"})
  public String resetPassword(@RequestParam(value="id", required=true) Long userId, Model model, HttpSession session)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(userId.longValue()));
    return "/sys/user/companyUser/resetPassword";
  }

  @RequestMapping({"resetPasswordAction.do"})
  public String resetPasswordAction(@RequestParam(value="id", required=true) Long userId, @RequestParam(value="newPassword", required=true) String newPassword, Model model, HttpSession session) throws Exception {
    String msg = "";
    this.service.changePswd(userId.longValue(), MD5Util.string2MD5(newPassword));
    logger.debug("管理员重置了{}的密码。", userId);
    msg = "密码重置成功！";
    setOperationMessage(msg);
    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model) throws Exception {
    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));
    return "/sys/user/companyUser/userView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model) throws Exception
  {
    String msg = "";
    if (UserUtil.isSystemAdmin(id))
      msg = "删除失败！系统管理员不能被删除！";
    else {
      this.service.delete(id);
      msg = "删除成功！";
    }
    setOperationMessage(msg);

    return "redirect:list.do";
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model)
    throws Exception
  {
    this.service.batchDelete(ids);

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }
}