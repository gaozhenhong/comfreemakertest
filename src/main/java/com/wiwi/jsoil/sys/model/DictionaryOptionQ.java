package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.util.List;

public class DictionaryOptionQ extends PageUtil
{
  private Long id;
  private String name;
  private String code;
  private Integer orderNo;
  private String demo;
  private Long logo;
  private String color;
  private Long dictionaryId;
  private String dictionaryCode;

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

    if ((this.dictionaryCode != null) && (!(this.dictionaryCode.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND dictionaryCode = ? ";
      this.parameterList.add(this.dictionaryCode);
    }

    if ((this.code != null) && (!(this.code.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND code like ?";
      this.parameterList.add('%' + this.code + '%');
    }

    if ((this.orderNo != null) && (this.orderNo.intValue() != 0)) {
      sqlStr = sqlStr + " AND orderNo =?";
      this.parameterList.add(this.orderNo);
    }

    if ((this.demo != null) && (!(this.demo.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND demo like ?";
      this.parameterList.add('%' + this.demo + '%');
    }

    if ((this.logo != null) && (this.logo.longValue() != 0L)) {
      sqlStr = sqlStr + " AND logo =?";
      this.parameterList.add(this.logo);
    }

    if ((this.color != null) && (!(this.color.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND color like ?";
      this.parameterList.add('%' + this.color + '%');
    }

    if ((this.dictionaryId != null) && (this.dictionaryId.longValue() != 0L)) {
      sqlStr = sqlStr + " AND dictionaryId =?";
      this.parameterList.add(this.dictionaryId);
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

  public Integer getOrderNo() {
    return this.orderNo;
  }

  public void setOrderNo(Integer orderNo) {
    this.orderNo = orderNo;
  }

  public String getDemo() {
    return this.demo;
  }

  public void setDemo(String demo) {
    this.demo = demo;
  }

  public Long getLogo() {
    return this.logo;
  }

  public void setLogo(Long logo) {
    this.logo = logo;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Long getDictionaryId() {
    return this.dictionaryId;
  }

  public void setDictionaryId(Long dictionaryId) {
    this.dictionaryId = dictionaryId;
  }

  public String getDictionaryCode() {
    return this.dictionaryCode;
  }

  public void setDictionaryCode(String dictionaryCode) {
    this.dictionaryCode = dictionaryCode;
  }
}