package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class OperationLogQ extends PageUtil
{
  private String id;
  private Long userId;
  private String userName;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date operateTime;
  private String url;
  private String ip;
  private String browser;
  private String operateType;
  private String parameter;
  private Long userOrgId;
  private String userOrgName;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (!(this.id.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND id like ?";
      this.parameterList.add('%' + this.id + '%');
    }

    if ((this.userId != null) && (this.userId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND userId =?";
      this.parameterList.add(this.userId);
    }

    if ((this.userName != null) && (!(this.userName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND userName like ?";
      this.parameterList.add('%' + this.userName + '%');
    }

    if (this.operateTime != null) {
      sqlStr = sqlStr + " AND operateTime =?";
      this.parameterList.add(this.operateTime);
    }

    if ((this.url != null) && (!(this.url.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND url like ?";
      this.parameterList.add('%' + this.url + '%');
    }

    if ((this.ip != null) && (!(this.ip.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND ip like ?";
      this.parameterList.add('%' + this.ip + '%');
    }

    if ((this.browser != null) && (!(this.browser.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND browser like ?";
      this.parameterList.add('%' + this.browser + '%');
    }

    if ((this.operateType != null) && (!(this.operateType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND operateType like ?";
      this.parameterList.add('%' + this.operateType + '%');
    }

    if ((this.parameter != null) && (!(this.parameter.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND parameter like ?";
      this.parameterList.add('%' + this.parameter + '%');
    }

    if ((this.userOrgId != null) && (this.userOrgId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND userOrgId =?";
      this.parameterList.add(this.userOrgId);
    }

    if ((this.userOrgName != null) && (!(this.userOrgName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND userOrgName like ?";
      this.parameterList.add('%' + this.userOrgName + '%');
    }

    return sqlStr;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getOperateTime() {
    return this.operateTime;
  }

  public void setOperateTime(Date operateTime) {
    this.operateTime = operateTime;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getBrowser() {
    return this.browser;
  }

  public void setBrowser(String browser) {
    this.browser = browser;
  }

  public String getOperateType() {
    return this.operateType;
  }

  public void setOperateType(String operateType) {
    this.operateType = operateType;
  }

  public String getParameter() {
    return this.parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public Long getUserOrgId() {
    return this.userOrgId;
  }

  public void setUserOrgId(Long userOrgId) {
    this.userOrgId = userOrgId;
  }

  public String getUserOrgName() {
    return this.userOrgName;
  }

  public void setUserOrgName(String userOrgName) {
    this.userOrgName = userOrgName;
  }
}