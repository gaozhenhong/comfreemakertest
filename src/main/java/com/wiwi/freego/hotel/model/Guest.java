package com.wiwi.freego.hotel.model;

public class Guest
{
  private Long id;
  private String name;
  private String idCardType;
  private String idCardNo;
  private String telephone;
  private Integer gender;
  private String nation;
  private String address;

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

  public String getNation() {
    return this.nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}