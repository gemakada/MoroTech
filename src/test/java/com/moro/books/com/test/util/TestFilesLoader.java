/*
 * @(#)TestFilesLoader.java
 */
package com.moro.books.com.test.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class TestFilesLoader {

	private final Map<String, String> fileContentCacheMap;

	/**
	 * Default constructor.
	 */
	private TestFilesLoader() {
		fileContentCacheMap = new ConcurrentHashMap<>();
	}

	/**
	 * This helper class is used to initialize singleton.
	 */
	private static class SingletonHelper {

		/**
		 * Represents singleton instance of {@code TestFilesLoader} implementation.
		 */
		private static final TestFilesLoader TEST_FILES_LOADER = new TestFilesLoader();

		/**
		 * Default private constructor.
		 */
		private SingletonHelper() {
			super();
		}

	}

	/**
	 * This method provides the singleton instance of this class.
	 *
	 * @return singleton instance of {@link TestFilesLoader} implementation
	 */
	public static TestFilesLoader getInstance() {
		return SingletonHelper.TEST_FILES_LOADER;
	}

	/**
	 * This method will try to load file content as string
	 *
	 * @param filePath the file path
	 * @return file content as string
	 */
	public String loadFileContentAsString(final String filePath) {
		String fileContentAsString = fileContentCacheMap.get(filePath);
		if (fileContentAsString == null) {
			try ( Scanner scanner = new Scanner(getFileFromResourceAsStream(filePath), StandardCharsets.UTF_8.name())) {
				fileContentAsString = scanner.useDelimiter("\\A").next();
			}
			if (fileContentAsString != null && !fileContentAsString.isEmpty()) {
				fileContentCacheMap.put(filePath, fileContentAsString);
			}
		}
		return fileContentAsString;
	}

	/**
	 * This method reads files from class path.
	 *
	 * @param fileName the file to read
	 * @return instance of {@link InputStream}
	 */
	private InputStream getFileFromResourceAsStream(final String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}

}
