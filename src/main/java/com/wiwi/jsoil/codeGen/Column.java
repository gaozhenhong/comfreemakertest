package com.wiwi.jsoil.codeGen;

public class Column
{
  private String id;
  private String name;
  private String type;
  private boolean isNotNull;
  private boolean isTableHead;
  private boolean isQueryColumn;
  private boolean isInput;

  public Column()
  {
    this.id = "";
    this.name = "";
    this.type = "";
    this.isNotNull = false;

    this.isTableHead = true;

    this.isQueryColumn = true;
    this.isInput = true;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isNotNull()
  {
    return this.isNotNull;
  }

  public void setNotNull(boolean isNotNull) {
    this.isNotNull = isNotNull;
  }

  public boolean isTableHead() {
    return this.isTableHead;
  }

  public void setTableHead(boolean isTableHead) {
    this.isTableHead = isTableHead;
  }

  public boolean isQueryColumn() {
    return this.isQueryColumn;
  }

  public void setQueryColumn(boolean isQueryColumn) {
    this.isQueryColumn = isQueryColumn;
  }

  public boolean isInput() {
    return this.isInput;
  }

  public void setInput(boolean isInput) {
    this.isInput = isInput;
  }
}