package com.wiwi.jsoil.sys.model;

import com.wiwi.jsoil.db.PageUtil;

public class CityQ extends PageUtil
{
  private String ids;
  private String name;
  private String parentIds;
  private String notInIds;
  private String code;

  public String toWhereString()
  {
    String sqlStr = super.getOtherCondition();

    if ((this.ids != null) && (!(this.ids.equalsIgnoreCase(""))) && (!(this.ids.equalsIgnoreCase("null"))))
      if (this.ids.indexOf(",") == -1)
        sqlStr = sqlStr + " AND id = '" + this.ids + "'";
      else {
        if (this.ids.indexOf(",") != -1)
          this.ids = this.ids.replaceAll(",", "','");

        if (!(this.ids.startsWith("'")))
          this.ids = "'" + this.ids;

        if (!(this.ids.endsWith("'")))
          this.ids = this.ids + "'";

        sqlStr = sqlStr + " AND id in (" + this.ids + ")";
      }


    if ((this.name != null) && (!(this.name.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND name like '%" + this.name + "%'";
    }

    if ((this.parentIds != null) && (!(this.parentIds.equalsIgnoreCase(""))))
      if (this.parentIds.indexOf(",") == -1)
        sqlStr = sqlStr + " AND parentId = '" + this.parentIds + "'";
      else {
        if (this.parentIds.indexOf(",") != -1)
          this.parentIds = this.parentIds.replaceAll(",", "','");

        if (!(this.parentIds.startsWith("'")))
          this.parentIds = "'" + this.parentIds;

        if (!(this.parentIds.endsWith("'")))
          this.parentIds = this.parentIds + "'";

        sqlStr = sqlStr + " AND parentId in (" + this.parentIds + ")";
      }


    if ((this.notInIds != null) && (!(this.notInIds.equalsIgnoreCase(""))))
      if (this.notInIds.indexOf(",") == -1)
        sqlStr = sqlStr + " AND id <> '" + this.notInIds + "'";
      else {
        if (this.notInIds.indexOf(",") != -1)
          this.notInIds = this.notInIds.replaceAll(",", "','");

        if (!(this.notInIds.startsWith("'")))
          this.notInIds = "'" + this.notInIds;

        if (!(this.notInIds.endsWith("'")))
          this.notInIds = this.notInIds + "'";

        sqlStr = sqlStr + " AND id not in (" + this.notInIds + ")";
      }


    if ((this.code != null) && (!(this.code.equalsIgnoreCase(""))))
      sqlStr = sqlStr + " AND code like '" + this.code + "'";

    return sqlStr;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParentIds() {
    return this.parentIds;
  }

  public void setParentIds(String parentIds) {
    this.parentIds = parentIds;
  }

  public String getIds() {
    return this.ids;
  }

  public void setIds(String ids) {
    this.ids = ids;
  }

  public String getNotInIds() {
    return this.notInIds;
  }

  public void setNotInIds(String notInIds) {
    this.notInIds = notInIds;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}