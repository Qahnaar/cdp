package com.markhyvka.intervals.domain;

import java.util.Collection;

public interface MergableCollection<T> extends Collection<T> {
	boolean merge(T interval) throws IllegalStateException;
}
