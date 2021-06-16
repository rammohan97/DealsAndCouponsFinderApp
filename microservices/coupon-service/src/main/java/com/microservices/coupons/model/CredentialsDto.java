package com.microservices.coupons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CredentialsDto {

    private String email;
    private String password;
    private String role;

    
    public CredentialsDto(String email, String password, String role) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public CredentialsDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
}