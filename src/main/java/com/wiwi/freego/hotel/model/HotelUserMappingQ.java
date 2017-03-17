package com.wiwi.freego.hotel.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class HotelUserMappingQ extends PageUtil
{
  private Integer id;
  private Integer userId;
  private Integer hotelId;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.intValue() != 0)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.userId != null) && (this.userId.intValue() != 0)) {
      sqlStr = sqlStr + " AND userId =?";
      this.parameterList.add(this.userId);
    }

    if ((this.hotelId != null) && (this.hotelId.intValue() != 0)) {
      sqlStr = sqlStr + " AND hotelId =?";
      this.parameterList.add(this.hotelId);
    }

    return sqlStr;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Integer hotelId) {
    this.hotelId = hotelId;
  }
}