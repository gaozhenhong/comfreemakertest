package com.wiwi.freego.hotel.orderGuest.service;

import com.wiwi.freego.hotel.orderGuest.dao.OrderGuestMappingDao;
import com.wiwi.freego.hotel.orderGuest.model.OrderGuestMapping;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class OrderGuestMappingService
{
  public void insert(OrderGuestMapping instance)
    throws DaoException, RenderException
  {
    OrderGuestMappingDao dao = new OrderGuestMappingDao();
    dao.insert(instance);
  }

  public void update(OrderGuestMapping instance) throws DaoException, RenderException {
    OrderGuestMappingDao dao = new OrderGuestMappingDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    OrderGuestMappingDao dao = new OrderGuestMappingDao();
    dao.delete(ids);
  }

  public OrderGuestMapping get(long id) throws DaoException, RenderException {
    OrderGuestMappingDao dao = new OrderGuestMappingDao();
    return dao.get(id);
  }

  public List<OrderGuestMapping> getList(PageUtil pageUtil) throws DaoException, RenderException {
    OrderGuestMappingDao dao = new OrderGuestMappingDao();
    return dao.getList(pageUtil);
  }
}