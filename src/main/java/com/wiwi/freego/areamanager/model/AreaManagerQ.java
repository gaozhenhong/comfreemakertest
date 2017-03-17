package com.wiwi.freego.areamanager.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class AreaManagerQ extends PageUtil
{
  private Long id;
  private Long hotelId;
  private String scene;
  private Float longitude;
  private Float latitude;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.hotelId != null) && (this.hotelId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND hotelId =?";
      this.parameterList.add(this.hotelId);
    }

    if ((this.scene != null) && (!(this.scene.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND scene like ?";
      this.parameterList.add('%' + this.scene + '%');
    }

    if (this.longitude != null) {
      sqlStr = sqlStr + " AND longitude =?";
      this.parameterList.add(this.longitude);
    }

    if (this.latitude != null) {
      sqlStr = sqlStr + " AND latitude =?";
      this.parameterList.add(this.latitude);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public String getScene() {
    return this.scene;
  }

  public void setScene(String scene) {
    this.scene = scene;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }
}