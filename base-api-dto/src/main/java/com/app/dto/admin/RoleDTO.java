package com.app.dto.admin;

import javax.validation.constraints.NotEmpty;

public class RoleDTO {

	private String id;
	//TODO revisar que mensaje de error poner por default
	@NotEmpty
	private String roleName;
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
