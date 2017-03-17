package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Dictionary;
import com.wiwi.jsoil.sys.model.DictionaryOption;
import com.wiwi.jsoil.sys.model.DictionaryOptionQ;
import com.wiwi.jsoil.sys.service.DictionaryOptionService;
import com.wiwi.jsoil.sys.service.DictionaryService;
import com.wiwi.jsoil.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/dictionary/option/"})
public class DictionaryOptionController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(DictionaryOptionController.class);
  DictionaryOptionService service;

  public DictionaryOptionController()
  {
    this.service = new DictionaryOptionService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") DictionaryOptionQ query, Model model)
    throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/dictionary/option/dictionaryOptionList";
  }

  @RequestMapping({"add.do"})
  public String addView(@RequestParam(required=true, value="dictionaryId") Long dictionaryId, Model model)
    throws DaoException, RenderException
  {
    Dictionary dict = new DictionaryService().get(dictionaryId.longValue());
    DictionaryOption option = new DictionaryOption();
    option.setDictionaryId(dictionaryId);
    option.setDictionaryCode(dict.getCode());
    model.addAttribute("instance", option);

    return "/sys/dictionary/option/dictionaryOptionAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") DictionaryOption instance, HttpServletRequest request, Model model)
    throws Exception
  {
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setLogo(thumbId);
    this.service.insert(instance);

    setOperationMessage("添加成功！");
    return "redirect:/sys/dictionary/list.do?chooseDictionaryId=" + instance.getDictionaryId();
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/dictionary/option/dictionaryOptionEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") DictionaryOption instance, Model model)
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

    return "/sys/dictionary/option/dictionaryOptionView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true, value="dictionaryId") Long dictionaryId, @RequestParam(required=true) long id, Model model)
    throws Exception
  {
    this.service.delete(id);

    setOperationMessage("删除成功！");

    return "redirect:/sys/dictionary/list.do?chooseDictionaryId=" + dictionaryId;
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model)
    throws Exception
  {
    this.service.batchDelete(ids);

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"optionFragment.do"})
  public String optionFragment(@RequestParam long dictionaryId, Model model)
    throws Exception
  {
    Dictionary dict = new DictionaryService().get(dictionaryId);
    model.addAttribute("optionList", this.service.getListByDictionaryId(dict.getId().longValue()));
    model.addAttribute("dictionary", dict);
    return "/sys/dictionary/dictionaryFragment :: optionFragment";
  }
}