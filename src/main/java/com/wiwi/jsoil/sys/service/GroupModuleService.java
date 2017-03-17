package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.sys.dao.GroupModuleDao;
import java.util.List;

public class GroupModuleService
{
  public void save(String groupId, String[] moduleId)
    throws Exception
  {
    if (moduleId == null)
      return;
    GroupModuleDao groupRightDao = new GroupModuleDao();
    groupRightDao.save(groupId, moduleId);
  }

  public void delete(String groupId, String functionIds)
    throws Exception
  {
    if (functionIds == null)
      functionIds = "";

    if (functionIds.equalsIgnoreCase("")) {
      return;
    }

    GroupModuleDao groupRightDAO = new GroupModuleDao();
    groupRightDAO.delete(groupId, functionIds);
  }

  public List getList(String groupId)
    throws Exception
  {
    GroupModuleDao groupRightDAO = new GroupModuleDao();
    return groupRightDAO.getFunctionList(Integer.parseInt(groupId));
  }
}