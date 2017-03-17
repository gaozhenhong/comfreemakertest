package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;

public class OrganizationQ extends PageUtil
{
  private Integer id;
  private String code;
  private String name;
  private String typeId;
  private String parentOrgId;
  private String parentOrgName;
  private Integer cityId;
  private String linkMan;
  private String linkTel;
  private String mobilePhone;
  private String address;
  private String email;
  private String url;
  private String demo;
  private String inputUserId;
  private Date inputTime;
  private String lastModifiedUserId;
  private Date lastModifiedTime;
  private Integer status;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.intValue() != 0)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.code != null) && (!(this.code.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND code like ?";
      this.parameterList.add(this.code);
    }

    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like ?";
      this.parameterList.add('%' + this.name + '%');
    }

    if ((this.parentOrgName != null) && (!(this.parentOrgName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND parentOrgName like ?";
      this.parameterList.add('%' + this.parentOrgName + '%');
    }

    if ((this.typeId != null) && (!(this.typeId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND typeId like ?";
      this.parameterList.add('%' + this.typeId + '%');
    }

    if ((this.parentOrgId != null) && (!(this.parentOrgId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND parentOrgId like ?";
      this.parameterList.add('%' + this.parentOrgId + '%');
    }

    if ((this.cityId != null) && (this.cityId.intValue() != 0)) {
      sqlStr = sqlStr + " AND cityId =?";
      this.parameterList.add(this.cityId);
    }

    if ((this.linkMan != null) && (!(this.linkMan.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND linkMan like ?";
      this.parameterList.add('%' + this.linkMan + '%');
    }

    if ((this.linkTel != null) && (!(this.linkTel.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND linkTel like ?";
      this.parameterList.add('%' + this.linkTel + '%');
    }

    if ((this.mobilePhone != null) && (!(this.mobilePhone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND mobilePhone like ?";
      this.parameterList.add('%' + this.mobilePhone + '%');
    }

    if ((this.address != null) && (!(this.address.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND address like ?";
      this.parameterList.add('%' + this.address + '%');
    }

    if ((this.email != null) && (!(this.email.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND email like ?";
      this.parameterList.add('%' + this.email + '%');
    }

    if ((this.url != null) && (!(this.url.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND url like ?";
      this.parameterList.add('%' + this.url + '%');
    }

    if ((this.demo != null) && (!(this.demo.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND demo like ?";
      this.parameterList.add('%' + this.demo + '%');
    }

    if ((this.inputUserId != null) && (!(this.inputUserId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND inputUserId like ?";
      this.parameterList.add('%' + this.inputUserId + '%');
    }

    if (this.inputTime != null) {
      sqlStr = sqlStr + " AND inputTime =?";
      this.parameterList.add(this.inputTime);
    }

    if ((this.lastModifiedUserId != null) && (!(this.lastModifiedUserId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND lastModifiedUserId like ?";
      this.parameterList.add('%' + this.lastModifiedUserId + '%');
    }

    if (this.lastModifiedTime != null) {
      sqlStr = sqlStr + " AND lastModifiedTime =?";
      this.parameterList.add(this.lastModifiedTime);
    }

    if ((this.status != null) && (this.status.intValue() != 0)) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    return sqlStr;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTypeId() {
    return this.typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  public String getParentOrgId()
  {
    return this.parentOrgId;
  }

  public void setParentOrgId(String parentOrgId) {
    this.parentOrgId = parentOrgId;
  }

  public String getParentOrgName() {
    return this.parentOrgName;
  }

  public void setParentOrgName(String parentOrgName) {
    this.parentOrgName = parentOrgName;
  }

  public Integer getCityId() {
    return this.cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public String getLinkMan() {
    return this.linkMan;
  }

  public void setLinkMan(String linkMan) {
    this.linkMan = linkMan;
  }

  public String getLinkTel() {
    return this.linkTel;
  }

  public void setLinkTel(String linkTel) {
    this.linkTel = linkTel;
  }

  public String getMobilePhone() {
    return this.mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDemo() {
    return this.demo;
  }

  public void setDemo(String demo) {
    this.demo = demo;
  }

  public String getInputUserId() {
    return this.inputUserId;
  }

  public void setInputUserId(String inputUserId) {
    this.inputUserId = inputUserId;
  }

  public Date getInputTime() {
    return this.inputTime;
  }

  public void setInputTime(Date inputTime) {
    this.inputTime = inputTime;
  }

  public String getLastModifiedUserId() {
    return this.lastModifiedUserId;
  }

  public void setLastModifiedUserId(String lastModifiedUserId) {
    this.lastModifiedUserId = lastModifiedUserId;
  }

  public Date getLastModifiedTime() {
    return this.lastModifiedTime;
  }

  public void setLastModifiedTime(Date lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}