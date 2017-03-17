package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Notice;
import java.util.List;

public class NoticeDao extends DaoBase
{
  private String sql;

  public NoticeDao()
  {
    this.sql = null; }

  public void insert(Notice instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_notice");
  }

  public void update(Notice instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_notice");
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

    this.sql = "DELETE FROM s_notice WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Notice get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM s_notice WHERE id ='" + id + "'";
    return ((Notice)DbAdapter.get(this.sql, Notice.class));
  }

  public List<Notice> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_notice";
    return DbAdapter.getList(this.sql, pageUtil, Notice.class);
  }
}