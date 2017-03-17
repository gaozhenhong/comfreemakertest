package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;
import java.io.Serializable;
import java.util.List;

public class ModuleQ extends PageUtil
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String name;
  private String demo;
  private String moduleType;
  private String code;
  private String parentId;

  public ModuleQ()
  {
    this.code = "";

    this.parentId = "";
  }

  public String toWhereString() {
    this.parameterList.clear();
    String sqlStr = super.getOtherCondition();
    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like ?";
      this.parameterList.add("%" + this.name.trim() + "%");
    }
    if ((this.demo != null) && (!(this.demo.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND demo like ?";
      this.parameterList.add("%" + this.demo.trim() + "%");
    }

    if ((this.code != null) && (!(this.code.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND code like ?";
      this.parameterList.add("%" + this.code.trim() + "%");
    }

    if ((this.moduleType != null) && (!(this.moduleType.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND moduleType = ?";
      this.parameterList.add(this.moduleType);
    }

    if ((this.parentId != null) && (!(this.parentId.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND parentId = ?";
      this.parameterList.add(this.parentId.trim());
    }

    return sqlStr;
  }

  public String getDemo() {
    return this.demo;
  }

  public void setDemo(String demo) {
    this.demo = demo;
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

  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getModuleType() {
    return this.moduleType;
  }

  public void setModuleType(String moduleType) {
    this.moduleType = moduleType;
  }
}