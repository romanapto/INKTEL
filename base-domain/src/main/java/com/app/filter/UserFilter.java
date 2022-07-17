package com.app.filter;

import java.util.List;

public class UserFilter {

	private String text;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> accountNames;
	private List<String> roles;

	public UserFilter() {

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public List<String> getAccountNames() {
		return accountNames;
	}

	public void setAccountNames(List<String> accountNames) {
		this.accountNames = accountNames;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
