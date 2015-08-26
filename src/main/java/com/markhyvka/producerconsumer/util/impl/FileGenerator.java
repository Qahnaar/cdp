package com.markhyvka.producerconsumer.util.impl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class FileGenerator {

	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("src/main/resources/testData.txt", "UTF-8");
		for (int i = 0; i < 5000; i++) {
			writer.println(UUID.randomUUID().toString());
		}
		writer.close();
	}
}
