package com.wiwi.edb.order.service;

import java.util.List;

import org.json.JSONObject;

import com.wiwi.edb.order.dao.OrderDetailsDao;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.edb.order.model.OrderDetailsQ;
import com.wiwi.edb.order.model.OrderProcess;
import com.wiwi.freego.hotel.service.ReservationRoomService;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;

public class OrderDetailsService
{
  public void insert(OrderDetails instance)
    throws DaoException, RenderException
  {
    OrderDetailsDao dao = new OrderDetailsDao();
    dao.insert(instance);
  }
  
  public void update(OrderDetails instance)
    throws DaoException, RenderException
  {
    OrderDetailsDao dao = new OrderDetailsDao();
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
    OrderDetailsDao dao = new OrderDetailsDao();
    dao.delete(ids);
  }
  
  public void deleteByOrderId(long orderId)
    throws DaoException
  {
    OrderDetailsDao dao = new OrderDetailsDao();
    dao.deleteByOrderId(orderId);
  }
  
  public OrderDetails get(long id)
    throws DaoException, RenderException
  {
    OrderDetailsDao dao = new OrderDetailsDao();
    return dao.get(id);
  }
  
  public List<OrderDetails> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    OrderDetailsDao dao = new OrderDetailsDao();
    return dao.getList(pageUtil);
  }
  
  public List<OrderDetails> getListByOrderId(long orderId)
    throws DaoException, RenderException
  {
    OrderDetailsQ query = new OrderDetailsQ();
    query.setRecordPerPage(-1);
    query.setOrderId(Long.valueOf(orderId));
    OrderDetailsDao dao = new OrderDetailsDao();
    return dao.getList(query);
  }
  
  public void checkin(OrderDetails orderDetails, User user)
    throws DaoException
  {
    setStatus(orderDetails, Order.Status.CHECKIN, user, OrderProcess.OrderOperate.CHECKIN);
    new ReservationRoomService().checkin(orderDetails);
  }
  
  public void checkout(OrderDetails orderDetails, User user)
    throws DaoException
  {
    setStatus(orderDetails, Order.Status.CHECKOUT, user, OrderProcess.OrderOperate.CHECKOUT);
    new ReservationRoomService().checkout(orderDetails);
  }
  
  private void setStatus(OrderDetails orderDetails, Order.Status status, User user, OrderProcess.OrderOperate operateType)
    throws DaoException
  {
    if (orderDetails == null) {
      return;
    }
    OrderDetailsDao dao = new OrderDetailsDao();
    dao .setStatus(orderDetails.getId().longValue(), status);
    
    JSONObject remarkObject = new JSONObject();
    remarkObject.put("orderDetails", orderDetails.getId());
    remarkObject.put("status", status.name());
    remarkObject.put("operateType", operateType);
    HotelOrderService orderService = new HotelOrderService();
    orderService.updateStatusFromOrderDetails(orderDetails.getOrderId().longValue(), status, user, operateType, remarkObject.toString());
  }
}
