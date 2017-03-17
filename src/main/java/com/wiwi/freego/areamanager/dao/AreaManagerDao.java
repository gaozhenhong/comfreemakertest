package com.wiwi.freego.areamanager.dao;

import com.wiwi.freego.areamanager.model.AreaManager;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class AreaManagerDao extends DaoBase
{
  private String sql;

  public AreaManagerDao()
  {
    this.sql = null; }

  public void insert(AreaManager instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_areamanager");
  }

  public void update(AreaManager instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_areamanager");
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

    this.sql = "DELETE FROM fg_areamanager WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public AreaManager get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM fg_areamanager WHERE id ='" + id + "'";
    return ((AreaManager)DbAdapter.get(this.sql, AreaManager.class));
  }

  public List<AreaManager> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM fg_areamanager";
    return DbAdapter.getList(this.sql, pageUtil, AreaManager.class);
  }
}