package com.wiwi.freego.areamanager.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class AreaUserMappingQ extends PageUtil
{
  private Long id;
  private Long areaId;
  private Long userId;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.areaId != null) && (this.areaId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND areaId =?";
      this.parameterList.add(this.areaId);
    }

    if ((this.userId != null) && (this.userId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND userId =?";
      this.parameterList.add(this.userId);
    }

    return sqlStr;
  }

  public Long getId() {
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