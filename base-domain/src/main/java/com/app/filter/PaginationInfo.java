package com.app.filter;

public class PaginationInfo {

	private Integer from;
	private Integer size;
	private String sortBy;
	private String sortOrder;

	public PaginationInfo() {

	}

	public PaginationInfo(Integer from, Integer size) {
		this(from, size, "id", "asc");
	}

	public PaginationInfo(Integer from, Integer size, String sortBy, String sortOrder) {
		this.from = from;
		this.size = size;
		this.sortBy = sortBy;
		this.sortOrder = sortOrder;
	}

	public static PaginationInfo of(Integer from, Integer size, String sortBy, String sortOrder) {
		return new PaginationInfo(from, size, sortBy, sortOrder);
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
