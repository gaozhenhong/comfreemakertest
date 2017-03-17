package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.PlatformProperty;
import java.util.List;

public class PlatformPropertyDao extends DaoBase
{
  private String sql;

  public PlatformPropertyDao()
  {
    this.sql = null; }

  public void insert(PlatformProperty instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_platform_property");
  }

  public void update(PlatformProperty instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_platform_property");
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

    this.sql = "DELETE FROM s_platform_property WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public PlatformProperty get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM s_platform_property WHERE id ='" + id + "'";
    return ((PlatformProperty)DbAdapter.get(this.sql, PlatformProperty.class));
  }

  public PlatformProperty getByCode(String code) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_platform_property WHERE code ='" + code + "'";
    return ((PlatformProperty)DbAdapter.get(this.sql, PlatformProperty.class));
  }

  public List<PlatformProperty> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_platform_property";
    return DbAdapter.getList(this.sql, pageUtil, PlatformProperty.class);
  }
}