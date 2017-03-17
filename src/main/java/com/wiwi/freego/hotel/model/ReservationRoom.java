package com.wiwi.freego.hotel.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import com.wiwi.edb.order.model.Order;

public class ReservationRoom
{
  private Long id;
  private String roomNo;
  private String name;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date consumeDate;
  private Double price;
  private Long orderDetailsId;
  private Long roomTypeId;
  private String orderCode;
  private Long orderId;
  private Long hotelId;
  private Long roomId;
  private Order.Status status;
  private String remark;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date checkinTime;
  private Long checkinRegisterUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date checkoutTime;
  private Long checkoutRegisterUserId;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRoomNo() {
    return this.roomNo;
  }

  public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
  }

  public Date getConsumeDate() {
    return this.consumeDate;
  }

  public void setConsumeDate(Date consumeDate) {
    this.consumeDate = consumeDate;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getOrderCode() {
    return this.orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Long getOrderId() {
    return this.orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getOrderDetailsId() {
    return this.orderDetailsId;
  }

  public void setOrderDetailsId(Long orderDetailsId) {
    this.orderDetailsId = orderDetailsId;
  }

  public Long getRoomTypeId() {
    return this.roomTypeId;
  }

  public void setRoomTypeId(Long roomTypeId) {
    this.roomTypeId = roomTypeId;
  }

  public Order.Status getStatus()
  {
    return this.status;
  }

  public void setStatus(Order.Status status) {
    this.status = status;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String genRoomKey()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String key = getHotelId() + "_" + getRoomTypeId() + "_" + getRoomNo();
    key = key + "_" + sdf.format(getConsumeDate());
    return key;
  }

  public JSONObject praseOrder() {
    return new JSONObject(getRemark());
  }

  public Date getCheckinTime() {
    return this.checkinTime;
  }

  public void setCheckinTime(Date checkinTime) {
    this.checkinTime = checkinTime;
  }

  public Long getCheckinRegisterUserId() {
    return this.checkinRegisterUserId;
  }

  public void setCheckinRegisterUserId(Long checkinRegisterUserId) {
    this.checkinRegisterUserId = checkinRegisterUserId;
  }

  public Date getCheckoutTime() {
    return this.checkoutTime;
  }

  public void setCheckoutTime(Date checkoutTime) {
    this.checkoutTime = checkoutTime;
  }

  public Long getCheckoutRegisterUserId() {
    return this.checkoutRegisterUserId;
  }

  public void setCheckoutRegisterUserId(Long checkoutRegisterUserId) {
    this.checkoutRegisterUserId = checkoutRegisterUserId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getRoomId() {
    return this.roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }
}