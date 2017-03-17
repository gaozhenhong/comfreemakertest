package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.LoginLogQ;
import com.wiwi.jsoil.sys.service.LoginLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/loginLog"})
public class LoginLogController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(LoginLogController.class);
  LoginLogService service;

  public LoginLogController()
  {
    this.service = new LoginLogService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") LoginLogQ query, Model model) throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/loginLog/loginLogList";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) String id, Model model)
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
    this.service.delete(ids);

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }
}