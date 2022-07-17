package com.app.dto.admin;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

public class UserDTO {

	private String id;
	@NotEmpty
	@Size(max = 20, message = "error.firstname.size.max")
	private String firstName;
	@NotEmpty
	@Size(max = 20, message = "error.lastname.size.max")
	private String lastName;
	@NotEmpty
	@Size(max = 100, message = "error.email.size.max")
	private String email;
	@Valid
	private List<RoleDTO> roles;
	//@Valid
	//private List<UserSellerDTO> organizations;
        @NotNull
        private Boolean sendEmail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	/*public List<UserSellerDTO> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<UserSellerDTO> organizations) {
		this.organizations = organizations;
	}*/

        public Boolean getSendEmail() {
            return sendEmail;
        }

        public void setSendEmail(Boolean sendEmail) {
            this.sendEmail = sendEmail;
        }
}
