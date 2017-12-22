package com.iture.easyUtil.file;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {
	/**解析为行
	 * */
	public static List<String> readCsv(String filePath,boolean isFilterFirstLine){
		File file = new File(filePath);
		InputStream is = null;
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is));
			String line =null;
			int i=0;
			while((line=br.readLine())!=null){
				i++;
				if(isFilterFirstLine){
					if(i !=1){
						list.add(line);
					}
				}else{
					list.add(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**解析本地csv文件
	 * */
	private static  List<Map<String,String>> parseCsv( String filePath) {
		File file = new File(filePath);
		InputStream is = null;
		BufferedReader br = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is));
			String line =null;
			int i=0;
			String fieldArr[] = null ; //列名
			String dataArr[];
			Map<String,String> dataMap = null;
			while((line=br.readLine())!=null){
				if(i ==0){
					fieldArr = line.split(",");
				}else{
					dataMap =new  LinkedHashMap<String,String> (); 
					dataArr = line.split(",");
					dataMap.clear();
					for(int j=0;j<dataArr.length;j++){
						dataMap.put(fieldArr[j], dataArr[j]);
					}
					list.add(dataMap);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public boolean createCsv(String filePath, String field,List<Map<String, Object>> list) {
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
