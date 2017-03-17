package com.wiwi.freego.hotel.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class HotelBankAccountQ extends PageUtil
{
  private Long id;
  private Long hotelId;
  private Integer bankAccountType;
  private String bank;
  private String subsidiaryBank;
  private String accountHolder;
  private String accountNo;
  private Integer status;
  private Long lastModifyUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyDate;
  private Integer bankCityId;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.hotelId != null) && (this.hotelId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND hotelId =?";
      this.parameterList.add(this.hotelId);
    }

    if ((this.bankAccountType != null) && (this.bankAccountType.intValue() != 0)) {
      sqlStr = sqlStr + " AND bankAccountType =?";
      this.parameterList.add(this.bankAccountType);
    }

    if ((this.bank != null) && (!(this.bank.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND bank like ?";
      this.parameterList.add('%' + this.bank + '%');
    }

    if ((this.subsidiaryBank != null) && (!(this.subsidiaryBank.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND subsidiaryBank like ?";
      this.parameterList.add('%' + this.subsidiaryBank + '%');
    }

    if ((this.accountHolder != null) && (!(this.accountHolder.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND accountHolder like ?";
      this.parameterList.add('%' + this.accountHolder + '%');
    }

    if ((this.accountNo != null) && (!(this.accountNo.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND accountNo like ?";
      this.parameterList.add('%' + this.accountNo + '%');
    }

    if ((this.status != null) && (this.status.intValue() != 0)) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    if ((this.lastModifyUserId != null) && (this.lastModifyUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND lastModifyUserId =?";
      this.parameterList.add(this.lastModifyUserId);
    }

    if (this.lastModifyDate != null) {
      sqlStr = sqlStr + " AND lastModifyDate =?";
      this.parameterList.add(this.lastModifyDate);
    }

    if ((this.bankCityId != null) && (this.bankCityId.intValue() != 0)) {
      sqlStr = sqlStr + " AND bankCityId =?";
      this.parameterList.add(this.bankCityId);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public Integer getBankAccountType() {
    return this.bankAccountType;
  }

  public void setBankAccountType(Integer bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public String getBank() {
    return this.bank;
  }

  public void setBank(String bank) {
    this.bank = bank;
  }

  public String getSubsidiaryBank() {
    return this.subsidiaryBank;
  }

  public void setSubsidiaryBank(String subsidiaryBank) {
    this.subsidiaryBank = subsidiaryBank;
  }

  public String getAccountHolder() {
    return this.accountHolder;
  }

  public void setAccountHolder(String accountHolder) {
    this.accountHolder = accountHolder;
  }

  public String getAccountNo() {
    return this.accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getLastModifyUserId() {
    return this.lastModifyUserId;
  }

  public void setLastModifyUserId(Long lastModifyUserId) {
    this.lastModifyUserId = lastModifyUserId;
  }

  public Date getLastModifyDate() {
    return this.lastModifyDate;
  }

  public void setLastModifyDate(Date lastModifyDate) {
    this.lastModifyDate = lastModifyDate;
  }

  public Integer getBankCityId() {
    return this.bankCityId;
  }

  public void setBankCityId(Integer bankCityId) {
    this.bankCityId = bankCityId;
  }
}