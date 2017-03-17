package com.wiwi.jsoil.sys.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Organization{

    private Integer id;

    private String code;

    private String name;

    private Category type = null;

    private Organization parentOrg = null;

    private Integer cityId;

    private String linkMan;

    private String linkTel;

    private String mobilePhone;

    private String address;

    private String email;

    private String url;

    private String demo;

    private String inputUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private Date inputTime;

    private String lastModifiedUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private Date lastModifiedTime;

    private Integer status;

    public Integer getId() {
        return this.id;
    }

    public void setId (Integer id) {
        this.id=id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode (String code) {
        this.code=code;
    }

    public String getName() {
        return this.name;
    }

    public void setName (String name) {
        this.name=name;
    }

    public Category getType() {
        return this.type;
    }

    public void setType (Category type) {
        this.type=type;
    }


    public Organization getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(Organization parentOrg) {
		this.parentOrg = parentOrg;
	}

	public Integer getCityId() {
        return this.cityId;
    }

    public void setCityId (Integer cityId) {
        this.cityId=cityId;
    }

    public String getLinkMan() {
        return this.linkMan;
    }

    public void setLinkMan (String linkMan) {
        this.linkMan=linkMan;
    }

    public String getLinkTel() {
        return this.linkTel;
    }

    public void setLinkTel (String linkTel) {
        this.linkTel=linkTel;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone (String mobilePhone) {
        this.mobilePhone=mobilePhone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress (String address) {
        this.address=address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail (String email) {
        this.email=email;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl (String url) {
        this.url=url;
    }

    public String getDemo() {
        return this.demo;
    }

    public void setDemo (String demo) {
        this.demo=demo;
    }

    public String getInputUserId() {
        return this.inputUserId;
    }

    public void setInputUserId (String inputUserId) {
        this.inputUserId=inputUserId;
    }

    public Date getInputTime() {
        return this.inputTime;
    }

    public void setInputTime (Date inputTime) {
        this.inputTime=inputTime;
    }

    public String getLastModifiedUserId() {
        return this.lastModifiedUserId;
    }

    public void setLastModifiedUserId (String lastModifiedUserId) {
        this.lastModifiedUserId=lastModifiedUserId;
    }

    public Date getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    public void setLastModifiedTime (Date lastModifiedTime) {
        this.lastModifiedTime=lastModifiedTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus (Integer status) {
        this.status=status;
    }

}