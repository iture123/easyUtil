package com.iture.easyUtil.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	/***
	 * 从content中抽取符合正则表达式p的字符串
	 * 如extractData("连通率:53355", "连通率:(\\d+)")返回53355
	 * **/
	public static String extractData(String content, String p) {
		Pattern pattern = Pattern.compile(p, Pattern.DOTALL);
		Matcher m = pattern.matcher(content);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	/**
	 * 抽取出符合的数据
	 * ***/
	public static List<String> extractListData(String content, String... patterns) {
		List<String> list = new ArrayList<>();
		for(String p:patterns){
			Pattern pattern = Pattern.compile(p, Pattern.DOTALL);
			Matcher m = pattern.matcher(content);
			while (m.find()) {
				list.add(m.group(1));
			}
		}
		return list;
	}
	/**
	 * 抽取出符合的数据
	 * ***/
	public static List<String> matchListData(String content, String... patterns) {
		List<String> list = new ArrayList<>();
		for(String p:patterns){
			Pattern pattern = Pattern.compile(p, Pattern.DOTALL);
			Matcher m = pattern.matcher(content);
			while (m.find()) {
				list.add(m.group(0));
			}
		}
		return list;
	}
	public static List<String> matchListData(String content){
		return null;
	}
	public static void main(String[] args) {
		String oldCharP ="#\\{name\\}|\\$\\{.*?\\}";
		String sql ="where name=${name} ";
		System.out.println(matchListData(sql, oldCharP));
		System.out.println("aa bb   cc".replaceAll("\\s", ""));
	}

}
