package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.CategoryQ;
import com.wiwi.jsoil.sys.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/category"})
public class CategoryController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
  CategoryService service;

  public CategoryController()
  {
    this.service = new CategoryService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") CategoryQ query, Model model)
    throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);
    logger.debug("category list.....");
    return "/sys/category/categoryList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    CategoryQ query = new CategoryQ();
    query.setRecordPerPage(-1);
    model.addAttribute("instance", new Category());
    try {
      model.addAttribute("categoryList", this.service.getList(query));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "/sys/category/categoryAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Category instance, Model model)
    throws Exception
  {
    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    CategoryQ query = new CategoryQ();
    query.setRecordPerPage(-1);
    try {
      model.addAttribute("categoryList", this.service.getList(query));
    } catch (Exception e) {
      e.printStackTrace();
    }
    model.addAttribute("instance", this.service.get(id));

    return "/sys/category/categoryEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Category instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/category/categoryView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model)
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