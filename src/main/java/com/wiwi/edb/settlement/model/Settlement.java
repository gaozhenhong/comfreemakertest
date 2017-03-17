package com.wiwi.edb.settlement.model;

import com.wiwi.jsoil.sys.model.Organization;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Settlement
{
  private Long id;
  private String settlementTitle;
  private String settlementCode;
  private Long settlementUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date settlementTime;
  private Double settlementAmount;
  private Organization fromCompany;
  private Organization toCompany;
  private Long toSupplierId;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date settlementPeriod;
  private String remark;
  private String evidence;
  private Integer status;
  private Long confirmUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date confirmTime;
  private String confirmRemark;

  public Settlement()
  {
    this.settlementAmount = Double.valueOf(0D);

    this.status = Integer.valueOf(0);
  }

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSettlementTitle() {
    return this.settlementTitle;
  }

  public void setSettlementTitle(String settlementTitle) {
    this.settlementTitle = settlementTitle;
  }

  public String getSettlementCode() {
    return this.settlementCode;
  }

  public void setSettlementCode(String settlementCode) {
    this.settlementCode = settlementCode;
  }

  public Long getSettlementUserId() {
    return this.settlementUserId;
  }

  public void setSettlementUserId(Long settlementUserId) {
    this.settlementUserId = settlementUserId;
  }

  public Date getSettlementTime() {
    return this.settlementTime;
  }

  public void setSettlementTime(Date settlementTime) {
    this.settlementTime = settlementTime;
  }

  public Double getSettlementAmount() {
    return this.settlementAmount;
  }

  public void setSettlementAmount(Double settlementAmount) {
    this.settlementAmount = settlementAmount;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getEvidence() {
    return this.evidence;
  }

  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getConfirmUserId() {
    return this.confirmUserId;
  }

  public void setConfirmUserId(Long confirmUserId) {
    this.confirmUserId = confirmUserId;
  }

  public Date getConfirmTime() {
    return this.confirmTime;
  }

  public void setConfirmTime(Date confirmTime) {
    this.confirmTime = confirmTime;
  }

  public String getConfirmRemark() {
    return this.confirmRemark;
  }

  public void setConfirmRemark(String confirmRemark) {
    this.confirmRemark = confirmRemark;
  }

  public Organization getFromCompany()
  {
    return this.fromCompany;
  }

  public void setFromCompany(Organization fromOrg) {
    this.fromCompany = fromOrg;
  }

  public Organization getToCompany() {
    return this.toCompany;
  }

  public void setToCompany(Organization toOrg) {
    this.toCompany = toOrg;
  }

  public Long getToSupplierId() {
    return this.toSupplierId;
  }

  public void setToSupplierId(Long toSupplierId) {
    this.toSupplierId = toSupplierId;
  }

  public Date getSettlementPeriod() {
    return this.settlementPeriod;
  }

  public void setSettlementPeriod(Date settlementPeriod) {
    this.settlementPeriod = settlementPeriod;
  }
}