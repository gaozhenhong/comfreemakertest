package com.wiwi.jsoil.sys.sms.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SmsRecord
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

  public Long getId()
  {
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