package com.markhyvka.proxy.domain;

import java.math.BigDecimal;

public class DefaultCalculationStrategy implements CalcuationStrategy {

	public BigDecimal calculate(Cart cart) {
		BigDecimal result = BigDecimal.ZERO;

		if (cart != null && !cart.getProducts().isEmpty()) {
			for (Product product : cart.getProducts()) {
				result = result.add(product.getPrize());
			}
		}

		return result;
	}
}
