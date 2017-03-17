package com.wiwi.freego.hotel.model;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.sys.model.Category;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class HotelQ extends PageUtil
{
  private Integer id;
  private String name;
  private String code;
  private Integer cityId;
  private String cityName;
  private String street;
  private String address;
  private Integer logo;
  private Float longitude;
  private Float latitude;
  private Integer shopkeeper;
  private String status;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Integer createPersonId;
  private String telphone;
  private String weixin;
  private String installation;
  private String labels;
  private String description;
  private String noticeTelephone;
  private Integer orderNo;
  private Integer orgId;
  private Integer notCompanyId;
  private boolean delete;
  private Category category;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.intValue() != 0)) {
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

    if ((this.cityName != null) && (!(this.cityName.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND cityName like ?";
      this.parameterList.add('%' + this.cityName + '%');
    }

    if ((this.cityId != null) && (this.cityId.intValue() != 0)) {
      sqlStr = sqlStr + " AND cityId = ?";
      this.parameterList.add(this.cityId);
    }

    if ((this.street != null) && (!(this.street.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND street like ?";
      this.parameterList.add('%' + this.street + '%');
    }

    if ((this.address != null) && (!(this.address.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND address like ?";
      this.parameterList.add('%' + this.address + '%');
    }

    if ((this.logo != null) && (this.logo.intValue() != 0)) {
      sqlStr = sqlStr + " AND logo =?";
      this.parameterList.add(this.logo);
    }

    if (this.longitude != null) {
      sqlStr = sqlStr + " AND longitude =?";
      this.parameterList.add(this.longitude);
    }

    if (this.latitude != null) {
      sqlStr = sqlStr + " AND latitude =?";
      this.parameterList.add(this.latitude);
    }

    if ((this.shopkeeper != null) && (this.shopkeeper.intValue() != 0)) {
      sqlStr = sqlStr + " AND shopkeeper =?";
      this.parameterList.add(this.shopkeeper);
    }

    if ((this.status != null) && (!(this.status.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND status like ?";
      this.parameterList.add('%' + this.status + '%');
    }

    if (this.createTime != null) {
      sqlStr = sqlStr + " AND createTime =?";
      this.parameterList.add(this.createTime);
    }

    if ((this.createPersonId != null) && (this.createPersonId.intValue() != 0)) {
      sqlStr = sqlStr + " AND createPersonId =?";
      this.parameterList.add(this.createPersonId);
    }

    if ((this.telphone != null) && (!(this.telphone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND telphone like ?";
      this.parameterList.add('%' + this.telphone + '%');
    }

    if ((this.weixin != null) && (!(this.weixin.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND weixin like ?";
      this.parameterList.add('%' + this.weixin + '%');
    }

    if ((this.installation != null) && (!(this.installation.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND installation like ?";
      this.parameterList.add('%' + this.installation + '%');
    }

    if ((this.labels != null) && (!(this.labels.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND labels like ?";
      this.parameterList.add('%' + this.labels + '%');
    }

    if ((this.description != null) && (!(this.description.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND description like ?";
      this.parameterList.add('%' + this.description + '%');
    }

    if ((this.noticeTelephone != null) && (!(this.noticeTelephone.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND noticeTelephone like ?";
      this.parameterList.add('%' + this.noticeTelephone + '%');
    }

    if ((this.orderNo != null) && (this.orderNo.intValue() != 0)) {
      sqlStr = sqlStr + " AND orderNo =?";
      this.parameterList.add(this.orderNo);
    }

    if ((this.orgId != null) && (this.orgId.intValue() != 0)) {
      sqlStr = sqlStr + " AND orgId =?";
      this.parameterList.add(this.orgId);
    }
    if ((this.notCompanyId != null) && (this.notCompanyId.intValue() != 0)) {
      sqlStr = sqlStr + " AND orgId  <> ?";
      this.parameterList.add(this.notCompanyId);
    }

    if ((this.category != null) && (this.category.getId() != null)) {
      sqlStr = sqlStr + " AND categoryId =?";
      this.parameterList.add(this.category.getId());
    }

    if (isDelete())
      sqlStr = sqlStr + " AND deleteFlag =1";
    else {
      sqlStr = sqlStr + " AND (deleteFlag is null OR deleteFlag <>1)";
    }

    return sqlStr;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
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

  public String getStreet()
  {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Integer getLogo() {
    return this.logo;
  }

  public void setLogo(Integer logo) {
    this.logo = logo;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  public Integer getShopkeeper() {
    return this.shopkeeper;
  }

  public void setShopkeeper(Integer shopkeeper) {
    this.shopkeeper = shopkeeper;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getCreatePersonId() {
    return this.createPersonId;
  }

  public void setCreatePersonId(Integer createPersonId) {
    this.createPersonId = createPersonId;
  }

  public String getTelphone() {
    return this.telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public String getWeixin() {
    return this.weixin;
  }

  public void setWeixin(String weixin) {
    this.weixin = weixin;
  }

  public String getInstallation() {
    return this.installation;
  }

  public void setInstallation(String installation) {
    this.installation = installation;
  }

  public String getLabels() {
    return this.labels;
  }

  public void setLabels(String labels) {
    this.labels = labels;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNoticeTelephone() {
    return this.noticeTelephone;
  }

  public void setNoticeTelephone(String noticeTelephone) {
    this.noticeTelephone = noticeTelephone;
  }

  public Integer getOrderNo() {
    return this.orderNo;
  }

  public void setOrderNo(Integer orderNo) {
    this.orderNo = orderNo;
  }

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public boolean isDelete() {
    return this.delete;
  }

  public void setDelete(boolean delete) {
    this.delete = delete;
  }

  public Integer getCityId() {
    return this.cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public String getCityName() {
    return this.cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Integer getNotCompanyId() {
    return this.notCompanyId;
  }

  public void setNotCompanyId(Integer notCompanyId) {
    this.notCompanyId = notCompanyId;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}