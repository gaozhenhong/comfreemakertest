package com.wiwi.edb.order.hotelOrder.model;

import com.wiwi.freego.hotel.model.ReservationRoom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelOrderDetails
{
  private Long roomTypeId;
  private Integer orderNumber;
  private int consumeDay;
  private Date consumeBeginDate;
  private double singleRoomPrice;
  private Map<String, List<ReservationRoom>> reservationRoomMap;
  private String remark;

  public HotelOrderDetails()
  {
    this.singleRoomPrice = 0D;

    this.reservationRoomMap = new HashMap();
  }

  public Long getRoomTypeId()
  {
    return this.roomTypeId; }

  public void setRoomTypeId(Long roomTypeId) {
    this.roomTypeId = roomTypeId; }

  public Integer getOrderNumber() {
    return this.orderNumber; }

  public void setOrderNumber(Integer orderNumber) {
    this.orderNumber = orderNumber; }

  public int getConsumeDay() {
    return this.consumeDay; }

  public void setConsumeDay(int consumeDay) {
    this.consumeDay = consumeDay; }

  public Date getConsumeBeginDate() {
    return this.consumeBeginDate; }

  public void setConsumeBeginDate(Date consumeBeginDate) {
    this.consumeBeginDate = consumeBeginDate; }

  public String getRemark() {
    return this.remark; }

  public void setRemark(String remark) {
    this.remark = remark; }

  public Map<String, List<ReservationRoom>> getReservationRoomMap() {
    return this.reservationRoomMap; }

  public void setReservationRoomMap(Map<String, List<ReservationRoom>> reservationRoomMap) {
    this.reservationRoomMap = reservationRoomMap; }

  public double getSingleRoomPrice() {
    return this.singleRoomPrice; }

  public void setSingleRoomPrice(double singleRoomPrice) {
    this.singleRoomPrice = singleRoomPrice;
  }
}