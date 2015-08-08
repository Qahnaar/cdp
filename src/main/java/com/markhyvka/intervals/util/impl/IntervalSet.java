package com.markhyvka.intervals.util.impl;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.markhyvka.intervals.domain.MergableCollection;
import com.markhyvka.intervals.domain.impl.Interval;

public class IntervalSet<D extends Number, T extends Interval<D>> extends
		AbstractSet<T> implements MergableCollection<T> {

	private SortedSet<T> set;

	public IntervalSet() {
		set = new TreeSet<T>();
	}

	public IntervalSet(Collection<T> collection) {
		set = new TreeSet<T>(collection);
	}

	@Override
	public boolean add(T e) {
		return set.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return set.addAll(c);
	}

	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean merge(T interval) {
		T lowerBound = null, upperBound = null;

		for (T entry : set) {
			if (entry.isLowerBoundApplicable(interval.getLowerBound())) {
				lowerBound = entry;
				break;
			}
		}

		Set<T> reverseOrderedSet = new TreeSet<T>(Collections.reverseOrder());
		reverseOrderedSet.addAll(set);
		for (T entry : reverseOrderedSet) {
			if (entry.isUpperBoundApplicable(interval.getUpperBound())) {
				upperBound = entry;
				break;
			}
		}

		Set<T> subsetForReplacement = retrieveSubsetForReplacement(lowerBound,
				upperBound);
		return finishMerging(subsetForReplacement, interval);
	}

	private Set<T> retrieveSubsetForReplacement(T lowerBound, T upperBound) {
		Set<T> subset = Collections.emptySet();

		if (lowerBound == null) {
			if (upperBound == null) {
				subset = set;
			} else {
				subset = set.headSet(upperBound);
			}
		} else {
			if (upperBound == null) {
				subset = set.tailSet(lowerBound);
			} else {
				if (!lowerBound.equals(upperBound)) {
					subset = set.subSet(lowerBound, upperBound);
				} else {
					subset = Collections.emptySet();
				}
			}
		}

		return subset;
	}

	private boolean finishMerging(Set<T> subset, T interval) {
		if (subset.size() == 0)
			return Boolean.FALSE;

		if (subset.size() == set.size()) {
			subset.clear();
			set.add(interval);
			return Boolean.TRUE;
		}

		interval.setLowerBound(subset.iterator().next().getLowerBound());
		interval.setUpperBound(getLastElement(subset).getUpperBound());
		subset.clear();
		set.add(interval);
		return Boolean.TRUE;
	}

	private T getLastElement(final Collection<T> c) {
		final Iterator<T> itr = c.iterator();
		T lastElement = itr.next();
		while (itr.hasNext()) {
			lastElement = itr.next();
		}
		return lastElement;
	}
}
