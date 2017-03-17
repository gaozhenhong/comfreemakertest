package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.DictionaryOption;
import java.util.List;

public class DictionaryOptionDao extends DaoBase
{
  private String sql;

  public DictionaryOptionDao()
  {
    this.sql = null; }

  public void insert(DictionaryOption instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_dictionary_option");
  }

  public void update(DictionaryOption instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_dictionary_option");
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

    this.sql = "DELETE FROM s_dictionary_option WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public DictionaryOption get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM s_dictionary_option WHERE id ='" + id + "'";
    return ((DictionaryOption)DbAdapter.get(this.sql, DictionaryOption.class));
  }

  public DictionaryOption getByCode(String dictionaryCode, String optionCode)
    throws DaoException, RenderException
  {
    this.sql = "select * FROM s_dictionary_option WHERE  dictionaryCode=? and code =?";
    return ((DictionaryOption)DbAdapter.get(this.sql, DictionaryOption.class, new String[] { dictionaryCode, optionCode }));
  }

  public List<DictionaryOption> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    this.sql = "select * FROM s_dictionary_option";
    return DbAdapter.getList(this.sql, pageUtil, DictionaryOption.class);
  }
}