package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.GroupDao;
import com.wiwi.jsoil.sys.model.Group;
import com.wiwi.jsoil.sys.model.GroupQ;
import com.wiwi.jsoil.sys.model.User;
import java.util.ArrayList;
import java.util.List;

public class GroupService
{
  public void insert(Group instance)
    throws DaoException, RenderException
  {
    GroupDao dao = new GroupDao();
    dao.insert(instance);
  }
  
  public void update(Group instance)
    throws DaoException, RenderException
  {
    GroupDao dao = new GroupDao();
    dao.update(instance);
  }
  
  public void delete(int id)
    throws DaoException
  {
    batchDelete(id+"");
  }
  
  public void batchDelete(String ids)
    throws DaoException
  {
    GroupDao dao = new GroupDao();
    dao.delete(ids);
  }
  
  public Group get(int id)
    throws DaoException, RenderException
  {
    GroupDao dao = new GroupDao();
    return dao.get(id);
  }
  
  public List<Group> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    GroupDao dao = new GroupDao();
    return dao.getList(pageUtil);
  }
  
  public List<Group> getListByUserId(Long userId)
    throws DaoException, RenderException
  {
    PageUtil query = new GroupQ();
    query.setRecordPerPage(-1);
    String otherCondition = " where id in (select groupId from s_groupuser where userId=" + userId + ")";
    query.setOtherCondition(otherCondition);
    GroupDao dao = new GroupDao();
    return dao.getList(query);
  }
  
  public void addUserToGroup(int groupId, String userIds)
    throws DaoException
  {
    if (userIds == null) {
      userIds = "";
    }
    if (userIds.startsWith(",")) {
      userIds = userIds.substring(1);
    }
    if (userIds.endsWith(",")) {
      userIds = userIds.substring(0, userIds.length() - 1);
    }
    String[] useIdArray = userIds.split(",");
    Long[] idLongs = new Long[useIdArray.length];
    for (int i = 0; i < useIdArray.length; i++) {
      idLongs[i] = Long.valueOf(Long.parseLong(useIdArray[i]));
    }
    GroupDao dao = new GroupDao();
    dao.addUserToGroup(groupId, idLongs);
  }
  
  public void removeUserFromGroup(int groupId, String userIds)
    throws DaoException
  {
    if (userIds == null) {
      userIds = "";
    }
    if (userIds.startsWith(",")) {
      userIds = userIds.substring(1);
    }
    if (userIds.endsWith(",")) {
      userIds = userIds.substring(0, userIds.length() - 1);
    }
    String[] useIdArray = userIds.split(",");
    Long[] idLongs = new Long[useIdArray.length];
    for (int i = 0; i < useIdArray.length; i++) {
      idLongs[i] = Long.valueOf(Long.parseLong(useIdArray[i]));
    }
    GroupDao dao = new GroupDao();
    dao.removeUserFromGroup(groupId, idLongs);
  }
  
  public void updateGroupModule(int groupId, String moduleIdStrs)
    throws DaoException
  {
    if ((moduleIdStrs == null) || (moduleIdStrs.length() < 1)) {
      return;
    }
    if (moduleIdStrs.startsWith(",")) {
      moduleIdStrs = moduleIdStrs.substring(1);
    }
    if (moduleIdStrs.endsWith(",")) {
      moduleIdStrs = moduleIdStrs.substring(0, moduleIdStrs.length() - 1);
    }
    String[] moduleIds = moduleIdStrs.split(",");
    GroupDao dao = new GroupDao();
    dao.updateGroupModule(groupId, moduleIds);
  }
  
  public boolean inGroup(long groupId, long userId)
    throws DaoException
  {
    if ((groupId == 0L) || (userId == 0L)) {
      return false;
    }
    GroupDao groupDao = new GroupDao();
    return groupDao.inGroup(groupId, userId);
  }
  
  public static List<String> getTopWorkbenchList(User user)
    throws DaoException, RenderException
  {
    return getWorkbenchList(user, true);
  }
  
  public static List<String> getWelocmeWorkbenchList(User user)
    throws DaoException, RenderException
  {
    return getWorkbenchList(user, false);
  }
  
  private static List<String> getWorkbenchList(User user, boolean isTop)
    throws DaoException, RenderException
  {
    List<String> workbenchList = new ArrayList();
    List<Group> groupList = new GroupService().getListByUserId(user.getId());
    for (Group group : groupList) {
      if ((group.getWelcomeUrl() != null) && (!group.getWelcomeUrl().isEmpty())) {
        if ((isTop) && (group.getWelcomeUrl().startsWith("top:")))
        {
          String url = group.getWelcomeUrl().substring(4);
          if (!workbenchList.contains(url)) {
            workbenchList.add(url);
          }
        }
        else if ((!isTop) && (!group.getWelcomeUrl().startsWith("top:")) && 
          (!workbenchList.contains(group.getWelcomeUrl())))
        {
          workbenchList.add(group.getWelcomeUrl());
        }
      }
    }
    return workbenchList;
  }
}
