package com.wiwi.jsoil.member.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Member
{
  private Long id;
  private String telphone;
  private String name;
  private String nickName;
  private Integer gender;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date registerTime;
  private Integer credits;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastLoginTime;
  private Integer logoId;
  private Integer status;
  private Long imageId;
  private String siteCode;
  private String openId;
  private String wxLogoUrl;
  private String password;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTelphone() {
    return this.telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNickName() {
    return this.nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Integer getGender() {
    return this.gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Date getRegisterTime() {
    return this.registerTime;
  }

  public void setRegisterTime(Date registerTime) {
    this.registerTime = registerTime;
  }

  public Integer getCredits() {
    return this.credits;
  }

  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  public Date getLastLoginTime() {
    return this.lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public Integer getLogoId() {
    return this.logoId;
  }

  public void setLogoId(Integer logoId) {
    this.logoId = logoId;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getImageId() {
    return this.imageId;
  }

  public void setImageId(Long imageId) {
    this.imageId = imageId;
  }

  public String getSiteCode() {
    return this.siteCode;
  }

  public void setSiteCode(String siteCode) {
    this.siteCode = siteCode;
  }

  public String getOpenId() {
    return this.openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getWxLogoUrl() {
    return this.wxLogoUrl;
  }

  public void setWxLogoUrl(String wxLogoUrl) {
    this.wxLogoUrl = wxLogoUrl;
  }

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}
}