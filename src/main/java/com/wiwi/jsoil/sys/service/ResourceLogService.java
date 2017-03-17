package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.ResourceLogDao;
import com.wiwi.jsoil.sys.model.ResourceLog;
import java.util.List;

public class ResourceLogService
{
  public void insert(ResourceLog instance)
    throws DaoException, RenderException
  {
    ResourceLogDao dao = new ResourceLogDao();
    dao.insert(instance);
  }

  public void update(ResourceLog instance) throws DaoException, RenderException {
    ResourceLogDao dao = new ResourceLogDao();
    dao.update(instance);
  }

  public void delete(String id) throws DaoException {
    batchDelete(id);
  }

  public void batchDelete(String ids) throws DaoException {
    ResourceLogDao dao = new ResourceLogDao();
    dao.delete(ids);
  }

  public ResourceLog get(String id) throws DaoException, RenderException {
    ResourceLogDao dao = new ResourceLogDao();
    return dao.get(id);
  }

  public List<ResourceLog> getList(PageUtil pageUtil) throws DaoException, RenderException {
    ResourceLogDao dao = new ResourceLogDao();
    return dao.getList(pageUtil);
  }
}