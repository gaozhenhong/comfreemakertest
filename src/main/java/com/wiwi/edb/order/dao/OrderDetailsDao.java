package com.wiwi.edb.order.dao;

import java.util.List;

import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;

public class OrderDetailsDao extends DaoBase
{
  private String sql;

  public OrderDetailsDao()
  {
    this.sql = null; }

  public void insert(OrderDetails instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "edb_order_details");
  }

  public void update(OrderDetails instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "edb_order_details");
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

    this.sql = "DELETE FROM edb_order_details WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public void deleteByOrderId(long orderId)
    throws DaoException
  {
    this.sql = "DELETE FROM edb_order_details WHERE orderId =?";
    DbAdapter.executeUpdate(this.sql, new Long[] { Long.valueOf(orderId) });
  }

  public OrderDetails get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM edb_order_details WHERE id ='" + id + "'";
    return ((OrderDetails)DbAdapter.get(this.sql, OrderDetails.class));
  }

  public void setStatus(long orderDetailsId, Order.Status status)
    throws DaoException
  {
    this.sql = "UPDATE EDB_ORDER_DETAILS set status=? where id=?";
    DbAdapter.executeUpdate(this.sql, new Object[] { status.name(), Long.valueOf(orderDetailsId) });
  }

  public List<OrderDetails> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM edb_order_details";
    return DbAdapter.getList(this.sql, pageUtil, OrderDetails.class);
  }
}