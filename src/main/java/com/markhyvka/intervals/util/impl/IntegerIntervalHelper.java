package com.markhyvka.intervals.util.impl;

import java.util.Collection;

import com.markhyvka.intervals.domain.impl.Bound;
import com.markhyvka.intervals.domain.impl.Bound.BoundType;
import com.markhyvka.intervals.domain.impl.Interval;
import com.markhyvka.intervals.util.IntervalHelper;

public class IntegerIntervalHelper implements IntervalHelper<Integer> {

	@Override
	public Interval<Integer> createInterval(BoundType lowerBoundType,
			Integer lowerBoundValue, BoundType upperBoundType,
			Integer upperBoundValue) {
		Interval<Integer> interval = new Interval<>();

		Bound<Integer> lowerBound = new Bound<>();
		lowerBound.setBoundType(lowerBoundType);
		lowerBound.setBoundValue(lowerBoundValue);

		Bound<Integer> upperBound = new Bound<>();
		upperBound.setBoundType(upperBoundType);
		upperBound.setBoundValue(upperBoundValue);

		interval.setLowerBound(lowerBound);
		interval.setUpperBound(upperBound);
		return interval;
	}

	@Override
	public void populateCollection(Collection<Interval<Integer>> collection) {
		collection.add(createInterval(BoundType.EXCLUSIVE, Integer.valueOf(1),
				BoundType.EXCLUSIVE, Integer.valueOf(3)));
		collection.add(createInterval(BoundType.EXCLUSIVE, Integer.valueOf(4),
				BoundType.EXCLUSIVE, Integer.valueOf(5)));
		collection.add(createInterval(BoundType.EXCLUSIVE, Integer.valueOf(6),
				BoundType.EXCLUSIVE, Integer.valueOf(10)));
		collection.add(createInterval(BoundType.EXCLUSIVE, Integer.valueOf(15),
				BoundType.INCLUSIVE, Integer.valueOf(20)));
	}
}
