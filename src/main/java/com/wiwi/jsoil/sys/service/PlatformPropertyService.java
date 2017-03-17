package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.PlatformPropertyDao;
import com.wiwi.jsoil.sys.model.PlatformProperty;
import java.util.List;

public class PlatformPropertyService
{
  public void insert(PlatformProperty instance)
    throws DaoException, RenderException
  {
    PlatformPropertyDao dao = new PlatformPropertyDao();
    dao.insert(instance);
  }

  public void update(PlatformProperty instance) throws DaoException, RenderException {
    PlatformPropertyDao dao = new PlatformPropertyDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    PlatformPropertyDao dao = new PlatformPropertyDao();
    dao.delete(ids);
  }

  public PlatformProperty get(long id) throws DaoException, RenderException {
    PlatformPropertyDao dao = new PlatformPropertyDao();
    return dao.get(id);
  }

  public PlatformProperty getByCode(String code) throws DaoException, RenderException
  {
    PlatformPropertyDao dao = new PlatformPropertyDao();
    return dao.getByCode(code);
  }

  public List<PlatformProperty> getList(PageUtil pageUtil) throws DaoException, RenderException {
    PlatformPropertyDao dao = new PlatformPropertyDao();
    return dao.getList(pageUtil);
  }
}