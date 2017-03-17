package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class OperationLog
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
  private Integer userOrgId;
  private String userOrgName;

  public String getId()
  {
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

  public Integer getUserOrgId() {
    return this.userOrgId;
  }

  public void setUserOrgId(Integer userOrgId) {
    this.userOrgId = userOrgId;
  }

  public String getUserOrgName() {
    return this.userOrgName;
  }

  public void setUserOrgName(String userOrgName) {
    this.userOrgName = userOrgName;
  }
}