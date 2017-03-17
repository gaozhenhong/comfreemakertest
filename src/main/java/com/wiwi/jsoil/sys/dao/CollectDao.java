package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Collect;
import java.util.List;

public class CollectDao extends DaoBase
{
  private String sql;

  public CollectDao()
  {
    this.sql = null; }

  public void insert(Collect instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_collect");
  }

  public void update(Collect instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_collect");
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

    this.sql = "DELETE FROM s_collect WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Collect get(long id) throws DaoException, RenderException {
    this.sql = "select c.*,u.name as userName FROM s_collect c,s_user u WHERE c.id =" + 
      id + " and c.userId = u.id";
    return ((Collect)DbAdapter.get(this.sql, Collect.class));
  }

  public List<Collect> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select c.*,u.name as userName FROM s_collect c,s_user u where c.userId = u.id";

    return DbAdapter.getList(this.sql, pageUtil, Collect.class);
  }

  public Collect getCollect(String collectBeanId, String collectModule) throws DaoException, RenderException {
    this.sql = "select c.*,u.name as userName FROM s_collect c,s_user u WHERE c.collectBeanId ='" + 
      collectBeanId + "' and c.collectModule like '%" + collectModule + "%' and c.userId = u.id";
    return ((Collect)DbAdapter.get(this.sql, Collect.class));
  }
}