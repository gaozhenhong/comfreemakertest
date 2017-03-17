package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.db.Transaction;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import java.util.List;
import org.apache.log4j.Logger;

public class UserDao
{
  private static Logger logger = Logger.getLogger(UserDao.class);
  private String sql;

  public UserDao()
  {
    this.sql = null; }

  public boolean isLoginNameRepeat(String loginName, long id) throws DaoException {
    this.sql = "SELECT id FROM S_User WHERE loginName=? AND id<>" + id;
    return DbAdapter.havaRecord(this.sql, new String[] { loginName.trim() });
  }

  public void insert(User instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "S_User");
  }

  public void update(User instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "S_User");
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1) {
      ids = ids.replaceAll(",", "','");
    }

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    if (ids.indexOf("'1'") > -1)
    {
      ids = ids.replaceAll("'1'", "");
      if (!(ids.startsWith(",")))
        ids = ids.substring(1);

      if (!(ids.endsWith(",")))
        ids = ids.substring(0, ids.length() - 1);
    }

    Transaction tran = new Transaction();
    this.sql = "DELETE FROM S_GroupUser WHERE userId in (" + ids + ") ";
    tran.executeUpdate(this.sql);
    this.sql = "DELETE FROM S_User WHERE id in (" + ids + ") ";
    tran.executeUpdate(this.sql);
    tran.commit();
  }

  public User get(long id) throws DaoException, RenderException {
    this.sql = "select m.*,d.name as orgName from S_User m,S_organization d where m.orgId=d.id and m.id='" + id + "'";
    return ((User)DbAdapter.get(this.sql, User.class));
  }

  public User getByLoginName(String loginName) throws DaoException, RenderException {
    this.sql = "select m.*,d.name as orgName from S_User m,S_organization d where m.orgId=d.id and m.loginName=?";
    return ((User)DbAdapter.get(this.sql, User.class, new String[] { loginName.trim() }));
  }

  public List getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select m.*,d.name as orgName,d.typeId as deptTypeId,t.name as deptTypeName,d.cityId as deptSiteId from S_User m,S_organization d,s_category t  where m.orgId=d.id and d.typeId=t.id ";

    return DbAdapter.getList(this.sql, pageUtil, User.class);
  }

  public User login(String loginName, String loginPswd) throws DaoException, RenderException {
    this.sql = "select u.*,d.name as orgName from S_User u,S_organization d where u.orgId=d.id and u.status='1' and u.loginName=? and u.loginPswd=? ";
    User user = (User)DbAdapter.get(this.sql, User.class, new String[] { loginName.trim(), loginPswd });
    return user;
  }

  public boolean checkPswd(Long userId, String loginPswd) throws DaoException {
    this.sql = "select * from S_User where id='" + userId + "' and loginPswd=?";
    return DbAdapter.havaRecord(this.sql, new String[] { loginPswd });
  }

  public void changePswd(Long userId, String newPswd) throws DaoException {
    this.sql = "update S_User set loginPswd='" + newPswd + "' where id='" + userId + "'";
    DbAdapter.executeUpdate(this.sql);
  }

  public void setStatus(String ids, String status, String userId) throws DaoException {
    Transaction tran = new Transaction();
    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "update S_User set status='" + status + "' where id in (" + ids + ")";
    tran.executeUpdate(this.sql);

    tran.commit();
  }
}