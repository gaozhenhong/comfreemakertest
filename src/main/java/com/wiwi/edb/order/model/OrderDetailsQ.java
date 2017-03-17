package com.wiwi.edb.order.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderDetailsQ extends PageUtil
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
  private Integer companyId;
  private Long productId;
  private String remark;
  private Date consumeBeginTime;
  private Date consumeEndTime;
  private Integer deleteFlag;

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

    if ((this.orderCode != null) && (!(this.orderCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND orderCode like ?";
      this.parameterList.add('%' + this.orderCode + '%');
    }

    if ((this.memberId != null) && (this.memberId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND memberId =?";
      this.parameterList.add(this.memberId);
    }

    if ((this.productSnapshotId != null) && (this.productSnapshotId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND productSnapshotId =?";
      this.parameterList.add(this.productSnapshotId);
    }

    if (this.orderTime != null) {
      sqlStr = sqlStr + " AND orderTime =?";
      this.parameterList.add(this.orderTime);
    }

    if (this.orderPrice != null) {
      sqlStr = sqlStr + " AND orderPrice =?";
      this.parameterList.add(this.orderPrice);
    }

    if ((this.orderNumber != null) && (this.orderNumber.intValue() != 0)) {
      sqlStr = sqlStr + " AND orderNumber =?";
      this.parameterList.add(this.orderNumber);
    }

    if (this.totalPrice != null) {
      sqlStr = sqlStr + " AND totalPrice =?";
      this.parameterList.add(this.totalPrice);
    }

    if ((this.productName != null) && (!(this.productName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND productName like ?";
      this.parameterList.add('%' + this.productName + '%');
    }

    if ((this.productType != null) && (!(this.productType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND productType like ?";
      this.parameterList.add('%' + this.productType + '%');
    }

    if ((this.supplierId != null) && (this.supplierId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND supplierId =?";
      this.parameterList.add(this.supplierId);
    }

    if ((this.productId != null) && (this.productId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND productId =?";
      this.parameterList.add(this.productId);
    }

    if ((this.remark != null) && (!(this.remark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND remark like ?";
      this.parameterList.add('%' + this.remark + '%');
    }

    if (this.consumeBeginTime != null) {
      sqlStr = sqlStr + " AND consumeBeginTime =?";
      this.parameterList.add(this.consumeBeginTime);
    }

    if (this.deleteFlag != null) {
      sqlStr = sqlStr + " AND deleteFlag =?";
      this.parameterList.add(this.deleteFlag);
    }
    else { sqlStr = sqlStr + " AND( deleteFlag is null OR deleteFlag = 0)";
    }

    if (this.consumeEndTime != null) {
      sqlStr = sqlStr + " AND consumeEndTime =?";
      this.parameterList.add(this.consumeEndTime);
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

  public Long getSupplierId() {
    return this.supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
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

  public Integer getDeleteFlag() {
    return this.deleteFlag;
  }

  public void setDeleteFlag(Integer deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public Integer getCompanyId() {
    return this.companyId;
  }

  public void setCompanyId(Integer companyId) {
    this.companyId = companyId;
  }
}