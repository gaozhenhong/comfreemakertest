package com.wiwi.edb.product.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class ProductSnapshotQ extends PageUtil
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

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.productId != null) && (this.productId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND productId =?";
      this.parameterList.add(this.productId);
    }

    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like ?";
      this.parameterList.add('%' + this.name + '%');
    }

    if ((this.productType != null) && (this.productType.intValue() != 0)) {
      sqlStr = sqlStr + " AND productType =?";
      this.parameterList.add(this.productType);
    }

    if (this.price != null) {
      sqlStr = sqlStr + " AND price =?";
      this.parameterList.add(this.price);
    }

    if ((this.priceType != null) && (this.priceType.intValue() != 0)) {
      sqlStr = sqlStr + " AND priceType =?";
      this.parameterList.add(this.priceType);
    }

    if ((this.area != null) && (!(this.area.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND area like ?";
      this.parameterList.add('%' + this.area + '%');
    }

    if ((this.supplierId != null) && (!(this.supplierId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND supplierId like ?";
      this.parameterList.add('%' + this.supplierId + '%');
    }

    if ((this.productTelphone != null) && (!(this.productTelphone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND productTelphone like ?";
      this.parameterList.add('%' + this.productTelphone + '%');
    }

    if ((this.remark != null) && (!(this.remark.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND remark like ?";
      this.parameterList.add('%' + this.remark + '%');
    }

    if ((this.productDetails != null) && (!(this.productDetails.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND productDetails like ?";
      this.parameterList.add('%' + this.productDetails + '%');
    }

    return sqlStr;
  }

  public Long getId() {
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