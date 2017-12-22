package com.iture.easyUtil.str;

import java.util.Collection;

public class StrUtils {
	/**
	 * 批量旧的字符串列表为新字符
	 * ***/
	public static String replace(String source,Collection<String> oldChars,String newChar){
		for(String oldChar : oldChars){
			source = source.replace(oldChar, newChar);
		}
		return source;
	}

}
