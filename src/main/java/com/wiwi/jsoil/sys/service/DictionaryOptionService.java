package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.DictionaryOptionDao;
import com.wiwi.jsoil.sys.model.DictionaryOption;
import com.wiwi.jsoil.sys.model.DictionaryOptionQ;
import java.util.List;

public class DictionaryOptionService
{
  public void insert(DictionaryOption instance)
    throws DaoException, RenderException
  {
    DictionaryOptionDao dao = new DictionaryOptionDao();
    dao.insert(instance);
  }

  public void update(DictionaryOption instance) throws DaoException, RenderException {
    DictionaryOptionDao dao = new DictionaryOptionDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    DictionaryOptionDao dao = new DictionaryOptionDao();
    dao.delete(ids);
  }

  public DictionaryOption get(long id) throws DaoException, RenderException {
    DictionaryOptionDao dao = new DictionaryOptionDao();
    return dao.get(id);
  }

  public DictionaryOption getByCode(String dictionaryCode, String optionCode) throws DaoException, RenderException {
    DictionaryOptionDao dao = new DictionaryOptionDao();
    return dao.getByCode(dictionaryCode, optionCode);
  }

  public List<DictionaryOption> getList(PageUtil pageUtil) throws DaoException, RenderException {
    DictionaryOptionDao dao = new DictionaryOptionDao();
    return dao.getList(pageUtil);
  }

  public List<DictionaryOption> getListByDictionaryCode(String dictionaryCode)
    throws DaoException, RenderException
  {
    DictionaryOptionQ query = new DictionaryOptionQ();
    query.setRecordPerPage(-1);
    query.setDictionaryCode(dictionaryCode);
    query.setOrderByKind("DESC");
    query.setOrderByProperty("orderNo");
    DictionaryOptionDao dao = new DictionaryOptionDao();
    return dao.getList(query);
  }

  public List<DictionaryOption> getListByDictionaryId(long dictionaryId)
    throws DaoException, RenderException
  {
    DictionaryOptionQ query = new DictionaryOptionQ();
    query.setRecordPerPage(-1);
    query.setOrderByKind("DESC");
    query.setOrderByProperty("orderNo");
    query.setDictionaryId(Long.valueOf(dictionaryId));
    DictionaryOptionDao dao = new DictionaryOptionDao();
    return dao.getList(query);
  }
}