package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.io.Serializable;

public class LoginLogQ extends PageUtil
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String userName;
  private String userLoginName;
  private String userDeptName;
  private Integer userDeptId;
  private String loginTimeStart;
  private String loginTimeEnd;
  private String logoutTimeStart;
  private String logoutTimeEnd;
  private String clientIp;

  public String toWhereString()
  {
    String sqlStr = super.getOtherCondition();
    if ((this.userName != null) && (!(this.userName.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND userName like '%" + this.userName.trim() + "%'";

    if ((this.userLoginName != null) && (!(this.userLoginName.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND userLoginName like '%" + this.userLoginName.trim() + "%'";

    if ((this.userDeptName != null) && (!(this.userDeptName.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND userDeptName like '%" + this.userDeptName.trim() + "%'";

    if ((this.userDeptId != null) && (this.userDeptId.intValue() != 0))
      sqlStr = sqlStr + " AND userDeptId = " + this.userDeptId;

    if ((this.loginTimeStart != null) && (!(this.loginTimeStart.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND loginTime >= '" + this.loginTimeStart.trim() + "'";

    if ((this.loginTimeEnd != null) && (!(this.loginTimeEnd.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND loginTime <= '" + this.loginTimeEnd.trim() + "'";

    if ((this.logoutTimeStart != null) && (!(this.logoutTimeStart.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND logoutTime >= '" + this.logoutTimeStart.trim() + "'";

    if ((this.logoutTimeEnd != null) && (!(this.logoutTimeEnd.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND logoutTime <= '" + this.logoutTimeEnd.trim() + "'";

    if ((this.clientIp != null) && (!(this.clientIp.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND clientIp = '" + this.clientIp.trim() + "'";

    return sqlStr;
  }

  public String getClientIp()
  {
    return this.clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getLoginTimeEnd() {
    return this.loginTimeEnd;
  }

  public void setLoginTimeEnd(String loginTimeEnd) {
    this.loginTimeEnd = loginTimeEnd;
  }

  public String getLoginTimeStart() {
    return this.loginTimeStart;
  }

  public void setLoginTimeStart(String loginTimeStart) {
    this.loginTimeStart = loginTimeStart;
  }

  public String getLogoutTimeEnd() {
    return this.logoutTimeEnd;
  }

  public void setLogoutTimeEnd(String logoutTimeEnd) {
    this.logoutTimeEnd = logoutTimeEnd;
  }

  public String getLogoutTimeStart() {
    return this.logoutTimeStart;
  }

  public void setLogoutTimeStart(String logoutTimeStart) {
    this.logoutTimeStart = logoutTimeStart;
  }

  public Integer getUserDeptId() {
    return this.userDeptId;
  }

  public void setUserDeptId(Integer userDeptId) {
    this.userDeptId = userDeptId;
  }

  public String getUserDeptName() {
    return this.userDeptName;
  }

  public void setUserDeptName(String userDeptName) {
    this.userDeptName = userDeptName;
  }

  public String getUserLoginName() {
    return this.userLoginName;
  }

  public void setUserLoginName(String userLoginName) {
    this.userLoginName = userLoginName;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}