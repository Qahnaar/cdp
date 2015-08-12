package com.markhyvka.intervals.util.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.markhyvka.intervals.domain.impl.Bound.BoundType;
import com.markhyvka.intervals.domain.impl.Interval;

@RunWith(Parameterized.class)
public class IntervalSetTest {

	final static Logger LOG = Logger.getLogger(IntervalSetTest.class);

	private IntervalSet<Integer, Interval<Integer>> unit;

	private static IntegerIntervalHelper helper;

	private Interval<Integer> firstInterval;
	private Interval<Integer> secondInterval;
	private Interval<Integer> thirdInterval;

	private Interval<Integer> mergedInterval;

	private boolean expectedResult;

	static {
		helper = new IntegerIntervalHelper();
	}

	@Before
	public void setUp() {
		unit = new IntervalSet<Integer, Interval<Integer>>(
				new HashSet<Interval<Integer>>(Arrays.asList(firstInterval,
						secondInterval, thirdInterval)));
	}

	public IntervalSetTest(Interval<Integer> firstInterval,
			Interval<Integer> secondInterval, Interval<Integer> thirdInterval,
			Interval<Integer> mergedInterval, boolean expectedResult) {
		this.firstInterval = firstInterval;
		this.secondInterval = secondInterval;
		this.thirdInterval = thirdInterval;
		this.mergedInterval = mergedInterval;
		this.expectedResult = expectedResult;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> intervals() {
		Object[][] params = {
				{
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(1), BoundType.EXCLUSIVE,
								Integer.valueOf(3)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(4), BoundType.EXCLUSIVE,
								Integer.valueOf(5)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(6), BoundType.EXCLUSIVE,
								Integer.valueOf(10)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(0), BoundType.EXCLUSIVE,
								Integer.valueOf(1)), Boolean.FALSE },
				{
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(1), BoundType.EXCLUSIVE,
								Integer.valueOf(3)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(4), BoundType.EXCLUSIVE,
								Integer.valueOf(5)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(6), BoundType.EXCLUSIVE,
								Integer.valueOf(10)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(0), BoundType.INCLUSIVE,
								Integer.valueOf(1)), Boolean.TRUE },
				{
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(1), BoundType.EXCLUSIVE,
								Integer.valueOf(3)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(4), BoundType.EXCLUSIVE,
								Integer.valueOf(5)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(6), BoundType.EXCLUSIVE,
								Integer.valueOf(10)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(2), BoundType.INCLUSIVE,
								Integer.valueOf(2)), Boolean.TRUE },
				{
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(1), BoundType.EXCLUSIVE,
								Integer.valueOf(3)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(4), BoundType.EXCLUSIVE,
								Integer.valueOf(5)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(6), BoundType.EXCLUSIVE,
								Integer.valueOf(10)),
						helper.createInterval(BoundType.INCLUSIVE,
								Integer.valueOf(3), BoundType.INCLUSIVE,
								Integer.valueOf(4)), Boolean.TRUE },
				{
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(1), BoundType.EXCLUSIVE,
								Integer.valueOf(3)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(4), BoundType.EXCLUSIVE,
								Integer.valueOf(5)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(6), BoundType.EXCLUSIVE,
								Integer.valueOf(10)),
						helper.createInterval(BoundType.EXCLUSIVE,
								Integer.valueOf(4), BoundType.EXCLUSIVE,
								Integer.valueOf(6)), Boolean.TRUE } };
		return Arrays.asList(params);
	}

	@Test
	public void testPrimeNumberChecker() {
		LOG.debug("Before merge: " + unit);
		LOG.debug("Merged interval: " + mergedInterval);
		assertEquals(expectedResult, unit.merge(mergedInterval));
		LOG.debug("After merge: " + unit + "\n");
	}
}
