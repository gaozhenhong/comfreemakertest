package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class PlatformPropertyQ extends PageUtil
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

    if ((this.code != null) && (!(this.code.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND code like ?";
      this.parameterList.add('%' + this.code + '%');
    }

    if ((this.value != null) && (!(this.value.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND value like ?";
      this.parameterList.add('%' + this.value + '%');
    }

    if ((this.companyId != null) && (this.companyId.intValue() != 0)) {
      sqlStr = sqlStr + " AND companyId =?";
      this.parameterList.add(this.companyId);
    }

    if ((this.orgId != null) && (this.orgId.intValue() != 0)) {
      sqlStr = sqlStr + " AND orgId =?";
      this.parameterList.add(this.orgId);
    }

    if (this.lastModifyTime != null) {
      sqlStr = sqlStr + " AND lastModifyTime =?";
      this.parameterList.add(this.lastModifyTime);
    }

    if ((this.lastModifyUser != null) && (this.lastModifyUser.longValue() != 0L)) {
      sqlStr = sqlStr + " AND lastModifyUser =?";
      this.parameterList.add(this.lastModifyUser);
    }

    if ((this.remark != null) && (!(this.remark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND remark like ?";
      this.parameterList.add('%' + this.remark + '%');
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