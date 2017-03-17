package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class CollectQ extends PageUtil
{
  private Long id;
  private String collectTitle;
  private String redirectPath;
  private String collectModule;
  private Long collectBeanId;
  private User user;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date collectTime;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.collectTitle != null) && (!(this.collectTitle.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND collectTitle like ?";
      this.parameterList.add('%' + this.collectTitle + '%');
    }

    if ((this.user != null) && (this.user.getId().longValue() != 0L)) {
      sqlStr = sqlStr + " AND userId =?";
      this.parameterList.add(this.user.getId());
    }

    return sqlStr;
  }

  public Long getId() {
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

  public Long getCollectBeanId() {
    return this.collectBeanId;
  }

  public void setCollectBeanId(Long collectBeanId) {
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