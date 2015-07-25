package com.markhyvka.domain;

import java.util.List;

public class User extends Principal {

	private long userId;

	protected List<Long> favouriteUsers;

	public byte[] icon;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Long> getFavouriteUsers() {
		return favouriteUsers;
	}

	public void setFavouriteUsers(List<Long> favouriteUsers) {
		this.favouriteUsers = favouriteUsers;
	}
}
