package com.app.persistence.model.es.user;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "sc-user", type = "sc-user")
public class User {

	@Id
	@Field(type = FieldType.Keyword)
	private String id;
	@Field(type = FieldType.Text, index = false)
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	@Field(type = FieldType.Nested)
	private List<Role> roles;
        @Field(type = FieldType.Boolean)
	private Boolean sendEmail;
        
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

        public Boolean getSendEmail() {
            return sendEmail;
        }

        public void setSendEmail(Boolean sendEmail) {
            this.sendEmail = sendEmail;
        }
}
