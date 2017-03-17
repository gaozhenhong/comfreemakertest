package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Resource;
import java.util.List;

public class ResourceDao extends DaoBase
{
  private String sql;

  public ResourceDao()
  {
    this.sql = null; }

  public void insert(Resource instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_resource");
  }

  public void update(Resource instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_resource");
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

    this.sql = "DELETE FROM s_resource WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public void addDownloadCount(long id)
    throws DaoException
  {
    this.sql = "update s_resource set downloadCount = downloadCount + 1 where id=? ";
    DbAdapter.executeUpdate(this.sql, new Long[] { Long.valueOf(id) });
  }

  public void addBrowseCount(long id)
    throws DaoException
  {
    this.sql = "update s_resource set browseCount = browseCount + 1 where id=? ";
    DbAdapter.executeUpdate(this.sql, new Long[] { Long.valueOf(id) });
  }

  public Resource get(long id) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_resource WHERE id ='" + id + "'";
    return ((Resource)DbAdapter.get(this.sql, Resource.class));
  }

  public List<Resource> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_resource";
    return DbAdapter.getList(this.sql, pageUtil, Resource.class);
  }
}