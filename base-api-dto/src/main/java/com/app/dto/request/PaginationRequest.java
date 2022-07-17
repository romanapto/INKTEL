package com.app.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class PaginationRequest {

	@Min(value = 0, message = "error.pagination.page.min")
	private Integer from = 0;
	@Min(value = 0, message = "error.pagination.size.min")
	@Max(value = 100, message = "error.pagination.size.max")
	private Integer size = 50;
	private String sortBy = "id";
	private String sortOrder = "asc";

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
