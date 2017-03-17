package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Notice
{
  private Long id;
  private String title;
  private String content;
  private String scope;
  private String scopeDetails;
  private Long publishPersonId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date publishTime;
  private Long createPersonId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Integer status;
  private String type;
  private String resourceIds;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getScope() {
    return this.scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public Long getPublishPersonId() {
    return this.publishPersonId;
  }

  public void setPublishPersonId(Long publishPerson) {
    this.publishPersonId = publishPerson;
  }

  public Date getPublishTime() {
    return this.publishTime;
  }

  public void setPublishTime(Date publishTime) {
    this.publishTime = publishTime;
  }

  public Long getCreatePersonId() {
    return this.createPersonId;
  }

  public void setCreatePersonId(Long createPerson) {
    this.createPersonId = createPerson;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getScopeDetails() {
    return this.scopeDetails;
  }

  public void setScopeDetails(String scopeDetails) {
    this.scopeDetails = scopeDetails;
  }

  public String getResourceIds() {
    return this.resourceIds;
  }

  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }
}