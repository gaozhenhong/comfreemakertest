package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class UserQ extends PageUtil
{
  private Long id;
  private String name;
  private String loginName;
  private String loginPswd;
  private String telephone;
  private String orgId;
  private String orgName;
  private String createUserId;
  private String createTime;
  private String demo;
  private String status;
  private String groupId;
  private String groupIdNotIn;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like ?";
      this.parameterList.add('%' + this.name + '%');
    }

    if ((this.loginName != null) && (!(this.loginName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND loginName like ?";
      this.parameterList.add('%' + this.loginName + '%');
    }

    if ((this.loginPswd != null) && (!(this.loginPswd.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND loginPswd like ?";
      this.parameterList.add('%' + this.loginPswd + '%');
    }

    if ((this.telephone != null) && (!(this.telephone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND telephone like ?";
      this.parameterList.add('%' + this.telephone + '%');
    }

    if ((this.orgId != null) && (!(this.orgId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND orgId like ?";
      this.parameterList.add('%' + this.orgId + '%');
    }

    if ((this.orgName != null) && (!(this.orgName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND orgName like ?";
      this.parameterList.add('%' + this.orgName + '%');
    }

    if ((this.createUserId != null) && (!(this.createUserId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND createUserId like ?";
      this.parameterList.add('%' + this.createUserId + '%');
    }

    if ((this.createTime != null) && (!(this.createTime.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND createTime like ?";
      this.parameterList.add('%' + this.createTime + '%');
    }

    if ((this.demo != null) && (!(this.demo.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND demo like ?";
      this.parameterList.add('%' + this.demo + '%');
    }

    if ((this.status != null) && (!(this.status.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND status like ?";
      this.parameterList.add('%' + this.status + '%');
    }

    if ((this.groupId != null) && (!(this.groupId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND id in (select userId from S_GroupUser where groupId=?)";
      this.parameterList.add(this.groupId);
    }
    if ((this.groupIdNotIn != null) && (!(this.groupIdNotIn.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND id not in (select userId from S_GroupUser where groupId=?)";
      this.parameterList.add(this.groupIdNotIn);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLoginName() {
    return this.loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getLoginPswd() {
    return this.loginPswd;
  }

  public void setLoginPswd(String loginPswd) {
    this.loginPswd = loginPswd;
  }

  public String getTelephone() {
    return this.telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getOrgId() {
    return this.orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getCreateUserId() {
    return this.createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getDemo() {
    return this.demo;
  }

  public void setDemo(String demo) {
    this.demo = demo;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getGroupIdNotIn() {
    return this.groupIdNotIn;
  }

  public void setGroupIdNotIn(String groupIdNotIn) {
    this.groupIdNotIn = groupIdNotIn;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
}