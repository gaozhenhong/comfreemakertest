package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class KnowledgeQ extends PageUtil
{
  private Long id;
  private String name;
  private Long categoryId;
  private String labels;
  private Long fileId;
  private String parameters;
  private String fileType;
  private Long logoId;
  private Integer browseCount;
  private Integer downloadCount;
  private Long orgId;
  private Long createUserId;
  private Integer status;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Long lastModifyUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyTime;
  private String description;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like ?";
      this.parameterList.add('%' + this.name + '%');
    }

    if ((this.categoryId != null) && (this.categoryId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND categoryId =?";
      this.parameterList.add(this.categoryId);
    }

    if ((this.labels != null) && (!(this.labels.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND labels like ?";
      this.parameterList.add('%' + this.labels + '%');
    }

    if ((this.fileId != null) && (this.fileId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND fileId =?";
      this.parameterList.add(this.fileId);
    }
    if ((this.status != null) && (this.status.intValue() != 0)) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    if ((this.parameters != null) && (!(this.parameters.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND parameters like ?";
      this.parameterList.add('%' + this.parameters + '%');
    }

    if ((this.fileType != null) && (!(this.fileType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND fileType like ?";
      this.parameterList.add('%' + this.fileType + '%');
    }

    if ((this.logoId != null) && (this.logoId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND logoId =?";
      this.parameterList.add(this.logoId);
    }

    if ((this.browseCount != null) && (this.browseCount.intValue() != 0)) {
      sqlStr = sqlStr + " AND browseCount =?";
      this.parameterList.add(this.browseCount);
    }

    if ((this.downloadCount != null) && (this.downloadCount.intValue() != 0)) {
      sqlStr = sqlStr + " AND downloadCount =?";
      this.parameterList.add(this.downloadCount);
    }

    if ((this.orgId != null) && (this.orgId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND orgId =?";
      this.parameterList.add(this.orgId);
    }

    if ((this.createUserId != null) && (this.createUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND createUserId =?";
      this.parameterList.add(this.createUserId);
    }

    if (this.createTime != null) {
      sqlStr = sqlStr + " AND createTime =?";
      this.parameterList.add(this.createTime);
    }

    if ((this.lastModifyUserId != null) && (this.lastModifyUserId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND lastModifyUserId =?";
      this.parameterList.add(this.lastModifyUserId);
    }

    if (this.lastModifyTime != null) {
      sqlStr = sqlStr + " AND lastModifyTime =?";
      this.parameterList.add(this.lastModifyTime);
    }

    if ((this.description != null) && (!(this.description.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND description like ?";
      this.parameterList.add('%' + this.description + '%');
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getLabels() {
    return this.labels;
  }

  public void setLabels(String labels) {
    this.labels = labels;
  }

  public Long getFileId() {
    return this.fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public String getParameters() {
    return this.parameters;
  }

  public void setParameters(String parameters) {
    this.parameters = parameters;
  }

  public String getFileType() {
    return this.fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public Long getLogoId() {
    return this.logoId;
  }

  public void setLogoId(Long logoId) {
    this.logoId = logoId;
  }

  public Integer getBrowseCount() {
    return this.browseCount;
  }

  public void setBrowseCount(Integer browseCount) {
    this.browseCount = browseCount;
  }

  public Integer getDownloadCount() {
    return this.downloadCount;
  }

  public void setDownloadCount(Integer downloadCount) {
    this.downloadCount = downloadCount;
  }

  public Long getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }

  public Long getCreateUserId() {
    return this.createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getLastModifyUserId() {
    return this.lastModifyUserId;
  }

  public void setLastModifyUserId(Long lastModifyUserId) {
    this.lastModifyUserId = lastModifyUserId;
  }

  public Date getLastModifyTime() {
    return this.lastModifyTime;
  }

  public void setLastModifyTime(Date lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}