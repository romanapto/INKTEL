package com.app.dto.managementuser;

public class UserDomainItemDTO {

	private String id;
	private String name;

	public UserDomainItemDTO() {

	}

	public UserDomainItemDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
