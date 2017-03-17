package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Group;
import java.util.List;

public class GroupDao extends DaoBase
{
  private String sql;

  public GroupDao()
  {
    this.sql = null; }

  public void insert(Group instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_group");
  }

  public void update(Group instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_group");
  }

  public void delete(String ids) throws DaoException {
    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "DELETE FROM s_group WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Group get(int id) throws DaoException, RenderException {
    this.sql = "select * FROM s_group WHERE id ='" + id + "'";
    return ((Group)DbAdapter.get(this.sql, Group.class));
  }

  public List<Group> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    this.sql = "select * FROM s_group";
    return DbAdapter.getList(this.sql, pageUtil, Group.class);
  }

  public void addUserToGroup(int groupId, Long[] userIds)
    throws DaoException
  {
    Long[] arrayOfLong = userIds; int j = userIds.length; for (int i = 0; i < j; ++i) { Long userId = arrayOfLong[i];
      this.sql = "insert into S_GroupUser (GroupId,UserId) VALUES(?,?)";
      DbAdapter.executeUpdate(this.sql, new Object[] { Integer.valueOf(groupId), userId });
    }
  }

  public void removeUserFromGroup(int groupId, Long[] userIds)
    throws DaoException
  {
    Long[] arrayOfLong = userIds; int j = userIds.length; for (int i = 0; i < j; ++i) { Long userId = arrayOfLong[i];
      this.sql = "delete from S_GroupUser where groupId=? and userId=?";
      DbAdapter.executeUpdate(this.sql, new Object[] { Integer.valueOf(groupId), userId });
    }
  }

  public void updateGroupModule(int groupId, String[] moduleIds)
    throws DaoException
  {
    this.sql = "delete from s_groupmodule where groupId=?";
    DbAdapter.executeUpdate(this.sql, new Object[] { Integer.valueOf(groupId) });

    this.sql = "insert into s_groupmodule (groupId,moduleId) VALUES(?,?)";
    String[] arrayOfString = moduleIds; int j = moduleIds.length; for (int i = 0; i < j; ++i) { String moduleId = arrayOfString[i];
      DbAdapter.executeUpdate(this.sql, new Object[] { Integer.valueOf(groupId), new Integer(moduleId) });
    }
  }

  public boolean inGroup(long groupId, long userId)
    throws DaoException
  {
    this.sql = "select * from S_GroupUser where groupId=" + groupId + " and userId=" + userId;
    return (DbAdapter.count(this.sql) > 0L);
  }
}