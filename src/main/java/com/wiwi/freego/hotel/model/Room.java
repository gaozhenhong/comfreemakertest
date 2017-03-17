package com.wiwi.freego.hotel.model;

public class Room
{
  private Long id;
  private String roomNo;
  private RoomType roomType;
  private Long hotelId;
  private Status status;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRoomNo() {
    return this.roomNo;
  }

  public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
  }

  public Status getStatus()
  {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Long getHotelId()
  {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public RoomType getRoomType() {
    return this.roomType;
  }

  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }

  public static enum Status
  {
    VACANT(
      "空房"),  USING("使用中"), SHUTOUT("不可用");

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