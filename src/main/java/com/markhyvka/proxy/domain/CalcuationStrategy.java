package com.markhyvka.proxy.domain;

import java.math.BigDecimal;

public interface CalcuationStrategy {
	
	BigDecimal calculate(Cart cart);
}
