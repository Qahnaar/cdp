package com.markhyvka.intervals.entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.markhyvka.intervals.domain.MergableCollection;
import com.markhyvka.intervals.domain.impl.Bound.BoundType;
import com.markhyvka.intervals.domain.impl.Interval;
import com.markhyvka.intervals.util.IntervalHelper;
import com.markhyvka.intervals.util.impl.IntegerIntervalHelper;
import com.markhyvka.intervals.util.impl.IntervalSet;

public class EntryPoint {

	private final static Logger LOG = LoggerFactory.getLogger(EntryPoint.class);

	public static void main(String[] args) {
		IntervalHelper<Integer> intervalHelper = new IntegerIntervalHelper();
		MergableCollection<Interval<Integer>> collection = new IntervalSet<>();

		intervalHelper.populateCollection(collection);
		LOG.debug(collection.toString());

		collection.merge(intervalHelper.createInterval(BoundType.EXCLUSIVE,
				Integer.valueOf(2), BoundType.EXCLUSIVE, Integer.valueOf(14)));
		LOG.debug(collection.toString());
	}
}
