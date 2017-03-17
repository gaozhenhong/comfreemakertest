package com.wiwi.freego.hotel.model;

import com.wiwi.jsoil.db.PageUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class SpecialTimePriceQ extends PageUtil
{
  private Long id;
  private Long roomTypeId;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date specialTime;
  private Float pirce;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.roomTypeId != null) && (this.roomTypeId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND roomTypeId =?";
      this.parameterList.add(this.roomTypeId);
    }

    if (this.specialTime != null) {
      sqlStr = sqlStr + " AND specialTime like ?";
      this.parameterList.add(new SimpleDateFormat("yyyy-MM-dd").format(this.specialTime) + '%');
    }

    if (this.pirce != null) {
      sqlStr = sqlStr + " AND pirce =?";
      this.parameterList.add(this.pirce);
    }

    return sqlStr;
  }

  public Date getSpecialTime()
  {
    return this.specialTime;
  }

  public void setSpecialTime(Date specialTime) {
    this.specialTime = specialTime;
  }

  public Float getPirce() {
    return this.pirce;
  }

  public void setPirce(Float pirce) {
    this.pirce = pirce;
  }

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getRoomTypeId()
  {
    return this.roomTypeId;
  }

  public void setRoomTypeId(Long roomTypeId)
  {
    this.roomTypeId = roomTypeId;
  }
}