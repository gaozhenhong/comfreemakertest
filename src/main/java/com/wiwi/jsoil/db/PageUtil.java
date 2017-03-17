package com.wiwi.jsoil.db;

import java.util.ArrayList;
import java.util.List;


public abstract class PageUtil {
	
	private int recordPerPage = 14; // 每页显示的条数。

	private int totalRecord = 0; // 总记录条数。

	private int totalPage = 1; // 总页数。

	private int currentPage = 1; // 当前第几页。
	
	private String orderByProperty="id";

	private String orderByKind="desc";
	
	private String orderByProperty1=null;

	private String orderByKind1=null;
	
	private String otherCondition = " where 1=1 "; // 其他查询条件。

	private String parentObjectName = "query"; // 此页面对象的父对象名称。用于，构造客户端翻页按钮名称名称。

	
	protected List<Object> parameterList  = new ArrayList<Object>();
	 
	public PageUtil() {
		
	}

	public abstract String toWhereString();
	
	public void render(int totalRecord) {
		this.totalRecord=totalRecord;
		render();
	}

	public void render() {
		if(this.recordPerPage==0)
			this.recordPerPage=this.totalRecord;
		this.totalPage=(this.totalRecord + this.recordPerPage - 1)
					/ this.recordPerPage;
		if(currentPage>totalPage)
		    currentPage=totalPage;
		if(currentPage==0)
		    currentPage=1;
	}


	public int getCurrentPage() {
		return currentPage;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
		if(this.recordPerPage<0)
			this.recordPerPage=0;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getParentObjectName() {
		return parentObjectName;
	}

	public void setParentObjectName(String parentObjectName) {
		this.parentObjectName = parentObjectName;
	}

	/**
	 * @return Returns the orderByKind.
	 */
	public String getOrderByKind() {
		return orderByKind;
	}

	/**
	 * @param orderByKind The orderByKind to set.
	 */
	public void setOrderByKind(String orderByKind) {
		this.orderByKind = orderByKind;
	}

	/**
	 * @return Returns the orderByProperty.
	 */
	public String getOrderByProperty() {
		return orderByProperty;
	}

	/**
	 * @param orderByProperty The orderByProperty to set.
	 */
	public void setOrderByProperty(String orderByProperty) {
		this.orderByProperty = orderByProperty;
	}

	public String getOtherCondition() {
		return otherCondition;
	}

	public void setOtherCondition(String otherCondition) {
		this.otherCondition = otherCondition;
	}

	public String getOrderByProperty1() {
		return orderByProperty1;
	}

	public void setOrderByProperty1(String orderByProperty1) {
		this.orderByProperty1 = orderByProperty1;
	}

	public String getOrderByKind1() {
		return orderByKind1;
	}

	public void setOrderByKind1(String orderByKind1) {
		this.orderByKind1 = orderByKind1;
	}

	public List<Object> getParameterList(){
		return parameterList;
	} 

}
