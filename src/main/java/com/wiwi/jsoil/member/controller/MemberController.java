package com.wiwi.jsoil.member.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.member.model.Member;
import com.wiwi.jsoil.member.model.MemberQ;
import com.wiwi.jsoil.member.service.MemberService;
import com.wiwi.jsoil.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/member/manager/"})
public class MemberController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
  MemberService service;

  public MemberController()
  {
    this.service = new MemberService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") MemberQ query, Model model)
    throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/member/manager/memberList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new Member());

    return "/member/manager/memberAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Member instance, Model model)
    throws Exception
  {
    String defaultPassword = RequestUtil.getParameter(getRequest(), "password", "111111");
    this.service.insert(instance, defaultPassword);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam Long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/member/manager/memberEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Member instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam Long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/member/manager/memberView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) Long id, Model model)
    throws Exception
  {
    this.service.delete(id);

    setOperationMessage("删除成功！");

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