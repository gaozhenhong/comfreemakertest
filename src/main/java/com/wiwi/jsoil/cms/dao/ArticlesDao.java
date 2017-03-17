package com.wiwi.jsoil.cms.dao;

import com.wiwi.jsoil.cms.model.Articles;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class ArticlesDao extends DaoBase
{
  private String sql;

  public ArticlesDao()
  {
    this.sql = null; }

  public void insert(Articles instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "cms_articles");
  }

  public void update(Articles instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "cms_articles");
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

    this.sql = "UPDATE cms_articles set status = 4 WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Articles get(long id) throws DaoException, RenderException {
    this.sql = " select c.*,u.name as authorName,t.name as typeName  FROM cms_articles c,s_user u,s_category t where c.authorId = u.id and c.typeId = t.id and c.id ='" + 
      id + "'";
    return ((Articles)DbAdapter.get(this.sql, Articles.class));
  }

  public List<Articles> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = " select c.*,u.name as authorName,t.name as typeName  FROM cms_articles c,s_user u,s_category t where c.authorId = u.id and c.typeId = t.id";

    return DbAdapter.getList(this.sql, pageUtil, Articles.class);
  }
}