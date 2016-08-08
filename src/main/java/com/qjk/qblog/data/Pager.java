package com.qjk.qblog.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pager<T> {

	/**
	 * 属性筛选条件
	 */
	private Map<String, Object> fieldParams = new HashMap<String, Object>();
	
	/**
	 * sql筛选
	 */
	private Map<String, Object> sqlParams = new HashMap<String, Object>();
	
	/**
	 * 排序
	 */
	private String order;
	
	

	/**
	 * 是否统计
	 */
	private boolean counter = false;

	/**
	 * 当前页码
	 */
	private int pageIndex;

	/**
	 * 每页数量
	 */
	private int pageSize;

	/**
	 * 总数
	 */
	private int totalRows;

	/**
	 * 总页数
	 */
	private int totalPages;

	/**
	 * 结果集
	 */
	private List<T> list;
	
	

	public Map<String, Object> getFieldParams() {
		return fieldParams;
	}

	public Pager<T> setFieldParams(Map<String, Object> fieldParams) {
		this.fieldParams = fieldParams;
		return this;
	}

	public Map<String, Object> getSqlParams() {
		return sqlParams;
	}

	public Pager<T> setSqlParams(Map<String, Object> sqlParams) {
		this.sqlParams = sqlParams;
		return this;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public Pager<T> setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex == 0 ? 1 : pageIndex;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Pager<T> setPageSize(int pageSize) {
		this.pageSize = pageSize == 0 ? 20 : pageSize;
		return this;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public Pager<T> setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		return this;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getList() {
		return list;
	}

	public Pager<T> setList(List<T> list) {
		this.list = list;
		return this;
	}

	public boolean isCounter() {
		return counter;
	}

	public void setCounter(boolean counter) {
		this.counter = counter;
	}

	public Pager<T> closeCounter() {
		this.counter = false;
		return this;
	}

	public Pager<T> openCounter() {
		this.counter = true;
		this.pageIndex = 1;
		this.pageSize = 20;
		return this;
	}

	public Pager<T> openCounter(int pageIndex, int pageSize) {
		this.counter = true;
		this.pageIndex = pageIndex == 0 ? 1 : pageIndex;
		this.pageSize = pageSize == 0 ? 20 : pageSize;
		return this;
	}

	public Pager<T> openCounter(int pageIndex) {
		this.counter = true;
		this.pageIndex = pageIndex == 0 ? 1 : pageIndex;
		this.pageSize = 20;
		return this;
	}

	public Pager<T> calculate() {
		if (!this.counter) {
			return this;
		} else {
			this.totalPages = this.totalRows % this.pageSize == 0 ? this.totalRows
					/ this.pageSize
					: this.totalRows / this.pageSize + 1;
		}
		return this;
	}

	public int getFistRowNum() {

		return (this.pageIndex - 1) * this.pageSize;
	}

	public String getOrder() {
		return order;
	}

	public Pager<T> setOrder(String order) {
		this.order = order;
		return this;
	}
	
	

}
