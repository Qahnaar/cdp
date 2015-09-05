package com.markhyvka.producerconsumer.domain.impl;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;

public class DefaultProducerConsumerContext<T> implements
		ProducerConsumerContext<T> {

	private BlockingQueue<T> producerProcessorQueue;

	private BlockingQueue<T> processorPersisterQueue;

	private volatile ProducerConsumerState producerState;

	private volatile ProducerConsumerState processorState;

	private volatile ProducerConsumerState persisterState;

	private AtomicInteger producerAccumulator;

	private AtomicInteger processorAccumulator;

	private AtomicInteger persisterAccumulator;

	private ReadWriteLock producerStateLock;

	private ReadWriteLock processorStateLock;

	private ReadWriteLock persisterStateLock;

	private Lock workEndMonitor;

	public DefaultProducerConsumerContext(int capacity) {
		this.producerProcessorQueue = new LinkedBlockingQueue<>(capacity);
		this.processorPersisterQueue = new LinkedBlockingQueue<>(capacity);
		producerState = ProducerConsumerState.IDLE;
		processorState = ProducerConsumerState.IDLE;
		persisterState = ProducerConsumerState.IDLE;
		producerAccumulator = new AtomicInteger();
		processorAccumulator = new AtomicInteger();
		persisterAccumulator = new AtomicInteger();
		producerStateLock = new ReentrantReadWriteLock();
		processorStateLock = new ReentrantReadWriteLock();
		persisterStateLock = new ReentrantReadWriteLock();
		workEndMonitor = new ReentrantLock();
	}

	@Override
	public ProducerConsumerState getProducerState() {
		try {
			producerStateLock.readLock().lock();
			return producerState;
		} finally {
			producerStateLock.readLock().unlock();
		}
	}

	@Override
	public void setProducerState(ProducerConsumerState state) {
		try {
			producerStateLock.writeLock().lock();
			this.producerState = state;
		} finally {
			producerStateLock.writeLock().unlock();
		}
	}

	@Override
	public ProducerConsumerState getProcessorState() {
		try {
			processorStateLock.readLock().lock();
			return processorState;
		} finally {
			processorStateLock.readLock().unlock();
		}
	}

	@Override
	public void setProcessorState(ProducerConsumerState state) {
		try {
			processorStateLock.writeLock().lock();
			this.processorState = state;
		} finally {
			processorStateLock.writeLock().unlock();
		}
	}

	@Override
	public ProducerConsumerState getPersisterState() {
		try {
			persisterStateLock.readLock().lock();
			return persisterState;
		} finally {
			persisterStateLock.readLock().unlock();
		}
	}

	@Override
	public void setPersisterState(ProducerConsumerState state) {
		try {
			persisterStateLock.writeLock().lock();
			this.persisterState = state;
		} finally {
			persisterStateLock.writeLock().unlock();
		}
	}

	@Override
	public boolean hasProducerEnded() {
		return ProducerConsumerState.DONE.equals(getProducerState());
	}

	@Override
	public boolean hasProcessorEnded() {
		return ProducerConsumerState.DONE.equals(getProcessorState());
	}

	@Override
	public boolean hasPersisterEnded() {
		return ProducerConsumerState.DONE.equals(getPersisterState());
	}

	@Override
	public T retrieveProducerProcessorWorkUnit() throws InterruptedException {
		return producerProcessorQueue.take();
	}

	@Override
	public void addProducerProcessorWorkUnit(T workUnit)
			throws InterruptedException {
		producerProcessorQueue.put(workUnit);
		producerAccumulator.incrementAndGet();
	}

	@Override
	public T retrieveProcessorPersisterWorkUnit() throws InterruptedException {
		return processorPersisterQueue.take();
	}

	@Override
	public void addProcessorPersisterWorkUnit(T workUnit)
			throws InterruptedException {
		processorPersisterQueue.put(workUnit);
		processorAccumulator.incrementAndGet();
	}

	@Override
	public void registerPersisterWorkUnit() {
		persisterAccumulator.incrementAndGet();
	}

	@Override
	public boolean isProducerProcessorQueueEmpty() {
		return producerProcessorQueue.size() == BigDecimal.ZERO.intValue();
	}

	@Override
	public boolean isProcessorPersisterQueueEmpty() {
		return processorPersisterQueue.size() == BigDecimal.ZERO.intValue();
	}

	@Override
	public boolean hasProducerConsumerEnded() {
		return hasProducerEnded() && hasProcessorEnded()
				&& isProcessorPersisterQueueEmpty();
	}

	@Override
	public int getProducedLineCounter() {
		return producerAccumulator.get();
	}

	@Override
	public int getProcessedLineCounter() {
		return processorAccumulator.get();
	}

	@Override
	public int getPersisterLineCounter() {
		return persisterAccumulator.get();
	}

	@Override
	public void waitForWorkEnd() throws InterruptedException {
		synchronized (workEndMonitor) {
			workEndMonitor.wait();
		}
	}

	@Override
	public void notifyOfWorkEnd() throws InterruptedException {
		synchronized (workEndMonitor) {
			workEndMonitor.notify();
		}
	}
}
