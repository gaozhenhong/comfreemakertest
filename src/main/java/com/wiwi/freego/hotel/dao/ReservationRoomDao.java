package com.wiwi.freego.hotel.dao;

import java.util.List;

import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.freego.hotel.model.ReservationRoom;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;

public class ReservationRoomDao extends DaoBase
{
  private String sql;

  public ReservationRoomDao()
  {
    this.sql = null; }

  public void insert(ReservationRoom instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "FG_RESERVATION_ROOM");
  }

  public void update(ReservationRoom instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "FG_RESERVATION_ROOM");
  }

  public void deleteByOrderId(Long orderId)
    throws DaoException
  {
    this.sql = "DELETE FROM FG_RESERVATION_ROOM WHERE orderId =?";
    DbAdapter.executeUpdate(this.sql, new Long[] { orderId });
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "DELETE FROM FG_RESERVATION_ROOM WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public ReservationRoom get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM FG_RESERVATION_ROOM WHERE id ='" + id + "'";
    return ((ReservationRoom)DbAdapter.get(this.sql, ReservationRoom.class));
  }

  public List<ReservationRoom> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM FG_RESERVATION_ROOM";
    return DbAdapter.getList(this.sql, pageUtil, ReservationRoom.class);
  }

  public void setStatus(long reservationRoomId, Order.Status status)
    throws DaoException
  {
    this.sql = "UPDATE FG_RESERVATION_ROOM set status=? where id=?";
    DbAdapter.executeUpdate(this.sql, new Object[] { status.name(), Long.valueOf(reservationRoomId) });
  }

  public void setStatusByOrderDetails(OrderDetails orderDetails, Order.Status status)
    throws DaoException
  {
    this.sql = "UPDATE FG_RESERVATION_ROOM set status=? where orderId=? and orderDetailsId=?";
    DbAdapter.executeUpdate(this.sql, new Object[] { status.name(), orderDetails.getOrderId(), orderDetails.getId() });
  }
}