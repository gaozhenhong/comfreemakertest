package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Knowledge;
import java.util.List;

public class KnowledgeDao extends DaoBase
{
  private String sql;

  public KnowledgeDao()
  {
    this.sql = null; }

  public void insert(Knowledge instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "km_knowledge");
  }

  public void update(Knowledge instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "km_knowledge");
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

    this.sql = "DELETE FROM km_knowledge WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Knowledge get(long id) throws DaoException, RenderException {
    this.sql = " select m.*, c.name as categoryName  FROM km_knowledge m,s_category c  WHERE m.categoryId = c.id and m. id ='" + 
      id + "'";
    return ((Knowledge)DbAdapter.get(this.sql, Knowledge.class));
  }

  public List<Knowledge> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    this.sql = " select m.*, c.name as categoryName  FROM km_knowledge m,s_category c  WHERE m.categoryId = c.id";

    return DbAdapter.getList(this.sql, pageUtil, Knowledge.class);
  }

  public void addDownloadCount(long id)
    throws DaoException
  {
    this.sql = "update km_knowledge set downloadCount = downloadCount + 1 where id=? ";
    DbAdapter.executeUpdate(this.sql, new Long[] { Long.valueOf(id) });
  }

  public void addBrowseCount(long id)
    throws DaoException
  {
    this.sql = "update km_knowledge set browseCount = browseCount + 1 where id=? ";
    DbAdapter.executeUpdate(this.sql, new Long[] { Long.valueOf(id) });
  }
}