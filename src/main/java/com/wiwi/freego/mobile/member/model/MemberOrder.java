package com.wiwi.freego.mobile.member.model;

import com.wiwi.edb.order.model.Order;
import com.wiwi.freego.hotel.model.Hotel;

public class MemberOrder
{
  private Order orderBase;
  private Hotel hotel;

  public Order getOrderBase()
  {
    return this.orderBase;
  }

  public void setOrderBase(Order orderBase) {
    this.orderBase = orderBase;
  }

  public Hotel getHotel() {
    return this.hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
}