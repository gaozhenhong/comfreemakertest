package com.wiwi.freego.hotel.dao;

import com.wiwi.freego.hotel.model.SpecialTimePrice;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class SpecialTimePriceDao extends DaoBase
{
  private String sql;

  public SpecialTimePriceDao()
  {
    this.sql = null; }

  public void insert(SpecialTimePrice instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_specialTimePrice");
  }

  public void update(SpecialTimePrice instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_specialTimePrice");
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

    this.sql = "DELETE FROM fg_specialTimePrice WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public SpecialTimePrice get(int id) throws DaoException, RenderException {
    this.sql = "select * FROM fg_specialTimePrice WHERE id ='" + id + "'";
    return ((SpecialTimePrice)DbAdapter.get(this.sql, SpecialTimePrice.class));
  }

  public List<SpecialTimePrice> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM fg_specialTimePrice";
    return DbAdapter.getList(this.sql, pageUtil, SpecialTimePrice.class);
  }
}