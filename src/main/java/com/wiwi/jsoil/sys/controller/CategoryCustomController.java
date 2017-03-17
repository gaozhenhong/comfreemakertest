package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.CategoryQ;
import com.wiwi.jsoil.sys.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/category/{customCategoryCode}"})
public class CategoryCustomController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(CategoryCustomController.class);
  CategoryService service;

  public CategoryCustomController()
  {
    this.service = new CategoryService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") CategoryQ query, @PathVariable String customCategoryCode, Model model) throws Exception
  {
    query.setTopCode(customCategoryCode);

    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);
    model.addAttribute("customCategory", this.service.getByCode(customCategoryCode));

    processOperationMessage(model);
    logger.debug("category list.....");
    return "/sys/category/custom/categoryList";
  }

  @RequestMapping({"add.do"})
  public String addView(@PathVariable String customCategoryCode, Model model)
    throws DaoException, RenderException
  {
    CategoryQ query = new CategoryQ();
    query.setRecordPerPage(-1);
    query.setTopCode(customCategoryCode);
    model.addAttribute("instance", new Category());
    model.addAttribute("customCategory", this.service.getByCode(customCategoryCode));
    try {
      model.addAttribute("categoryList", this.service.getList(query));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "/sys/category/custom/categoryAdd";
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
  public String editView(@RequestParam long id, @PathVariable String customCategoryCode, Model model)
    throws Exception
  {
    CategoryQ query = new CategoryQ();
    query.setRecordPerPage(-1);
    query.setTopCode(customCategoryCode);
    model.addAttribute("customCategory", this.service.getByCode(customCategoryCode));
    try {
      model.addAttribute("categoryList", this.service.getList(query));
    } catch (Exception e) {
      e.printStackTrace();
    }
    model.addAttribute("instance", this.service.get(id));

    return "/sys/category/custom/categoryEdit";
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
  public String view(@RequestParam long id, @PathVariable String customCategoryCode, Model model)
    throws Exception
  {
    model.addAttribute("customCategory", this.service.getByCode(customCategoryCode));
    model.addAttribute("instance", this.service.get(id));

    return "/sys/category/custom/categoryView";
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