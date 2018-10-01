package com.iture.easyUtil.txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class TxtUtils {
	/**
	 * 把数据行写入新的文件
	 * @param filePath
	 * @param rowList
	 */
	public void write(String filePath, List<String> rowList) {
		OutputStream os = null;
		BufferedWriter bw = null;
		try {
			os = new FileOutputStream(new File(filePath));
			bw = new BufferedWriter(new OutputStreamWriter(os));
			int rowSize = rowList.size();
			for (int i = 0; i < rowSize; i++) {
				bw.write(rowList.get(i));
				if (i != rowSize - 1) {
					bw.newLine();
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
