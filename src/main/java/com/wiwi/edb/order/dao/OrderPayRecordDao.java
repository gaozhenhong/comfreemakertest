package com.wiwi.edb.order.dao;

import com.wiwi.edb.order.model.OrderPayRecord;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class OrderPayRecordDao extends DaoBase
{
  private String sql;

  public OrderPayRecordDao()
  {
    this.sql = null; }

  public void insert(OrderPayRecord instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "edb_order_pay_record");
  }

  public void update(OrderPayRecord instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "edb_order_pay_record");
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

    this.sql = "DELETE FROM edb_order_pay_record WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public OrderPayRecord get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM edb_order_pay_record WHERE id ='" + id + "'";
    return ((OrderPayRecord)DbAdapter.get(this.sql, OrderPayRecord.class));
  }

  public List<OrderPayRecord> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM edb_order_pay_record";
    return DbAdapter.getList(this.sql, pageUtil, OrderPayRecord.class);
  }
}