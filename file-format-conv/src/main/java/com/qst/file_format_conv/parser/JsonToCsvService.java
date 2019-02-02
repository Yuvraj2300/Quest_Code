package com.qst.file_format_conv.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonToCsvService {
	public static String convert(String inp) throws IOException {
		List<Map<String, String>> flatJson = JSONFlattener.parseJson(inp);
		// Using the default separator ','
		String csvData = CSVWriter.getCSV(flatJson);
		return csvData;
	}
}
