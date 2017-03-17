package com.wiwi.freego.areamanager.service;

import com.wiwi.freego.areamanager.dao.AreaUserMappingDao;
import com.wiwi.freego.areamanager.model.AreaUserMapping;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class AreaUserMappingService
{
  public void insert(AreaUserMapping instance)
    throws DaoException, RenderException
  {
    AreaUserMappingDao dao = new AreaUserMappingDao();
    dao.insert(instance);
  }

  public void update(AreaUserMapping instance) throws DaoException, RenderException {
    AreaUserMappingDao dao = new AreaUserMappingDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    AreaUserMappingDao dao = new AreaUserMappingDao();
    dao.delete(ids);
  }

  public AreaUserMapping get(long id) throws DaoException, RenderException {
    AreaUserMappingDao dao = new AreaUserMappingDao();
    return dao.get(id);
  }

  public List<AreaUserMapping> getList(PageUtil pageUtil) throws DaoException, RenderException {
    AreaUserMappingDao dao = new AreaUserMappingDao();
    return dao.getList(pageUtil);
  }

  public void addUserToArea(long areaId, Long[] userIds)
    throws DaoException, RenderException
  {
    AreaUserMappingDao dao = new AreaUserMappingDao();
    Long[] arrayOfLong = userIds; int j = userIds.length; for (int i = 0; i < j; ++i) { Long userId = arrayOfLong[i];
      AreaUserMapping aum = new AreaUserMapping();
      aum.setAreaId(Long.valueOf(areaId));
      aum.setUserId(userId);
      dao.insert(aum);
    }
  }

  public void removeUserFromArea(int areaId, String userIds) throws DaoException {
    if (userIds == null)
      userIds = "";
    if (userIds.startsWith(","))
      userIds = userIds.substring(1);
    if (userIds.endsWith(","))
      userIds = userIds.substring(0, userIds.length() - 1);
    String[] useIdArray = userIds.split(",");
    Long[] idLongs = new Long[useIdArray.length];
    for (int i = 0; i < useIdArray.length; ++i)
      idLongs[i] = Long.valueOf(Long.parseLong(useIdArray[i]));

    AreaUserMappingDao dao = new AreaUserMappingDao();
    dao.removeUserFromArea(areaId, idLongs);
  }
}