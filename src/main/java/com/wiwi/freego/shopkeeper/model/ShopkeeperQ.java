package com.wiwi.freego.shopkeeper.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class ShopkeeperQ extends PageUtil
{
  private Long id;
  private String name;
  private String weixin;
  private Long logo;
  private String description;
  private String telephone;
  private Integer orgId;
  private String orgName;

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

    if ((this.orgId != null) && (this.orgId.intValue() != 0)) {
      sqlStr = sqlStr + " AND orgId = ?";
      this.parameterList.add(this.orgId);
    }

    if ((this.weixin != null) && (!(this.weixin.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND weixin like ?";
      this.parameterList.add('%' + this.weixin + '%');
    }

    if ((this.logo != null) && (this.logo.longValue() != 0L)) {
      sqlStr = sqlStr + " AND logo =?";
      this.parameterList.add(this.logo);
    }

    if ((this.description != null) && (!(this.description.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND description like ?";
      this.parameterList.add('%' + this.description + '%');
    }

    if ((this.telephone != null) && (!(this.telephone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND telephone like ?";
      this.parameterList.add('%' + this.telephone + '%');
    }

    if ((this.orgName != null) && (!(this.orgName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND orgName like ?";
      this.parameterList.add('%' + this.orgName + '%');
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

  public String getWeixin() {
    return this.weixin;
  }

  public void setWeixin(String weixin) {
    this.weixin = weixin;
  }

  public Long getLogo() {
    return this.logo;
  }

  public void setLogo(Long logo) {
    this.logo = logo;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTelephone() {
    return this.telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
}