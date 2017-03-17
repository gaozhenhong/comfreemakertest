package com.wiwi.jsoil.cms.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.cms.model.Articles;
import com.wiwi.jsoil.cms.model.ArticlesQ;
import com.wiwi.jsoil.cms.service.ArticlesService;
import com.wiwi.jsoil.sys.CategoryUtil;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.CategoryService;

@Controller
@RequestMapping({"/cms/articles/"})
public class ArticlesController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(ArticlesController.class);
  ArticlesService service;

  public ArticlesController()
  {
    this.service = new ArticlesService();
  }

  @RequestMapping({"list.do"})
  public String list(@RequestParam(required=false) String method, @ModelAttribute("query") ArticlesQ query, Model model) throws Exception {
    if ((query.getOrderByProperty() == null) || (query.getOrderByProperty().trim().length() < 1)) {
      query.setOrderByProperty("modified");
      query.setOrderByKind("desc");
    }
    if ("add".equals(method))
      query.setAuthor(getUser());

    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("pager", query);

    model.addAttribute("typeList", getArticleCategoryList());

    model.addAttribute("method", method);

    processOperationMessage(model);

    return "/cms/articles/articlesList"; } 
  private java.util.List<com.wiwi.jsoil.sys.model.Category> getArticleCategoryList() { // Byte code:
      	 
	  CategoryService cservice = new CategoryService();
	  try{
		  
       List allCategoryList = cservice.getListByCode(CategoryUtil.ORG_TYPE_CODE);
       
       return allCategoryList;
	  }catch(Exception e){
			  e.printStackTrace();
	  }
      	return null;
  }
  
  @RequestMapping({"add.do"})
  public String addView(Model model) { model.addAttribute("list", getArticleCategoryList());
    model.addAttribute("instance", new Articles());

    return "/cms/articles/articlesAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Articles instance, Model model) throws Exception
  {
    User user = getUser();
    instance.setAuthor(user);
    instance.setCreateDate(new Date());
    instance.setModified(new Date());

    instance.setStatus(Integer.valueOf(0));

    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model, @RequestParam(required=false) String method) throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("list", getArticleCategoryList());
    model.addAttribute("method", method);

    return "/cms/articles/articlesEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Articles instance, Model model, @RequestParam(required=false) String method) throws Exception
  {
    instance.setModified(new Date());
    this.service.update(instance);
    model.addAttribute("method", method);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, Model model, @RequestParam(required=false) String method)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("method", method);

    return "/cms/articles/articlesView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model, @RequestParam(required=false) String method)
    throws Exception
  {
    Articles bean = this.service.get(id);
    if (bean != null) {
      bean.setStatus(Integer.valueOf(4));
      this.service.update(bean);
    }
    model.addAttribute("method", method);

    setOperationMessage("删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model, @RequestParam(required=false) String method)
    throws Exception
  {
    this.service.batchDelete(ids);

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"approveAction.do"})
  public String approveAction(@RequestParam(required=true) long id, @RequestParam(required=true) int status, Model model, @RequestParam(required=false) String method)
    throws Exception
  {
    Articles bean = this.service.get(id);
    if (bean != null) {
      bean.setStatus(Integer.valueOf(status));
      bean.setModified(new Date());
      this.service.update(bean);
    }
    model.addAttribute("method", method);

    setOperationMessage("操作成功！");

    return "redirect:list.do";
  }
}