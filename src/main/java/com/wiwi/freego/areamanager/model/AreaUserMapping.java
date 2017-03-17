package com.wiwi.freego.areamanager.model;

public class AreaUserMapping
{
  private Long id;
  private Long areaId;
  private Long userId;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAreaId() {
    return this.areaId;
  }

  public void setAreaId(Long areaId) {
    this.areaId = areaId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}