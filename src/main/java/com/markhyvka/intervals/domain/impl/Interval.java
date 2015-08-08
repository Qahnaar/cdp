package com.markhyvka.intervals.domain.impl;

import com.markhyvka.intervals.domain.DoubleBounded;
import com.markhyvka.intervals.domain.impl.Bound.BoundType;

public class Interval<T extends Number> implements DoubleBounded<Bound<T>>,
		Comparable<Interval<T>> {

	private Bound<T> lowerBound;

	private Bound<T> upperBound;

	public Bound<T> getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Bound<T> leftBound) {
		this.lowerBound = leftBound;
	}

	public Bound<T> getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Bound<T> rightBound) {
		this.upperBound = rightBound;
	}

	@Override
	public int compareTo(Interval<T> o) {
		double thisValue = this.getLowerBound().getBoundValue().doubleValue();
		double thatValue = o.getLowerBound().getBoundValue().doubleValue();
		return thisValue < thatValue ? -1 : (thisValue > thatValue) ? 1 : 0;
	}

	@Override
	public boolean isLowerBoundApplicable(Bound<T> other) {
		if (BoundType.EXCLUSIVE.equals(other.getBoundType())
				&& BoundType.EXCLUSIVE.equals(upperBound.getBoundType())) {
			return other.getBoundValue().doubleValue() < upperBound
					.getBoundValue().doubleValue();
		}

		return other.getBoundValue().doubleValue() <= upperBound
				.getBoundValue().doubleValue();
	}

	@Override
	public boolean isUpperBoundApplicable(Bound<T> other) {
		if (BoundType.EXCLUSIVE.equals(other.getBoundType())
				&& BoundType.EXCLUSIVE.equals(lowerBound.getBoundType())) {
			return other.getBoundValue().doubleValue() > lowerBound
					.getBoundValue().doubleValue();
		}

		return other.getBoundValue().doubleValue() >= lowerBound
				.getBoundValue().doubleValue();
	}
}
