package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.ResourceDao;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.sys.model.ResourceLog;
import com.wiwi.jsoil.sys.model.User;
import java.util.Date;
import java.util.List;

public class ResourceService
{
  public void insert(Resource instance)
    throws DaoException, RenderException
  {
    ResourceDao dao = new ResourceDao();
    dao.insert(instance);
  }

  public void update(Resource instance) throws DaoException, RenderException {
    ResourceDao dao = new ResourceDao();
    dao.update(instance);
  }

  public void addDownloadCount(long id, User user) throws DaoException, RenderException {
    ResourceDao dao = new ResourceDao();
    dao.addDownloadCount(id);

    ResourceLogService resourceLogService = new ResourceLogService();
    ResourceLog resourceLog = new ResourceLog();
    resourceLog.setOperateTime(new Date());
    resourceLog.setOperateType("download");
    resourceLog.setResourceId(Long.valueOf(id));
    resourceLog.setUserId(Long.valueOf(user.getId().longValue()));
    resourceLogService.insert(resourceLog);
  }

  public void addBrowseCount(long id) throws DaoException, RenderException {
    ResourceDao dao = new ResourceDao();
    dao.addBrowseCount(id);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    ResourceDao dao = new ResourceDao();
    dao.delete(ids);
  }

  public Resource get(long id) throws DaoException, RenderException {
    ResourceDao dao = new ResourceDao();
    return dao.get(id);
  }

  public List<Resource> getList(PageUtil pageUtil) throws DaoException, RenderException {
    ResourceDao dao = new ResourceDao();
    return dao.getList(pageUtil);
  }
}