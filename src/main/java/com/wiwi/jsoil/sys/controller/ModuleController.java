package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Group;
import com.wiwi.jsoil.sys.model.Module;
import com.wiwi.jsoil.sys.model.ModuleQ;
import com.wiwi.jsoil.sys.service.ModuleService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/module/"})
public class ModuleController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);
  ModuleService service;

  public ModuleController()
  {
    this.service = new ModuleService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") ModuleQ query, Model model) throws Exception {
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("pager", query);
    processOperationMessage(model);
    return "sys/module/moduleList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model) throws Exception {
    ModuleQ query = new ModuleQ();
    query.setRecordPerPage(-1);
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("instance", new Module());
    return "sys/module/moduleAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Module instance, Model model) throws Exception
  {
    logger.info("addAction ..........");
    this.service.save(instance);
    setOperationMessage("添加成功[" + instance.getName() + "]！");
    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam int id, Model model) throws Exception
  {
    logger.info("edit ..........");
    ModuleQ query = new ModuleQ();
    query.setRecordPerPage(-1);
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("instance", this.service.get(id));
    return "sys/module/moduleEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Module instance, Model model) throws Exception
  {
    logger.info("editAction ..........");
    this.service.update(instance);
    setOperationMessage("修改成功[" + instance.getName() + "]！");
    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam int id, Model model) throws Exception {
    logger.info("view ..........");
    ModuleQ query = new ModuleQ();
    query.setRecordPerPage(-1);
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("instance", this.service.get(id));
    return "sys/module/moduleView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) int id, Model model) throws Exception
  {
    logger.info("delete ..........");
    this.service.delete(id);
    setOperationMessage("删除成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model) throws Exception
  {
    logger.info("batch delete ..........");
    this.service.batchDelete(ids);
    setOperationMessage("批量删除成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"allModuleList2Json.do"})
  @ResponseBody 
  public List<Module> allModuleList2Json()
    throws Exception
  {
    List list = this.service.getAllModuleList();
    return list;
  }

  @RequestMapping({"userModuleList2Json.do"})
  @ResponseBody
  public List<Module> userModuleList2Json(@RequestBody Group group)
    throws Exception
  {
    List list = new ArrayList();

    list = this.service.getMenuListByGroupId(group.getId().intValue());

    return list;
  }
}