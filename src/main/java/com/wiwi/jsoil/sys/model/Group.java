package com.wiwi.jsoil.sys.model;

public class Group
{
  private Integer id;
  private String name;
  private String demo;
  private String welcomeUrl;

  public Integer getId()
  {
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

  public String getDemo() {
    return this.demo;
  }

  public void setDemo(String demo) {
    this.demo = demo;
  }

  public String getWelcomeUrl() {
    return this.welcomeUrl;
  }

  public void setWelcomeUrl(String welcomeUrl) {
    this.welcomeUrl = welcomeUrl;
  }
}