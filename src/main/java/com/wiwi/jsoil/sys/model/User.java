package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class User
{
  private Long id;
  private String name;
  private String loginName;
  private String telephone;
  private Organization org;
  private Long createUserId;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private String demo;
  private long logoId;
  private int status;
  
  public User() {}
  
  public User(Long id)
  {
    this.id = id;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getLoginName()
  {
    return this.loginName;
  }
  
  public void setLoginName(String loginName)
  {
    this.loginName = loginName;
  }
  
  public String getTelephone()
  {
    return this.telephone;
  }
  
  public void setTelephone(String telephone)
  {
    this.telephone = telephone;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getDemo()
  {
    return this.demo;
  }
  
  public void setDemo(String demo)
  {
    this.demo = demo;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public Organization getOrg()
  {
    return this.org;
  }
  
  public void setOrg(Organization org)
  {
    this.org = org;
  }
  
  public Long getCreateUserId()
  {
    return this.createUserId;
  }
  
  public void setCreateUserId(Long createUserId)
  {
    this.createUserId = createUserId;
  }
  
  public String toString()
  {
    return 
    
      "User [id=" + this.id + ", name=" + this.name + ", loginName=" + this.loginName + ", telephone=" + this.telephone + ", org=" + this.org + ", createUserId=" + this.createUserId + ", createTime=" + this.createTime + ", demo=" + this.demo + ", status=" + this.status + "]";
  }
  
  public long getLogoId()
  {
    return this.logoId;
  }
  
  public void setLogoId(long logoId)
  {
    this.logoId = logoId;
  }
}
