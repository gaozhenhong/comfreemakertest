package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class MessageQ extends PageUtil
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
  private Long sentPersonId;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();
    sqlStr = sqlStr + " AND  ( deleteFlag is null OR deleteFlag <> 1 )";

    if ((this.id != null) && (!(this.id.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND id like ?";
      this.parameterList.add('%' + this.id + '%');
    }

    if ((this.userId != null) && (this.userId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND userId =?";
      this.parameterList.add(this.userId);
    }

    if ((this.readStatus != null) && (this.readStatus.intValue() != 0)) {
      sqlStr = sqlStr + " AND readStatus =?";
      this.parameterList.add(this.readStatus);
    }

    if (this.readTime != null) {
      sqlStr = sqlStr + " AND readTime =?";
      this.parameterList.add(this.readTime);
    }

    if (this.sentTime != null) {
      sqlStr = sqlStr + " AND sentTime =?";
      this.parameterList.add(this.sentTime);
    }

    if ((this.messageType != null) && (!(this.messageType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND messageType like ?";
      this.parameterList.add('%' + this.messageType + '%');
    }

    if ((this.originName != null) && (!(this.originName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND originName like ?";
      this.parameterList.add('%' + this.originName + '%');
    }

    if ((this.messageTitle != null) && (!(this.messageTitle.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND messageTitle like ?";
      this.parameterList.add('%' + this.messageTitle + '%');
    }

    if ((this.link != null) && (!(this.link.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND link like ?";
      this.parameterList.add('%' + this.link + '%');
    }

    if ((this.message != null) && (!(this.message.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND message like ?";
      this.parameterList.add('%' + this.message + '%');
    }

    if ((this.deleteFlag != null) && (this.deleteFlag.intValue() != 0)) {
      sqlStr = sqlStr + " AND deleteFlag =?";
      this.parameterList.add(this.deleteFlag);
    }

    if ((this.sentPersonId != null) && (this.sentPersonId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND sentPersonId =?";
      this.parameterList.add(this.sentPersonId);
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

  public Long getSentPersonId() {
    return this.sentPersonId;
  }

  public void setSentPersonId(Long sentPersonId) {
    this.sentPersonId = sentPersonId;
  }
}