package com.tmlk.framework.util;

import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	/**
	 * 根据文件后缀名，获得Excel工作薄
	 * 
	 * @param fileName
	 *            文件名
	 * @param stream
	 *            Excel文件流
	 * @return 工作薄
	 * @throws java.io.IOException
	 *             异常
	 */
	public static Workbook readWorkbook(String fileName, InputStream stream) {
		Workbook wb = null;
		try {
			if (fileName.endsWith(".xls")) {
				wb = new HSSFWorkbook(stream);
			} else if (fileName.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(stream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 获得工作表的最大行号
	 * 
	 * @param sheet
	 * @return
	 */
	public static int getSheetRowNum(Sheet sheet) {
		return sheet.getLastRowNum();
	}

	/**
	 * 获得工作表，对应行的最大列号
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	public static int getSheetColCount(Sheet sheet, int rowIndex) {
		return sheet.getRow(rowIndex).getLastCellNum();
	}

	/**
	 * 获得单元格数据
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public static Object getCellValue(Sheet sheet, int rowIndex, int colIndex, String dataType) {
		Object value = null;
		Cell c = sheet.getRow(rowIndex).getCell(colIndex);
		if (c != null) {
			if (dataType.equals(Constants.DATA_TYPE_STRING)) {
				if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
					c.setCellType(Cell.CELL_TYPE_STRING);
					String temp = c.getStringCellValue();
					if(temp.length()>2000)
						value = temp.substring(0, 2000);
					else 
						value = temp;
				}
			} else if (dataType.equals(Constants.DATA_TYPE_INT)) {
				if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
					if (c.getCellType() != Cell.CELL_TYPE_NUMERIC) {
						c.setCellType(Cell.CELL_TYPE_STRING);
						value = c.getStringCellValue();
					} else {
						c.setCellType(Cell.CELL_TYPE_NUMERIC);
						value = (int) c.getNumericCellValue();
					}
				}
			} else if (dataType.equals(Constants.DATA_TYPE_DOUBLE)) {
				if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
					if (c.getCellType() != Cell.CELL_TYPE_NUMERIC) {
						c.setCellType(Cell.CELL_TYPE_STRING);
						value = c.getStringCellValue();
					} else {
						c.setCellType(Cell.CELL_TYPE_NUMERIC);
						value = c.getNumericCellValue();
					}
				}
			} else if (dataType.equals(Constants.DATA_TYPE_DATE)) {
				if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
					c.setCellType(Cell.CELL_TYPE_NUMERIC);
					value = c.getDateCellValue();
				}
			} else if (dataType.equals(Constants.DATA_TYPE_BOOL)) {
				if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
					c.setCellType(Cell.CELL_TYPE_BOOLEAN);
					boolean a = c.getBooleanCellValue();
					if (a) {
						value = 1;
					} else {
						value = 0;
					}
				}
			}

		}
		return value;
	}
}
