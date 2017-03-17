package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class PlatformProperty
{
  private Long id;
  private String name;
  private String code;
  private String value;
  private Integer companyId;
  private Integer orgId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyTime;
  private Long lastModifyUser;
  private String remark;

  public Long getId()
  {
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

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Integer getCompanyId() {
    return this.companyId;
  }

  public void setCompanyId(Integer companyId) {
    this.companyId = companyId;
  }

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public Date getLastModifyTime() {
    return this.lastModifyTime;
  }

  public void setLastModifyTime(Date lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }

  public Long getLastModifyUser() {
    return this.lastModifyUser;
  }

  public void setLastModifyUser(Long lastModifyUser) {
    this.lastModifyUser = lastModifyUser;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}