package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.db.Transaction;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Dictionary;
import java.util.List;

public class DictionaryDao extends DaoBase
{
  private String sql;

  public DictionaryDao()
  {
    this.sql = null; }

  public void insert(Dictionary instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_dictionary");
  }

  public void update(Dictionary instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_dictionary");
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'"))) {
      ids = ids + "'";
    }

    Transaction tran = new Transaction();
    this.sql = "delete from s_dictionary_option where dictionaryId in (" + ids + ") ";
    tran.executeUpdate(this.sql);
    this.sql = "DELETE FROM s_dictionary WHERE id in (" + ids + ") ";
    tran.executeUpdate(this.sql);
    tran.commit();
  }

  public Dictionary get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM s_dictionary WHERE id ='" + id + "'";
    return ((Dictionary)DbAdapter.get(this.sql, Dictionary.class));
  }

  public List<Dictionary> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_dictionary";
    return DbAdapter.getList(this.sql, pageUtil, Dictionary.class);
  }
}