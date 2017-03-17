package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class OrgBankAccountQ extends PageUtil
{
  private Long id;
  private Integer orgId;
  private Integer bankAccountType;
  private String bankCode;
  private String subsidiarybank;
  private String accountHolder;
  private Integer status;
  private Long lastModifyUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyDate;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.orgId != null) && (this.orgId.intValue() != 0)) {
      sqlStr = sqlStr + " AND orgId =?";
      this.parameterList.add(this.orgId);
    }

    if ((this.bankAccountType != null) && (this.bankAccountType.intValue() != 0)) {
      sqlStr = sqlStr + " AND bankAccountType =?";
      this.parameterList.add(this.bankAccountType);
    }

    if ((this.bankCode != null) && (!(this.bankCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND bankCode like ?";
      this.parameterList.add('%' + this.bankCode + '%');
    }

    if ((this.subsidiarybank != null) && (!(this.subsidiarybank.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND subsidiarybank like ?";
      this.parameterList.add('%' + this.subsidiarybank + '%');
    }

    if ((this.accountHolder != null) && (!(this.accountHolder.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND accountHolder like ?";
      this.parameterList.add('%' + this.accountHolder + '%');
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

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public Integer getBankAccountType() {
    return this.bankAccountType;
  }

  public void setBankAccountType(Integer bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public String getBankCode() {
    return this.bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getSubsidiarybank() {
    return this.subsidiarybank;
  }

  public void setSubsidiarybank(String subsidiarybank) {
    this.subsidiarybank = subsidiarybank;
  }

  public String getAccountHolder() {
    return this.accountHolder;
  }

  public void setAccountHolder(String accountHolder) {
    this.accountHolder = accountHolder;
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
}