package com.wiwi.jsoil.codeGen;

import java.util.ArrayList;
import java.util.List;

public class Function
{
  private String name;
  private String packageName;
  private String className;
  private String tableName;
  private String requestBasePath;
  private int genViewPage;
  private List<Column> columnList;

  public Function()
  {
    this.name = "";
    this.packageName = "";

    this.className = "";

    this.tableName = "";

    this.requestBasePath = "";

    this.genViewPage = 1;
    this.columnList = new ArrayList(); }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPackageName() {
    return this.packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<Column> getColumnList() {
    return this.columnList;
  }

  public void setColumnList(List<Column> columnList) {
    this.columnList = columnList;
  }

  public String getRequestBasePath() {
    return this.requestBasePath;
  }

  public void setRequestBasePath(String requestBasePath) {
    this.requestBasePath = requestBasePath;
  }

  public int getGenViewPage() {
    return this.genViewPage;
  }

  public void setGenViewPage(int genViewPage) {
    this.genViewPage = genViewPage;
  }
}