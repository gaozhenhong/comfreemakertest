package com.wiwi.edb.order.hotelOrder.model;

import com.wiwi.jsoil.member.model.Member;
import java.util.ArrayList;
import java.util.List;

public class HotelOrder
{
  private Member member;
  private Long hotelId;
  public static String ORDER_ORIGIN_FREEGO = "orderOrigin#freego";
  public String payType;
  private String microSiteCode;
  private String remark;
  private String planCheckinTime;
  private String planCheckoutTime;
  private String orderName;
  private String orderTelephone;
  private String origin;
  private double payPrice;
  private double totalPrice;
  List<HotelOrderDetails> orderDetails;

  public HotelOrder()
  {
    this.payType = "payType#wxpay";

    this.origin = ORDER_ORIGIN_FREEGO;

    this.payPrice = 0D;
    this.totalPrice = 0D;

    this.orderDetails = new ArrayList();
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public List<HotelOrderDetails> getOrderDetails() {
    return this.orderDetails;
  }

  public void setOrderDetails(List<HotelOrderDetails> orderDetails) {
    this.orderDetails = orderDetails;
  }

  public Member getMember() {
    return this.member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getMicroSiteCode() {
    return this.microSiteCode;
  }

  public void setMicroSiteCode(String microSiteCode) {
    this.microSiteCode = microSiteCode;
  }

  public String getOrderName()
  {
    return this.orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  public String getOrderTelephone() {
    return this.orderTelephone;
  }

  public void setOrderTelephone(String orderTelephone) {
    this.orderTelephone = orderTelephone;
  }

  public double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public double getPayPrice() {
    return this.payPrice;
  }

  public void setPayPrice(double payPrice) {
    this.payPrice = payPrice;
  }

  public String getOrigin() {
    return this.origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getPlanCheckinTime() {
    return this.planCheckinTime;
  }

  public void setPlanCheckinTime(String planCheckinTime) {
    this.planCheckinTime = planCheckinTime;
  }

  public String getPlanCheckoutTime() {
    return this.planCheckoutTime;
  }

  public void setPlanCheckoutTime(String planCheckoutTime) {
    this.planCheckoutTime = planCheckoutTime;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getPayType() {
    return this.payType;
  }
}