package com.wiwi.freego.hotel.model;

public class HotelUserMapping
{
  private Long id;
  private Long userId;
  private Long hotelId;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }
}