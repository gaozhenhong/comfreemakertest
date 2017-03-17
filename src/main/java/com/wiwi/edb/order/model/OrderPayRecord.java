package com.wiwi.edb.order.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderPayRecord
{
  private Long id;
  private Long orderId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date payTime;
  private FundType fundType;
  private Double amount;
  private Long receiverId;
  private String payType;
  private Long orderDetailsId;

  public Long getId()
  {
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

  public FundType getFundType() {
    return this.fundType;
  }

  public void setFundType(FundType fundType) {
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

  public Long getOrderDetailsId()
  {
    return this.orderDetailsId;
  }

  public void setOrderDetailsId(Long orderDetailsId) {
    this.orderDetailsId = orderDetailsId;
  }

  public static enum FundType
  {
    PAYMENT("订金"), 
    DRAWBACK("退押金"), 
    DEPOSIT("押金");

    String label = "";

    private FundType(String label) {
      this.label = label;
    }

    public String getLabel() {
      return this.label;
    }

    public void setLabel(String label) {
      this.label = label;
    }
  }
}