package com.markhyvka.producerconsumer.domain.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

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

	public DefaultProducerConsumerContext(int capacity) {
		this.producerProcessorQueue = new LinkedBlockingQueue<>(capacity);
		this.processorPersisterQueue = new LinkedBlockingQueue<>(capacity);
		producerState = ProducerConsumerState.IDLE;
		processorState = ProducerConsumerState.IDLE;
		persisterState = ProducerConsumerState.IDLE;
		producerAccumulator = new AtomicInteger();
		processorAccumulator = new AtomicInteger();
		persisterAccumulator = new AtomicInteger();
	}

	@Override
	public BlockingQueue<T> getProducerProcessorQueue() {
		return producerProcessorQueue;
	}

	@Override
	public void setProducerProcessorQueue(BlockingQueue<T> queue) {
		synchronized (this.producerProcessorQueue) {
			this.producerProcessorQueue = queue;
		}
	}

	@Override
	public BlockingQueue<T> getProcessorPersisterQueue() {
		return processorPersisterQueue;
	}

	@Override
	public void setProcessorPersisterQueue(BlockingQueue<T> queue) {
		synchronized (this.processorPersisterQueue) {
			this.processorPersisterQueue = queue;
		}
	}

	@Override
	public ProducerConsumerState getProducerState() {
		synchronized (this.producerState) {
			return producerState;
		}
	}

	@Override
	public void setProducerState(ProducerConsumerState state) {
		synchronized (this.producerState) {
			this.producerState = state;
		}
	}

	@Override
	public ProducerConsumerState getProcessorState() {
		synchronized (this.processorState) {
			return processorState;
		}
	}

	@Override
	public void setProcessorState(ProducerConsumerState state) {
		synchronized (this.processorState) {
			this.processorState = state;
		}
	}

	@Override
	public ProducerConsumerState getPersisterState() {
		synchronized (this.persisterState) {
			return persisterState;
		}
	}

	@Override
	public void setPersisterState(ProducerConsumerState state) {
		synchronized (this.persisterState) {
			this.persisterState = state;
		}
	}

	@Override
	public AtomicInteger getProducerAccumulator() {
		return producerAccumulator;
	}

	@Override
	public AtomicInteger getProcessorAccumulator() {
		return processorAccumulator;
	}

	@Override
	public AtomicInteger getPersisterAccumulator() {
		return persisterAccumulator;
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
	public boolean hasWorkEnded() {
		return hasProducerEnded() && hasProcessorEnded() && hasPersisterEnded();
	}
}
