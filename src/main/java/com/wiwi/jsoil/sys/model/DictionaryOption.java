package com.wiwi.jsoil.sys.model;

public class DictionaryOption
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
  
  public DictionaryOption() {}
  
  public DictionaryOption(String name)
  {
    this.name = name;
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
  
  public Integer getOrderNo()
  {
    return this.orderNo;
  }
  
  public void setOrderNo(Integer orderNo)
  {
    this.orderNo = orderNo;
  }
  
  public String getDemo()
  {
    return this.demo;
  }
  
  public void setDemo(String demo)
  {
    this.demo = demo;
  }
  
  public Long getLogo()
  {
    return this.logo;
  }
  
  public void setLogo(Long logo)
  {
    this.logo = logo;
  }
  
  public String getColor()
  {
    return this.color;
  }
  
  public void setColor(String color)
  {
    this.color = color;
  }
  
  public Long getDictionaryId()
  {
    return this.dictionaryId;
  }
  
  public void setDictionaryId(Long dictionaryId)
  {
    this.dictionaryId = dictionaryId;
  }
  
  public String getDictionaryCode()
  {
    return this.dictionaryCode;
  }
  
  public void setDictionaryCode(String dictionaryCode)
  {
    this.dictionaryCode = dictionaryCode;
  }
}
