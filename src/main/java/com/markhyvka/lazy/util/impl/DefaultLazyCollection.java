package com.markhyvka.lazy.util.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import com.markhyvka.lazy.util.DataSource;
import com.markhyvka.lazy.util.LazyCollection;

public class DefaultLazyCollection<T> extends AbstractList<T> implements
		LazyCollection<T>, List<T> {

	private List<T> list;

	private DataSource<T> dataSource;

	// Few constructors
	public DefaultLazyCollection() {
		list = new ArrayList<T>();
	}

	public DefaultLazyCollection(DataSource<T> dataSource) {
		this();
		this.dataSource = dataSource;
	}

	public DefaultLazyCollection(Collection<T> c) {
		list = new ArrayList<>(c);
	}

	public DefaultLazyCollection(Collection<T> c, DataSource<T> dataSource) {
		this(c);
		this.dataSource = dataSource;
	}

	// Methods overridden from AbstractList
	@Override
	public int size() {
		return list.size();
	}

	// Methods overridden from LazyCollection
	@Override
	public List<T> getFirstAmount(int amount) {
		list = dataSource.getAmountWithOffset(0, amount);
		return list;
	}

	@Override
	public List<T> getNextAmount(int amount) {
		list = dataSource.getNextAmount(amount);
		return list;
	}

	@Override
	public List<T> getPreviousAmount(int amount) {
		list = dataSource.getPreviousAmount(amount);
		return list;
	}

	@Override
	public List<T> getAmountWithOffset(int offset, int amount) {
		list = dataSource.getAmountWithOffset(offset, amount);
		return list;
	}

	@Override
	public List<T> getLastAmount(int amount) {
		int size = dataSource.getDataSourceSize();
		int offset = size - amount;
		list = getAmountWithOffset(offset < 0 ? 0 : offset, amount);
		return list;
	}

	public void setDataSource(DataSource<T> dataSource) {
		this.dataSource = dataSource;
	}

	// Methods overridden from List
	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(int index) {
		return list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
}
