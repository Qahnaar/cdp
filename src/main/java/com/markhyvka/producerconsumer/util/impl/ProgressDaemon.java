package com.markhyvka.producerconsumer.util.impl;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;

public class ProgressDaemon implements Runnable {

	private ProducerConsumerContext<String> context;

	public ProgressDaemon(ProducerConsumerContext<String> context) {
		this.context = context;
	}

	@Override
	public void run() {
		System.out.println("Progress Daemon: started watching the progress.");
		while (!context.hasWorkEnded()) {
			try {
				TimeUnit.SECONDS.wait(BigDecimal.ONE.intValue());
				System.out.println("Produced lines: "
						+ context.getProducedLineCounter());
				System.out.println("Processed lines: "
						+ context.getProcessedLineCounter());
				System.out.println("Persisted lines: "
						+ context.getPersisterLineCounter());
			} catch (InterruptedException e) {
				System.out
						.println("Progress Daemon: interrupted while watching the progress. Ending work.");
			}
		}
		System.out.println("Progress Daemon: ended watching the progress.");
	}
}
