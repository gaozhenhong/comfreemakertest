package com.wiwi.freego.areamanager.service;

import com.wiwi.freego.areamanager.dao.AreaManagerDao;
import com.wiwi.freego.areamanager.model.AreaManager;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class AreaManagerService
{
  public void insert(AreaManager instance)
    throws DaoException, RenderException
  {
    AreaManagerDao dao = new AreaManagerDao();
    dao.insert(instance);
  }

  public void update(AreaManager instance) throws DaoException, RenderException {
    AreaManagerDao dao = new AreaManagerDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    AreaManagerDao dao = new AreaManagerDao();
    dao.delete(ids);
  }

  public AreaManager get(long id) throws DaoException, RenderException {
    AreaManagerDao dao = new AreaManagerDao();
    return dao.get(id);
  }

  public List<AreaManager> getList(PageUtil pageUtil) throws DaoException, RenderException {
    AreaManagerDao dao = new AreaManagerDao();
    return dao.getList(pageUtil);
  }
}