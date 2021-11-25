package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Auth{

	public void setData(AuthData authData) {
		this.authData = authData;
	}

	@SerializedName("data")
	@Expose
	private AuthData authData;

	public AuthData getData(){
		return authData;
	}
}