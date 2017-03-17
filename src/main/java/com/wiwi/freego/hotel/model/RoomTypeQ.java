package com.wiwi.freego.hotel.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class RoomTypeQ extends PageUtil
{
  private Long id;
  private String name;
  private Float defaultPrice;
  private Long logo;
  private String description;
  private Long hotelId;
  private String hotelName;
  private Integer cityId;
  private Float mondayPrice;
  private Float tuesdayPrice;
  private Float wednesdayPrice;
  private Float thursdayPrice;
  private Float fridayPrice;
  private Float saturdayPrice;
  private Float sundayPrice;
  private Integer roomNumber;
  private String status;
  private Integer orderNo;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like ?";
      this.parameterList.add('%' + this.name + '%');
    }

    if (this.defaultPrice != null) {
      sqlStr = sqlStr + " AND defaultPrice =?";
      this.parameterList.add(this.defaultPrice);
    }

    if ((this.logo != null) && (this.logo.longValue() != 0L)) {
      sqlStr = sqlStr + " AND logo =?";
      this.parameterList.add(this.logo);
    }

    if ((this.description != null) && (!(this.description.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND description like ?";
      this.parameterList.add('%' + this.description + '%');
    }

    if ((this.hotelId != null) && (this.hotelId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND hotelId =?";
      this.parameterList.add(this.hotelId);
    }

    String subSql = "";
    if ((this.cityId != null) && (this.cityId.intValue() != 0)) {
      subSql = " cityId = '" + this.cityId + "' ";
    }

    if ((this.hotelName != null) && (this.hotelName.trim().length() > 0)) {
      if (subSql.trim().length() > 0)
        subSql = subSql + " and ";

      subSql = subSql + " name like '%" + this.hotelName + "%' ";
    }
    if (subSql.trim().length() > 0) {
      sqlStr = sqlStr + " and hotelId in (select id from fg_hotel where " + subSql + ")";
    }

    if (this.mondayPrice != null) {
      sqlStr = sqlStr + " AND mondayPrice =?";
      this.parameterList.add(this.mondayPrice);
    }

    if (this.tuesdayPrice != null) {
      sqlStr = sqlStr + " AND tuesdayPrice =?";
      this.parameterList.add(this.tuesdayPrice);
    }

    if (this.wednesdayPrice != null) {
      sqlStr = sqlStr + " AND wednesdayPrice =?";
      this.parameterList.add(this.wednesdayPrice);
    }

    if (this.thursdayPrice != null) {
      sqlStr = sqlStr + " AND thursdayPrice =?";
      this.parameterList.add(this.thursdayPrice);
    }

    if (this.fridayPrice != null) {
      sqlStr = sqlStr + " AND fridayPrice =?";
      this.parameterList.add(this.fridayPrice);
    }

    if (this.saturdayPrice != null) {
      sqlStr = sqlStr + " AND saturdayPrice =?";
      this.parameterList.add(this.saturdayPrice);
    }

    if (this.sundayPrice != null) {
      sqlStr = sqlStr + " AND sundayPrice =?";
      this.parameterList.add(this.sundayPrice);
    }

    if ((this.roomNumber != null) && (this.roomNumber.intValue() != 0)) {
      sqlStr = sqlStr + " AND roomNumber =?";
      this.parameterList.add(this.roomNumber);
    }

    if ((this.status != null) && (!(this.status.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND status like ?";
      this.parameterList.add('%' + this.status + '%');
    }

    if ((this.orderNo != null) && (this.orderNo.intValue() != 0)) {
      sqlStr = sqlStr + " AND orderNo =?";
      this.parameterList.add(this.orderNo);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Float getDefaultPrice() {
    return this.defaultPrice;
  }

  public void setDefaultPrice(Float defaultPrice) {
    this.defaultPrice = defaultPrice;
  }

  public Long getLogo() {
    return this.logo;
  }

  public void setLogo(Long logo) {
    this.logo = logo;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public Float getMondayPrice() {
    return this.mondayPrice;
  }

  public void setMondayPrice(Float mondayPrice) {
    this.mondayPrice = mondayPrice;
  }

  public Float getTuesdayPrice() {
    return this.tuesdayPrice;
  }

  public void setTuesdayPrice(Float tuesdayPrice) {
    this.tuesdayPrice = tuesdayPrice;
  }

  public Float getWednesdayPrice() {
    return this.wednesdayPrice;
  }

  public void setWednesdayPrice(Float wednesdayPrice) {
    this.wednesdayPrice = wednesdayPrice;
  }

  public Float getThursdayPrice() {
    return this.thursdayPrice;
  }

  public void setThursdayPrice(Float thursdayPrice) {
    this.thursdayPrice = thursdayPrice;
  }

  public Float getFridayPrice() {
    return this.fridayPrice;
  }

  public void setFridayPrice(Float fridayPrice) {
    this.fridayPrice = fridayPrice;
  }

  public Float getSaturdayPrice() {
    return this.saturdayPrice;
  }

  public void setSaturdayPrice(Float saturdayPrice) {
    this.saturdayPrice = saturdayPrice;
  }

  public Float getSundayPrice() {
    return this.sundayPrice;
  }

  public void setSundayPrice(Float sundayPrice) {
    this.sundayPrice = sundayPrice;
  }

  public Integer getRoomNumber() {
    return this.roomNumber;
  }

  public void setRoomNumber(Integer roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getOrderNo() {
    return this.orderNo;
  }

  public void setOrderNo(Integer orderNo) {
    this.orderNo = orderNo;
  }

  public String getHotelName() {
    return this.hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public Integer getCityId() {
    return this.cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }
}