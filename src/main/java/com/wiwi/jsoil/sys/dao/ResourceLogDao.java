package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.ResourceLog;
import java.util.List;

public class ResourceLogDao extends DaoBase
{
  private String sql;

  public ResourceLogDao()
  {
    this.sql = null; }

  public void insert(ResourceLog instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_resource_log");
  }

  public void update(ResourceLog instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_resource_log");
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

    this.sql = "DELETE FROM s_resource_log WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public ResourceLog get(String id) throws DaoException, RenderException {
    this.sql = "select * FROM s_resource_log WHERE id ='" + id + "'";
    return ((ResourceLog)DbAdapter.get(this.sql, ResourceLog.class));
  }

  public List<ResourceLog> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_resource_log";
    return DbAdapter.getList(this.sql, pageUtil, ResourceLog.class);
  }
}