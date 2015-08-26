package com.markhyvka.producerconsumer.util.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;
import com.markhyvka.producerconsumer.domain.impl.ProducerConsumerState;
import com.markhyvka.producerconsumer.util.WorkUnit;

public class LineProcessorUnit implements WorkUnit<String> {

	private final static Logger LOG = Logger.getLogger(LineProcessorUnit.class);

	private ProducerConsumerContext<String> context;

	public LineProcessorUnit(ProducerConsumerContext<String> context) {
		this.context = context;
	}

	@Override
	public void run() {
		LOG.debug("Line Processor: started data processing.");
		while (!hasProducerConsumerEnded()) {
			String obj = consume();
			LOG.debug("Line Processor: another entry retrieved (" + obj + ").");
			String processedObj = process(obj);
			LOG.debug("Line Processor: another entry processed (" + obj + " -> " + processedObj + ").");
			produce(processedObj);
			LOG.debug("Line Processor: another entry inserted (" + processedObj + ").");
			context.getProcessorAccumulator().incrementAndGet();
		}
		LOG.debug("Line Processor: ended data processing.");
		context.setProcessorState(ProducerConsumerState.DONE);
	}

	private boolean isProducerProcessorQueueEmpty() {
		return context.getProducerProcessorQueue().size() == BigDecimal.ZERO.intValue();
	}

	private boolean hasProducerConsumerEnded() {
		return context.hasProducerEnded() && isProducerProcessorQueueEmpty();
	}

	private String consume() {
		String obj = StringUtils.EMPTY;
		try {
			obj = context.getProducerProcessorQueue().take();
		} catch (InterruptedException e) {
			LOG.debug("Line Processor: interrupted while retrieving another entry from queue. Ending work.");
			context.setProcessorState(ProducerConsumerState.IDLE);
		}
		return obj;
	}

	private String process(String obj) {
		return new StringBuilder(obj).reverse().toString();
	}

	private void produce(String obj) {
		try {
			context.getProcessorPersisterQueue().put(obj);
		} catch (InterruptedException e) {
			LOG.debug("Line Processor: interrupted while putting another entry into queue. Ending work.");
			context.setProcessorState(ProducerConsumerState.IDLE);
		}
	}
}
