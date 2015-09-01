package com.markhyvka.producerconsumer.util.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;
import com.markhyvka.producerconsumer.domain.impl.ProducerConsumerState;
import com.markhyvka.producerconsumer.util.WorkUnit;

public class LineProcessorUnit implements WorkUnit<String> {

	private final static Logger LOG = LoggerFactory.getLogger(LineProcessorUnit.class);

	private ProducerConsumerContext<String> context;

	public LineProcessorUnit(ProducerConsumerContext<String> context) {
		this.context = context;
	}

	@Override
	public void run() {
		LOG.debug("Line Processor: started data processing.");
		while (!context.hasProducerConsumerEnded()) {
			String obj = consume();
			LOG.debug("Line Processor: another entry retrieved (" + obj + ").");
			String processedObj = process(obj);
			LOG.debug("Line Processor: another entry processed (" + obj
					+ " -> " + processedObj + ").");
			produce(processedObj);
			LOG.debug("Line Processor: another entry inserted (" + processedObj
					+ ").");
		}
		LOG.debug("Line Processor: ended data processing.");
		context.setProcessorState(ProducerConsumerState.DONE);
	}

	private String consume() {
		String obj = StringUtils.EMPTY;
		try {
			obj = context.retrieveProducerProcessorWorkUnit();
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
			context.addProcessorPersisterWorkUnit(obj);
		} catch (InterruptedException e) {
			LOG.debug("Line Processor: interrupted while putting another entry into queue. Ending work.");
			context.setProcessorState(ProducerConsumerState.IDLE);
		}
	}
}
