package com.markhyvka.lazy.util.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.markhyvka.lazy.util.DataSource;

public class RowModelCachingDataSource<T> implements DataSource<T> {

	private final static Logger LOG = LoggerFactory
			.getLogger(RowModelCachingDataSource.class);

	private List<T> loadedLines;

	private int cursorCounter;

	private boolean isDataLoaded;

	public RowModelCachingDataSource() {
		loadedLines = new ArrayList<T>();
		cursorCounter = 0;
	}

	@Override
	public List<T> getNextAmount(int amount) {
		checkData();
		cursorCounter += amount;
		return returnNLinesFromOffset(amount);
	}

	@Override
	public List<T> getPreviousAmount(int amount) {
		checkData();
		cursorCounter = cursorCounter < amount ? 0 : cursorCounter - amount;
		return returnNLinesFromOffset(amount);
	}

	@Override
	public List<T> getAmountWithOffset(int offset, int amount) {
		checkData();
		cursorCounter = offset;
		return returnNLinesFromOffset(amount);
	}

	@Override
	public int getDataSourceSize() {
		return loadedLines.size();
	}

	private List<T> returnNLinesFromOffset(int limit) {
		return loadedLines.stream().skip(cursorCounter).limit(limit)
				.collect(Collectors.toList());
	}

	private boolean checkData() {
		if (!isDataLoaded) {
			try {
				loadData();
				isDataLoaded = Boolean.TRUE;
			} catch (IOException e) {
				LOG.error("Error reading from file");
				isDataLoaded = Boolean.FALSE;
			} catch (URISyntaxException e) {
				LOG.error("Error locating file");
				isDataLoaded = Boolean.FALSE;
			}
		}

		return isDataLoaded;
	}

	@SuppressWarnings("unchecked")
	private void loadData() throws IOException, URISyntaxException {
		loadedLines = (List<T>) Files.readAllLines(Paths.get(this.getClass()
				.getResource("/testData.txt").toURI()));
	}
}
