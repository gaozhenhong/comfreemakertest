package com.wiwi.freego.hotel.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class GuestQ extends PageUtil
{
  private Long id;
  private String name;
  private String idCardType;
  private String idCardNo;
  private String telephone;
  private Integer gender;

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

    if ((this.idCardType != null) && (!(this.idCardType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND idCardType like ?";
      this.parameterList.add('%' + this.idCardType + '%');
    }

    if ((this.idCardNo != null) && (!(this.idCardNo.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND idCardNo like ?";
      this.parameterList.add('%' + this.idCardNo + '%');
    }

    if ((this.telephone != null) && (!(this.telephone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND telephone like ?";
      this.parameterList.add('%' + this.telephone + '%');
    }

    if ((this.gender != null) && (this.gender.intValue() != 0)) {
      sqlStr = sqlStr + " AND gender =?";
      this.parameterList.add(this.gender);
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

  public String getIdCardType() {
    return this.idCardType;
  }

  public void setIdCardType(String idcardType) {
    this.idCardType = idcardType;
  }

  public String getIdCardNo() {
    return this.idCardNo;
  }

  public void setIdCardNo(String idcard) {
    this.idCardNo = idcard;
  }

  public String getTelephone() {
    return this.telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Integer getGender() {
    return this.gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }
}