package com.markhyvka.producerconsumer.util.impl;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.markhyvka.lazy.util.DataSource;
import com.markhyvka.lazy.util.impl.CustomizedLazyCollection;
import com.markhyvka.lazy.util.impl.RowModelCachingDataSource;
import com.markhyvka.producerconsumer.domain.ProducerConsumerContext;
import com.markhyvka.producerconsumer.domain.impl.ProducerConsumerState;
import com.markhyvka.producerconsumer.util.WorkUnit;

public class LineReaderUnit implements WorkUnit<String> {

	private final static Logger LOG = Logger.getLogger(LineReaderUnit.class);

	private static final int DEFAULT_SIZE = 10;

	private ProducerConsumerContext<String> context;

	public LineReaderUnit(ProducerConsumerContext<String> context) {
		this.context = context;
	}

	@Override
	public void run() {
		LOG.debug("Line Reader: started data read.");
		context.setProducerState(ProducerConsumerState.WORKING);
		produce();
		LOG.debug("Line Reader: ended data read.");
	}

	private void produce() {
		DataSource<String> dataSource = new RowModelCachingDataSource<>();
		Collection<String> collection = new CustomizedLazyCollection<>(DEFAULT_SIZE, dataSource);
		Iterator<String> iterator = collection.iterator();

		while (iterator.hasNext()) {
			try {
				String obj = iterator.next();
				LOG.debug("Line Reader: another entry found (" + obj + ").");
				context.getProducerProcessorQueue().put(obj);
				LOG.debug("Line Reader: another entry inserted (" + obj + ").");
			} catch (InterruptedException e) {
				LOG.debug("Line Reader: interrupted while putting another entry into queue. Ending work...");
				context.setProducerState(ProducerConsumerState.IDLE);
			}
		}
		context.setProducerState(ProducerConsumerState.DONE);
	}
}
