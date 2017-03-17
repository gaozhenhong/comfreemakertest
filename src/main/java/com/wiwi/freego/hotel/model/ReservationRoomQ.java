package com.wiwi.freego.hotel.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.wiwi.edb.order.model.Order;
import com.wiwi.jsoil.db.PageUtil;

public class ReservationRoomQ extends PageUtil
{
  private Long id;
  private String roomNo;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date consumeDate;
  private Double price;
  private String orderCode;
  private Long orderId;
  private Long hotelId;
  private Order.Status status;
  private List<Order.Status> multiStatus;
  private List<Order.Status> notStatus;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date beginConsumeDate;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date endConsumeDate;

  public ReservationRoomQ()
  {
    this.multiStatus = new ArrayList();
    this.notStatus = new ArrayList();
  }

  public String toWhereString()
  {
    String inSql;
    Order.Status status;
    Iterator localIterator;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if ((this.roomNo != null) && (!(this.roomNo.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND roomNo like ?";
      this.parameterList.add('%' + this.roomNo + '%');
    }

    if ((this.beginConsumeDate != null) && (this.endConsumeDate != null)) {
      sqlStr = sqlStr + " AND date(consumeDate) between '" + sdf.format(this.beginConsumeDate) + "' and '" + sdf.format(this.endConsumeDate) + "' ";
    }

    if (this.consumeDate != null) {
      sqlStr = sqlStr + " AND consumeDate =?";
      this.parameterList.add(this.consumeDate);
    }

    if (this.price != null) {
      sqlStr = sqlStr + " AND price =?";
      this.parameterList.add(this.price);
    }

    if ((this.orderCode != null) && (!(this.orderCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND orderCode like ?";
      this.parameterList.add('%' + this.orderCode + '%');
    }

    if ((this.orderId != null) && (this.orderId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND orderId =?";
      this.parameterList.add(this.orderId);
    }

    if ((this.hotelId != null) && (this.hotelId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND hotelId =?";
      this.parameterList.add(this.hotelId);
    }

    if (this.status != null) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    if ((this.multiStatus != null) && (this.multiStatus.size() > 0)) {
      inSql = "";
      for (localIterator = this.multiStatus.iterator(); localIterator.hasNext(); ) { status = (Order.Status)localIterator.next();
        inSql = inSql + ",?";
        this.parameterList.add(status);
      }
      inSql = inSql.substring(1);
      sqlStr = sqlStr + " AND status  in (" + inSql + ")";
    }

    if ((this.notStatus != null) && (this.notStatus.size() > 0)) {
      inSql = "";
      for (localIterator = this.notStatus.iterator(); localIterator.hasNext(); ) { status = (Order.Status)localIterator.next();
        inSql = inSql + ",?";
        this.parameterList.add(status);
      }
      inSql = inSql.substring(1);
      sqlStr = sqlStr + " AND status not in (" + inSql + ")";
    }

    return sqlStr;
  }

  public Long getId() {
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

  public Date getConsumeDate() {
    return this.consumeDate;
  }

  public void setConsumeDate(Date consumeDate) {
    this.consumeDate = consumeDate;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getOrderCode() {
    return this.orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Long getOrderId() {
    return this.orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getHotelId() {
    return this.hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public Date getBeginConsumeDate() {
    return this.beginConsumeDate;
  }

  public void setBeginConsumeDate(Date beginConsumeDate) {
    this.beginConsumeDate = beginConsumeDate;
  }

  public Date getEndConsumeDate() {
    return this.endConsumeDate;
  }

  public void setEndConsumeDate(Date endConsumeDate) {
    this.endConsumeDate = endConsumeDate;
  }

  public Order.Status getStatus() {
    return this.status;
  }

  public void setStatus(Order.Status status) {
    this.status = status;
  }

  public List<Order.Status> getMultiStatus() {
    return this.multiStatus;
  }

  public void setMultiStatus(List<Order.Status> multiStatus) {
    this.multiStatus = multiStatus;
  }

  public List<Order.Status> getNotStatus() {
    return this.notStatus;
  }

  public void setNotStatus(List<Order.Status> notStatus) {
    this.notStatus = notStatus;
  }
}