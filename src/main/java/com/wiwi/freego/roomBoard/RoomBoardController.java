package com.wiwi.freego.roomBoard;

import com.wiwi.edb.order.hotelOrder.HotelOrderUtil;
import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.hotelOrder.model.HotelOrderDetails;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.edb.order.model.OrderPayRecord;
import com.wiwi.edb.order.model.OrderPayRecord.FundType;
import com.wiwi.edb.order.service.OrderDetailsService;
import com.wiwi.edb.order.service.OrderPayRecordService;
import com.wiwi.freego.hotel.model.Guest;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.ReservationRoom;
import com.wiwi.freego.hotel.model.Room;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.freego.hotel.service.GuestService;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.ReservationRoomService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.freego.hotel.service.RoomTypeService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.util.CalendarUtils;
import com.wiwi.jsoil.util.DateUtils;
import com.wiwi.jsoil.util.JSONUtil;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/roomBoard/"})
public class RoomBoardController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(RoomBoardController.class);
  HotelService hotelService;
  HotelOrderService hotelOrderService;
  RoomTypeService roomTypeService;
  RoomService roomService;

  public RoomBoardController()
  {
    this.hotelService = new HotelService();
    this.hotelOrderService = new HotelOrderService();
    this.roomTypeService = new RoomTypeService();
    this.roomService = new RoomService();
  }

  @RequestMapping({"list.do"})
  public String list(@RequestParam(value="hotelId", required=false) Long hotelId, @RequestParam(value="firstDay", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date firstDay, @RequestParam(value="dayNumber", required=false) Integer dayNumber, @RequestParam(value="modal", required=false) String modal, Model model)
    throws Exception
  {
	User user = getUser();
    List myHotelList = this.hotelService.getHotelListByUserId(getUser());
    if ((myHotelList == null) || (myHotelList.size() < 1))
      return "/roomBoard/noHotelInfo";

    model.addAttribute("myHotelList", myHotelList);
    model.addAttribute("user", user);
    if (hotelId == null)
      hotelId = ((Hotel)myHotelList.get(0)).getId();
    
    System.out.println("hotelId=="+hotelId);
    

    if (firstDay == null)
      firstDay = DateUtils.addDay(new Date(), -2);
    if (dayNumber == null)
      dayNumber = Integer.valueOf(10);
    List roomTypeList = this.roomTypeService.getOpeningList(hotelId);
    Map roomTypeMap = new HashMap();
    for (Iterator localIterator1 = roomTypeList.iterator(); localIterator1.hasNext(); ) { RoomType rt = (RoomType)localIterator1.next();
      roomTypeMap.put(rt.getId(), rt);
    }

    Date endDate = DateUtils.addDay(firstDay, dayNumber.intValue());
    List reservationRoomList = new ReservationRoomService().getList(hotelId, firstDay, endDate);
    Map reservationRoomMap = new HashMap();

    for (Iterator localIterator2 = reservationRoomList.iterator(); localIterator2.hasNext(); ) { ReservationRoom room = (ReservationRoom)localIterator2.next();
      reservationRoomMap.put(room.genRoomKey(), room);
    }

    System.out.println("hotelId=="+hotelId);
    model.addAttribute("hotel", this.hotelService.get(hotelId.longValue()));
    model.addAttribute("roomList", this.roomService.getListByHotelId(hotelId.longValue()));
    model.addAttribute("modal", modal);
    model.addAttribute("roomTypeList", roomTypeList);
    model.addAttribute("firstDay", firstDay);
    model.addAttribute("dayNumber", dayNumber);
    model.addAttribute("roomTypeMap", roomTypeMap);
    model.addAttribute("reservationRoomMap", reservationRoomMap);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("CalendarUtils", CalendarUtils.class);
    model.addAttribute("JSONUtil", JSONUtil.class);
    processOperationMessage(model);
    return "/roomBoard/roomBoardList";
  }

  @RequestMapping({"editOrder.do"})
  public String editOrder(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="orderId", required=true) Long orderId, @RequestParam(value="modal", required=false) String modal, Model model)
    throws DaoException, RenderException
  {
    List orderDetailsList = new OrderDetailsService().getListByOrderId(orderId.longValue());
    Order order = this.hotelOrderService.get(orderId.longValue());
    Map reservationRoomMap = new ReservationRoomService().getOrderReservationRooms(orderId.longValue());

    model.addAttribute("roomList", this.roomService.getListByHotelId(hotelId.longValue()));
    model.addAttribute("orderDetailsList", orderDetailsList);
    model.addAttribute("reservationRoomMap", reservationRoomMap);
    model.addAttribute("instance", order);
    model.addAttribute("hotelId", hotelId);
    model.addAttribute("modal", modal);
    model.addAttribute("DateUtils", DateUtils.class);
    return "/roomBoard/orderEdit";
  }

  @RequestMapping({"editOrderAction.do"})
  public String editOrderAction(@ModelAttribute("instance") Order order, @RequestParam(value="modal", required=false, defaultValue="") String modal, HttpServletRequest request, Model model)
    throws Exception
  {
    logger.debug("通过房态面板修改订单！");
    long hotelId = order.getSupplierId();
    String[] consumeBeginTimes = request.getParameterValues("consumeBeginTime");
    int[] consumeDays = RequestUtil.getIntegerParameters(request, "consumeDay");
    double[] rowTotalPrices = RequestUtil.getDoubleParameters(request, "roomTotalPrice");
    long[] roomIds = RequestUtil.getLongParameters(request, "roomId");
    Date consumeBeginTime = null;
    Date consumeEndTime = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    double allPrice = 0D;
    Room room = null;
    List hotelOrderDetailsList = new ArrayList();
    boolean haveEnoughRoom = true;
    long roomTypeId = 0L;
    for (int i = 0; i < roomIds.length; ++i)
    {
      String beginTime = consumeBeginTimes[i];
      consumeBeginTime = sdf.parse(beginTime);
      consumeEndTime = DateUtils.addDay(consumeBeginTime, consumeDays[i]);
      if (this.roomService.haveReservationRoomByRoomId(Long.valueOf(hotelId), Long.valueOf(roomIds[i]), consumeBeginTime, consumeEndTime, order.getId().longValue())) {
        haveEnoughRoom = false;
        break;
      }
      HotelOrderDetails orderDetails = new HotelOrderDetails();
      orderDetails.setConsumeBeginDate(consumeBeginTime);
      orderDetails.setOrderNumber(Integer.valueOf(1));
      orderDetails.setConsumeDay(consumeDays[i]);
      room = this.roomService.get(roomIds[i]);
      orderDetails.setRemark(room.getRoomType().getName() + room.getRoomNo());
      roomTypeId = room.getRoomType().getId().longValue();
      orderDetails.setRoomTypeId(Long.valueOf(roomTypeId));
      orderDetails.setSingleRoomPrice(rowTotalPrices[i]);
      allPrice = allPrice + rowTotalPrices[i];
      Date consumeDate = null;
      List reservationRoomList = new ArrayList();
      double[] roomDayPrices = RequestUtil.getDoubleParameters(request, "roomDayPrice-" + room.getId());
      for (int cyc = 0; cyc < consumeDays[i]; ++cyc) {
        ReservationRoom reservationRoom = new ReservationRoom();

        reservationRoom.setRoomTypeId(Long.valueOf(roomTypeId));
        reservationRoom.setRoomId(room.getId());
        reservationRoom.setPrice(Double.valueOf(roomDayPrices[cyc]));
        consumeDate = DateUtils.addDay(consumeBeginTime, cyc);
        reservationRoom.setConsumeDate(consumeDate);
        reservationRoom.setRoomNo(room.getRoomNo());
        reservationRoom.setName(room.getRoomType().getName() + room.getRoomNo());
        reservationRoom.setHotelId(Long.valueOf(hotelId));
        reservationRoomList.add(reservationRoom);
      }
      String key = room.getId() + "#" + room.getRoomType().getName() + room.getRoomNo();
      Map reservationRoomMap = new HashMap();
      reservationRoomMap.put(key, reservationRoomList);
      orderDetails.setReservationRoomMap(reservationRoomMap);
      hotelOrderDetailsList.add(orderDetails);
    }
    if (haveEnoughRoom) {
      User user = getUser();

      double addPayPriceAll = 0D;
      String[] addPayTypes = request.getParameterValues("addPayType");
      double[] addPayPrices = RequestUtil.getDoubleParameters(request, "addPayPrice");
      if ((addPayPrices != null) && (addPayPrices.length > 0))
        for (int i = 0; i < addPayPrices.length; ++i)
          if (addPayPrices[i] > 0D) {
            addPayPriceAll = addPayPriceAll + addPayPrices[i];
            OrderPayRecord payRecord = new OrderPayRecord();
            payRecord.setAmount(Double.valueOf(addPayPrices[i]));
            payRecord.setFundType(OrderPayRecord.FundType.PAYMENT);
            payRecord.setOrderId(order.getId());
            payRecord.setPayTime(new Date());
            payRecord.setReceiverId(user.getId());
            payRecord.setPayType(addPayTypes[i]);
            new OrderPayRecordService().insert(payRecord, user);
          }



      this.hotelOrderService.plusPayPrice(order.getId().longValue(), addPayPriceAll);
      this.hotelOrderService.update(order.getId().longValue(), allPrice, order.getOrderName(), 
        order.getOrderTelephone(), order.getOrigin(), hotelOrderDetailsList, user);

      setOperationMessage("修改成功！");
    }
    else setOperationMessage("修改失败！该时段内已经有人预定了，请重新指定房间！");

    return "redirect:list.do?modal=" + modal + "&hotelId=" + hotelId;
  }

  @RequestMapping({"confirmOrderAction.do"})
  public String confirmOrderAction(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="orderId", required=true) Long orderId, @RequestParam(value="modal", required=false, defaultValue="") String modal, Model model)
    throws Exception
  {
    this.hotelOrderService.confirmOrder(orderId.longValue(), getUser(), "");
    return "redirect:list.do?modal=" + modal + "&hotelId=" + hotelId;
  }

  @RequestMapping({"deleteOrderAction.do"})
  public String deleteOrderAction(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="orderId", required=true) Long orderId, @RequestParam(value="modal", required=false, defaultValue="") String modal, Model model)
    throws Exception
  {
    this.hotelOrderService.deleteOrder(orderId.longValue(), getUser(), "");
    return "redirect:list.do?modal=" + modal + "&hotelId=" + hotelId;
  }

  @RequestMapping({"checkout.do"})
  public String checkout(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="orderId", required=true) Long orderId, @RequestParam(value="reservationRoomId", required=true) Long reservationRoomId, @RequestParam(value="modal", required=false, defaultValue="") String modal, Model model)
    throws DaoException, RenderException
  {
    Order order = this.hotelOrderService.get(orderId.longValue());
    ReservationRoom reservationRoom = new ReservationRoomService().get(reservationRoomId.longValue());
    OrderPayRecord deposit = new OrderPayRecordService().getDepositByOrder(orderId.longValue(), reservationRoom.getOrderDetailsId().longValue());
    model.addAttribute("reservationRoom", reservationRoom);
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", new OrderDetailsService().get(reservationRoom.getOrderDetailsId().longValue()));
    model.addAttribute("orderDetailsList", new OrderDetailsService().getListByOrderId(orderId.longValue()));
    model.addAttribute("deposit", deposit);
    model.addAttribute("modal", modal);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("hotelId", hotelId);

    return "/roomBoard/checkout";
  }

  @RequestMapping({"checkoutAction.do"})
  public String checkoutAction(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="orderId", required=true) Long orderId, @RequestParam(value="orderDetailsId", required=true) Long orderDetailsId, @RequestParam(value="reservationRoomId", required=true) Long reservationRoomId, @RequestParam(value="modal", required=false, defaultValue="") String modal, HttpServletRequest request, Model model)
    throws Exception
  {
    User user = getUser();
    Order order = this.hotelOrderService.get(orderId.longValue());

    OrderDetailsService odService = new OrderDetailsService();
    OrderDetails orderDetails = odService.get(orderDetailsId.longValue());
    double payTotalPrice = 0D;

    String drawbackType = request.getParameter("drawbackType");
    double drawbackAmount = RequestUtil.getDoubleParameter(request, "drawbackAmount");
    if (drawbackType != null) {
      OrderPayRecord payRecord = new OrderPayRecord();
      payRecord.setAmount(Double.valueOf(0D - drawbackAmount));
      payRecord.setFundType(OrderPayRecord.FundType.DRAWBACK);
      payRecord.setOrderId(order.getId());
      payRecord.setPayTime(new Date());
      payRecord.setReceiverId(user.getId());
      payRecord.setPayType(drawbackType);
      payRecord.setOrderDetailsId(orderDetailsId);
      new OrderPayRecordService().insert(payRecord, user);
      payTotalPrice = payTotalPrice - drawbackAmount;
    }

    String[] paymentTypes = request.getParameterValues("paymentType");
    double[] paymentAmounts = RequestUtil.getDoubleParameters(request, "paymentAmount");

    if (paymentTypes != null) {
      OrderPayRecord payRecord = null;
      for (int i = 0; i < paymentTypes.length; ++i) {
        payRecord = new OrderPayRecord();
        payRecord.setAmount(Double.valueOf(paymentAmounts[i]));
        payRecord.setFundType(OrderPayRecord.FundType.PAYMENT);
        payRecord.setOrderId(order.getId());
        payRecord.setPayTime(new Date());
        payRecord.setReceiverId(user.getId());
        payRecord.setPayType(paymentTypes[i]);
        payRecord.setOrderDetailsId(orderDetailsId);
        new OrderPayRecordService().insert(payRecord, user);
        payTotalPrice = payTotalPrice + paymentAmounts[i];
      }
    }

    odService.checkout(orderDetails, user);

    this.hotelOrderService.updatePayPrice(orderId.longValue(), order.getPayPrice().doubleValue() + payTotalPrice);
    model.addAttribute("order", order);

    model.addAttribute("orderDetails", orderDetails);
    model.addAttribute("orderDetailsList", new OrderDetailsService().getListByOrderId(orderId.longValue()));
    model.addAttribute("DateUtils", DateUtils.class);
    return "redirect:list.do?modal=" + modal + "&hotelId=" + hotelId;
  }

  @RequestMapping({"checkin.do"})
  public String checkin(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="orderId", required=true) Long orderId, @RequestParam(value="reservationRoomId", required=true) Long reservationRoomId, @RequestParam(value="modal", required=false, defaultValue="") String modal, Model model)
    throws DaoException, RenderException
  {
    Order order = this.hotelOrderService.get(orderId.longValue());
    ReservationRoom reservationRoom = new ReservationRoomService().get(reservationRoomId.longValue());
    model.addAttribute("reservationRoom", reservationRoom);
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", new OrderDetailsService().get(reservationRoom.getOrderDetailsId().longValue()));
    model.addAttribute("orderDetailsList", new OrderDetailsService().getListByOrderId(orderId.longValue()));
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("hotelId", hotelId);
    model.addAttribute("modal", modal);
    return "/roomBoard/checkin";
  }

  @RequestMapping({"checkinAction.do"})
  public String checkinAction(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="orderId", required=true) Long orderId, @RequestParam(value="orderDetailsId", required=true) Long orderDetailsId, @RequestParam(value="reservationRoomId", required=true) Long reservationRoomId, @RequestParam(value="modal", required=false, defaultValue="") String modal, HttpServletRequest request, Model model)
    throws Exception
  {
    User user = getUser();
    Order order = this.hotelOrderService.get(orderId.longValue());
    OrderDetailsService odService = new OrderDetailsService();
    OrderDetails orderDetails = odService.get(orderDetailsId.longValue());

    registerGuest(request, order, user);

    double depositAmount = RequestUtil.getDoubleParameter(request, "depositAmount");
    String depositType = request.getParameter("depositType");
    OrderPayRecord payRecord = new OrderPayRecord();
    payRecord.setAmount(Double.valueOf(depositAmount));
    payRecord.setFundType(OrderPayRecord.FundType.DEPOSIT);
    payRecord.setOrderId(order.getId());
    payRecord.setPayTime(new Date());
    payRecord.setReceiverId(user.getId());
    payRecord.setPayType(depositType);
    payRecord.setOrderDetailsId(orderDetailsId);
    new OrderPayRecordService().insert(payRecord, user);

    double[] paymentAmounts = RequestUtil.getDoubleParameters(request, "paymentAmount");
    String[] paymentTypes = request.getParameterValues("paymentType");
    double payTotalPrice = depositAmount;
    if (paymentTypes != null)
      for (int i = 0; i < paymentTypes.length; ++i) {
        payRecord = new OrderPayRecord();
        payRecord.setAmount(Double.valueOf(paymentAmounts[i]));
        payRecord.setFundType(OrderPayRecord.FundType.PAYMENT);
        payRecord.setOrderId(order.getId());
        payRecord.setPayTime(new Date());
        payRecord.setReceiverId(user.getId());
        payRecord.setPayType(paymentTypes[i]);
        payRecord.setOrderDetailsId(orderDetailsId);
        new OrderPayRecordService().insert(payRecord, user);
        payTotalPrice = payTotalPrice + paymentAmounts[i];
      }


    odService.checkin(orderDetails, user);

    this.hotelOrderService.updatePayPrice(orderId.longValue(), order.getPayPrice().doubleValue() + payTotalPrice);
    model.addAttribute("order", order);

    model.addAttribute("orderDetails", orderDetails);
    model.addAttribute("orderDetailsList", new OrderDetailsService().getListByOrderId(orderId.longValue()));
    model.addAttribute("DateUtils", DateUtils.class);
    return "redirect:list.do?modal=" + modal + "&hotelId=" + hotelId;
  }

  private void registerGuest(HttpServletRequest request, Order order, User user)
  {
    String[] guestNames = request.getParameterValues("guestName");
    String[] telephones = request.getParameterValues("telephone");
    int[] genders = RequestUtil.getIntegerParameters(request, "gender");
    String[] nations = request.getParameterValues("nation");
    String[] idCardTypes = request.getParameterValues("idCardType");
    String[] idCardNos = request.getParameterValues("idCardNo");
    String[] addressArray = request.getParameterValues("address");
    logger.debug("登记入住人信息：orderId=[{}],入住人数：{}", order.getId(), Integer.valueOf(guestNames.length));
    if (guestNames == null)
      return;
    List guestList = new ArrayList();
    for (int i = 0; i < guestNames.length; ++i) {
      Guest guest = new Guest();
      guest.setName(guestNames[i]);
      guest.setTelephone(telephones[i]);
      guest.setGender(Integer.valueOf(genders[i]));
      guest.setIdCardType(idCardTypes[i]);
      guest.setIdCardNo(idCardNos[i]);
      guest.setAddress(addressArray[i]);
      guest.setNation(nations[i]);
      guestList.add(guest);
    }
    new GuestService().batchUpdateOrderGuest(order, guestList, user);
  }

  @RequestMapping({"addOrder.do"})
  public String addOrder(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="roomTypeId", required=true) String roomTypeIdStrs, @RequestParam(value="roomId", required=true) String roomIdStrs, @RequestParam(value="consumeBeginDate", required=true) String consumeBeginDateStrs, @RequestParam(value="modal", required=false) String modal, Model model)
    throws DaoException, RenderException
  {
    String[] roomTypeIdArray = roomTypeIdStrs.split(",");
    String[] roomIdArray = roomIdStrs.split(",");
    String[] consumeBeginDateArray = consumeBeginDateStrs.split(",");

    HotelOrder hOrder = new HotelOrder();
    hOrder.setOrigin(null);
    hOrder.setPayType(null);
    hOrder.setHotelId(hotelId);
    double orderTotalPrice = 0D;
    double orderDetailsPrice = 0D;
    List oneDayOrderDetailsList = new ArrayList();
    Map priceMap = new HashMap();
    for (int i = 0; i < roomIdArray.length; ++i) {
      long roomId = Long.parseLong(roomIdArray[i]);
      RoomType roomType = this.roomTypeService.get(Long.parseLong(roomTypeIdArray[i]));
      Room room = this.roomService.get(roomId);
      OrderDetails orderDetails = new OrderDetails();
      Date consumeBeginTime = new Date();
      try {
        consumeBeginTime = new SimpleDateFormat("yyyy-MM-dd").parse(consumeBeginDateArray[i]);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      orderDetailsPrice = roomType.getPrice(consumeBeginTime);
      orderTotalPrice = orderTotalPrice + orderDetailsPrice;
      orderDetails.setConsumeBeginTime(consumeBeginTime);
      orderDetails.setOrderNumber(Integer.valueOf(1));
      orderDetails.setProductName(room.getRoomType().getName() + room.getRoomNo());
      orderDetails.setOrderPrice(Double.valueOf(orderDetailsPrice));
      orderDetails.setTotalPrice(Double.valueOf(orderDetailsPrice));
      orderDetails.setProductId(Long.valueOf(roomId));
      priceMap.put(roomId + "#" + consumeBeginDateArray[i], Double.valueOf(orderDetailsPrice));
      oneDayOrderDetailsList.add(orderDetails);
    }

    List orderDetailsList = HotelOrderUtil.combinationContinuousDay(oneDayOrderDetailsList);

    hOrder.setTotalPrice(orderTotalPrice);
    model.addAttribute("roomList", this.roomService.getListByHotelId(hotelId.longValue()));
    model.addAttribute("orderDetailsList", orderDetailsList);
    model.addAttribute("instance", hOrder);
    model.addAttribute("hotelId", hotelId);
    model.addAttribute("priceMap", priceMap);
    model.addAttribute("modal", modal);
    model.addAttribute("DateUtils", DateUtils.class);
    return "/roomBoard/orderAdd";
  }

  @RequestMapping({"addOrderAction.do"})
  public String addOrderAction(@ModelAttribute("instance") HotelOrder hotelOrder, @RequestParam(value="modal", required=false, defaultValue="") String modal, HttpServletRequest request, Model model)
    throws Exception
  {
    logger.debug("通过房态面板添加订单！");
    long hotelId = hotelOrder.getHotelId().longValue();
    String[] consumeBeginTimes = request.getParameterValues("consumeBeginTime");
    int[] consumeDays = RequestUtil.getIntegerParameters(request, "consumeDay");
    double[] rowTotalPrices = RequestUtil.getDoubleParameters(request, "roomTotalPrice");
    long[] roomIds = RequestUtil.getLongParameters(request, "roomId");
    Date consumeBeginTime = null;
    Date consumeEndTime = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    double allPrice = 0D;
    Room room = null;
    List hotelOrderDetailsList = new ArrayList();
    boolean haveEnoughRoom = true;
    long roomTypeId = 0L;

    for (int i = 0; i < roomIds.length; ++i)
    {
      String beginTime = consumeBeginTimes[i];
      consumeBeginTime = sdf.parse(beginTime);
      consumeEndTime = DateUtils.addDay(consumeBeginTime, consumeDays[i]);
      if (this.roomService.haveReservationRoomByRoomId(Long.valueOf(hotelId), Long.valueOf(roomIds[i]), consumeBeginTime, consumeEndTime)) {
        haveEnoughRoom = false;
        break;
      }
      HotelOrderDetails orderDetails = new HotelOrderDetails();
      orderDetails.setConsumeBeginDate(consumeBeginTime);
      orderDetails.setOrderNumber(Integer.valueOf(1));
      orderDetails.setConsumeDay(consumeDays[i]);
      room = this.roomService.get(roomIds[i]);
      orderDetails.setRemark(room.getRoomType().getName() + room.getRoomNo());
      roomTypeId = room.getRoomType().getId().longValue();
      orderDetails.setRoomTypeId(Long.valueOf(roomTypeId));
      orderDetails.setSingleRoomPrice(rowTotalPrices[i]);
      allPrice = allPrice + rowTotalPrices[i];
      Date consumeDate = null;
      List reservationRoomList = new ArrayList();
      double[] roomDayPrices = RequestUtil.getDoubleParameters(request, "roomDayPrice-" + room.getId());
      for (int cyc = 0; cyc < consumeDays[i]; ++cyc) {
        ReservationRoom reservationRoom = new ReservationRoom();

        reservationRoom.setRoomTypeId(Long.valueOf(roomTypeId));
        reservationRoom.setRoomId(room.getId());
        reservationRoom.setPrice(Double.valueOf(roomDayPrices[cyc]));
        consumeDate = DateUtils.addDay(consumeBeginTime, cyc);
        reservationRoom.setConsumeDate(consumeDate);
        reservationRoom.setRoomNo(room.getRoomNo());
        reservationRoom.setName(room.getRoomType().getName() + room.getRoomNo());
        reservationRoom.setHotelId(hotelOrder.getHotelId());
        reservationRoomList.add(reservationRoom);
      }
      String key = room.getId() + "#" + room.getRoomType().getName() + room.getRoomNo();
      Map reservationRoomMap = new HashMap();
      reservationRoomMap.put(key, reservationRoomList);
      orderDetails.setReservationRoomMap(reservationRoomMap);
      hotelOrderDetailsList.add(orderDetails);
    }
    hotelOrder.setOrderDetails(hotelOrderDetailsList);
    hotelOrder.setTotalPrice(allPrice);
    if (haveEnoughRoom) {
      User user = getUser();
      Order order = this.hotelOrderService.save(hotelOrder, Order.Status.CONFIRM, user);

      if (order.getPayPrice().doubleValue() > 0D) {
        OrderPayRecord payRecord = new OrderPayRecord();
        payRecord.setAmount(order.getPayPrice());
        payRecord.setFundType(OrderPayRecord.FundType.PAYMENT);
        payRecord.setOrderId(order.getId());
        payRecord.setPayTime(new Date());
        payRecord.setReceiverId(user.getId());
        payRecord.setPayType(order.getPayType());
        new OrderPayRecordService().insert(payRecord, user);
      }
      setOperationMessage("添加成功！");
    }
    else setOperationMessage("预定失败！该时段内已经有人预定了，请重新预定！");

    return "redirect:list.do?modal=" + modal + "&hotelId=" + hotelId;
  }
}