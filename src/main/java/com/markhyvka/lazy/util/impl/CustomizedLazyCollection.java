package com.markhyvka.lazy.util.impl;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.markhyvka.lazy.util.DataSource;

public class CustomizedLazyCollection<E> extends AbstractCollection<E> {

	private Collection<E> col;

	private DataSource<E> dataSource;

	private int capacity;

	public CustomizedLazyCollection(int capacity, DataSource<E> dataSource) {
		this.col = new ArrayList<>(capacity);
		this.capacity = capacity;
		this.dataSource = dataSource;
	}

	public CustomizedLazyCollection(Collection<E> collection) {
		col = new ArrayList<>(collection);
	}

	@Override
	public Iterator<E> iterator() {
		return new LazyIterator();
	}

	@Override
	public int size() {
		return col.size();
	}

	private class LazyIterator implements Iterator<E> {

		private Iterator<E> iterator;

		private int cursor;

		public LazyIterator() {
			this.cursor = 0;
			this.iterator = (Iterator<E>) col.iterator();
		}

		@Override
		public boolean hasNext() {
			if (isThereNextElement()) {
				return Boolean.TRUE;
			}

			loadNewData();

			return isThereNextElement();
		}

		private void loadNewData() {
			col.clear();
			col.addAll(dataSource.getAmountWithOffset(cursor, capacity));
			cursor += capacity;
			iterator = (Iterator<E>) col.iterator();
		}

		@Override
		public E next() {
			return iterator.next();
		}

		private boolean isThereNextElement() {
			return iterator.hasNext();
		}
	}
}
