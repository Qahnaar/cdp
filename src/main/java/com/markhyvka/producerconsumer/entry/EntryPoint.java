package com.markhyvka.producerconsumer.entry;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;
import com.markhyvka.producerconsumer.domain.impl.DefaultProducerConsumerContext;
import com.markhyvka.producerconsumer.util.impl.LinePersisterUnit;
import com.markhyvka.producerconsumer.util.impl.LineProcessorUnit;
import com.markhyvka.producerconsumer.util.impl.LineProducerUnit;
import com.markhyvka.producerconsumer.util.impl.ProgressDaemon;

public class EntryPoint {

	private final static Logger LOG = Logger.getLogger(LinePersisterUnit.class);

	private static final int DEFAULT_SIZE = 10;

	public static void main(String[] args)
			throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
		ProducerConsumerContext<String> context = new DefaultProducerConsumerContext<>(DEFAULT_SIZE);
		Thread progressDaemon = new Thread(new ProgressDaemon(context));
		progressDaemon.setDaemon(Boolean.TRUE);
		progressDaemon.start();
		LOG.debug("Producer - Processor - Persister started.");
		ExecutorService readerExecutor = Executors.newSingleThreadExecutor();
		readerExecutor.execute(new LineProducerUnit(context));
		ExecutorService processorExecutor = Executors.newFixedThreadPool(DEFAULT_SIZE);
		processorExecutor.execute(new LineProcessorUnit(context));
		ExecutorService persisterExecutor = Executors.newSingleThreadExecutor();
		persisterExecutor.execute(new LinePersisterUnit(context));
		while (!context.hasWorkEnded()) {
		}
		readerExecutor.shutdownNow();
		processorExecutor.shutdownNow();
		persisterExecutor.shutdownNow();
		LOG.debug("Producer - Processor - Persister ended.");
	}
}
