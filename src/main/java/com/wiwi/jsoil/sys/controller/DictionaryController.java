package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Dictionary;
import com.wiwi.jsoil.sys.model.DictionaryQ;
import com.wiwi.jsoil.sys.service.DictionaryOptionService;
import com.wiwi.jsoil.sys.service.DictionaryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/dictionary/"})
public class DictionaryController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
  DictionaryService service;
  DictionaryOptionService optionService;

  public DictionaryController()
  {
    this.service = new DictionaryService();
    this.optionService = new DictionaryOptionService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") DictionaryQ query, @RequestParam(required=false, value="chooseDictionaryId") Long chooseDictionaryId, Model model)
    throws Exception
  {
    List list = this.service.getList(query);
    model.addAttribute("list", list);
    if ((chooseDictionaryId == null) && (list.size() > 0)) {
      chooseDictionaryId = ((Dictionary)list.get(0)).getId();
    }

    model.addAttribute("pager", query);
    model.addAttribute("list", list);
    model.addAttribute("chooseDictionaryId", chooseDictionaryId);
    processOperationMessage(model);

    return "/sys/dictionary/dictionaryList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new Dictionary());

    return "/sys/dictionary/dictionaryAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Dictionary instance, Model model)
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
    model.addAttribute("instance", this.service.get(id));

    return "/sys/dictionary/dictionaryEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Dictionary instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/sys/dictionary/dictionaryView";
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