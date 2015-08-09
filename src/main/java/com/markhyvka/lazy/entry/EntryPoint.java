package com.markhyvka.lazy.entry;

import org.apache.log4j.Logger;

import com.markhyvka.lazy.util.DataSource;
import com.markhyvka.lazy.util.LazyCollection;
import com.markhyvka.lazy.util.impl.DefaultLazyCollection;
import com.markhyvka.lazy.util.impl.RowModelCachingDataSource;

public class EntryPoint {
	final static Logger LOG = Logger.getLogger(EntryPoint.class);

	public static void main(String[] args) {
		DataSource<String> dataSource = new RowModelCachingDataSource<>();
		LazyCollection<String> lazy = new DefaultLazyCollection<>(dataSource);

		LOG.debug("First 10: " + lazy.getFirstAmount(10));
		LOG.debug("Next 10: " + lazy.getNextAmount(10));
		LOG.debug("Get(5th) element of currently loaded bulk " + lazy.get(4));
		LOG.debug("Last 10: " + lazy.getLastAmount(10));

		LOG.debug("10 with the offset of 7: " + lazy.getAmountWithOffset(7, 10));
		LOG.debug("Previous 10: " + lazy.getPreviousAmount(10));
	}
}
