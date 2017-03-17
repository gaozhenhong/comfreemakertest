package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.Date;
import java.util.List;

public class CategoryQ extends PageUtil
{
  private Long id;
  private String name;
  private String code;
  private String topCode;
  private Long parentCategoryId;
  private String parentCategoryName;
  private Integer categoryIndex;
  private String orgId;
  private Date createTime;
  private String createUserId;
  private Long logoId;
  private String remark;
  private String viewUrl;
  private Long deleteFlag;
  private String parentCategoryCode;

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

    if ((this.code != null) && (!(this.code.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND code like ?";
      this.parameterList.add('%' + this.code + '%');
    }

    if ((this.topCode != null) && (!(this.topCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND code like ?";
      this.parameterList.add(this.topCode + '%');
      sqlStr = sqlStr + " AND code <> ?";
      this.parameterList.add(this.topCode);
    }

    if ((this.parentCategoryId != null) && (this.parentCategoryId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND parentCategoryId =?";
      this.parameterList.add(this.parentCategoryId);
    }

    if ((this.parentCategoryName != null) && (!(this.parentCategoryName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND parentCategoryName  like ?";
      this.parameterList.add('%' + this.parentCategoryName + '%');
    }

    if ((this.categoryIndex != null) && (this.categoryIndex.intValue() != 0)) {
      sqlStr = sqlStr + " AND categoryIndex =?";
      this.parameterList.add(this.categoryIndex);
    }

    if ((this.orgId != null) && (!(this.orgId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND orgId like ?";
      this.parameterList.add('%' + this.orgId + '%');
    }

    if (this.createTime != null) {
      sqlStr = sqlStr + " AND createTime =?";
      this.parameterList.add(this.createTime);
    }

    if ((this.createUserId != null) && (!(this.createUserId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND createUserId like ?";
      this.parameterList.add('%' + this.createUserId + '%');
    }

    if ((this.logoId != null) && (this.logoId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND logoId =?";
      this.parameterList.add(this.logoId);
    }

    if ((this.remark != null) && (!(this.remark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND remark like ?";
      this.parameterList.add('%' + this.remark + '%');
    }

    if ((this.viewUrl != null) && (!(this.viewUrl.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND viewUrl like ?";
      this.parameterList.add('%' + this.viewUrl + '%');
    }

    if ((this.deleteFlag != null) && (this.deleteFlag.longValue() != 0L)) {
      sqlStr = sqlStr + " AND deleteFlag =?";
      this.parameterList.add(this.deleteFlag);
    }

    if ((this.parentCategoryCode != null) && (!(this.parentCategoryCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND parentCategoryCode =?";
      this.parameterList.add(this.parentCategoryCode);
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

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Long getParentCategoryId()
  {
    return this.parentCategoryId;
  }

  public void setParentCategoryId(Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public String getParentCategoryName() {
    return this.parentCategoryName;
  }

  public void setParentCategoryName(String parentCategoryName) {
    this.parentCategoryName = parentCategoryName;
  }

  public Integer getCategoryIndex() {
    return this.categoryIndex;
  }

  public void setCategoryIndex(Integer categoryIndex) {
    this.categoryIndex = categoryIndex;
  }

  public String getOrgId() {
    return this.orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getCreateUserId() {
    return this.createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public Long getLogoId() {
    return this.logoId;
  }

  public void setLogoId(Long logoId) {
    this.logoId = logoId;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getViewUrl() {
    return this.viewUrl;
  }

  public void setViewUrl(String viewUrl) {
    this.viewUrl = viewUrl;
  }

  public Long getDeleteFlag() {
    return this.deleteFlag;
  }

  public void setDeleteFlag(Long deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public String getTopCode() {
    return this.topCode;
  }

  public void setTopCode(String topCode) {
    this.topCode = topCode;
  }

  public String getParentCategoryCode() {
    return this.parentCategoryCode;
  }

  public void setParentCategoryCode(String parentCategoryCode) {
    this.parentCategoryCode = parentCategoryCode;
  }
}