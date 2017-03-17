package com.wiwi.edb.order.controller;

import com.wiwi.edb.order.hotelOrder.HotelOrderUtil;
import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.hotelOrder.model.HotelOrderDetails;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.PayStatus;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.edb.order.model.OrderProcessQ;
import com.wiwi.edb.order.model.OrderQ;
import com.wiwi.edb.order.service.OrderDetailsService;
import com.wiwi.edb.order.service.OrderProcessService;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.ReservationRoomService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.freego.hotel.service.RoomTypeService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.User;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/order/"})
public class OrderController extends BaseController
{
  HotelOrderService service;
  RoomTypeService rtService;
  HotelService hotelService;
  RoomService roomService;

  public OrderController()
  {
    this.service = new HotelOrderService();
    this.rtService = new RoomTypeService();
    this.hotelService = new HotelService();
    this.roomService = new RoomService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") OrderQ query, Model model) throws Exception {
	User user = getUser();
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("supplierList", this.service.getSupplierList());

    model.addAttribute("pager", query);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);
    model.addAttribute("user",user);
    processOperationMessage(model);

    return "/order/orderList";
  }

  @RequestMapping({"allCanSettlementOrderList.do"})
  public String allCanSettlementOrderList(@ModelAttribute("query") OrderQ orderQuery, Model model) throws Exception
  {
    List allCanSettlementOrderList = this.service.getAllCanSettlementOrderList(orderQuery);
    model.addAttribute("supplierList", this.service.getSupplierList());
    model.addAttribute("canSettlementOrderList", allCanSettlementOrderList);

    model.addAttribute("pager", orderQuery);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    return "/order/allCanSettlementOrderList";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/order/orderEdit";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    Order order = this.service.get(id);
    Map reservationRoomMap = new ReservationRoomService().getOrderReservationRooms(id);
    model.addAttribute("instance", order);
    OrderProcessService opService = new OrderProcessService();
    OrderProcessQ opq = new OrderProcessQ();
    opq.setRecordPerPage(-1);
    opq.setOrderId(Long.valueOf(id));
    opq.setOrderByKind("DESC");
    opq.setOrderByProperty("processTime");
    model.addAttribute("processList", opService.getList(opq));
    model.addAttribute("orderDetailsList", new OrderDetailsService().getListByOrderId(id));
    model.addAttribute("reservationRoomMap", reservationRoomMap);
    model.addAttribute("OrderStatus", Order.Status.class);
    Hotel hotel = this.hotelService.get(order.getSupplierId());
    model.addAttribute("supplier", hotel);

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/order/orderView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model)
    throws Exception
  {
    this.service.delete(id, getUser());

    setOperationMessage("删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model)
    throws Exception
  {
    this.service.batchDelete(ids, getUser());

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"getRoomTypePrice.do"})
  @ResponseBody
  public String getRoomTypePrice(@RequestBody HotelOrderDetails hotelOrderDetails, Model model)
    throws Exception
  {
    return HotelOrderUtil.getPrice(hotelOrderDetails).toString();
  }

  @RequestMapping({"haveReservationRoomByRoomId.do"})
  @ResponseBody
  public boolean haveReservationRoomByRoomId(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="roomId", required=true) Long roomId, @RequestParam(value="consumeBeginTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date consumeBeginTime, @RequestParam(value="consumeEndTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date consumeEndTime, Model model)
    throws Exception
  {
    return this.roomService.haveReservationRoomByRoomId(hotelId, roomId, consumeBeginTime, consumeEndTime);
  }

  @RequestMapping({"cancelOrderAction.do"})
  public String cancelOrderAction(@RequestParam(value="id", required=true) long id) throws Exception
  {
    User user = getUser();
    this.service.setAllStatus(id, Order.Status.CANCEL_ORDER, user, "");
    this.service.setStatusOnlyOrder(id, Order.Status.CANCEL_ORDER);
    setOperationMessage("退单成功！");

    return "redirect:list.do";
  }
}