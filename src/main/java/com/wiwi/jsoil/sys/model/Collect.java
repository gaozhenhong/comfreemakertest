package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Collect
{
  private Long id;
  private String collectTitle;
  private String redirectPath;
  private String collectModule;
  private String collectBeanId;
  private User user;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date collectTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCollectTitle() {
    return this.collectTitle;
  }

  public void setCollectTitle(String collectTitle) {
    this.collectTitle = collectTitle;
  }

  public String getRedirectPath() {
    return this.redirectPath;
  }

  public void setRedirectPath(String redirectPath) {
    this.redirectPath = redirectPath;
  }

  public String getCollectModule() {
    return this.collectModule;
  }

  public void setCollectModule(String collectModule) {
    this.collectModule = collectModule;
  }

  public String getCollectBeanId() {
    return this.collectBeanId;
  }

  public void setCollectBeanId(String collectBeanId) {
    this.collectBeanId = collectBeanId;
  }

  public User getUser()
  {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getCollectTime() {
    return this.collectTime;
  }

  public void setCollectTime(Date collectTime) {
    this.collectTime = collectTime;
  }
}