package com.wiwi.freego.hotel.model;

import com.wiwi.freego.hotel.service.SpecialTimePriceService;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.util.ResourceUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoomType
{
  private Long id;
  private String name;
  private Double defaultPrice;
  private Long logo;
  private String description;
  private Long hotelId;
  private Double mondayPrice;
  private Double tuesdayPrice;
  private Double wednesdayPrice;
  private Double thursdayPrice;
  private Double fridayPrice;
  private Double saturdayPrice;
  private Double sundayPrice;
  private Integer roomNumber;
  private Status status;
  private String bedType;
  private Integer availableNumber;
  private Integer orderNo;

  public RoomType()
  {
  }

  public RoomType(Long id)
  {
    this.id = id;
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

  public Double getDefaultPrice() {
    return this.defaultPrice;
  }

  public void setDefaultPrice(Double defaultPrice) {
    this.defaultPrice = defaultPrice;
  }

  public Long getLogo() {
    return this.logo;
  }

  public void setLogo(Long logo) {
    this.logo = logo;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public Double getMondayPrice() {
    return this.mondayPrice;
  }

  public void setMondayPrice(Double mondayPrice) {
    this.mondayPrice = mondayPrice;
  }

  public Double getTuesdayPrice() {
    return this.tuesdayPrice;
  }

  public void setTuesdayPrice(Double tuesdayPrice) {
    this.tuesdayPrice = tuesdayPrice;
  }

  public Double getWednesdayPrice() {
    return this.wednesdayPrice;
  }

  public void setWednesdayPrice(Double wednesdayPrice) {
    this.wednesdayPrice = wednesdayPrice;
  }

  public Double getThursdayPrice() {
    return this.thursdayPrice;
  }

  public void setThursdayPrice(Double thursdayPrice) {
    this.thursdayPrice = thursdayPrice;
  }

  public Double getFridayPrice() {
    return this.fridayPrice;
  }

  public void setFridayPrice(Double fridayPrice) {
    this.fridayPrice = fridayPrice;
  }

  public Double getSaturdayPrice() {
    return this.saturdayPrice;
  }

  public void setSaturdayPrice(Double saturdayPrice) {
    this.saturdayPrice = saturdayPrice;
  }

  public Double getSundayPrice() {
    return this.sundayPrice;
  }

  public void setSundayPrice(Double sundayPrice) {
    this.sundayPrice = sundayPrice;
  }

  public Integer getRoomNumber() {
    return this.roomNumber;
  }

  public void setRoomNumber(Integer roomNumber) {
    this.roomNumber = roomNumber;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Integer getOrderNo() {
    return this.orderNo;
  }

  public void setOrderNo(Integer orderNo) {
    this.orderNo = orderNo;
  }

  public double readCurrentPrice()
  {
    return getPrice(new Date());
  }

  public double getPrice(Date date)
  {
    SpecialTimePriceService priceService = new SpecialTimePriceService();
    SpecialTimePrice price = priceService.getPrice(getId().longValue(), date);
    if (price != null) {
      return price.getPrice().floatValue();
    }

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    switch (c.get(7)) {
    case 2:
      return ((getMondayPrice() == null) || (getMondayPrice().doubleValue() == 0.0D) ? getDefaultPrice() : getMondayPrice()).doubleValue();
    case 3:
      return ((getTuesdayPrice() == null) || (getTuesdayPrice().doubleValue() == 0.0D) ? getDefaultPrice() : getTuesdayPrice()).doubleValue();
    case 4:
      return ((getWednesdayPrice() == null) || (getWednesdayPrice().doubleValue() == 0.0D) ? getDefaultPrice() : getWednesdayPrice()).doubleValue();
    case 5:
      return ((getThursdayPrice() == null) || (getThursdayPrice().doubleValue() == 0.0D) ? getDefaultPrice() : getThursdayPrice()).doubleValue();
    case 6:
      return ((getFridayPrice() == null) || (getFridayPrice().doubleValue() == 0.0D) ? getDefaultPrice() : getFridayPrice()).doubleValue();
    case 7:
      return ((getSaturdayPrice() == null) || (getSaturdayPrice().doubleValue() == 0.0D) ? getDefaultPrice() : getSaturdayPrice()).doubleValue();
    case 1:
      return ((getSundayPrice() == null) || (getSundayPrice().doubleValue() == 0.0D) ? getDefaultPrice() : getSundayPrice()).doubleValue();
    }
    return this.defaultPrice.doubleValue();
  }

  public boolean haveWeekendPrice()
  {
    return getMondayPrice().doubleValue() + getTuesdayPrice().doubleValue() + getWednesdayPrice().doubleValue() + 
      getThursdayPrice().doubleValue() + getFridayPrice().doubleValue() + getSaturdayPrice().doubleValue() + getSundayPrice().doubleValue() > 0.0D;
  }

  public List<Resource> getImages()
  {
    return getImages(5);
  }

  public List<Resource> getImages(int count)
  {
    return ResourceUtil.getResources(RoomType.class.getName(), getId()+"", count);
  }

  public Integer getAvailableNumber()
  {
    return this.availableNumber;
  }

  public void setAvailableNumber(Integer availableNumber) {
    this.availableNumber = availableNumber;
  }

  public String getBedType() {
    return this.bedType;
  }

  public void setBedType(String bedType) {
    this.bedType = bedType;
  }

  public static enum Status
  {
    EDIT("编辑中"), OPENING("营业中");

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