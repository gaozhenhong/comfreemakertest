package com.wiwi.freego.hotel.orderGuest.controller;

import com.wiwi.freego.hotel.orderGuest.model.OrderGuestMapping;
import com.wiwi.freego.hotel.orderGuest.model.OrderGuestMappingQ;
import com.wiwi.freego.hotel.orderGuest.service.OrderGuestMappingService;
import com.wiwi.jsoil.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/hotel/guest/orderGuestMapping/"})
public class OrderGuestMappingController
  extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(OrderGuestMappingController.class);
  OrderGuestMappingService service = new OrderGuestMappingService();
  
  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") OrderGuestMappingQ query, Model model)
    throws Exception
  {
    model.addAttribute("list", this.service.getList(query));
    
    model.addAttribute("pager", query);
    
    processOperationMessage(model);
    
    return "/hotel/guest/orderGuestMapping/order_guest_mappingList";
  }
  
  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new OrderGuestMapping());
    
    return "/hotel/guest/orderGuestMapping/order_guest_mappingAdd";
  }
  
  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") OrderGuestMapping instance, Model model)
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
    
    return "/hotel/guest/orderGuestMapping/order_guest_mappingEdit";
  }
  
  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") OrderGuestMapping instance, Model model)
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
    
    model.addAttribute("isModal", Boolean.valueOf(isModal == null ? true : isModal.booleanValue()));
    
    return "/hotel/guest/orderGuestMapping/order_guest_mappingView";
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
