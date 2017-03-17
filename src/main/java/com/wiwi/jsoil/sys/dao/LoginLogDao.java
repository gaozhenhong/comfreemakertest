package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.KeyGenerator;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.db.Transaction;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.sys.model.LoginLog;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginLogDao extends DaoBase
{
  private String sql = null;

  private Connection conn = null;

  private PreparedStatement ps = null;

  private ResultSet rs = null;

  private static Logger log = LoggerFactory.getLogger(LoginLogDao.class);

  public void add(LoginLog loginInfo)
    throws Exception
  {
    try
    {
      KeyGenerator keygen = null;

      keygen = KeyGenerator.getInstance();
      String id = keygen.getUUID();

      this.conn = getConn(true);

      this.sql = "insert into s_loginlog(id,userId,loginTime,logoutTime,clientIp,clientHost) VALUES(?,?,?,?,?,?)";
      this.ps = this.conn.prepareStatement(this.sql);
      this.ps.setString(1, id);
      this.ps.setLong(2, loginInfo.getUser().getId());
      this.ps.setTimestamp(3, new Timestamp(loginInfo.getLoginTime().getTime()));
      this.ps.setTimestamp(4, new Timestamp(loginInfo.getLogoutTime().getTime()));
      this.ps.setString(5, loginInfo.getClientIp());
      this.ps.setString(6, loginInfo.getClientHost());
      this.ps.executeUpdate();
      log.debug("记录登录日志：", this.sql);
    } catch (SQLException e) {
      log.error("写登录日志出现sql异常: ", e);
    } finally {
      closeStmt(this.ps);
      closeConn(this.conn);
    }
  }

  public List<LoginLog> getList(PageUtil pageUtil) throws Exception {
    List list = new ArrayList();
    try {
      this.conn = getConn(true);
      this.sql = "select l.*,u.name as UserName,u.loginName as userLoginName,u.createTime as userCreateTime,u.orgId,d.cityId,d.name as userOrgName from s_loginlog l inner join S_User u on(l.userId=u.id) inner join s_organization d on (u.orgId=d.id) ";

      this.rs = DbAdapter.getRsPerPage(this.conn, this.ps, this.sql, pageUtil, "id");

      Organization org = null;
      while (this.rs.next()) {
        LoginLog instance = new LoginLog();
        instance.setId(this.rs.getString("id"));
        instance.setClientIp(this.rs.getString("clientIp"));
        instance.setLoginTime(this.rs.getTimestamp("loginTime"));
        instance.setLogoutTime(this.rs.getTimestamp("logoutTime"));

        User user = new User();
        user.setId(Long.valueOf(this.rs.getLong("userId")));
        user.setName(this.rs.getString("userName"));
        user.setCreateTime(this.rs.getDate("userCreateTime"));
        user.setLoginName(this.rs.getString("userLoginName"));

        org = new OrganizationDao().get(this.rs.getInt("orgId"));
        if (org == null)
          org = new Organization();
        user.setOrg(org);
        instance.setUser(user);
        list.add(instance);
      }
      return list;
    } catch (SQLException e) {
      log.error("获得登录日志信息列表时出现错误:" + e);
      throw new DaoException("从数据库中查询人员列表时出现错误！");
    } finally {
      closeRs(this.rs);
      closeStmt(this.ps);
      closeConn(this.conn);
    }
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(",")) {
      ids = ids.substring(1);
    }
    if (ids.indexOf(",") != -1) {
      ids = ids.replaceAll(",", "','");
    }
    if (!ids.startsWith("'")) {
      ids = "'" + ids;
    }
    if (!ids.endsWith("'")) {
      ids = ids + "'";
    }
    Transaction tran = new Transaction();
    this.sql = ("delete from s_loginlog where id in (" + ids + ") ");
    tran.executeUpdate(this.sql);
    tran.commit();
  }
}