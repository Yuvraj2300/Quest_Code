package com.qst.file_format_conv.parser;

import java.util.List;
import java.util.Map;

public class JsonToCsvService {
	public	static	void	converterService(String	inp) {
		List<Map<String, String>> flatJson = JSONFlattener.parseJson(inp);
		CSVWriter.writeToFile(CSVWriter.getCSV(flatJson), "files/output_.csv");
		//INSERT INTO DB.....
		
	}
}
