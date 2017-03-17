package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class ResourceQ extends PageUtil
{
  private Long id;
  private String resName;
  private String resType;
  private String notResType;
  private String fileType;
  private String resPath;
  private String params;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Long createUserId;
  private Long orgId;
  private String className;
  private String primaryKey;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.resName != null) && (!(this.resName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND resName like ?";
      this.parameterList.add('%' + this.resName + '%');
    }

    if ((this.resType != null) && (!(this.resType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND resType like ?";
      this.parameterList.add('%' + this.resType + '%');
    }

    if ((this.fileType != null) && (!(this.fileType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND fileType like ?";
      this.parameterList.add('%' + this.fileType + '%');
    }

    if ((this.resPath != null) && (!(this.resPath.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND resPath like ?";
      this.parameterList.add('%' + this.resPath + '%');
    }

    if ((this.params != null) && (!(this.params.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND params like ?";
      this.parameterList.add('%' + this.params + '%');
    }

    if (this.createTime != null) {
      sqlStr = sqlStr + " AND createTime =?";
      this.parameterList.add(this.createTime);
    }

    if ((this.createUserId != null) && (this.createUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND createUserId =?";
      this.parameterList.add(this.createUserId);
    }

    if ((this.orgId != null) && (this.orgId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND orgId =?";
      this.parameterList.add(this.orgId);
    }

    if ((this.className != null) && (!(this.className.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND className =?";
      this.parameterList.add(this.className);
    }

    if ((this.primaryKey != null) && (!(this.primaryKey.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND primaryKey =?";
      this.parameterList.add(this.primaryKey);
    }

    if ((this.notResType != null) && (!(this.notResType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND resType not like ?";
      this.parameterList.add('%' + this.notResType + '%');
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getResName() {
    return this.resName;
  }

  public void setResName(String resName) {
    this.resName = resName;
  }

  public String getResType() {
    return this.resType;
  }

  public void setResType(String resType) {
    this.resType = resType;
  }

  public String getResPath() {
    return this.resPath;
  }

  public void setResPath(String resPath) {
    this.resPath = resPath;
  }

  public String getParams() {
    return this.params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getCreateUserId() {
    return this.createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Long getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }

  public String getFileType() {
    return this.fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getPrimaryKey() {
    return this.primaryKey;
  }

  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getNotResType() {
    return this.notResType;
  }

  public void setNotResType(String notResType) {
    this.notResType = notResType;
  }
}