package com.wiwi.freego.shopkeeper.dao;

import com.wiwi.freego.shopkeeper.model.Shopkeeper;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class ShopkeeperDao extends DaoBase
{
  private String sql;

  public ShopkeeperDao()
  {
    this.sql = null; }

  public void insert(Shopkeeper instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_shopkeeper");
  }

  public void update(Shopkeeper instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_shopkeeper");
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

    this.sql = "DELETE FROM fg_shopkeeper WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Shopkeeper get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM fg_shopkeeper WHERE id ='" + id + "'";
    return ((Shopkeeper)DbAdapter.get(this.sql, Shopkeeper.class));
  }

  public List<Shopkeeper> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM fg_shopkeeper";
    return DbAdapter.getList(this.sql, pageUtil, Shopkeeper.class);
  }
}