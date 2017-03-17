package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.ResourceLog;
import com.wiwi.jsoil.sys.model.ResourceLogQ;
import com.wiwi.jsoil.sys.service.ResourceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/resource/resourceLog/"})
public class ResourceLogController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(ResourceLogController.class);
  ResourceLogService service;

  public ResourceLogController()
  {
    this.service = new ResourceLogService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") ResourceLogQ query, Model model)
    throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/resource/resourceLog/resourceLogList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new ResourceLog());

    return "/sys/resource/resourceLog/resourceLogAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") ResourceLog instance, Model model)
    throws Exception
  {
    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam String id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/resource/resourceLog/resourceLogEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") ResourceLog instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam String id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/sys/resource/resourceLog/resourceLogView";
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
    this.service.batchDelete(ids);

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }
}