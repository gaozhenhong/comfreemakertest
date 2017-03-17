package com.wiwi.edb.order.model;

import com.wiwi.jsoil.sys.model.Organization;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderDetails
{
  private Long id;
  private Long orderId;
  private String orderCode;
  private Long memberId;
  private Long productSnapshotId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date orderTime;
  private Double orderPrice;
  private Integer orderNumber;
  private Double totalPrice;
  private String productName;
  private String productType;
  private Long supplierId;
  private Organization company;
  private Long productId;
  private String remark;
  private Date consumeBeginTime;
  private Date consumeEndTime;
  private Order.Status status;

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

  public Long getMemberId() {
    return this.memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public Long getProductSnapshotId() {
    return this.productSnapshotId;
  }

  public void setProductSnapshotId(Long productSnapshotId) {
    this.productSnapshotId = productSnapshotId;
  }

  public Date getOrderTime() {
    return this.orderTime;
  }

  public void setOrderTime(Date orderTime) {
    this.orderTime = orderTime;
  }

  public Double getOrderPrice() {
    return this.orderPrice;
  }

  public void setOrderPrice(Double orderPrice) {
    this.orderPrice = orderPrice;
  }

  public Integer getOrderNumber() {
    return this.orderNumber;
  }

  public void setOrderNumber(Integer orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getProductName() {
    return this.productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductType() {
    return this.productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public Long getProductId() {
    return this.productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Date getConsumeBeginTime() {
    return this.consumeBeginTime;
  }

  public void setConsumeBeginTime(Date consumeBeginTime) {
    this.consumeBeginTime = consumeBeginTime;
  }

  public Date getConsumeEndTime() {
    return this.consumeEndTime;
  }

  public void setConsumeEndTime(Date consumeEndTime) {
    this.consumeEndTime = consumeEndTime;
  }

  public Order.Status getStatus() {
    return this.status;
  }

  public void setStatus(Order.Status status) {
    this.status = status;
  }

  public void setSupplierId(Long supplierId)
  {
    this.supplierId = supplierId;
  }

  public Organization getCompany() {
    return this.company;
  }

  public void setCompany(Organization company) {
    this.company = company;
  }

  public Long getSupplierId() {
    return this.supplierId;
  }

  public String toString()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return "OrderDetails [id=" + this.id + ", orderId=" + this.orderId + ", orderCode=" + this.orderCode + ", memberId=" + this.memberId + ", productSnapshotId=" + this.productSnapshotId + ", orderTime=" + this.orderTime + ", orderPrice=" + this.orderPrice + ", orderNumber=" + 
      this.orderNumber + ", totalPrice=" + this.totalPrice + ", productName=" + this.productName + ", productType=" + this.productType + ", supplierId=" + this.supplierId + ", company=" + this.company + ", productId=" + this.productId + ", remark=" + this.remark + 
      ", consumeBeginTime=" + ((this.consumeBeginTime == null) ? "" : sdf.format(this.consumeBeginTime)) + ", consumeEndTime=" + ((this.consumeEndTime == null) ? "" : sdf.format(this.consumeEndTime)) + ", status=" + this.status + "]";
  }
}