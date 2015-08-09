package com.markhyvka.lazy.util;

import java.util.List;

public interface DataSource<T> {
	List<T> getNextAmount(int amount);

	List<T> getPreviousAmount(int amount);

	List<T> getAmountWithOffset(int offset, int amount);

	int getDataSourceSize();
}
