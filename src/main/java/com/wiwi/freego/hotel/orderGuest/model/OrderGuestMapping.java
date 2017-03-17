package com.wiwi.freego.hotel.orderGuest.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderGuestMapping
{
  private Long id;
  private Long orderId;
  private Long guestId;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date checkinTime;
  private Long checkinRegisterUserId;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date checkoutTime;
  private Long checkoutRegisterUserId;
  
  public OrderGuestMapping(Long orderId, Long guestId)
  {
    this.orderId = orderId;
    this.guestId = guestId;
  }
  
  public OrderGuestMapping() {}
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Long getOrderId()
  {
    return this.orderId;
  }
  
  public void setOrderId(Long orderId)
  {
    this.orderId = orderId;
  }
  
  public Long getGuestId()
  {
    return this.guestId;
  }
  
  public void setGuestId(Long guestId)
  {
    this.guestId = guestId;
  }
  
  public Date getCheckinTime()
  {
    return this.checkinTime;
  }
  
  public void setCheckinTime(Date checkinTime)
  {
    this.checkinTime = checkinTime;
  }
  
  public Long getCheckinRegisterUserId()
  {
    return this.checkinRegisterUserId;
  }
  
  public void setCheckinRegisterUserId(Long checkinRegisterUserId)
  {
    this.checkinRegisterUserId = checkinRegisterUserId;
  }
  
  public Date getCheckoutTime()
  {
    return this.checkoutTime;
  }
  
  public void setCheckoutTime(Date checkoutTime)
  {
    this.checkoutTime = checkoutTime;
  }
  
  public Long getCheckoutRegisterUserId()
  {
    return this.checkoutRegisterUserId;
  }
  
  public void setCheckoutRegisterUserId(Long checkoutRegisterUserId)
  {
    this.checkoutRegisterUserId = checkoutRegisterUserId;
  }
}
