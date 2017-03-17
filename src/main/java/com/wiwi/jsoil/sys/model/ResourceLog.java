package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class ResourceLog
{
  private String id;
  private Long userId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date operateTime;
  private String operateType;
  private Long resourceId;

  public String getId()
  {
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