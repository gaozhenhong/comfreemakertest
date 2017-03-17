package com.wiwi.edb.order.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderProcessQ extends PageUtil
{
  private Long id;
  private Long orderId;
  private String orderCode;
  private Long operatorId;
  private String operatorName;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date processTime;
  private String operatType;
  private String operate;
  private String linkman;
  private String linkway;
  private String remark;

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

    if ((this.operatorId != null) && (this.operatorId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND operatorId =?";
      this.parameterList.add(this.operatorId);
    }

    if ((this.operatorName != null) && (!(this.operatorName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND operatorName like ?";
      this.parameterList.add('%' + this.operatorName + '%');
    }

    if (this.processTime != null) {
      sqlStr = sqlStr + " AND processTime =?";
      this.parameterList.add(this.processTime);
    }

    if ((this.operatType != null) && (!(this.operatType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND operatType like ?";
      this.parameterList.add('%' + this.operatType + '%');
    }

    if ((this.operate != null) && (!(this.operate.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND operate like ?";
      this.parameterList.add('%' + this.operate + '%');
    }

    if ((this.linkman != null) && (!(this.linkman.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND linkman like ?";
      this.parameterList.add('%' + this.linkman + '%');
    }

    if ((this.linkway != null) && (!(this.linkway.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND linkway like ?";
      this.parameterList.add('%' + this.linkway + '%');
    }

    if ((this.remark != null) && (!(this.remark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND remark like ?";
      this.parameterList.add('%' + this.remark + '%');
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

  public String getOperatType() {
    return this.operatType;
  }

  public void setOperatType(String operatType) {
    this.operatType = operatType;
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
}