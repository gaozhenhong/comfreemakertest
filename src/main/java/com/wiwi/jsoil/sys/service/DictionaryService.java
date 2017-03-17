package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.DictionaryDao;
import com.wiwi.jsoil.sys.model.Dictionary;
import java.util.List;

public class DictionaryService
{
  public void insert(Dictionary instance)
    throws DaoException, RenderException
  {
    DictionaryDao dao = new DictionaryDao();
    dao.insert(instance);
  }

  public void update(Dictionary instance) throws DaoException, RenderException {
    DictionaryDao dao = new DictionaryDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    DictionaryDao dao = new DictionaryDao();
    dao.delete(ids);
  }

  public Dictionary get(long id) throws DaoException, RenderException {
    DictionaryDao dao = new DictionaryDao();
    return dao.get(id);
  }

  public List<Dictionary> getList(PageUtil pageUtil) throws DaoException, RenderException {
    DictionaryDao dao = new DictionaryDao();
    return dao.getList(pageUtil);
  }
}