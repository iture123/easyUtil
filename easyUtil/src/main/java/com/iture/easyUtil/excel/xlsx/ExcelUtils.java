package com.iture.easyUtil.excel.xlsx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	/**
	 * 创建一个新的excel文件，适合用来写入单sheet 页
	 * @param filePath 文件路径
	 * @param dataRows 数据行
	 */
	public static void writeNewFile(String filePath, List<List<String>> dataRows) {
		writeNewFile(filePath,dataRows,"sheet1");
	}
	/**
	 * 创建一个新的excel文件，适合用来写入单sheet 页
	 * @param filePath 文件路径
	 * @param dataRows 数据行
	 * @param sheetName sheet名
	 */
	public static void writeNewFile(String filePath, List<List<String>> dataRows,String sheetName) {
		// 创建工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 新建工作表
		XSSFSheet sheet = workbook.createSheet(sheetName);
		for(int i =0 ;i <dataRows.size();i++) {
			List<String> dataRow = dataRows.get(i);
			// 创建行,i表示第i行
			XSSFRow row = sheet.createRow(i);
			for(int j =0;j < dataRow.size();j++) {
				XSSFCell cell = row.createCell(j);
				cell.setCellValue(dataRow.get(j));
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(filePath));
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	public static XSSFWorkbook createXSSFWorkbook() {
		// 创建工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		return workbook;
	}
	/**
	 * 把XSSFWorkbook 实例写入本地文件，并且关闭该实例
	 * @param workbook
	 * @param filePath
	 */
	public static void writeXSSFWorkbookToLocal(XSSFWorkbook workbook,String filePath) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(filePath));
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 *  添加sheet 页
	 * @param workbook
	 * @param sheetName
	 * @param dataRows
	 */
	public static void appendSheet(XSSFWorkbook workbook,String sheetName,List<List<String>> dataRows) {
		XSSFSheet sheet = workbook.createSheet(sheetName);
		for(int i =0 ;i <dataRows.size();i++) {
			List<String> dataRow = dataRows.get(i);
			// 创建行,i表示第i行
			XSSFRow row = sheet.createRow(i);
			for(int j =0;j < dataRow.size();j++) {
				XSSFCell cell = row.createCell(j);
				cell.setCellValue(dataRow.get(j));
			}
		}
	}
	public static void main(String[] args) {
		List<List<String>> dataRows  = new ArrayList<>();
		List<String> row = new ArrayList<>();
		row.add("a");
		row.add("2");
		dataRows.add(row );
		dataRows.add(row);
		ExcelUtils.writeNewFile("d:/a.xlsx", dataRows);
	}
}
