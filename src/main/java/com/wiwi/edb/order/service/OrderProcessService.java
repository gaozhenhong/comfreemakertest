package com.wiwi.edb.order.service;

import com.wiwi.edb.order.dao.OrderProcessDao;
import com.wiwi.edb.order.model.OrderProcess;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class OrderProcessService
{
  public void insert(OrderProcess instance)
    throws DaoException, RenderException
  {
    OrderProcessDao dao = new OrderProcessDao();
    dao.insert(instance);
  }

  public void update(OrderProcess instance) throws DaoException, RenderException {
    OrderProcessDao dao = new OrderProcessDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    OrderProcessDao dao = new OrderProcessDao();
    dao.delete(ids);
  }

  public OrderProcess get(long id) throws DaoException, RenderException {
    OrderProcessDao dao = new OrderProcessDao();
    return dao.get(id);
  }

  public List<OrderProcess> getList(PageUtil pageUtil) throws DaoException, RenderException {
    OrderProcessDao dao = new OrderProcessDao();
    return dao.getList(pageUtil);
  }
}