package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.OperationLog;
import java.util.List;

public class OperationLogDao extends DaoBase
{
  private String sql;

  public OperationLogDao()
  {
    this.sql = null; }

  public void insert(OperationLog instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_log");
  }

  public void update(OperationLog instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_log");
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

    this.sql = "DELETE FROM s_log WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public OperationLog get(String id) throws DaoException, RenderException {
    this.sql = "select * FROM s_log WHERE id ='" + id + "'";
    return ((OperationLog)DbAdapter.get(this.sql, OperationLog.class));
  }

  public List<OperationLog> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_log";
    return DbAdapter.getList(this.sql, pageUtil, OperationLog.class);
  }
}