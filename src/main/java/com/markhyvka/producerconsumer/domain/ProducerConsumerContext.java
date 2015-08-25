package com.markhyvka.producerconsumer.domain;

import java.util.concurrent.BlockingQueue;

import com.markhyvka.producerconsumer.domain.impl.ProducerConsumerState;

public interface ProducerConsumerContext<T> {

	BlockingQueue<T> getProducerProcessorQueue();

	void setProducerProcessorQueue(BlockingQueue<T> queue);

	BlockingQueue<T> getProcessorPersisterQueue();

	void setProcessorPersisterQueue(BlockingQueue<T> queue);

	ProducerConsumerState getProducerState();

	void setProducerState(ProducerConsumerState state);

	ProducerConsumerState getProcessorState();

	void setProcessorState(ProducerConsumerState state);

	ProducerConsumerState getPersisterState();

	void setPersisterState(ProducerConsumerState state);
}
