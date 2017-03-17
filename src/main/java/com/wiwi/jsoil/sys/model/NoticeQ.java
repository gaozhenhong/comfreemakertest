package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class NoticeQ extends PageUtil
{
  private Long id;
  private String title;
  private String content;
  private String scope;
  private Long publishPerson;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date publishTime;
  private Long createPerson;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Integer status;
  private String type;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.title != null) && (!(this.title.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND title like ?";
      this.parameterList.add('%' + this.title + '%');
    }

    if ((this.content != null) && (!(this.content.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND content like ?";
      this.parameterList.add('%' + this.content + '%');
    }

    if ((this.scope != null) && (!(this.scope.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND scope like ?";
      this.parameterList.add('%' + this.scope + '%');
    }

    if ((this.publishPerson != null) && (this.publishPerson.longValue() != 0L)) {
      sqlStr = sqlStr + " AND publishPerson =?";
      this.parameterList.add(this.publishPerson);
    }

    if (this.publishTime != null) {
      sqlStr = sqlStr + " AND publishTime =?";
      this.parameterList.add(this.publishTime);
    }

    if ((this.createPerson != null) && (this.createPerson.longValue() != 0L)) {
      sqlStr = sqlStr + " AND createPerson =?";
      this.parameterList.add(this.createPerson);
    }

    if (this.createTime != null) {
      sqlStr = sqlStr + " AND createTime =?";
      this.parameterList.add(this.createTime);
    }

    if ((this.status != null) && (this.status.intValue() != 0)) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    if ((this.type != null) && (!(this.type.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND type like ?";
      this.parameterList.add('%' + this.type + '%');
    }

    return sqlStr;
  }

  public Long getId() {
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

  public Long getPublishPerson() {
    return this.publishPerson;
  }

  public void setPublishPerson(Long publishPerson) {
    this.publishPerson = publishPerson;
  }

  public Date getPublishTime() {
    return this.publishTime;
  }

  public void setPublishTime(Date publishTime) {
    this.publishTime = publishTime;
  }

  public Long getCreatePerson() {
    return this.createPerson;
  }

  public void setCreatePerson(Long createPerson) {
    this.createPerson = createPerson;
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
}