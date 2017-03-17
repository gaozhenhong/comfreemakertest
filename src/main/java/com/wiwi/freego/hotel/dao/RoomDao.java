package com.wiwi.freego.hotel.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import com.wiwi.edb.order.hotelOrder.HotelOrderUtil;
import com.wiwi.edb.order.model.Order;
import com.wiwi.freego.hotel.model.Room;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.util.SqlUtil;

public class RoomDao
  extends DaoBase
{
  private String sql = null;
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  
  public void insert(Room instance)
    throws DaoException, RenderException
  {
    DbAdapter.insert2SingleTable(instance, "fg_room");
  }
  
  public void update(Room instance)
    throws DaoException, RenderException
  {
    DbAdapter.update2SingleTable(instance, "fg_room");
  }
  
  public void delete(String ids)
    throws DaoException
  {
    if (ids.startsWith(",")) {
      ids = ids.substring(1);
    }
    if (ids.indexOf(",") != -1) {
      ids = ids.replaceAll(",", "','");
    }
    if (!ids.startsWith("'")) {
      ids = "'" + ids;
    }
    if (!ids.endsWith("'")) {
      ids = ids + "'";
    }
    this.sql = ("DELETE FROM fg_room WHERE id in (" + ids + ") ");
    DbAdapter.executeUpdate(this.sql);
  }
  
  public void deleteRoomsByRoomTypeId(long roomTypeId, long[] notDeleteRoomIds)
    throws DaoException
  {
    String notDeletes = "";
    if (notDeleteRoomIds != null)
    {
      long[] arrayOfLong;
      int j = (arrayOfLong = notDeleteRoomIds).length;
      for (int i = 0; i < j; i++)
      {
        long id = arrayOfLong[i];
        notDeletes = notDeletes + "," + id;
      }
    }
    if (notDeletes.trim().length() > 1) {
      notDeletes = notDeletes.substring(1);
    }
    if (notDeletes.trim().length() > 0) {
      this.sql = ("DELETE FROM fg_room WHERE roomTypeId =?  AND id not in (" + notDeletes + ")");
    } else {
      this.sql = "DELETE FROM fg_room WHERE roomTypeId =? ";
    }
    DbAdapter.executeUpdate(this.sql, new Long[] { Long.valueOf(roomTypeId) });
  }
  
  public Room get(long id)
    throws DaoException, RenderException
  {
    this.sql = 
      ("select m.*, t.name as roomTypeName ,t.availableNumber as roomTypeAvailableNumber,t.bedType as roomTypeBedType FROM fg_room m ,fg_roomType t WHERE m.roomTypeId = t.id  and m.id ='" + id + "'");
    return (Room)DbAdapter.get(this.sql, Room.class);
  }
  
  public List<Room> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    this.sql = "select m.*, t.name as roomTypeName,t.availableNumber as roomTypeAvailableNumber,t.bedType as roomTypeBedType FROM fg_room m ,fg_roomType t WHERE m.roomTypeId = t.id ";
    
    return DbAdapter.getList(this.sql, pageUtil, Room.class);
  }
  
  public boolean haveVacantRoomByRoomTypeId(Long hotelId, Long roomTypeId, Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    this.sql = "select m.*, t.name as roomTypeName FROM fg_room m ,fg_roomType t WHERE m.roomTypeId = t.id";
    if (hotelId != null) {
      this.sql = (this.sql + " and m.hotelId = '" + hotelId + "'");
    }
    if (roomTypeId != null) {
      this.sql = (this.sql + " and m.roomTypeId = '" + roomTypeId + "'");
    }
    this.sql = ("select count(*) from (" + this.sql + ") m  WHERE 1=1 " + getReadVacantRoomQuery(hotelId, roomTypeId, beginDate, endDate));
    return DbAdapter.havaRecord(this.sql);
  }
  
  public List<Room> getVacantRoomListByRoomTypeId(Long hotelId, Long roomTypeId, Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    this.sql = "select m.*, t.name as roomTypeName,t.availableNumber as roomTypeAvailableNumber,t.bedType as roomTypeBedType FROM fg_room m ,fg_roomType t WHERE m.roomTypeId = t.id ";
    if (hotelId != null) {
      this.sql = (this.sql + " and m.hotelId = '" + hotelId + "'");
    }
    if (roomTypeId != null) {
      this.sql = (this.sql + " and m.roomTypeId = '" + roomTypeId + "'");
    }
    this.sql += getReadVacantRoomQuery(hotelId, roomTypeId, beginDate, endDate);
    return DbAdapter.getList(this.sql, Room.class);
  }
  
  private String getReadVacantRoomQuery(Long hotelId, Long roomTypeId, Date beginDate, Date endDate)
  {
    String statusSql = SqlUtil.getInSqlStr(HotelOrderUtil.getNotVacantStatus());
    
    String otherCondition = " and m.id not in (\tselect roomId from  fg_reservation_room where 1=1 ";
    if (hotelId != null) {
      otherCondition = otherCondition + " and hotelId='" + hotelId + "'";
    }
    if (roomTypeId != null) {
      otherCondition = otherCondition + " and roomTypeId='" + roomTypeId + "'";
    }
    if ((beginDate != null) && (endDate != null)) {
      otherCondition = 
        otherCondition + " and date(consumeDate) between '" + this.sdf.format(beginDate) + "' and '" + this.sdf.format(endDate) + "'";
    }
    if ((statusSql != null) && (statusSql.trim().length() > 0)) {
      otherCondition = otherCondition + " and status in (" + statusSql + ")";
    }
    otherCondition = otherCondition + " )";
    return otherCondition;
  }
  
  public boolean haveReservationRoomByRoomId(Long hotelId, long roomId, Date beginDate, Date endDate, Long excludeOrderId)
    throws DaoException, RenderException
  {
    this.sql = 
      ("select count(*) from  fg_reservation_room where  hotelId='" + hotelId + "' and roomId = '" + roomId + "'");
    List<Order.Status> statusList = HotelOrderUtil.getNotReservationStatus();
    String statusSql = "";
    for (int i = 0; i < statusList.size(); i++)
    {
      if (i > 0) {
        statusSql = statusSql + " and ";
      }
      statusSql = statusSql + " status <>'" + ((Order.Status)statusList.get(i)).name() + "'";
    }
    if (statusSql.trim().length() > 0) {
      this.sql = (this.sql + " and (" + statusSql + ") ");
    }
    if ((excludeOrderId != null) && (excludeOrderId.longValue() != 0L)) {
      this.sql = (this.sql + " and orderId<>'" + excludeOrderId + "'");
    }
    this.sql = (this.sql + " and date(consumeDate) between '" + this.sdf.format(beginDate) + "' and '" + this.sdf.format(endDate) + "'");
    return DbAdapter.count(this.sql) != 0L;
  }
  
  public JSONArray getHotelRoomNumber(String ids)
    throws DaoException, RenderException
  {
    this.sql = "select t.hotelId as hotelId, count(*) as roomNumber from  fg_room r,fg_roomType t ";
    this.sql = 
    
      (this.sql + " where r.roomTypeId = t.id and t.status='" + RoomType.Status.OPENING + "'" + " and t.hotelId in (" + SqlUtil.getInSqlStr(ids) + ")" + " group by hotelId");
    return DbAdapter.getJSONArray(this.sql);
  }
  
  public JSONArray getHotelVacantRoomNumber(String ids)
    throws DaoException, RenderException
  {
    String statusSql = SqlUtil.getInSqlStr(HotelOrderUtil.getNotVacantStatus());
    this.sql = "select t.hotelId as hotelId, count(*) as vacantRoomNumber from  fg_room r,fg_roomType t ";
    this.sql = 
    
      (this.sql + " where r.roomTypeId = t.id and t.status='" + RoomType.Status.OPENING + "'" + " and t.hotelId in (" + SqlUtil.getInSqlStr(ids) + ")" + " and r.id not in(" + " select roomId from fg_reservation_room " + " where consumeDate like '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + '%' + "' and hotelId in (" + SqlUtil.getInSqlStr(ids) + ") " + " and status in (" + statusSql + ")" + " )" + " group by hotelId");
    return DbAdapter.getJSONArray(this.sql);
  }
  
  public long getHotelVacantRoomNumberOnDate(long hotelId, Date date)
    throws DaoException, RenderException
  {
    String statusSql = SqlUtil.getInSqlStr(HotelOrderUtil.getNotVacantStatus());
    this.sql = "select count(*) as vacantRoomNumber from  fg_room r,fg_roomType t ";
    this.sql = 
    
      (this.sql + " where r.roomTypeId = t.id and t.status='" + RoomType.Status.OPENING + "'" + " and t.hotelId ='" + hotelId + "'" + " and r.id not in(" + " select roomId from fg_reservation_room " + " where date(consumeDate) = '" + this.sdf.format(date) + "'" + " and hotelId ='" + hotelId + "'" + " and status in (" + statusSql + ")" + " )" + " group by t.hotelId");
    return DbAdapter.count(this.sql);
  }
}
