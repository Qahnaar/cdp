package com.markhyvka.intervals.util.impl;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.markhyvka.intervals.domain.MergableCollection;
import com.markhyvka.intervals.domain.impl.Bound;
import com.markhyvka.intervals.domain.impl.Interval;

public class IntervalSet<D extends Number, T extends Interval<D>> extends
		AbstractSet<T> implements SortedSet<T>, MergableCollection<T> {

	private SortedSet<T> set;

	// Few constructors
	public IntervalSet() {
		set = new TreeSet<T>();
	}

	public IntervalSet(Collection<T> collection) {
		set = new TreeSet<T>(collection);
	}

	// Methods overridden from AbstractSet
	@Override
	public boolean add(T e) {
		return set.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return set.addAll(c);
	}

	// Methods overridden from SortedSet
	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Comparator<? super T> comparator() {
		return set.comparator();
	}

	@Override
	public SortedSet<T> subSet(T fromElement, T toElement) {
		return set.subSet(fromElement, toElement);
	}

	@Override
	public SortedSet<T> headSet(T toElement) {
		return set.headSet(toElement);
	}

	@Override
	public SortedSet<T> tailSet(T fromElement) {
		return set.tailSet(fromElement);
	}

	@Override
	public T first() {
		return set.first();
	}

	@Override
	public T last() {
		return set.last();
	}

	// Methods overridden from Object
	@Override
	public String toString() {
		return "IntervalSet contains " + set;
	}

	// Methods overridden from MergableCollection
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
				T next = getNextElement(upperBound);
				if (next.equals(upperBound)) {
					subset = set.tailSet(lowerBound);
				} else {
					subset = set.subSet(lowerBound, next);
				}
			}
		}

		return subset;
	}

	private T getNextElement(T upperBound) {
		T element = upperBound;
		Iterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			T next = iterator.next();
			if (next.equals(upperBound)) {
				if (iterator.hasNext()) {
					element = iterator.next();
					break;
				}
			}
		}
		return element;
	}

	private boolean finishMerging(Set<T> subset, T interval) {
		if (subset.size() == 0)
			return Boolean.FALSE;

		interval.setLowerBound(getLowestValue(subset.iterator().next()
				.getLowerBound(), interval.getLowerBound()));
		interval.setUpperBound(getBiggestValue(getLastElement(subset)
				.getUpperBound(), interval.getUpperBound()));

		subset.clear();
		set.add(interval);
		return Boolean.TRUE;
	}

	private Bound<D> getLowestValue(Bound<D> first, Bound<D> second) {
		return (first.getBoundValue().doubleValue() < second.getBoundValue()
				.doubleValue()) ? first : second;
	}

	private Bound<D> getBiggestValue(Bound<D> first, Bound<D> second) {
		return (first.getBoundValue().doubleValue() > second.getBoundValue()
				.doubleValue()) ? first : second;
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
