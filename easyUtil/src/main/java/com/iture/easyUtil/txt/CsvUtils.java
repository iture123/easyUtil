package com.iture.easyUtil.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {
	/**
	 * 追加数据
	 * 
	 * @param file
	 * @param rowList
	 */
	public static void appendData(File file, List<List<String>> rowList) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.newLine();
			for(int i = 0;i< rowList.size();i++) {
				List<String> row = rowList.get(i);
				out.write(String.join(",", row));
				if(i != rowList.size() -1) {
					out.newLine();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static List<String> getFirstRow(File file) {
		return readCsvAsList(file, false).get(0);
	}

	public static List<String> getLastData(File file, boolean isFilterFirstLine) {
		List<List<String>> rowList = readCsvAsList(file, isFilterFirstLine);
		if (rowList.size() == 0) {
			return null;
		}
		return rowList.get(rowList.size() - 1);
	}

	public static List<List<String>> readCsvAsList(String filePath, boolean isFilterFirstLine) {
		File file = new File(filePath);
		return readCsvAsList(file, isFilterFirstLine);
	}

	public static List<List<String>> readCsvAsList(File file, boolean isFilterFirstLine) {
		List<String> list = readCsv(file, isFilterFirstLine);
		List<List<String>> outList = new ArrayList<>();
		list.forEach(x -> {
			outList.add(Arrays.asList(x.split(",")));
		});
		return outList;
	}

	/**
	 * 
	 * @param filePath          路径
	 * @param isFilterFirstLine 是否过滤首行
	 * @return
	 */
	public static List<String> readCsv(String filePath, boolean isFilterFirstLine) {
		File file = new File(filePath);
		return readCsv(file, isFilterFirstLine);

	}

	public static List<String> readCsv(File file, boolean isFilterFirstLine) {
		InputStream is = null;
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			int i = 0;
			while ((line = br.readLine()) != null) {
				i++;
				if (isFilterFirstLine) {
					if (i != 1) {
						list.add(line);
					}
				} else {
					list.add(line);
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return list;
	}

	/**
	 * 解析本地csv文件 首行作为列名
	 */
	private static List<Map<String, String>> parseCsv(String filePath) {
		File file = new File(filePath);
		InputStream is = null;
		BufferedReader br = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			int i = 0;
			String fieldArr[] = null; // 列名
			String dataArr[];
			Map<String, String> dataMap = null;
			while ((line = br.readLine()) != null) {
				if (i == 0) {
					fieldArr = line.split(",");
				} else {
					dataMap = new LinkedHashMap<String, String>();
					dataArr = line.split(",");
					dataMap.clear();
					for (int j = 0; j < dataArr.length; j++) {
						dataMap.put(fieldArr[j], dataArr[j]);
					}
					list.add(dataMap);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return list;
	}

	/**
	 * 写入一个新的csv文件
	 * 
	 * @param filePath
	 * @param field
	 * @param list
	 */
	public void writeCsv(String filePath, String field, List<Map<String, Object>> list) {
		String fieldArr[] = field.split(",");
		OutputStream os = null;
		BufferedWriter bw = null;
		try {
			os = new FileOutputStream(new File(filePath));
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(field);
			bw.newLine();
			for (Map<String, Object> map : list) {
				for (int i = 0, length = fieldArr.length; i < length; i++) {
					bw.write(map.get(fieldArr[i]).toString());
					if (i != length - 1) {
						bw.write(",");
					} else {
						bw.newLine();
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				bw.close();
				os.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
