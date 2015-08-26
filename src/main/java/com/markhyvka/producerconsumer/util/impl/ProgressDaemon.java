package com.markhyvka.producerconsumer.util.impl;

import org.apache.log4j.Logger;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;

public class ProgressDaemon implements Runnable {

	private final static Logger LOG = Logger.getLogger(ProgressDaemon.class);

	private static final long DAEMON_SLEEP_IN_MILLISECONDS = 100L;

	private ProducerConsumerContext<String> context;

	public ProgressDaemon(ProducerConsumerContext<String> context) {
		this.context = context;
	}

	@Override
	public void run() {
		LOG.debug("Progress Daemon: started watching the progress.");
		while (!context.hasWorkEnded()) {
			try {
				Thread.sleep(DAEMON_SLEEP_IN_MILLISECONDS);
				LOG.info("Produced lines: "
						+ context.getProducerAccumulator().get());
				LOG.info("Processed lines: "
						+ context.getProcessorAccumulator().get());
				LOG.info("Persisted lines: "
						+ context.getPersisterAccumulator().get());
			} catch (InterruptedException e) {
				LOG.debug("Progress Daemon: interrupted while watching the progress. Ending work.");
			}
		}
		LOG.debug("Progress Daemon: ended watching the progress.");
	}
}
