package com.markhyvka.intervals.domain.impl;

public class Bound<T> {

	private BoundType boundType;

	private T boundValue;

	public BoundType getBoundType() {
		return boundType;
	}

	public void setBoundType(BoundType boundType) {
		this.boundType = boundType;
	}

	public T getBoundValue() {
		return boundValue;
	}

	public void setBoundValue(T boundValue) {
		this.boundValue = boundValue;
	}

	public enum BoundType {
		INCLUSIVE, EXCLUSIVE;
	}
}
