package com.wiwi.jsoil.sys.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Resource
{
  private Long id;
  private String resName;
  private String resType;
  private String resPath;
  private String params;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Long createUserId;
  private Long downloadCount;
  private Long browseCount;
  private Integer orgId;
  private String fileType;
  private String className;
  private String primaryKey;
  private int orderNo;

  public Resource()
  {
    this.id = Long.valueOf(0L);

    this.downloadCount = Long.valueOf(0L);
    this.browseCount = Long.valueOf(0L);
  }

  public Long getId()
  {
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

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public String getFileType() {
    return this.fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public Long getDownloadCount() {
    return this.downloadCount;
  }

  public void setDownloadCount(Long downloadCount) {
    this.downloadCount = downloadCount;
  }

  public Long getBrowseCount() {
    return this.browseCount;
  }

  public void setBrowseCount(Long browseCount) {
    this.browseCount = browseCount;
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

  public int getOrderNo() {
    return this.orderNo;
  }

  public void setOrderNo(int orderNo) {
    this.orderNo = orderNo;
  }
}