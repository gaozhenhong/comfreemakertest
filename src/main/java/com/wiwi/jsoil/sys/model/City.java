package com.wiwi.jsoil.sys.model;

public class City
{
  private Long id;
  private String abbreviation;
  private String code;
  private String name;
  private String demo;
  private Integer parentId;
  private Float latitude;
  private Float longitude;

  public String getCode()
  {
    return this.code; }

  public void setCode(String code) {
    this.code = code; }

  public String getDemo() {
    return this.demo; }

  public void setDemo(String demo) {
    this.demo = demo; }

  public Long getId() {
    return this.id; }

  public void setId(Long id) {
    this.id = id; }

  public String getName() {
    return this.name; }

  public void setName(String name) {
    this.name = name; }

  public Integer getParentId() {
    return this.parentId; }

  public void setParentId(Integer parentId) {
    this.parentId = parentId; }

  public String getAbbreviation() {
    return this.abbreviation; }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation; }

  public Float getLatitude() {
    return this.latitude; }

  public void setLatitude(Float latitude) {
    this.latitude = latitude; }

  public Float getLongitude() {
    return this.longitude; }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }
}