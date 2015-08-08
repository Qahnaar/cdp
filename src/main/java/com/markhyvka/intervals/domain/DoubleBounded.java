package com.markhyvka.intervals.domain;

public interface DoubleBounded<T> {

	T getLowerBound();

	T getUpperBound();

	boolean isLowerBoundApplicable(T other);

	boolean isUpperBoundApplicable(T other);
}
