package com.wiwi.edb.settlement.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class SettlementDetailsQ extends PageUtil
{
  private Long id;
  private Long payOrgId;
  private Long receiveOrgId;
  private Float amount;
  private String title;
  private Long orderId;
  private Long originOrgId;
  private Float orderPayPrice;
  private Float percentage;
  private Long settlementId;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.payOrgId != null) && (this.payOrgId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND payOrgId =?";
      this.parameterList.add(this.payOrgId);
    }

    if ((this.receiveOrgId != null) && (this.receiveOrgId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND receiveOrgId =?";
      this.parameterList.add(this.receiveOrgId);
    }

    if (this.amount != null) {
      sqlStr = sqlStr + " AND amount =?";
      this.parameterList.add(this.amount);
    }

    if ((this.title != null) && (!(this.title.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND title like ?";
      this.parameterList.add('%' + this.title + '%');
    }

    if ((this.orderId != null) && (this.orderId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND orderId =?";
      this.parameterList.add(this.orderId);
    }

    if ((this.originOrgId != null) && (this.originOrgId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND originOrgId =?";
      this.parameterList.add(this.originOrgId);
    }

    if (this.orderPayPrice != null) {
      sqlStr = sqlStr + " AND orderPayPrice =?";
      this.parameterList.add(this.orderPayPrice);
    }

    if (this.percentage != null) {
      sqlStr = sqlStr + " AND percentage =?";
      this.parameterList.add(this.percentage);
    }

    if ((this.settlementId != null) && (this.settlementId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND settlementId =?";
      this.parameterList.add(this.settlementId);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPayOrgId() {
    return this.payOrgId;
  }

  public void setPayOrgId(Long payOrgId) {
    this.payOrgId = payOrgId;
  }

  public Long getReceiveOrgId() {
    return this.receiveOrgId;
  }

  public void setReceiveOrgId(Long receiveOrgId) {
    this.receiveOrgId = receiveOrgId;
  }

  public Float getAmount() {
    return this.amount;
  }

  public void setAmount(Float amount) {
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

  public Long getOriginOrgId() {
    return this.originOrgId;
  }

  public void setOriginOrgId(Long originOrgId) {
    this.originOrgId = originOrgId;
  }

  public Float getOrderPayPrice() {
    return this.orderPayPrice;
  }

  public void setOrderPayPrice(Float orderPayPrice) {
    this.orderPayPrice = orderPayPrice;
  }

  public Float getPercentage() {
    return this.percentage;
  }

  public void setPercentage(Float percentage) {
    this.percentage = percentage;
  }

  public Long getSettlementId() {
    return this.settlementId;
  }

  public void setSettlementId(Long settlementId) {
    this.settlementId = settlementId;
  }
}