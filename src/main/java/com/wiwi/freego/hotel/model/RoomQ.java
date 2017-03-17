package com.wiwi.freego.hotel.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class RoomQ extends PageUtil
{
  private Long id;
  private Integer roomNo;
  private Long roomTypeId;
  private Long hotelId;
  private Room.Status status;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.roomNo != null) && (this.roomNo.intValue() != 0)) {
      sqlStr = sqlStr + " AND roomNo =?";
      this.parameterList.add(this.roomNo);
    }

    if ((this.roomTypeId != null) && (this.roomTypeId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND roomTypeId =?";
      this.parameterList.add(this.roomTypeId);
    }

    if ((this.hotelId != null) && (this.hotelId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND hotelId =?";
      this.parameterList.add(this.hotelId);
    }

    if (this.status != null) {
      sqlStr = sqlStr + " AND status = ?";
      this.parameterList.add(this.status);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getRoomNo() {
    return this.roomNo;
  }

  public void setRoomNo(Integer roomNo) {
    this.roomNo = roomNo;
  }

  public Long getRoomTypeId() {
    return this.roomTypeId;
  }

  public void setRoomTypeId(Long roomTypeId) {
    this.roomTypeId = roomTypeId;
  }

  public Room.Status getStatus() {
    return this.status;
  }

  public void setStatus(Room.Status status) {
    this.status = status;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }
}