package com.wiwi.edb.order.dao;

import com.wiwi.edb.order.model.OrderProcess;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class OrderProcessDao extends DaoBase
{
  private String sql;

  public OrderProcessDao()
  {
    this.sql = null; }

  public void insert(OrderProcess instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "edb_order_process");
  }

  public void update(OrderProcess instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "edb_order_process");
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

    this.sql = "DELETE FROM edb_order_process WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public OrderProcess get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM edb_order_process WHERE id ='" + id + "'";
    return ((OrderProcess)DbAdapter.get(this.sql, OrderProcess.class));
  }

  public List<OrderProcess> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM edb_order_process";
    return DbAdapter.getList(this.sql, pageUtil, OrderProcess.class);
  }
}