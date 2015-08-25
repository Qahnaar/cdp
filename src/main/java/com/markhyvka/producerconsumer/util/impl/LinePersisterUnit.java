package com.markhyvka.producerconsumer.util.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;
import com.markhyvka.producerconsumer.domain.impl.ProducerConsumerState;
import com.markhyvka.producerconsumer.util.WorkUnit;

public class LinePersisterUnit implements WorkUnit<String> {

	private final static Logger LOG = Logger.getLogger(LinePersisterUnit.class);

	private ProducerConsumerContext<String> context;

	public LinePersisterUnit(ProducerConsumerContext<String> context) {
		this.context = context;
	}

	@Override
	public void run() {
		LOG.debug("Line Persister: started data persisting.");
		while (!hasProducerConsumerEnded()) {
			String obj = consume();
			LOG.debug("Line Persister: another line processed (" + obj + ").");
			LOG.debug("FUTURE FILE: added line " + obj);
		}
		LOG.debug("Line Persister: ended data persisting.");
		context.setPersisterState(ProducerConsumerState.DONE);
	}

	private boolean hasProducerEnded() {
		return ProducerConsumerState.DONE.equals(context.getProducerState());
	}

	private boolean hasProcessorEnded() {
		return ProducerConsumerState.DONE.equals(context.getProcessorState());
	}

	private boolean isProcessorPersisterQueueEmpty() {
		return context.getProcessorPersisterQueue().size() == BigDecimal.ZERO.intValue();
	}

	private boolean hasProducerConsumerEnded() {
		return hasProducerEnded() && hasProcessorEnded() && isProcessorPersisterQueueEmpty();
	}

	private String consume() {
		String obj = StringUtils.EMPTY;
		try {
			obj = context.getProcessorPersisterQueue().take();
		} catch (InterruptedException e) {
			LOG.debug("Line Persister: interrupted while retrieving another entry from queue. Ending work...");
			context.setPersisterState(ProducerConsumerState.IDLE);
		}
		return obj;
	}
}
