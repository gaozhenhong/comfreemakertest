package com.wiwi.jsoil.sys.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.wiwi.jsoil.util.BankUtil;

public class OrgBankAccount
{
  private Long id;
  private Organization org;
  private Integer bankAccountType;
  private BankUtil.BANK bank;
  private String subsidiaryBank;
  private String accountHolder;
  private Integer status;
  private Integer bankCityId;
  private Long lastModifyUserId;
  private String accountNo;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyDate;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Integer getBankAccountType()
  {
    return this.bankAccountType;
  }
  
  public void setBankAccountType(Integer bankAccountType)
  {
    this.bankAccountType = bankAccountType;
  }
  
  public String getSubsidiaryBank()
  {
    return this.subsidiaryBank;
  }
  
  public void setSubsidiaryBank(String subsidiaryBank)
  {
    this.subsidiaryBank = subsidiaryBank;
  }
  
  public String getAccountHolder()
  {
    return this.accountHolder;
  }
  
  public void setAccountHolder(String accountHolder)
  {
    this.accountHolder = accountHolder;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public Long getLastModifyUserId()
  {
    return this.lastModifyUserId;
  }
  
  public void setLastModifyUserId(Long lastModifyUserId)
  {
    this.lastModifyUserId = lastModifyUserId;
  }
  
  public Date getLastModifyDate()
  {
    return this.lastModifyDate;
  }
  
  public void setLastModifyDate(Date lastModifyDate)
  {
    this.lastModifyDate = lastModifyDate;
  }
  
  public String getAccountNo()
  {
    return this.accountNo;
  }
  
  public void setAccountNo(String accountNo)
  {
    this.accountNo = accountNo;
  }
  
  public Organization getOrg()
  {
    return this.org;
  }
  
  public void setOrg(Organization org)
  {
    this.org = org;
  }
  
  public Integer getBankCityId()
  {
    return this.bankCityId;
  }
  
  public void setBankCityId(Integer bankCityId)
  {
    this.bankCityId = bankCityId;
  }
  
  public BankUtil.BANK getBank()
  {
    return this.bank;
  }
  
  public void setBank(BankUtil.BANK bank)
  {
    this.bank = bank;
  }
}
