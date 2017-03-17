package com.wiwi.edb.settlement.controller;

import java.util.Date;

import org.json.JSONArray;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderQ;
import com.wiwi.edb.settlement.model.SettlementQ;
import com.wiwi.edb.settlement.service.SettlementService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.util.JSONUtil;

@Controller
@RequestMapping({"/settlement/mySettlement/"})
public class MySettlementController extends BaseController
{
  SettlementService service;
  HotelOrderService hotelOrderService;

  public MySettlementController()
  {
    this.service = new SettlementService();
    this.hotelOrderService = new HotelOrderService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") SettlementQ query, Model model) throws Exception {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    Organization company = getCompany();

    OrderQ orderQ = new OrderQ();
    orderQ.setCompanyId(company.getId());
    orderQ.setRecordPerPage(-1);
    model.addAttribute("list", this.service.getList(query));

    return "/settlement/mySettlement/mySettlementList";
  }

  @RequestMapping({"waitForConfirmSettlementList.do"})
  public String waitForConfirmSettlementList(@ModelAttribute("query") SettlementQ query, Model model)
    throws Exception
  {
    query.setStatus(Integer.valueOf(1));
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("pager", query);
    processOperationMessage(model);

    return "/settlement/mySettlement/waitForConfirmSettlementList";
  }

  @RequestMapping({"confirmSettlementAction.do"})
  public String confirmSettlementAction(@RequestParam Long settlementId, Model model) throws Exception
  {
    this.service.confirmSettlement(settlementId, getUser(), "");
    return "redirect:allWaitForConfirmSettlement.do";
  }

  @RequestMapping({"canSettlementSupplierList.do"})
  public String canSettlementSupplierList(@ModelAttribute("query") OrderQ orderQuery, @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date beginDate, @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, Model model)
    throws Exception
  {
    int selfCompanyId = getCompany().getId().intValue();
    JSONArray canSettlementSupplierList = this.hotelOrderService.getCanSettlementSupplierList(selfCompanyId, beginDate, endDate);
    model.addAttribute("canSettlementSupplierList", canSettlementSupplierList);
    model.addAttribute("JSONUtil", JSONUtil.class);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    return "/settlement/mySettlement/canSettlementSupplierList";
  }
}