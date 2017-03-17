package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.UserUtil;
import com.wiwi.jsoil.sys.model.OrganizationQ;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.model.UserQ;
import com.wiwi.jsoil.sys.service.OrganizationService;
import com.wiwi.jsoil.sys.service.ResourceService;
import com.wiwi.jsoil.sys.service.UserService;
import com.wiwi.jsoil.util.MD5Util;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUploadUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/user/"})
public class UserController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  OrganizationService orgService;
  UserService service;

  public UserController()
  {
    this.orgService = new OrganizationService();
    this.service = new UserService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") UserQ query, Model model) throws Exception {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/user/userList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
    throws Exception
  {
    model.addAttribute("instance", new User());
    model.addAttribute("orgList", this.orgService.getList(new OrganizationQ()));
    return "/sys/user/userAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") User instance, Model model, HttpSession session)
    throws Exception
  {
    instance.setCreateTime(new Date());
    instance.setCreateUserId(getUser().getId());
    String defaultPassword = RequestUtil.getParameter(getRequest(), "loginPswd", "123789");
    this.service.insert(instance, defaultPassword);
    setOperationMessage("添加成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("orgList", this.orgService.getList(new OrganizationQ()));

    return "/sys/user/userEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") User instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"personalInfo.do"})
  public String personalInfo(Model model, HttpSession session)
    throws Exception
  {
    User user = getUser();
    model.addAttribute("instance", user);
    model.addAttribute("orgList", this.orgService.getList(new OrganizationQ()));
    Resource resource = null;
    if (user.getLogoId() != 0L) {
      resource = new ResourceService().get(user.getLogoId());
    }

    if (resource == null) {
      resource = new Resource();
      String defaultUserLogoPath = "/resources/images/user.jpg";
      resource.setResPath(defaultUserLogoPath);
    }
    model.addAttribute("imgBasePath", ResourceUploadUtil.getFileBasePath(getRequest()));
    model.addAttribute("resource", resource);
    return "/sys/user/personalInfo";
  }

  @RequestMapping({"personalInfoEditAction.do"})
  public String personalInfoEditAction(@ModelAttribute("instance") User instance, @RequestParam(value="thumbId", required=false) Long logoId, Model model)
    throws Exception
  {
    instance.setLogoId(logoId.longValue());
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:personalInfo.do";
  }

  @RequestMapping({"changePassword.do"})
  public String changePassword(Model model, HttpSession session)
    throws Exception
  {
    model.addAttribute("instance", getUser());
    processOperationMessage(model);
    return "/sys/user/changePassword";
  }

  @RequestMapping({"changePasswordAction.do"})
  public String changePasswordAction(@RequestParam(value="newPassword", required=true) String newPassword, @RequestParam(value="oldPassword", required=true) String oldPassword, Model model, HttpSession session)
    throws Exception
  {
    String msg = "";
    User user = getUser();
    if (this.service.checkPswd(user.getId().longValue(), MD5Util.string2MD5(oldPassword))) {
      this.service.changePswd(user.getId().longValue(), MD5Util.string2MD5(newPassword));
      msg = "密码修改成功！";
    }
    else msg = "密码验证不对，请重新输入！";

    setOperationMessage(msg);
    return "redirect:changePassword.do";
  }

  @RequestMapping({"resetPassword.do"})
  public String resetPassword(@RequestParam(value="id", required=true) Long userId, Model model, HttpSession session)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(userId.longValue()));
    return "/sys/user/resetPassword";
  }

  @RequestMapping({"resetPasswordAction.do"})
  public String resetPasswordAction(@RequestParam(value="id", required=true) Long userId, @RequestParam(value="newPassword", required=true) String newPassword, Model model, HttpSession session)
    throws Exception
  {
    String msg = "";
    this.service.changePswd(userId.longValue(), MD5Util.string2MD5(newPassword));
    logger.debug("管理员重置了{}的密码。", userId);
    msg = "密码重置成功！";
    setOperationMessage(msg);
    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));
    return "/sys/user/userView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model)
    throws Exception
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

  @RequestMapping({"checkLoginNameRepeat.do"})
  public void checkLoginNameRepeat(HttpServletRequest request, HttpServletResponse response)
    throws DaoException, RenderException, IOException
  {
    response.reset();
    response.setContentType("text/html;charset=UTF-8");
    String loginName = request.getParameter("loginName");
    User user = this.service.getByLoginName(loginName);
    if (user != null)
      response.getWriter().print(false);
    else
      response.getWriter().print(true);
  }
}