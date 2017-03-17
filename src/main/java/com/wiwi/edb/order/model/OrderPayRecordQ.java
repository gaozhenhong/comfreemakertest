package com.wiwi.edb.order.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderPayRecordQ extends PageUtil
{
  private Long id;
  private Long orderId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date payTime;
  private OrderPayRecord.FundType fundType;
  private Double amount;
  private Long receiverId;
  private Long orderDetailsId;
  private String payType;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.orderId != null) && (this.orderId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND orderId =?";
      this.parameterList.add(this.orderId);
    }

    if ((this.orderDetailsId != null) && (this.orderDetailsId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND orderDetailsId =?";
      this.parameterList.add(this.orderDetailsId);
    }

    if (this.payTime != null) {
      sqlStr = sqlStr + " AND payTime =?";
      this.parameterList.add(this.payTime);
    }

    if (this.fundType != null) {
      sqlStr = sqlStr + " AND fundType = ?";
      this.parameterList.add(this.fundType);
    }

    if (this.amount != null) {
      sqlStr = sqlStr + " AND amount =?";
      this.parameterList.add(this.amount);
    }

    if ((this.receiverId != null) && (this.receiverId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND receiverId =?";
      this.parameterList.add(this.receiverId);
    }

    if ((this.payType != null) && (!(this.payType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND payType like ?";
      this.parameterList.add('%' + this.payType + '%');
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOrderId() {
    return this.orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Date getPayTime() {
    return this.payTime;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

  public OrderPayRecord.FundType getFundType() {
    return this.fundType;
  }

  public void setFundType(OrderPayRecord.FundType fundType) {
    this.fundType = fundType;
  }

  public Double getAmount() {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Long getReceiverId() {
    return this.receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public String getPayType() {
    return this.payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public Long getOrderDetailsId() {
    return this.orderDetailsId;
  }

  public void setOrderDetailsId(Long orderDetailsId) {
    this.orderDetailsId = orderDetailsId;
  }
}