package com.wiwi.edb.order.controller;

import com.wiwi.edb.order.model.OrderPayRecord;
import com.wiwi.edb.order.model.OrderPayRecordQ;
import com.wiwi.edb.order.service.OrderPayRecordService;
import com.wiwi.jsoil.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/order/payRecord/"})
public class OrderPayRecordController extends BaseController
{
  OrderPayRecordService service;

  public OrderPayRecordController()
  {
    this.service = new OrderPayRecordService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") OrderPayRecordQ query, Model model) throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/order/payRecord/orderPayRecordList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new OrderPayRecord());

    return "/order/payRecord/orderPayRecordAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") OrderPayRecord instance, Model model)
    throws Exception
  {
    this.service.insert(instance, getUser());

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/order/payRecord/orderPayRecordEdit";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/order/payRecord/orderPayRecordView";
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