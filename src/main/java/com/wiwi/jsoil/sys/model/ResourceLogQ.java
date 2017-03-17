package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class ResourceLogQ extends PageUtil
{
  private String id;
  private Long userId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date operateTime;
  private String operateType;
  private Long resourceId;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (!(this.id.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND id like ?";
      this.parameterList.add('%' + this.id + '%');
    }

    if ((this.userId != null) && (this.userId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND userId =?";
      this.parameterList.add(this.userId);
    }

    if (this.operateTime != null) {
      sqlStr = sqlStr + " AND operateTime =?";
      this.parameterList.add(this.operateTime);
    }

    if ((this.operateType != null) && (!(this.operateType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND operateType like ?";
      this.parameterList.add('%' + this.operateType + '%');
    }

    if ((this.resourceId != null) && (this.resourceId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND resourceId =?";
      this.parameterList.add(this.resourceId);
    }

    return sqlStr;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Date getOperateTime() {
    return this.operateTime;
  }

  public void setOperateTime(Date operateTime) {
    this.operateTime = operateTime;
  }

  public String getOperateType() {
    return this.operateType;
  }

  public void setOperateType(String operateType) {
    this.operateType = operateType;
  }

  public Long getResourceId() {
    return this.resourceId;
  }

  public void setResourceId(Long resourceId) {
    this.resourceId = resourceId;
  }
}