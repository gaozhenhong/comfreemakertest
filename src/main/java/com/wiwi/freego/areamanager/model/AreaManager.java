package com.wiwi.freego.areamanager.model;

public class AreaManager
{
  private Long id;
  private Long hotelId;
  private String scene;
  private Float longitude;
  private Float latitude;

  public Long getId()
  {
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