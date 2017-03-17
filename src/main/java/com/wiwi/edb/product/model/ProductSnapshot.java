package com.wiwi.edb.product.model;

public class ProductSnapshot
{
  private Long id;
  private Long productId;
  private String name;
  private Integer productType;
  private Double price;
  private Integer priceType;
  private String area;
  private String supplierId;
  private String productTelphone;
  private String remark;
  private String productDetails;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return this.productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getProductType() {
    return this.productType;
  }

  public void setProductType(Integer productType) {
    this.productType = productType;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getPriceType() {
    return this.priceType;
  }

  public void setPriceType(Integer priceType) {
    this.priceType = priceType;
  }

  public String getArea() {
    return this.area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getSupplierId() {
    return this.supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

  public String getProductTelphone() {
    return this.productTelphone;
  }

  public void setProductTelphone(String productTelphone) {
    this.productTelphone = productTelphone;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getProductDetails() {
    return this.productDetails;
  }

  public void setProductDetails(String productDetails) {
    this.productDetails = productDetails;
  }
}