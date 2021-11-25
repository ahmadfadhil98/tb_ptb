package com.example.aplikasiptb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthData {
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@SerializedName("updated_at")
	@Expose
	private String updatedAt;

	@SerializedName("created_at")
	@Expose
	private String createdAt;

	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("email")
	@Expose

	private String email;

	@SerializedName("username")
	@Expose
	private String username;

	@SerializedName("token")
	@Expose
	private String token;

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}

	public String getToken(){
		return token;
	}
}