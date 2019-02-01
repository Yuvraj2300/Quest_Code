package com.qst.file_format_conv.parser;

import java.io.InputStream;

//import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsToCsvService {
//	public static final Logger log = Logger.getLogger(XlsToCsvService.class.getName());

	public static String echoAsCSV(Sheet sheet) {
		Row row = null;
		String temp = "";
		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				Cell c = row.getCell(j);
				c.setCellType(CellType.STRING);
				temp = temp + "\"" + c + "\"" + "|";
			}
			temp = temp + "\n";

		}
		return temp;
	}

	public static String XlsToCsv(InputStream inp) throws Exception {
		Workbook wb = WorkbookFactory.create(inp);
		//log.info("Converted XLS to CSV Successfully  \n");
		String temp = echoAsCSV(wb.getSheetAt(0));
		return temp;
	}

}
