package com.wiwi.freego.hotel.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SpecialTimePrice
{
  private Long id;
  private Long roomTypeId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date specialTime;
  private Float price;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getSpecialTime()
  {
    return this.specialTime;
  }

  public void setSpecialTime(Date specialTime) {
    this.specialTime = specialTime;
  }

  public Float getPrice() {
    return this.price;
  }

  public void setPrice(Float pirce) {
    this.price = pirce;
  }

  public Long getRoomTypeId() {
    return this.roomTypeId;
  }

  public void setRoomTypeId(Long roomTypeId) {
    this.roomTypeId = roomTypeId;
  }
}