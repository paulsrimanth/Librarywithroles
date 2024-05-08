package com.library.model;

public class AuthenticationResponse {

	public AuthenticationResponse(String token, String roles) {
		super();
		this.token = token;
		this.roles = roles;
	}

	private String token;
	
	private String roles;

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;

	}

	public String getRoles() {
		return roles;
	}


	
}
