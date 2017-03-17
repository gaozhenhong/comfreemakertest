package com.wiwi.freego.hotel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.edb.order.service.OrderDetailsService;
import com.wiwi.freego.hotel.dao.RoomDao;
import com.wiwi.freego.hotel.model.Room;
import com.wiwi.freego.hotel.model.RoomQ;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;

public class RoomService
{
  public void insert(Room instance)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    dao.insert(instance);
  }
  
  public void update(Room instance)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    dao.update(instance);
  }
  
  public void delete(long id)
    throws DaoException
  {
    batchDelete(id+"");
  }
  
  public void batchDelete(String ids)
    throws DaoException
  {
    RoomDao dao = new RoomDao();
    dao.delete(ids);
  }
  
  public Room get(long id)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.get(id);
  }
  
  public List<Room> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.getList(pageUtil);
  }
  
  public void setRooms(long hotelId, long roomTypeId, String[] roomNos)
    throws DaoException, RenderException
  {
    setRooms(hotelId, roomTypeId, roomNos, null);
  }
  
  public void setRooms(long hotelId, long roomTypeId, String[] roomNos, long[] notDeleteRoomIds)
    throws DaoException, RenderException
  {
    if (roomTypeId == 0L) {
      return;
    }
    RoomDao dao = new RoomDao();
    if (notDeleteRoomIds != null) {
      dao.deleteRoomsByRoomTypeId(roomTypeId, notDeleteRoomIds);
    }
    if ((roomNos != null) && (roomNos.length > 0))
    {
      String[] arrayOfString;
      int j = (arrayOfString = roomNos).length;
      for (int i = 0; i < j; i++)
      {
        String roomNo = arrayOfString[i];
        Room room = new Room();
        room.setRoomNo(roomNo);
        room.setStatus(Room.Status.VACANT);
        room.setRoomType(new RoomType(Long.valueOf(roomTypeId)));
        room.setHotelId(Long.valueOf(hotelId));
        insert(room);
      }
    }
  }
  
  public List<Room> getPublishedList(long roomTypeId)
    throws DaoException, RenderException
  {
    if (roomTypeId == 0L) {
      return new ArrayList();
    }
    RoomDao dao = new RoomDao();
    RoomQ roomQ = new RoomQ();
    roomQ.setRoomTypeId(Long.valueOf(roomTypeId));
    roomQ.setRecordPerPage(-1);
    return dao.getList(roomQ);
  }
  
  public List<Room> getListByHotelId(long hotelId)
    throws DaoException, RenderException
  {
    if (hotelId == 0L) {
      return new ArrayList();
    }
    RoomQ roomQ = new RoomQ();
    roomQ.setHotelId(Long.valueOf(hotelId));
    roomQ.setRecordPerPage(-1);
    String otherCondition = " where roomTypeId in (select id from fg_roomtype where status='" + RoomType.Status.OPENING + "')";
    roomQ.setOtherCondition(otherCondition);
    RoomDao dao = new RoomDao();
    return dao.getList(roomQ);
  }
  
  public Map<Long, List<Room>> getMapByHotelId(long hotelId)
    throws DaoException, RenderException
  {
    if (hotelId == 0L) {
      return new HashMap();
    }
    List<Room> roomList = getListByHotelId(hotelId);
    Map<Long, List<Room>> roomMap = new HashMap();
    for (Room room : roomList)
    {
      List<Room> tempList = (List)roomMap.get(room.getRoomType().getId());
      if (tempList == null)
      {
        tempList = new ArrayList();
        roomMap.put(room.getRoomType().getId(), tempList);
      }
      ((List)roomMap.get(room.getRoomType().getId())).add(room);
    }
    return roomMap;
  }
  
  public boolean haveVacantRoomByRoomTypeId(Long hotelId, Long roomTypeId, Date beginDate, Date endDate)
  {
    RoomDao dao = new RoomDao();
    try
    {
      return dao.haveVacantRoomByRoomTypeId(hotelId, roomTypeId, beginDate, endDate);
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public List<Room> getVacantRoomListByRoomTypeId(Long hotelId, Long roomTypeId, Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.getVacantRoomListByRoomTypeId(hotelId, roomTypeId, beginDate, endDate);
  }
  
  public boolean haveReservationRoomByRoomId(Long hotelId, Long roomId, Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.haveReservationRoomByRoomId(hotelId, roomId.longValue(), beginDate, endDate, null);
  }
  
  public boolean haveReservationRoomByRoomId(Long hotelId, Long roomId, Date beginDate, Date endDate, long excludeOrderId)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.haveReservationRoomByRoomId(hotelId, roomId.longValue(), beginDate, endDate, Long.valueOf(excludeOrderId));
  }
  
  public boolean haveReservationRoomByOrder(Order order)
    throws DaoException, RenderException
  {
    if (order == null) {
      return true;
    }
    OrderDetailsService ods = new OrderDetailsService();
    List<OrderDetails> orderDetailsList = ods.getListByOrderId(order.getId().longValue());
    RoomDao dao = new RoomDao();
    for (OrderDetails od : orderDetailsList)
    {
      boolean haveReservation = dao.haveReservationRoomByRoomId(od.getSupplierId(), od.getProductId().longValue(), 
        od.getConsumeBeginTime(), od.getConsumeEndTime(), order.getId());
      if (haveReservation) {
        return true;
      }
    }
    return false;
  }
  
  public JSONArray getHotelRoomNumber(String ids)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.getHotelRoomNumber(ids);
  }
  
  public JSONArray getHotelVacantRoomNumber(String ids)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.getHotelVacantRoomNumber(ids);
  }
  
  public long getHotelVacantRoomNumberOnDate(long hotelId, Date date)
    throws DaoException, RenderException
  {
    RoomDao dao = new RoomDao();
    return dao.getHotelVacantRoomNumberOnDate(hotelId, date);
  }
}
