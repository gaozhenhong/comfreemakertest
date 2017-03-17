package com.wiwi.jsoil.sys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String code;
  private String forwardUrl;
  private String demo;
  private String icon;
  private String moduleType;
  private Long parentId;
  private List<Module> subModuleList = new ArrayList();

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDemo() {
    return this.demo;
  }

  public void setDemo(String demo) {
    this.demo = demo;
  }

  public String getForwardUrl() {
    return this.forwardUrl;
  }

  public void setForwardUrl(String forwardUrl) {
    this.forwardUrl = forwardUrl;
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

  public long getParentId() {
    return this.parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }

  public String getIcon()
  {
    return this.icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public List<Module> getSubModule() {
    return this.subModuleList;
  }

  public List<Module> getSubModuleList() {
    return this.subModuleList;
  }

  public void setSubModuleList(List<Module> subModuleList) {
    this.subModuleList = subModuleList;
  }

  public String getModuleType() {
    return this.moduleType;
  }

  public void setModuleType(String moduleType) {
    this.moduleType = moduleType;
  }
}