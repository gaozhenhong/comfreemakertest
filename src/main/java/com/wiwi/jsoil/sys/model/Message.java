package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Message
{
  private String id;
  private Long userId;
  private Integer readStatus;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date readTime;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date sentTime;
  private String messageType;
  private String originName;
  private String messageTitle;
  private String link;
  private String message;
  private Integer deleteFlag;
  private String resourceIds;
  private User sentPerson;

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

  public Integer getReadStatus() {
    return this.readStatus;
  }

  public void setReadStatus(Integer readStatus) {
    this.readStatus = readStatus;
  }

  public Date getReadTime() {
    return this.readTime;
  }

  public void setReadTime(Date readTime) {
    this.readTime = readTime;
  }

  public Date getSentTime() {
    return this.sentTime;
  }

  public void setSentTime(Date sentTime) {
    this.sentTime = sentTime;
  }

  public String getMessageType() {
    return this.messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public String getOriginName() {
    return this.originName;
  }

  public void setOriginName(String originName) {
    this.originName = originName;
  }

  public String getMessageTitle() {
    return this.messageTitle;
  }

  public void setMessageTitle(String messageTitle) {
    this.messageTitle = messageTitle;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getDeleteFlag() {
    return this.deleteFlag;
  }

  public void setDeleteFlag(Integer deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public User getSentPerson()
  {
    return this.sentPerson;
  }

  public void setSentPerson(User sentPerson) {
    this.sentPerson = sentPerson;
  }

  public String getResourceIds() {
    return this.resourceIds;
  }

  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }
}