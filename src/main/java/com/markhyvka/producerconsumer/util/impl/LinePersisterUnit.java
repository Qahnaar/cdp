package com.markhyvka.producerconsumer.util.impl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;
import com.markhyvka.producerconsumer.domain.impl.ProducerConsumerState;
import com.markhyvka.producerconsumer.util.WorkUnit;

public class LinePersisterUnit implements WorkUnit<String> {

	private final static Logger LOG = Logger.getLogger(LinePersisterUnit.class);

	private static final String OUTPUT_FILE = "src/main/resources/testDataOutput.txt";

	private static final String FILE_ENCODING = "UTF-8";

	private ProducerConsumerContext<String> context;

	private PrintWriter writer;

	public LinePersisterUnit(ProducerConsumerContext<String> context)
			throws FileNotFoundException, UnsupportedEncodingException {
		this.context = context;
		this.writer = new PrintWriter(OUTPUT_FILE, FILE_ENCODING);
	}

	@Override
	public void run() {
		LOG.debug("Line Persister: started data persisting.");
		while (!hasProducerConsumerEnded()) {
			String obj = consume();
			LOG.debug("Line Persister: another line processed (" + obj + ").");
			writer.println(obj);
			context.getPersisterAccumulator().incrementAndGet();
		}
		LOG.debug("Line Persister: ended data persisting.");
		writer.close();
		context.setPersisterState(ProducerConsumerState.DONE);
	}

	private boolean isProcessorPersisterQueueEmpty() {
		return context.getProcessorPersisterQueue().size() == BigDecimal.ZERO
				.intValue();
	}

	private boolean hasProducerConsumerEnded() {
		return context.hasProducerEnded() && context.hasProcessorEnded()
				&& isProcessorPersisterQueueEmpty();
	}

	private String consume() {
		String obj = StringUtils.EMPTY;
		try {
			obj = context.getProcessorPersisterQueue().take();
		} catch (InterruptedException e) {
			LOG.debug("Line Persister: interrupted while retrieving another entry from queue. Ending work.");
			context.setPersisterState(ProducerConsumerState.IDLE);
		}
		return obj;
	}
}
