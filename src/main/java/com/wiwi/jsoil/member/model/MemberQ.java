package com.wiwi.jsoil.member.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class MemberQ extends PageUtil
{
  private Long id;
  private String telphone;
  private String password;
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

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.telphone != null) && (!(this.telphone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND telphone like ?";
      this.parameterList.add('%' + this.telphone + '%');
    }

    if ((this.password != null) && (!(this.password.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND password like ?";
      this.parameterList.add('%' + this.password + '%');
    }

    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like ?";
      this.parameterList.add('%' + this.name + '%');
    }

    if ((this.nickName != null) && (!(this.nickName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND nickName like ?";
      this.parameterList.add('%' + this.nickName + '%');
    }

    if ((this.gender != null) && (this.gender.intValue() != 0)) {
      sqlStr = sqlStr + " AND gender =?";
      this.parameterList.add(this.gender);
    }

    if (this.registerTime != null) {
      sqlStr = sqlStr + " AND registerTime =?";
      this.parameterList.add(this.registerTime);
    }

    if ((this.credits != null) && (this.credits.intValue() != 0)) {
      sqlStr = sqlStr + " AND credits =?";
      this.parameterList.add(this.credits);
    }

    if (this.lastLoginTime != null) {
      sqlStr = sqlStr + " AND lastLoginTime =?";
      this.parameterList.add(this.lastLoginTime);
    }

    if ((this.logoId != null) && (this.logoId.intValue() != 0)) {
      sqlStr = sqlStr + " AND logoId =?";
      this.parameterList.add(this.logoId);
    }

    if ((this.status != null) && (this.status.intValue() != 0)) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    if ((this.imageId != null) && (this.imageId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND imageId =?";
      this.parameterList.add(this.imageId);
    }

    if (this.siteCode != null) {
      sqlStr = sqlStr + " AND siteCode =?";
      this.parameterList.add(this.siteCode);
    }

    if (this.openId != null) {
      sqlStr = sqlStr + " AND openId =?";
      this.parameterList.add(this.openId);
    }

    return sqlStr;
  }

  public Long getId() {
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

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
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
}