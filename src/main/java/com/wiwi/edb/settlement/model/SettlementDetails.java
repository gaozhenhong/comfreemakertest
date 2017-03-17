package com.wiwi.edb.settlement.model;

import com.wiwi.jsoil.sys.model.Organization;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SettlementDetails
{
  private Long id;
  private Organization receiveCompany;
  private Long receiveSupplierId;
  private Double amount;
  private String title;
  private Long orderId;
  private Integer originCompanyId;
  private Long originSupplierId;
  private Double orderPayPrice;
  private Double percentage;
  private Long settlementId;
  private String feeType;
  private String remark;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date period;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date insertTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getAmount()
  {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getOrderId() {
    return this.orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Double getOrderPayPrice()
  {
    return this.orderPayPrice;
  }

  public void setOrderPayPrice(Double orderPayPrice) {
    this.orderPayPrice = orderPayPrice;
  }

  public Double getPercentage() {
    return this.percentage;
  }

  public void setPercentage(Double percentage) {
    this.percentage = percentage;
  }

  public Long getSettlementId() {
    return this.settlementId;
  }

  public void setSettlementId(Long settlementId) {
    this.settlementId = settlementId;
  }

  public Date getPeriod() {
    return this.period;
  }

  public void setPeriod(Date period) {
    this.period = period;
  }

  public Integer getOriginCompanyId()
  {
    return this.originCompanyId;
  }

  public void setOriginCompanyId(Integer originOrgId) {
    this.originCompanyId = originOrgId;
  }

  public Long getOriginSupplierId() {
    return this.originSupplierId;
  }

  public void setOriginSupplierId(Long originSupplierId) {
    this.originSupplierId = originSupplierId;
  }

  public Long getReceiveSupplierId() {
    return this.receiveSupplierId;
  }

  public void setReceiveSupplierId(Long receiveSupplierId) {
    this.receiveSupplierId = receiveSupplierId;
  }

  public Date getInsertTime() {
    return this.insertTime;
  }

  public void setInsertTime(Date insertTime) {
    this.insertTime = insertTime;
  }

  public Organization getReceiveCompany() {
    return this.receiveCompany;
  }

  public void setReceiveCompany(Organization receiveCompany) {
    this.receiveCompany = receiveCompany;
  }

  public String getFeeType() {
    return this.feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}