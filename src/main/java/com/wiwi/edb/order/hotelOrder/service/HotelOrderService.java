package com.wiwi.edb.order.hotelOrder.service;

import com.wiwi.edb.order.OrderUtil;
import com.wiwi.edb.order.dao.OrderDao;
import com.wiwi.edb.order.hotelOrder.HotelOrderUtil;
import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.hotelOrder.model.HotelOrderDetails;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.OrderType;
import com.wiwi.edb.order.model.Order.PayStatus;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.edb.order.model.OrderProcess;
import com.wiwi.edb.order.model.OrderProcess.OrderOperate;
import com.wiwi.edb.order.model.OrderQ;
import com.wiwi.edb.order.service.OrderDetailsService;
import com.wiwi.edb.order.service.OrderProcessService;
import com.wiwi.edb.order.service.OrderService;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.ReservationRoom;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.ReservationRoomService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.member.model.Member;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.OrganizationService;
import com.wiwi.jsoil.util.DateUtils;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class HotelOrderService
  extends OrderService
{
  OrderDetailsService orderDetailsService = new OrderDetailsService();
  ReservationRoomService reservationRoomService = new ReservationRoomService();
  OrganizationService orgService = new OrganizationService();
  
  public Order save(HotelOrder hotelOrder, Order.Status status, User user)
    throws DaoException, RenderException
  {
    if (hotelOrder == null) {
      return null;
    }
    Date orderTime = new Date();
    Member member = hotelOrder.getMember();
    Long memberId = member == null ? null : member.getId();
    Order order = new Order();
    Hotel hotel = new HotelService().get(hotelOrder.getHotelId().longValue());
    Organization company = this.orgService.getCompany(hotel.getOrgId().intValue());
    order.setSupplierId(hotel.getId().longValue());
    order.setSupplierCode(hotel.getCode());
    order.setCompany(company);
    order.setMemberId(memberId);
    order.setOrderName(hotelOrder.getOrderName() == null ? member.getName() : hotelOrder.getOrderName());
    order.setOrderTelephone(hotelOrder.getOrderTelephone() == null ? member.getTelphone() : hotelOrder.getOrderTelephone());
    order.setOrderType(Order.OrderType.HOTEL);
    order.setRemark(hotelOrder.getRemark());
    order.setOrigin(hotelOrder.getOrigin());
    order.setPayPrice(Double.valueOf(hotelOrder.getPayPrice()));
    order.setOriginDetails(hotelOrder.getMicroSiteCode());
    order.setPayType(hotelOrder.getPayType());
    order.setTotalPrice(Double.valueOf(hotelOrder.getTotalPrice()));
    if (status == null) {
      status = Order.Status.RESERVATION;
    }
    order.setStatus(status);
    String orderCode = OrderUtil.genOrderCode(order.getOrderType());
    order.setOrderCode(orderCode);
    order.setOrderTime(orderTime);
    double totalPrice = order.getPayPrice() == null ? 0.0D : order.getPayPrice().doubleValue();
    double payPrice = order.getPayPrice() == null ? 0.0D : order.getPayPrice().doubleValue();
    double debt = totalPrice - payPrice;
    Order.PayStatus payStatus = null;
    if (debt == totalPrice) {
      payStatus = Order.PayStatus.NO_PAYMENT;
    } else if (debt > 0.0D) {
      payStatus = Order.PayStatus.DEBT;
    } else if (debt == 0.0D) {
      payStatus = Order.PayStatus.PAY_IN_FULL;
    } else if (debt < 0.0D) {
      payStatus = Order.PayStatus.EXCESS;
    }
    order.setPayStatus(payStatus);
    
    insert(order);
    
    List<HotelOrderDetails> hotelOrderDetailsList = hotelOrder.getOrderDetails();
    saveOrderDetails(order, hotelOrderDetailsList);
    
    OrderProcess orderProcess = new OrderProcess();
    if (user != null)
    {
      orderProcess.setOperatorId(user.getId());
      orderProcess.setOperatorName(user.getName());
    }
    orderProcess.setProcessTime(new Date());
    orderProcess.setOrderCode(order.getOrderCode());
    orderProcess.setOrderId(order.getId());
    orderProcess.setOperateType(OrderProcess.OrderOperate.CREATE);
    OrderProcessService orderProcessService = new OrderProcessService();
    orderProcessService.insert(orderProcess);
    
    return order;
  }
  
  public Order update(long orderId, double totalPrice, String orderName, String orderTelephone, String origin, List<HotelOrderDetails> hotelOrderDetailsList, User user)
    throws DaoException, RenderException
  {
    updateTotalPrice(orderId, totalPrice);
    updateOrderBaseInfo(orderName, orderTelephone, origin, orderId, user);
    
    this.reservationRoomService.deleteByOrderId(orderId);
    this.orderDetailsService.deleteByOrderId(orderId);
    Order order = get(orderId);
    saveOrderDetails(order, hotelOrderDetailsList);
    
    OrderProcess orderProcess = new OrderProcess();
    orderProcess.setOperatorId(user.getId());
    orderProcess.setOperatorName(user.getName());
    orderProcess.setProcessTime(new Date());
    orderProcess.setOrderCode(order.getOrderCode());
    orderProcess.setOrderId(order.getId());
    orderProcess.setOperateType(OrderProcess.OrderOperate.EDIT);
    OrderProcessService orderProcessService = new OrderProcessService();
    orderProcessService.insert(orderProcess);
    
    return order;
  }
  
  private void saveOrderDetails(Order order, List<HotelOrderDetails> hotelOrderDetailsList)
    throws DaoException, RenderException
  {
    Date consumeBeginTime = null;
    Date consumeEndTime = null;
    int consumeDay = 0;
    Date orderTime = order.getLastModifyTime();
    Iterator localIterator2;
    for (Iterator localIterator1 = hotelOrderDetailsList.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      HotelOrderDetails hotelOrderDetails = (HotelOrderDetails)localIterator1.next();
      consumeDay = hotelOrderDetails.getConsumeDay();
      consumeBeginTime = hotelOrderDetails.getConsumeBeginDate();
      consumeEndTime = DateUtils.addDay(consumeBeginTime, consumeDay);
      Map<String, List<ReservationRoom>> map = hotelOrderDetails.getReservationRoomMap();
      Set<String> roomIdSet = map.keySet();
      localIterator2 = roomIdSet.iterator(); 
      String roomStr = (String)localIterator2.next();
      long roomId = Long.parseLong(roomStr.split("#")[0]);
      String roomName = roomStr.split("#")[1];
      OrderDetails orderDetails = new OrderDetails();
      orderDetails.setOrderCode(order.getOrderCode());
      orderDetails.setOrderId(order.getId());
      orderDetails.setMemberId(order.getMemberId());
      orderDetails.setConsumeBeginTime(consumeBeginTime);
      orderDetails.setOrderNumber(Integer.valueOf(hotelOrderDetails.getConsumeDay()));
      orderDetails.setConsumeEndTime(consumeEndTime);
      orderDetails.setTotalPrice(Double.valueOf(hotelOrderDetails.getSingleRoomPrice()));
      orderDetails.setProductId(Long.valueOf(roomId));
      orderDetails.setProductName(roomName);
      orderDetails.setSupplierId(Long.valueOf(order.getSupplierId()));
      orderDetails.setCompany(order.getCompany());
      orderDetails.setStatus(order.getStatus());
      orderDetails.setRemark(hotelOrderDetails.getRemark());
      orderDetails.setProductType(Order.OrderType.HOTEL.name());
      orderDetails.setOrderTime(orderTime);
      this.orderDetailsService.insert(orderDetails);
      for (ReservationRoom resRoom : map.get(roomStr))
      {
        resRoom.setOrderDetailsId(orderDetails.getId());
        resRoom.setOrderCode(order.getOrderCode());
        resRoom.setOrderId(order.getId());
        resRoom.setStatus(order.getStatus());
        resRoom.setRemark(new JSONObject(order).toString());
        this.reservationRoomService.insert(resRoom);
      }
    }
  }
  
  public void insertAndConfirm(Order instance, List<OrderDetails> orderDetailsList, User user)
    throws DaoException, RenderException
  {
    instance.setStatus(Order.Status.CONFIRM);
    save(instance, orderDetailsList);
    
    OrderProcess orderProcess = new OrderProcess();
    orderProcess.setOperatorId(user.getId());
    orderProcess.setOperatorName(user.getName());
    orderProcess.setProcessTime(new Date());
    orderProcess.setOrderCode(instance.getOrderCode());
    orderProcess.setOrderId(instance.getId());
    orderProcess.setOperateType(OrderProcess.OrderOperate.CONFIRM);
    OrderProcessService orderProcessService = new OrderProcessService();
    orderProcessService.insert(orderProcess);
  }
  
  public void apply(Order instance, List<OrderDetails> orderDetailsList)
    throws DaoException, RenderException
  {
    instance.setStatus(Order.Status.RESERVATION);
    save(instance, orderDetailsList);
  }
  
  private synchronized void save(Order instance, List<OrderDetails> orderDetailsList)
    throws DaoException, RenderException
  {
    Date orderTime = new Date();
    String orderCode = OrderUtil.genOrderCode(instance.getOrderType());
    instance.setOrderCode(orderCode);
    instance.setOrderTime(orderTime);
    double totalPrice = instance.getTotalPrice().doubleValue();
    double payPrice = instance.getPayPrice() == null ? 0.0D : instance.getPayPrice().doubleValue();
    double debt = totalPrice - payPrice;
    Order.PayStatus status = null;
    if (debt == totalPrice) {
      status = Order.PayStatus.NO_PAYMENT;
    } else if (debt > 0.0D) {
      status = Order.PayStatus.DEBT;
    } else if (debt == 0.0D) {
      status = Order.PayStatus.PAY_IN_FULL;
    } else if (debt < 0.0D) {
      status = Order.PayStatus.EXCESS;
    }
    instance.setPayStatus(status);
    insert(instance);
    OrderDetailsService odService = new OrderDetailsService();
    for (OrderDetails orderDetails : orderDetailsList) {
      try
      {
        orderDetails.setOrderId(instance.getId());
        orderDetails.setOrderCode(instance.getOrderCode());
        orderDetails.setOrderTime(orderTime);
        orderDetails.setSupplierId(Long.valueOf(instance.getSupplierId()));
        orderDetails.setCompany(instance.getCompany());
        odService.insert(orderDetails);
        
        new ReservationRoomService().genReservationRooms(orderDetails, instance);
      }
      catch (Exception e)
      {
        roolBack(instance);
        throw e;
      }
    }
  }
  
  private void roolBack(Order instance)
    throws DaoException
  {
    new ReservationRoomService().deleteByOrderId(instance.getId().longValue());
    batchDelete(instance.getId()+"", null, true);
  }
  
  public void confirmOrder(long orderId, User user, String remark)
    throws DaoException
  {
    setAllStatus(orderId, Order.Status.CONFIRM, user, remark);
  }
  
  public void rejectOrder(long orderId, User user, String remark)
    throws DaoException
  {
    setAllStatus(orderId, Order.Status.REJECT, user, remark);
  }
  
  public void confirmCancelOrder(long orderId, User user, String remark)
    throws DaoException
  {
    setAllStatus(orderId, Order.Status.CANCELED, user, remark);
  }
  
  public List<Order> getOtherHotelCommendList(OrderQ orderQ, int selfCompanyId)
    throws DaoException, RenderException
  {
    orderQ.setCompanyId(Integer.valueOf(selfCompanyId));
    orderQ.setOrigin(HotelOrder.ORDER_ORIGIN_FREEGO);
    String otherCondition = " where originDetails not in ( select code from fg_hotel where companyId = '" + selfCompanyId + "')";
    orderQ.setOtherCondition(otherCondition);
    return getList(orderQ);
  }
  
  public List<Order> getyMyCommentOrderList(OrderQ orderQ, int selfCompanyId)
    throws DaoException, RenderException
  {
    orderQ.setOrigin(HotelOrder.ORDER_ORIGIN_FREEGO);
    String otherCondition = " where companyId <>'" + selfCompanyId + "' AND originDetails in ( select code from fg_hotel where companyId = '" + selfCompanyId + "')";
    orderQ.setOtherCondition(otherCondition);
    return getList(orderQ);
  }
  
  public List<Order> getCanSettlementOrderList(OrderQ orderQ, int selfCompanyId)
    throws DaoException, RenderException
  {
    orderQ.setIsSettlement(Integer.valueOf(0));
    orderQ.setOrigin(HotelOrder.ORDER_ORIGIN_FREEGO);
    orderQ.setMultiStatus(HotelOrderUtil.getCanSettlementStatus());
    String otherCondition = " where originDetails not in ( select code from fg_hotel where companyId = '" + selfCompanyId + "')";
    orderQ.setOtherCondition(otherCondition);
    return getList(orderQ);
  }
  
  public List<Order> getAllCanSettlementOrderList(OrderQ orderQ)
    throws DaoException, RenderException
  {
    orderQ.setIsSettlement(Integer.valueOf(0));
    orderQ.setOrigin(HotelOrder.ORDER_ORIGIN_FREEGO);
    orderQ.setMultiStatus(HotelOrderUtil.getCanSettlementStatus());
    return getList(orderQ);
  }
  
  public List<Order> getAllCanSettlementOrderList(OrderQ orderQ, Date endFinishTime)
    throws DaoException, RenderException
  {
    if (orderQ == null) {
      orderQ = new OrderQ();
    }
    orderQ.setIsSettlement(Integer.valueOf(0));
    orderQ.setOrigin(HotelOrder.ORDER_ORIGIN_FREEGO);
    orderQ.setMultiStatus(HotelOrderUtil.getCanSettlementStatus());
    orderQ.setEndFinishTime(endFinishTime);
    return getList(orderQ);
  }
  
  public List<Order> getAllCanSettlementOrderListByCompanyIdAndSupplierId(Integer companyId, Long supplierId)
    throws DaoException, RenderException
  {
    OrderQ orderQ = new OrderQ();
    orderQ.setSupplierId(supplierId);
    orderQ.setOrigin(HotelOrder.ORDER_ORIGIN_FREEGO);
    orderQ.setRecordPerPage(-1);
    return getAllCanSettlementOrderList(orderQ);
  }
  
  public JSONArray getCanSettlementSupplierList(long selfCompanyId, Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    OrderDao dao = new OrderDao();
    return dao.getCanSettlementSupplierList(selfCompanyId, beginDate, endDate);
  }
  
  public JSONArray getAllCanSettlementSupplierList(Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    OrderDao dao = new OrderDao();
    return dao.getSettlementInfoList(null, null, beginDate, endDate);
  }
  
  public JSONObject getCanSettlementSupplierInfo(Integer conpanyId, Long suplierId)
    throws DaoException, RenderException
  {
    OrderDao dao = new OrderDao();
    JSONArray jsonArray = dao.getSettlementInfoList(conpanyId, suplierId, null, null);
    if ((jsonArray != null) && (jsonArray.length() > 0)) {
      return (JSONObject)jsonArray.get(0);
    }
    return null;
  }
  
  public boolean isCanPay(Long orderId)
  {
    Order order = null;
    try
    {
      order = get(orderId.longValue());
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    if (order == null) {
      return false;
    }
    if (!order.getStatus().equals(Order.Status.RESERVATION)) {
      return false;
    }
    RoomService roomService = new RoomService();
    try
    {
      boolean haveReservationRoom = roomService.haveReservationRoomByOrder(order);
      return !haveReservationRoom;
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
