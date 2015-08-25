package com.markhyvka.producerconsumer.domain.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;

public class DefaultProducerConsumerContext<T> implements ProducerConsumerContext<T> {

	private BlockingQueue<T> producerProcessorQueue;

	private BlockingQueue<T> processorPersisterQueue;

	private ProducerConsumerState producerState;

	private ProducerConsumerState processorState;

	private ProducerConsumerState persisterState;

	public DefaultProducerConsumerContext(int capacity) {
		this.producerProcessorQueue = new LinkedBlockingQueue<>(capacity);
		this.processorPersisterQueue = new LinkedBlockingQueue<>(capacity);
		producerState = ProducerConsumerState.IDLE;
		processorState = ProducerConsumerState.IDLE;
		persisterState = ProducerConsumerState.IDLE;
	}

	@Override
	public BlockingQueue<T> getProducerProcessorQueue() {
		return producerProcessorQueue;
	}

	@Override
	public void setProducerProcessorQueue(BlockingQueue<T> queue) {
		this.producerProcessorQueue = queue;
	}

	@Override
	public BlockingQueue<T> getProcessorPersisterQueue() {
		return processorPersisterQueue;
	}

	@Override
	public void setProcessorPersisterQueue(BlockingQueue<T> queue) {
		this.processorPersisterQueue = queue;
	}

	@Override
	public ProducerConsumerState getProducerState() {
		return producerState;
	}

	@Override
	public void setProducerState(ProducerConsumerState state) {
		this.producerState = state;
	}

	@Override
	public ProducerConsumerState getProcessorState() {
		return processorState;
	}

	@Override
	public void setProcessorState(ProducerConsumerState state) {
		this.processorState = state;
	}

	@Override
	public ProducerConsumerState getPersisterState() {
		return persisterState;
	}

	@Override
	public void setPersisterState(ProducerConsumerState state) {
		this.persisterState = state;
	}
}
