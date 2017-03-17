package com.wiwi.jsoil.sys.model;

public class Dictionary
{
  private Long id;
  private String name;
  private String demo;
  private String code;

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

  public String getDemo() {
    return this.demo;
  }

  public void setDemo(String demo) {
    this.demo = demo;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}