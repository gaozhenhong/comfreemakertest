package com.wiwi.jsoil.sys.sms.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class SmsRecordQ extends PageUtil
{
  private Long id;
  private String telphone;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date sendTime;
  private String messageType;
  private String result;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date cancelTime;
  private String sendDatas;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.telphone != null) && (!(this.telphone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND telphone like ?";
      this.parameterList.add('%' + this.telphone + '%');
    }

    if (this.sendTime != null) {
      sqlStr = sqlStr + " AND sendTime =?";
      this.parameterList.add(this.sendTime);
    }

    if ((this.messageType != null) && (!(this.messageType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND messageType like ?";
      this.parameterList.add('%' + this.messageType + '%');
    }

    if ((this.result != null) && (!(this.result.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND result like ?";
      this.parameterList.add('%' + this.result + '%');
    }

    if (this.cancelTime != null) {
      sqlStr = sqlStr + " AND cancelTime =?";
      this.parameterList.add(this.cancelTime);
    }

    if ((this.sendDatas != null) && (!(this.sendDatas.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND sendDatas like ?";
      this.parameterList.add('%' + this.sendDatas + '%');
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTelphone() {
    return this.telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public Date getSendTime() {
    return this.sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public String getMessageType() {
    return this.messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public String getResult() {
    return this.result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Date getCancelTime() {
    return this.cancelTime;
  }

  public void setCancelTime(Date cancelTime) {
    this.cancelTime = cancelTime;
  }

  public String getSendDatas() {
    return this.sendDatas;
  }

  public void setSendDatas(String sendDatas) {
    this.sendDatas = sendDatas;
  }
}