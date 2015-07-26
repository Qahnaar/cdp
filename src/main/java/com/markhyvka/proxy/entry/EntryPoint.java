package com.markhyvka.proxy.entry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.markhyvka.proxy.util.ProxyUtil;
import com.markhyvla.proxy.domain.CalcuationStrategy;
import com.markhyvla.proxy.domain.Cart;
import com.markhyvla.proxy.domain.DefaultCalculationStrategy;
import com.markhyvla.proxy.domain.Product;

public class EntryPoint {
	private static final String USERNAME = "username";
	private static final String SPRITE = "sprite";
	private static final String BREAD = "bread";
	private static final BigDecimal PRIZE = new BigDecimal("1.25");

	public static void main(String[] args) {
		CalcuationStrategy calcStrategy = new DefaultCalculationStrategy();
		CalcuationStrategy calcStrategyProxy = (CalcuationStrategy) ProxyUtil
				.newInstance(calcStrategy,
						new Class[] { CalcuationStrategy.class });

		Cart cart = populateCart();
		System.out.println(calcStrategyProxy.calculate(cart).toPlainString());
	}

	private static Cart populateCart() {
		Cart cart = new Cart();

		List<Product> products = new ArrayList<Product>();
		products.add(createProduct(SPRITE, PRIZE));
		products.add(createProduct(BREAD, PRIZE.divide(BigDecimal.TEN)));

		cart.setUserName(USERNAME);
		cart.setProducts(products);

		return cart;
	}

	private static Product createProduct(String name, BigDecimal prize) {
		Product product = new Product();
		product.setName(name);
		product.setPrize(prize);
		return product;
	}
}
