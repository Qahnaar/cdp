package com.markhyvka.proxy.domain;

import java.math.BigDecimal;

public class Product {

	private BigDecimal prize;

	private String name;

	public BigDecimal getPrize() {
		return prize;
	}

	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
