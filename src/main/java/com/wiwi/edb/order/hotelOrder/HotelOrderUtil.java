package com.wiwi.edb.order.hotelOrder;

import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.hotelOrder.model.HotelOrderDetails;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.freego.hotel.model.ReservationRoom;
import com.wiwi.freego.hotel.model.Room;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.freego.hotel.service.RoomTypeService;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.util.DateUtils;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class HotelOrderUtil
{
  public static Order order(HotelOrder hotelOrder)
    throws DaoException, RenderException
  {
    return processOrder(hotelOrder, Order.Status.RESERVATION, null);
  }
  
  private static Order processOrder(HotelOrder hotelOrder, Order.Status status, User user)
    throws DaoException, RenderException
  {
    if (status == null) {
      return null;
    }
    Date consumeBeginTime = null;
    Date consumeEndTime = null;
    int consumeDay = 0;
    double price = 0.0D;
    List<HotelOrderDetails> hotelOrderDetailsList = hotelOrder.getOrderDetails();
    Long roomTypeId = Long.valueOf(0L);
    RoomType roomType = null;
    Room vacantRoom = null;
    boolean haveEnoughRoom = true;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, Double> roomTypePriceInDayMap = new HashMap();
    double totalPrice = 0.0D;
    for (HotelOrderDetails hotelOrderDetails : hotelOrderDetailsList)
    {
      consumeDay = hotelOrderDetails.getConsumeDay();
      consumeBeginTime = hotelOrderDetails.getConsumeBeginDate();
      consumeEndTime = DateUtils.addDay(consumeBeginTime, consumeDay);
      roomTypeId = hotelOrderDetails.getRoomTypeId();
      roomType = new RoomTypeService().get(roomTypeId.longValue());
      
      List<Room> vacantRoomList = new RoomService().getVacantRoomListByRoomTypeId(null, roomTypeId, consumeBeginTime, consumeEndTime);
      if (vacantRoomList.size() < hotelOrderDetails.getOrderNumber().intValue())
      {
        haveEnoughRoom = false;
        break;
      }
      Map<String, List<ReservationRoom>> reservationRoomMap = new HashMap();
      for (int i = 0; i < hotelOrderDetails.getOrderNumber().intValue(); i++)
      {
        vacantRoom = (Room)vacantRoomList.get(i);
        
        ReservationRoom reservationRoom = null;
        Double roomInDayPrice = Double.valueOf(0.0D);
        Date consumeDate = null;
        List<ReservationRoom> reservationRoomList = new ArrayList();
        double singleRoomPrice = 0.0D;
        for (int j = 0; j < consumeDay; j++)
        {
          reservationRoom = new ReservationRoom();
          reservationRoom.setRoomTypeId(roomTypeId);
          reservationRoom.setRoomId(vacantRoom.getId());
          
          reservationRoom.setPrice(Double.valueOf(price));
          consumeDate = DateUtils.addDay(consumeBeginTime, j);
          String key = roomTypeId + "-" + sdf.format(consumeDate);
          roomInDayPrice = (Double)roomTypePriceInDayMap.get(key);
          if (roomInDayPrice == null)
          {
            roomInDayPrice = Double.valueOf(roomType.getPrice(consumeDate));
            roomTypePriceInDayMap.put(key, roomInDayPrice);
          }
          reservationRoom.setPrice(roomInDayPrice);
          reservationRoom.setConsumeDate(consumeDate);
          reservationRoom.setRoomNo(vacantRoom.getRoomNo());
          reservationRoom.setName(roomType.getName() + vacantRoom.getRoomNo());
          reservationRoom.setHotelId(hotelOrder.getHotelId());
          reservationRoomList.add(reservationRoom);
          singleRoomPrice += roomInDayPrice.doubleValue();
          
          totalPrice += roomInDayPrice.doubleValue();
        }
        hotelOrderDetails.setSingleRoomPrice(singleRoomPrice);
        reservationRoomMap.put(vacantRoom.getId() + "#" + roomType.getName() + vacantRoom.getRoomNo(), reservationRoomList);
      }
      hotelOrderDetails.setReservationRoomMap(reservationRoomMap);
    }
    hotelOrder.setTotalPrice(totalPrice);
    if (!haveEnoughRoom) {
      return null;
    }
    return new HotelOrderService().save(hotelOrder, status, user);
  }
  
  public static boolean haveRoom(HotelOrderDetails hotelOrderDetails)
  {
    if (hotelOrderDetails == null) {
      return false;
    }
    Date consumeBeginDate = hotelOrderDetails.getConsumeBeginDate();
    if (consumeBeginDate == null) {
      return false;
    }
    Date endDate = DateUtils.addDay(consumeBeginDate, hotelOrderDetails.getConsumeDay());
    return new RoomService().haveVacantRoomByRoomTypeId(null, hotelOrderDetails.getRoomTypeId(), consumeBeginDate, endDate);
  }
  
  public static JSONObject getPrice(HotelOrderDetails hotelOrderDetails)
  {
    if (hotelOrderDetails == null) {
      return null;
    }
    JSONObject jsonResult = new JSONObject();
    RoomTypeService rtService = new RoomTypeService();
    JSONArray jsonArray = new JSONArray();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    double totalPrice = 0.0D;
    double price = 0.0D;
    try
    {
      RoomType roomType = rtService.get(hotelOrderDetails.getRoomTypeId().longValue());
      Date beginDate = hotelOrderDetails.getConsumeBeginDate();
      Date tempDate = null;
      for (int i = 0; i < hotelOrderDetails.getConsumeDay(); i++)
      {
        tempDate = DateUtils.addDay(beginDate, i);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roomType", roomType);
        
        jsonObject.put("consumeDate", sdf.format(tempDate));
        price = roomType.getPrice(tempDate);
        jsonObject.put("price", price);
        totalPrice += price;
        jsonArray.put(jsonObject);
      }
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    jsonResult.put("totalPrice", (hotelOrderDetails.getOrderNumber() == null ? 1 : hotelOrderDetails.getOrderNumber().intValue()) * totalPrice);
    jsonResult.put("dayPriceList", jsonArray);
    return jsonResult;
  }
  
  public static List<String> getNotVacantStatus()
  {
    List<String> list = new ArrayList();
    list.add(Order.Status.CONFIRM.name());
    list.add(Order.Status.CANCEL_ORDER.name());
    list.add(Order.Status.CHECKIN.name());
    return list;
  }
  
  public static List<String> getCanSettlementStatus()
  {
    List<String> list = new ArrayList();
    list.add(Order.Status.CHECKOUT.name());
    list.add(Order.Status.ON_TIME_CANCEL.name());
    return list;
  }
  
  public static List<Order.Status> getNotReservationStatus()
  {
    List<Order.Status> multiStatus = new ArrayList();
    multiStatus.add(Order.Status.RESERVATION);
    multiStatus.add(Order.Status.CANCELED);
    multiStatus.add(Order.Status.REJECT);
    return multiStatus;
  }
  
  public static List<Order.Status> getNotOnRoomBoardStatus()
  {
    List<Order.Status> multiStatus = new ArrayList();
    
    multiStatus.add(Order.Status.RESERVATION);
    multiStatus.add(Order.Status.CANCELED);
    multiStatus.add(Order.Status.REJECT);
    return multiStatus;
  }
  
  public static List<OrderDetails> combinationContinuousDay(List<OrderDetails> oneDayOrderDetailsList)
  {
    Map<Long, List<OrderDetails>> roomMap = new HashMap();
    for (OrderDetails od : oneDayOrderDetailsList)
    {
      long roomId = od.getProductId().longValue();
      if (roomMap.get(Long.valueOf(roomId)) == null) {
        roomMap.put(Long.valueOf(roomId), new ArrayList());
      }
      ((List)roomMap.get(Long.valueOf(roomId))).add(od);
    }
    List<OrderDetails> orderDetailsList = new ArrayList();
    List<OrderDetails> orderDetails;
    int i=0;
    for (Iterator roomId = roomMap.keySet().iterator(); roomId.hasNext();)
    {
      Long roomIdKey = (Long)roomId.next();
      orderDetails = (List)roomMap.get(roomIdKey);
      
      Collections.sort(orderDetails, new Comparator<OrderDetails>()
      {
        public int compare(OrderDetails arg0, OrderDetails arg1)
        {
          return arg0.getConsumeBeginTime().compareTo(arg1.getConsumeBeginTime());
        }
      });
      OrderDetails newOd = null;
      OrderDetails preOd = null;
      OrderDetails currentOd = null;
      if (newOd == null)
      {
        newOd = (OrderDetails)orderDetails.get(i);
        preOd = newOd;
        orderDetailsList.add(newOd);
      }
      else
      {
        currentOd = (OrderDetails)orderDetails.get(i);
        int span = DateUtils.compareDate(preOd.getConsumeBeginTime(), currentOd.getConsumeBeginTime());
        if (span == 1)
        {
          newOd.setOrderNumber(Integer.valueOf(newOd.getOrderNumber().intValue() + 1));
          newOd.setConsumeEndTime(currentOd.getConsumeBeginTime());
          newOd.setTotalPrice(Double.valueOf(newOd.getTotalPrice().doubleValue() + currentOd.getTotalPrice().doubleValue()));
          preOd = currentOd;
        }
        else
        {
          newOd = currentOd;
          preOd = newOd;
          orderDetailsList.add(newOd);
        }
      }
      i++;
    }
    return orderDetailsList;
  }
  
  public static void main(String[] args)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<OrderDetails> oneDayOrderDetailsList = new ArrayList();
    OrderDetails orderDetails = new OrderDetails();
    try
    {
      orderDetails.setConsumeBeginTime(sdf.parse("2016-02-17"));
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    orderDetails.setOrderNumber(Integer.valueOf(1));
    orderDetails.setTotalPrice(Double.valueOf(100.0D));
    orderDetails.setProductId(Long.valueOf(201L));
    oneDayOrderDetailsList.add(orderDetails);
    
    orderDetails = new OrderDetails();
    try
    {
      orderDetails.setConsumeBeginTime(sdf.parse("2016-02-18"));
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    orderDetails.setOrderNumber(Integer.valueOf(1));
    orderDetails.setTotalPrice(Double.valueOf(200.0D));
    orderDetails.setProductId(Long.valueOf(201L));
    oneDayOrderDetailsList.add(orderDetails);
    
    orderDetails = new OrderDetails();
    try
    {
      orderDetails.setConsumeBeginTime(sdf.parse("2016-02-19"));
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    orderDetails.setOrderNumber(Integer.valueOf(1));
    orderDetails.setTotalPrice(Double.valueOf(300.0D));
    orderDetails.setProductId(Long.valueOf(201L));
    oneDayOrderDetailsList.add(orderDetails);
    
    orderDetails = new OrderDetails();
    try
    {
      orderDetails.setConsumeBeginTime(sdf.parse("2016-02-25"));
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    orderDetails.setOrderNumber(Integer.valueOf(1));
    orderDetails.setTotalPrice(Double.valueOf(100.0D));
    orderDetails.setProductId(Long.valueOf(201L));
    oneDayOrderDetailsList.add(orderDetails);
    
    orderDetails = new OrderDetails();
    try
    {
      orderDetails.setConsumeBeginTime(sdf.parse("2016-02-26"));
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    orderDetails.setOrderNumber(Integer.valueOf(1));
    orderDetails.setTotalPrice(Double.valueOf(100.0D));
    orderDetails.setProductId(Long.valueOf(201L));
    oneDayOrderDetailsList.add(orderDetails);
    
    orderDetails = new OrderDetails();
    try
    {
      orderDetails.setConsumeBeginTime(sdf.parse("2016-02-29"));
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    orderDetails.setOrderNumber(Integer.valueOf(1));
    orderDetails.setTotalPrice(Double.valueOf(2900.0D));
    orderDetails.setProductId(Long.valueOf(201L));
    oneDayOrderDetailsList.add(orderDetails);
    
    List<OrderDetails> newOdList = combinationContinuousDay(oneDayOrderDetailsList);
    for (OrderDetails od : newOdList) {
      System.out.println(od);
    }
  }
}
