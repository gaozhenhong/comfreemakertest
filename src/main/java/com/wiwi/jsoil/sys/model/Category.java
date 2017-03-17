package com.wiwi.jsoil.sys.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Category
{
  private Long id;
  private String name;
  private String code;
  private Category parentCategory;
  private Integer categoryIndex;
  private String orgId;
  private Date createTime;
  private String createUserId;
  private Long logoId;
  private String remark;
  private String viewUrl;
  private Long deleteFlag;
  private List<Category> subCategoryList = new ArrayList();
  
  public Category() {}
  
  public Category(Long id)
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
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public Category getParentCategory()
  {
    return this.parentCategory;
  }
  
  public void setParentCategory(Category parentCategory)
  {
    this.parentCategory = parentCategory;
  }
  
  public Integer getCategoryIndex()
  {
    return this.categoryIndex;
  }
  
  public void setCategoryIndex(Integer categoryIndex)
  {
    this.categoryIndex = categoryIndex;
  }
  
  public String getOrgId()
  {
    return this.orgId;
  }
  
  public void setOrgId(String orgId)
  {
    this.orgId = orgId;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getCreateUserId()
  {
    return this.createUserId;
  }
  
  public void setCreateUserId(String createUserId)
  {
    this.createUserId = createUserId;
  }
  
  public Long getLogoId()
  {
    return this.logoId;
  }
  
  public void setLogoId(Long logoId)
  {
    this.logoId = logoId;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getViewUrl()
  {
    return this.viewUrl;
  }
  
  public void setViewUrl(String viewUrl)
  {
    this.viewUrl = viewUrl;
  }
  
  public Long getDeleteFlag()
  {
    return this.deleteFlag;
  }
  
  public void setDeleteFlag(Long deleteFlag)
  {
    this.deleteFlag = deleteFlag;
  }
  
  public List<Category> getSubCategoryList()
  {
    return this.subCategoryList;
  }
  
  public void setSubCategoryList(List<Category> subCategoryList)
  {
    this.subCategoryList = subCategoryList;
  }
}
