package com.wiwi.edb.order.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderProcess
{
  private Long id;
  private Long orderId;
  private String orderCode;
  private Long orderDetailsId;
  private Long operatorId;
  private String operatorName;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date processTime;
  private OrderOperate operateType;
  private String operate;
  private String linkman;
  private String linkway;
  private String remark;

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

  public String getOrderCode() {
    return this.orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Long getOperatorId() {
    return this.operatorId;
  }

  public void setOperatorId(Long operatorId) {
    this.operatorId = operatorId;
  }

  public String getOperatorName() {
    return this.operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public Date getProcessTime() {
    return this.processTime;
  }

  public void setProcessTime(Date processTime) {
    this.processTime = processTime;
  }

  public OrderOperate getOperateType() {
    return this.operateType;
  }

  public void setOperateType(OrderOperate operatType) {
    this.operateType = operatType;
  }

  public String getOperate() {
    return this.operate;
  }

  public void setOperate(String operate) {
    this.operate = operate;
  }

  public String getLinkman() {
    return this.linkman;
  }

  public void setLinkman(String linkman) {
    this.linkman = linkman;
  }

  public String getLinkway() {
    return this.linkway;
  }

  public void setLinkway(String linkway) {
    this.linkway = linkway;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Long getOrderDetailsId()
  {
    return this.orderDetailsId;
  }

  public void setOrderDetailsId(Long orderDetailsId) {
    this.orderDetailsId = orderDetailsId;
  }

  public static enum OrderOperate
  {
    CREATE("添加"), 
    EDIT("修改"), 
    DELETE("删除"), 
    RESERVATION("预定"), 
    CANCEL_RESERVATION(
      "取消预定"), 
    ON_TIME_CANCEL(
      "取消预定"), 
    CONFIRM(
      "确认订单"), 
    REJECT(
      "拒绝"), 
    RECEIPT_FUND(
      "收款"), 
    RECEIPT_DEPOSIT(
      "收取押金"), 
    DRAWBACK_DEPOSIT(
      "退回押金"), 
    CLOSED(
      "关闭"), 
    DRAWBACK(
      "退款"), 

    CHECKIN(
      "入住登记"), 

    CHECKOUT(
      "退房"), 

    CANCEL_ORDER("退单"), 

    CONFIRM_CANCEL("确认退单");

    String label = "";

    private OrderOperate(String label) {
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