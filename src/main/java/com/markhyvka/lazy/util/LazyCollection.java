package com.markhyvka.lazy.util;

import java.util.List;

public interface LazyCollection<T> extends List<T> {

	List<T> getFirstAmount(int amount);

	List<T> getNextAmount(int amount);

	List<T> getPreviousAmount(int amount);

	List<T> getAmountWithOffset(int offset, int amount);

	List<T> getLastAmount(int amount);
}
