package com.wiwi.freego.shopkeeper.model;

import com.wiwi.jsoil.sys.model.Organization;

public class Shopkeeper
{
  private Long id;
  private String name;
  private String weixin;
  private Long logo;
  private String description;
  private String telephone;
  private Organization org;
  private Long qrCodeId;

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

  public Organization getOrg() {
    return this.org;
  }

  public void setOrg(Organization org) {
    this.org = org;
  }

  public Long getQrCodeId() {
    return this.qrCodeId;
  }

  public void setQrCodeId(Long qrCodeId) {
    this.qrCodeId = qrCodeId;
  }
}