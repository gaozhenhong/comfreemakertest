package com.wiwi.edb.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderQ;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.HotelQ;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.freego.hotel.service.RoomTypeService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;

@Controller
@RequestMapping({"/order/myOrder"})
public class MyOrderController extends BaseController
{
  HotelOrderService service;
  RoomTypeService rtService;
  RoomService roomService;
  HotelService hotelService;

  public MyOrderController()
  {
    this.service = new HotelOrderService();
    this.rtService = new RoomTypeService();
    this.roomService = new RoomService();
    this.hotelService = new HotelService(); }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") OrderQ query, Model model) throws Exception {
    User user = getUser();
    Organization company = getCompany();
    query.setCompanyId(company.getId());

    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("hotelList", new HotelService().getHotelListByUserId(user));

    model.addAttribute("pager", query);
    
    model.addAttribute("user", user);

    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);

    processOperationMessage(model);

    return "/order/myOrder/myOrderList";
  }

  @RequestMapping({"myWaitForConfirmOrderList.do"})
  public String myWaitForConfirmOrderList(@ModelAttribute("query") OrderQ query, Model model) throws Exception
  {
    User user = getUser();
    Organization company = getCompany();
    query.setCompanyId(company.getId());
    query.setStatus(Order.Status.RESERVATION);
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("hotelList", new HotelService().getHotelListByUserId(user));

    model.addAttribute("pager", query);

    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);
    model.addAttribute("user", user);
    processOperationMessage(model);

    return "/order/myOrder/myWaitForConfirmOrderList";
  }

  @RequestMapping({"confirmOrderAction.do"})
  public String confirmOrderAction(@RequestParam(value="orderId", required=true) Long orderId, Model model)
    throws Exception
  {
    this.service.confirmOrder(orderId.longValue(), getUser(), "");
    return "redirect:myWaitForConfirmOrderList.do";
  }

  @RequestMapping({"rejectOrderAction.do"})
  public String rejectOrderAction(@RequestParam(value="orderId", required=true) Long orderId, Model model)
    throws Exception
  {
    this.service.rejectOrder(orderId.longValue(), getUser(), "");
    return "redirect:myWaitForConfirmOrderList.do";
  }

  @RequestMapping({"otherHotelCommentList.do"})
  public String otherHotelCommentList(@ModelAttribute("query") OrderQ query, Model model)
    throws Exception
  {
    User user = getUser();
    Organization company = getCompany();
    query.setCompanyId(company.getId());
    HotelQ hotelQ = new HotelQ();
    hotelQ.setRecordPerPage(-1);
    hotelQ.setDelete(false);
    hotelQ.setStatus(Hotel.Status.OPENING.toString());
    model.addAttribute("list", this.service.getOtherHotelCommendList(query, company.getId().intValue()));
    model.addAttribute("hotelList", this.hotelService.getList(hotelQ));
    model.addAttribute("myHotelList", this.hotelService.getHotelListByUserId(user));

    model.addAttribute("pager", query);
    model.addAttribute("user", user);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);

    processOperationMessage(model);
    return "/order/myOrder/otherHotelCommentOrder";
  }

  @RequestMapping({"myCommentOrderList.do"})
  public String myCommentOrderList(@ModelAttribute("query") OrderQ query, Model model)
    throws Exception
  {
    User user = getUser();
    Organization company = getCompany();

    model.addAttribute("list", this.service.getyMyCommentOrderList(query, company.getId().intValue()));
    model.addAttribute("hotelList", new HotelService().getHotelListByUserId(user));

    model.addAttribute("pager", query);

    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);
    model.addAttribute("user", user);
    processOperationMessage(model);

    return "/order/myOrder/myCommentOrderList";
  }

  @RequestMapping({"canSettlementOrderList.do"})
  public String canSettlementOrderList(@ModelAttribute("query") OrderQ orderQuery, Model model) throws Exception
  {
    int selfCompanyId = getCompany().getId().intValue();
    List canSettlementOrderList = this.service.getCanSettlementOrderList(orderQuery, selfCompanyId);

    User user = getUser();
    model.addAttribute("hotelList", new HotelService().getHotelListByUserId(user));
    model.addAttribute("canSettlementOrderList", canSettlementOrderList);
    model.addAttribute("user", user);
    model.addAttribute("pager", orderQuery);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    return "/order/myOrder/canSettlementOrderList";
  }

  @RequestMapping({"myCancleOrderList.do"})
  public String myCancleOrderList(@ModelAttribute("query") OrderQ query, Model model) throws Exception {
    Organization company = getCompany();
    query.setCompanyId(company.getId());

    List multiStatus = new ArrayList();
    multiStatus.add(Order.Status.CANCEL_ORDER.toString());
    multiStatus.add(Order.Status.CANCELED.toString());
    query.setMultiStatus(multiStatus);

    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("supplierList", this.service.getSupplierList());

    model.addAttribute("pager", query);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);

    processOperationMessage(model);

    return "/order/myOrder/myCancleOrder";
  }

  @RequestMapping({"confirmCancelOrder.do"})
  public String confirmCancelOrder(@RequestParam(value="id", required=true) long id) throws Exception
  {
    User user = getUser();
    this.service.setAllStatus(id, Order.Status.CANCELED, user, "");
    setOperationMessage("退单成功！");

    return "redirect:myCancleOrderList.do";
  }
}