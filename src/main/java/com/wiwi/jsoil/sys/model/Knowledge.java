package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Knowledge
{
  private Long id;
  private String name;
  private Category category;
  private String labels;
  private Long fileId;
  private String parameters;
  private String fileType;
  private Long logoId;
  private Integer browseCount;
  private Integer downloadCount;
  private Integer orgId;
  private Integer status;
  private Long createUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Long lastModifyUserId;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date lastModifyTime;
  private String description;

  public Long getId()
  {
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

  public String getLabels()
  {
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

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Integer orgId) {
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

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}