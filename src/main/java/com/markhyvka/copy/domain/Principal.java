package com.markhyvka.copy.domain;

import java.util.Calendar;

public class Principal {

	private String username;

	private String password;

	public boolean isActive;

	private Calendar lastUsed;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Calendar lastUsed) {
		this.lastUsed = lastUsed;
	}
}
