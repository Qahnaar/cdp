package com.markhyvka.intervals.util;

import java.util.Collection;

import com.markhyvka.intervals.domain.impl.Interval;
import com.markhyvka.intervals.domain.impl.Bound.BoundType;

public interface IntervalHelper<T extends Number> {
	Interval<T> createInterval(BoundType lowerBoundType, T lowerBoundValue,
			BoundType upperBoundType, T upperBoundValue);

	void populateCollection(Collection<Interval<T>> collection);
}
