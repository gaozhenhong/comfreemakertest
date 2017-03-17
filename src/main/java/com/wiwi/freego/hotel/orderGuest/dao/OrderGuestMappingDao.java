package com.wiwi.freego.hotel.orderGuest.dao;

import com.wiwi.freego.hotel.orderGuest.model.OrderGuestMapping;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class OrderGuestMappingDao extends DaoBase
{
  private String sql;

  public OrderGuestMappingDao()
  {
    this.sql = null; }

  public void insert(OrderGuestMapping instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_order_guest_mapping");
  }

  public void update(OrderGuestMapping instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_order_guest_mapping");
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

    this.sql = "DELETE FROM fg_order_guest_mapping WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public OrderGuestMapping get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM fg_order_guest_mapping WHERE id ='" + id + "'";
    return ((OrderGuestMapping)DbAdapter.get(this.sql, OrderGuestMapping.class));
  }

  public List<OrderGuestMapping> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM fg_order_guest_mapping";
    return DbAdapter.getList(this.sql, pageUtil, OrderGuestMapping.class);
  }
}