package com.markhyvla.proxy.domain;

import java.util.List;

public class Cart {

	private List<Product> products;

	private String userName;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
