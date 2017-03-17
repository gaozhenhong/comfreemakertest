package com.wiwi.freego.hotel.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.wiwi.jsoil.util.BankUtil;

public class HotelBankAccount
{
  private Long id;
  private Hotel hotel;
  private Integer bankAccountType;
  private BankUtil.BANK bank;
  private String subsidiaryBank;
  private String accountHolder;
  private String accountNo;
  private Integer status;
  private Long lastModifyUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyDate;
  private Integer bankCityId;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getBankAccountType()
  {
    return this.bankAccountType;
  }

  public void setBankAccountType(Integer bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public String getSubsidiaryBank()
  {
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

  public Hotel getHotel() {
    return this.hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public BankUtil.BANK getBank() {
    return this.bank;
  }

  public void setBank(BankUtil.BANK bank) {
    this.bank = bank;
  }
}