package com.wiwi.freego.hotel.orderGuest.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderGuestMappingQ extends PageUtil
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

    if ((this.guestId != null) && (this.guestId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND guestId =?";
      this.parameterList.add(this.guestId);
    }

    if (this.checkinTime != null) {
      sqlStr = sqlStr + " AND checkinTime =?";
      this.parameterList.add(this.checkinTime);
    }

    if ((this.checkinRegisterUserId != null) && (this.checkinRegisterUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND checkinRegisterUserId =?";
      this.parameterList.add(this.checkinRegisterUserId);
    }

    if (this.checkoutTime != null) {
      sqlStr = sqlStr + " AND checkoutTime =?";
      this.parameterList.add(this.checkoutTime);
    }

    if ((this.checkoutRegisterUserId != null) && (this.checkoutRegisterUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND checkoutRegisterUserId =?";
      this.parameterList.add(this.checkoutRegisterUserId);
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

  public Long getGuestId() {
    return this.guestId;
  }

  public void setGuestId(Long guestId) {
    this.guestId = guestId;
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
}