package com.wiwi.freego.hotel.model;

import com.wiwi.freego.shopkeeper.model.Shopkeeper;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.model.DictionaryOption;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.util.DictionaryUtil;
import com.wiwi.jsoil.util.ResourceUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Hotel
{
  private Long id;
  private String name;
  private String code;
  private City city;
  private String street;
  private String address;
  private Long logo;
  private Float longitude;
  private Float latitude;
  private Shopkeeper shopkeeper;
  private Status status;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  private Long createPersonId;
  private String telphone;
  private String weixin;
  private String installation;
  private String labels;
  private String description;
  private String noticeTelephone;
  private String email;
  private String alipay;
  private Integer orderNo;
  private Integer orgId;
  private Organization company;
  private Integer deleteFlag;
  private Category category;

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

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public City getCity() {
    return this.city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getStreet() {
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

  public Long getLogo() {
    return this.logo;
  }

  public void setLogo(Long logo) {
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

  public Shopkeeper getShopkeeper() {
    return this.shopkeeper;
  }

  public void setShopkeeper(Shopkeeper shopkeeper) {
    this.shopkeeper = shopkeeper;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getCreatePersonId() {
    return this.createPersonId;
  }

  public void setCreatePersonId(Long createPersonId) {
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

  public Integer getDeleteFlag()
  {
    return this.deleteFlag;
  }

  public void setDeleteFlag(Integer deleteFlag)
  {
    this.deleteFlag = deleteFlag;
  }

  public List<String> getInstallationNames()
  {
    List names = new ArrayList();
    String installations = getInstallation();
    if ((installations != null) && (installations.trim().length() > 0)) {
      String[] codes = installations.split(",");
      for (String code : codes) {
        names.add(DictionaryUtil.getOptionName(code).getName());
      }
    }
    return names;
  }

  public List<Resource> getImages()
  {
    return getImages(5);
  }

  public List<Resource> getImages(int count)
  {
    return ResourceUtil.getResources(Hotel.class.getName(), getId()+"", count);
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Organization getCompany() {
    return this.company;
  }

  public void setCompany(Organization company) {
    this.company = company;
  }

  public String getAlipay() {
    return this.alipay;
  }

  public void setAlipay(String alipay) {
    this.alipay = alipay;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String webSiteUrl() {
    return "/site/mobile/web/" + this.code + "/index";
  }

  public static enum Status
  {
    EDIT("编辑中"), OPENING("营业中"), SHUTOUT("停业中"), CLOSED("已关闭");

    String label = "";

    private Status(String label) {
      this.label = label;
    }

    public String getLabel() {
      return this.label;
    }

    public void setLabel(String label) {
      this.label = label;
    }
  }
}