package com.markhyvka.producerconsumer.entry;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;
import com.markhyvka.producerconsumer.domain.impl.DefaultProducerConsumerContext;
import com.markhyvka.producerconsumer.util.impl.LinePersisterUnit;
import com.markhyvka.producerconsumer.util.impl.LineProcessorUnit;
import com.markhyvka.producerconsumer.util.impl.LineReaderUnit;

public class EntryPoint {

	private final static Logger LOG = Logger.getLogger(LinePersisterUnit.class);

	private static final int DEFAULT_SIZE = 10;

	public static void main(String[] args) {
		LOG.debug("Producer - Processor - Persister started...");
		ProducerConsumerContext<String> context = new DefaultProducerConsumerContext<>(DEFAULT_SIZE);
		Executor readerExecutor = Executors.newSingleThreadExecutor();
		readerExecutor.execute(new LineReaderUnit(context));
		Executor processorExecutor = Executors.newFixedThreadPool(DEFAULT_SIZE);
		processorExecutor.execute(new LineProcessorUnit(context));
		Executor persisterExecutor = Executors.newSingleThreadExecutor();
		persisterExecutor.execute(new LinePersisterUnit(context));
		LOG.debug("Producer - Processor - Persister ended...");
	}
}
