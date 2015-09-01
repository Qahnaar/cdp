package com.markhyvka.producerconsumer.domain;

import com.markhyvka.producerconsumer.domain.impl.ProducerConsumerState;

public interface ProducerConsumerContext<T> {

	T retrieveProducerProcessorWorkUnit() throws InterruptedException;

	void addProducerProcessorWorkUnit(T workUnit) throws InterruptedException;

	T retrieveProcessorPersisterWorkUnit() throws InterruptedException;

	void addProcessorPersisterWorkUnit(T workUnit) throws InterruptedException;

	void registerPersisterWorkUnit();

	ProducerConsumerState getProducerState();

	void setProducerState(ProducerConsumerState state);

	ProducerConsumerState getProcessorState();

	void setProcessorState(ProducerConsumerState state);

	ProducerConsumerState getPersisterState();

	void setPersisterState(ProducerConsumerState state);

	boolean hasProducerEnded();

	boolean hasProcessorEnded();

	boolean hasPersisterEnded();

	boolean hasWorkEnded();

	boolean isProcessorPersisterQueueEmpty();

	boolean hasProducerConsumerEnded();

	boolean isProducerProcessorQueueEmpty();

	int getProducedLineCounter();

	int getProcessedLineCounter();

	int getPersisterLineCounter();

	void waitForWorkEnd() throws InterruptedException;

	void notifyOfWorkEnd() throws InterruptedException;
}
