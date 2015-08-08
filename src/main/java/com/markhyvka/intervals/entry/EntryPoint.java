package com.markhyvka.intervals.entry;

import com.markhyvka.intervals.domain.MergableCollection;
import com.markhyvka.intervals.domain.impl.Bound.BoundType;
import com.markhyvka.intervals.domain.impl.Interval;
import com.markhyvka.intervals.util.IntervalHelper;
import com.markhyvka.intervals.util.impl.IntegerIntervalHelper;
import com.markhyvka.intervals.util.impl.IntervalSet;

public class EntryPoint {

	// TODO: Use SLF4j here
	public static void main(String[] args) {
		IntervalHelper<Integer> intervalHelper = new IntegerIntervalHelper();
		MergableCollection<Interval<Integer>> collection = new IntervalSet<>();

		intervalHelper.populateCollection(collection);
		System.out.println(collection);

		collection.merge(intervalHelper.createInterval(BoundType.INCLUSIVE,
				Integer.valueOf(2), BoundType.EXCLUSIVE, Integer.valueOf(7)));
		System.out.println(collection);
	}
}
