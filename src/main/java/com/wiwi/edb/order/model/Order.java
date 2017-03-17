package com.wiwi.edb.order.model;

import com.wiwi.jsoil.sys.model.Organization;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Order
{
  private Long id;
  private Long memberId;
  private String orderCode;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date orderTime;
  private String payType;
  private Long parentOrderId;
  private Double totalPrice;
  private PayStatus payStatus;
  private Status status;
  private Integer isSettlement = Integer.valueOf(0);

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date settlementTime;
  private Long settlementUserId;
  private long supplierId;
  private String supplierCode;
  private Organization company;
  private OrderType orderType;
  private String origin;
  private String originDetails;
  private Double payPrice;
  private String remark;
  private String orderTelephone;
  private String orderName;
  private String planCheckinTime;
  private String planCheckoutTime;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date finishTime;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyTime;
  private long lastModifyUserId;

  public Long getId() { return this.id; }

  public void setId(Long id)
  {
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

  public PayStatus getPayStatus() {
    return this.payStatus;
  }

  public void setPayStatus(PayStatus payStatus) {
    this.payStatus = payStatus;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Integer getIsSettlement() {
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

  public OrderType getOrderType()
  {
    return this.orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
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

  public String getOrderTelephone() {
    return this.orderTelephone;
  }

  public void setOrderTelephone(String orderTelephone) {
    this.orderTelephone = orderTelephone;
  }

  public String getOrderName() {
    return this.orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  public String getOrigin()
  {
    return this.origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getOriginDetails() {
    return this.originDetails;
  }

  public void setOriginDetails(String originDetails) {
    this.originDetails = originDetails;
  }

  public Date getFinishTime()
  {
    return this.finishTime;
  }

  public void setFinishTime(Date finishTime) {
    this.finishTime = finishTime;
  }

  public long getSupplierId() {
    return this.supplierId;
  }

  public void setSupplierId(long supplierId) {
    this.supplierId = supplierId;
  }

  public Organization getCompany() {
    return this.company;
  }

  public void setCompany(Organization company) {
    this.company = company;
  }

  public String getPlanCheckoutTime() {
    return this.planCheckoutTime;
  }

  public void setPlanCheckoutTime(String planCheckoutTime) {
    this.planCheckoutTime = planCheckoutTime;
  }

  public String getPlanCheckinTime() {
    return this.planCheckinTime;
  }

  public void setPlanCheckinTime(String planCheckinTime) {
    this.planCheckinTime = planCheckinTime;
  }

  public String getSupplierCode() {
    return this.supplierCode;
  }

  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }

  public boolean selfCome()
  {
    if ((getOriginDetails() == null) || (getOriginDetails().trim().equals(""))) {
      return true;
    }
    return getOriginDetails().equals(getSupplierCode());
  }

  public Date getLastModifyTime() {
    return this.lastModifyTime;
  }

  public void setLastModifyTime(Date lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }

  public long getLastModifyUserId() {
    return this.lastModifyUserId;
  }

  public void setLastModifyUserId(long lastModifyUserId) {
    this.lastModifyUserId = lastModifyUserId;
  }

  public static enum OrderType
  {
    HOTEL('H', "客栈"), 
    FOOD('F', "食物"), 
    TRAVEL('T', "旅游");

    String label = "";
    char code = 'X';

    private OrderType(char code, String label) {
      this.label = label;
      this.code = code;
    }

    public String getLabel() {
      return this.label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public char getCode() {
      return this.code;
    }

    public void setCode(char code) {
      this.code = code;
    }
  }

  public static enum PayStatus
  {
    EXCESS("超额支付"), 
    PAY_IN_FULL("付清"), 
    DEBT("未付清"), 
    NO_PAYMENT("未付款");

    String label = "";

    private PayStatus(String label) { this.label = label; }

    public String getLabel()
    {
      return this.label;
    }

    public void setLabel(String label) {
      this.label = label;
    }
  }

  public static enum Status
  {
    RESERVATION("预定"), 

    CANCEL_RESERVATION(
      "客户取消预定"), 

    ON_TIME_CANCEL(
      "过时取消"), 

    REJECT("商家取消预定"), 

    CONFIRM(
      "已确认留房"), 

    CHECKIN("已入住"), 

    CHECKOUT("已退房"), 

    CLOSED("已关闭"), 

    CANCEL_ORDER("退单中"), 

    REFUND("已退单"),
	  
	CANCELED("已取消");

    String label = "";

    private Status(String label) {
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