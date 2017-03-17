package com.wiwi.edb.order.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderQ extends PageUtil
{
  private Long id;
  private Long memberId;
  private Long hotelId;
  private String orderCode;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date orderTime;
  private String payType;
  private Long parentOrderId;
  private Double totalPrice;
  private String payStatus;
  private Order.Status status;
  private List<String> multiStatus;
  private Integer isSettlement;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date settlementTime;
  private Long settlementUserId;
  private Long settlementId;
  private Long supplierId;
  private Integer companyId;
  private String orderType;
  private String origin;
  private String originDetails;
  private Double payPrice;
  private String remark;
  private Integer deleteFlag;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date endFinishTime;

  public OrderQ()
  {
    this.multiStatus = new ArrayList();
  }

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.memberId != null) && (this.memberId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND memberId =?";
      this.parameterList.add(this.memberId);
    }

    if ((this.orderCode != null) && (!(this.orderCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND orderCode like ?";
      this.parameterList.add('%' + this.orderCode + '%');
    }

    if (this.orderTime != null) {
      sqlStr = sqlStr + " AND orderTime =?";
      this.parameterList.add(this.orderTime);
    }

    if ((this.payType != null) && (!(this.payType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND payType like ?";
      this.parameterList.add('%' + this.payType + '%');
    }

    if ((this.parentOrderId != null) && (this.parentOrderId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND parentOrderId =?";
      this.parameterList.add(this.parentOrderId);
    }

    if (this.totalPrice != null) {
      sqlStr = sqlStr + " AND totalPrice =?";
      this.parameterList.add(this.totalPrice);
    }

    if ((this.payStatus != null) && (!(this.payStatus.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND payStatus =?";
      this.parameterList.add(this.payStatus);
    }

    if (this.status != null) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    if ((this.multiStatus != null) && (this.multiStatus.size() > 0)) {
      String inSql = "";
      for (Iterator localIterator = this.multiStatus.iterator(); localIterator.hasNext(); ) { String status = (String)localIterator.next();
        inSql = inSql + ",?";
        this.parameterList.add(status);
      }
      inSql = inSql.substring(1);
      sqlStr = sqlStr + " AND status  in (" + inSql + ")";
    }

    if ((this.isSettlement != null) && (this.isSettlement.intValue() != -1)) {
      sqlStr = sqlStr + " AND isSettlement =?";
      this.parameterList.add(this.isSettlement);
    }

    if (this.settlementTime != null) {
      sqlStr = sqlStr + " AND settlementTime =?";
      this.parameterList.add(this.settlementTime);
    }

    if ((this.settlementUserId != null) && (this.settlementUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND settlementUserId =?";
      this.parameterList.add(this.settlementUserId);
    }

    if ((this.settlementId != null) && (this.settlementId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND settlementId =?";
      this.parameterList.add(this.settlementId);
    }

    if ((this.supplierId != null) && (this.supplierId.longValue() != -1L)) {
      sqlStr = sqlStr + " AND supplierId =?";
      this.parameterList.add(this.supplierId);
    }

    if ((this.companyId != null) && (this.companyId.intValue() != -1)) {
      sqlStr = sqlStr + " AND companyId =?";
      this.parameterList.add(this.companyId);
    }

    if ((this.orderType != null) && (!(this.orderType.trim().isEmpty()))) {
      sqlStr = sqlStr + " AND orderType =?";
      this.parameterList.add(this.orderType);
    }

    if ((this.origin != null) && (!(this.origin.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND origin = ?";
      this.parameterList.add(this.origin);
    }

    if ((this.originDetails != null) && (!(this.originDetails.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND originDetails = ?";
      this.parameterList.add(this.originDetails);
    }

    if (this.payPrice != null) {
      sqlStr = sqlStr + " AND payPrice =?";
      this.parameterList.add(this.payPrice);
    }

    if (this.deleteFlag != null) {
      sqlStr = sqlStr + " AND deleteFlag =?";
      this.parameterList.add(this.deleteFlag);
    }
    else { sqlStr = sqlStr + " AND( deleteFlag is null OR deleteFlag = 0)";
    }

    if ((this.remark != null) && (!(this.remark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND remark like ?";
      this.parameterList.add('%' + this.remark + '%');
    }

    if (this.endFinishTime != null) {
      sqlStr = sqlStr + " AND finishTime < ?";
      this.parameterList.add(this.endFinishTime);
    }

    if ((this.hotelId != null) && (this.hotelId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id in ( select distinct orderId from edb_order_details od ,fg_room r where od.productId = r.id  and r.hotelId=?)";

      this.parameterList.add(this.hotelId);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getMemberId() {
    return this.memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public String getOrderCode() {
    return this.orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Date getOrderTime() {
    return this.orderTime;
  }

  public void setOrderTime(Date orderTime) {
    this.orderTime = orderTime;
  }

  public String getPayType() {
    return this.payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public Long getParentOrderId() {
    return this.parentOrderId;
  }

  public void setParentOrderId(Long parentOrderId) {
    this.parentOrderId = parentOrderId;
  }

  public Double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Integer getIsSettlement()
  {
    return this.isSettlement;
  }

  public void setIsSettlement(Integer isPosted) {
    this.isSettlement = isPosted;
  }

  public Date getSettlementTime() {
    return this.settlementTime;
  }

  public void setSettlementTime(Date postedTime) {
    this.settlementTime = postedTime;
  }

  public Long getSettlementUserId() {
    return this.settlementUserId;
  }

  public void setSettlementUserId(Long postedUserId) {
    this.settlementUserId = postedUserId;
  }

  public Long getSupplierId() {
    return this.supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public String getOrderType() {
    return this.orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public String getOrigin() {
    return this.origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public Double getPayPrice()
  {
    return this.payPrice;
  }

  public void setPayPrice(Double payPrice) {
    this.payPrice = payPrice;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getDeleteFlag() {
    return this.deleteFlag;
  }

  public void setDeleteFlag(Integer deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public String getPayStatus() {
    return this.payStatus;
  }

  public void setPayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public String getOriginDetails() {
    return this.originDetails;
  }

  public void setOriginDetails(String originDetails) {
    this.originDetails = originDetails;
  }

  public void setStatus(Order.Status status)
  {
    this.status = status;
  }

  public Order.Status getStatus() {
    return this.status;
  }

  public List<String> getMultiStatus() {
    return this.multiStatus;
  }

  public void setMultiStatus(List<String> multiStatus) {
    this.multiStatus = multiStatus;
  }

  public Integer getCompanyId() {
    return this.companyId;
  }

  public void setCompanyId(Integer companyId) {
    this.companyId = companyId;
  }

  public Long getSettlementId() {
    return this.settlementId;
  }

  public void setSettlementId(Long settlementId) {
    this.settlementId = settlementId;
  }

  public Date getEndFinishTime() {
    return this.endFinishTime;
  }

  public void setEndFinishTime(Date endFinishTime) {
    this.endFinishTime = endFinishTime;
  }
}