package com.wiwi.edb.settlement.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class SettlementQ extends PageUtil
{
  private Long id;
  private String settlementTitle;
  private String settlementCode;
  private Long settlementUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date settlementTime;
  private Double settlementAmount;
  private Integer fromCompanyId;
  private Integer toCompanyId;
  private String fromCompanyName;
  private String toCompanyName;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date settlementPeriod;
  private String remark;
  private String evidence;
  private Integer status;
  private Long confirmUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date confirmTime;
  private String confirmRemark;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.settlementTitle != null) && (!(this.settlementTitle.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND settlementTitle like ?";
      this.parameterList.add('%' + this.settlementTitle + '%');
    }

    if ((this.settlementCode != null) && (!(this.settlementCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND settlementCode like ?";
      this.parameterList.add('%' + this.settlementCode + '%');
    }

    if ((this.settlementUserId != null) && (this.settlementUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND settlementUserId =?";
      this.parameterList.add(this.settlementUserId);
    }

    if (this.settlementTime != null) {
      sqlStr = sqlStr + " AND settlementTime =?";
      this.parameterList.add(this.settlementTime);
    }

    if (this.settlementAmount != null) {
      sqlStr = sqlStr + " AND settlementAmount =?";
      this.parameterList.add(this.settlementAmount);
    }

    if ((this.fromCompanyId != null) && (this.fromCompanyId.intValue() != 0)) {
      sqlStr = sqlStr + " AND fromCompanyId =?";
      this.parameterList.add(this.fromCompanyId);
    }

    if ((this.toCompanyId != null) && (this.toCompanyId.intValue() != 0)) {
      sqlStr = sqlStr + " AND toCompanyId =?";
      this.parameterList.add(this.toCompanyId);
    }

    if ((this.fromCompanyName != null) && (this.fromCompanyName.trim().length() > 0)) {
      sqlStr = sqlStr + " AND fromCompanyName like ?";
      this.parameterList.add('%' + this.fromCompanyName + '%');
    }

    if ((this.toCompanyName != null) && (this.toCompanyName.trim().length() > 0)) {
      sqlStr = sqlStr + " AND toCompanyName like ?";
      this.parameterList.add('%' + this.toCompanyName + '%');
    }

    if (this.settlementPeriod != null) {
      sqlStr = sqlStr + " AND settlementPeriod =?";
      this.parameterList.add(this.settlementPeriod);
    }

    if ((this.remark != null) && (!(this.remark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND remark like ?";
      this.parameterList.add('%' + this.remark + '%');
    }

    if ((this.evidence != null) && (!(this.evidence.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND evidence like ?";
      this.parameterList.add('%' + this.evidence + '%');
    }

    if ((this.status != null) && (this.status.intValue() != -1)) {
      sqlStr = sqlStr + " AND status = ?";
      this.parameterList.add(this.status);
    }

    if ((this.confirmUserId != null) && (this.confirmUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND confirmUserId =?";
      this.parameterList.add(this.confirmUserId);
    }

    if (this.confirmTime != null) {
      sqlStr = sqlStr + " AND confirmTime =?";
      this.parameterList.add(this.confirmTime);
    }

    if ((this.confirmRemark != null) && (!(this.confirmRemark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND confirmRemark like ?";
      this.parameterList.add('%' + this.confirmRemark + '%');
    }

    return sqlStr;
  }

  public Long getId() {
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

  public Integer getFromCompanyId() {
    return this.fromCompanyId;
  }

  public void setFromCompanyId(Integer fromCompanyId) {
    this.fromCompanyId = fromCompanyId;
  }

  public Integer getToCompanyId() {
    return this.toCompanyId;
  }

  public void setToCompanyId(Integer toCompanyId) {
    this.toCompanyId = toCompanyId;
  }

  public Date getSettlementPeriod() {
    return this.settlementPeriod;
  }

  public void setSettlementPeriod(Date settlementPeriod) {
    this.settlementPeriod = settlementPeriod;
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

  public String getFromCompanyName() {
    return this.fromCompanyName;
  }

  public void setFromCompanyName(String fromOrgName) {
    this.fromCompanyName = fromOrgName;
  }

  public String getToCompanyName() {
    return this.toCompanyName;
  }

  public void setToCompanyName(String toOrgName) {
    this.toCompanyName = toOrgName;
  }
}