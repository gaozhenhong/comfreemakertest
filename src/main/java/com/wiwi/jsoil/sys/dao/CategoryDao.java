package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Category;
import java.util.List;

public class CategoryDao extends DaoBase
{
  private String sql;

  public CategoryDao()
  {
    this.sql = null; }

  public void insert(Category instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_category");
  }

  public void update(Category instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_category");
  }

  public void delete(String ids) throws DaoException {
    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "DELETE FROM s_category WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Category get(long id) throws DaoException, RenderException {
    this.sql = "select m.*,p.name as parentCategoryName , p.code as parentCategoryCode FROM s_category m,s_category p where m.parentCategoryId = p.id and m.id ='" + 
      id + "'";
    return ((Category)DbAdapter.get(this.sql, Category.class));
  }

  public Category getByCode(String code) throws DaoException, RenderException {
    this.sql = "select m.*,p.name as parentCategoryName, p.code as parentCategoryCode FROM s_category m,s_category p where m.parentCategoryId = p.id and m.code =?";

    return ((Category)DbAdapter.get(this.sql, Category.class, new Object[] { code }));
  }

  public List<Category> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select m.*,p.name as parentCategoryName, p.code as parentCategoryCode  FROM s_category m,s_category p where m.parentCategoryId = p.id";

    return DbAdapter.getList(this.sql, pageUtil, Category.class);
  }
}