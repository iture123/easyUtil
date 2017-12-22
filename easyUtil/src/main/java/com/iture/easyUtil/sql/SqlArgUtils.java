package com.iture.easyUtil.sql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.iture.easyUtil.regex.RegexUtils;

class SqlArgUtils {
	/**#{arg}类型的参数的值都存在
	 * **/
	 static Set<String> getNotValueRequiredArgs(String sql,Map<String,Object> param){
		List<String> notValueList = new ArrayList<>();
		String pattern = "#\\{(.*?)\\}";
		List<String> arglist = RegexUtils.extractListData(sql, pattern);
		if(! arglist.isEmpty()){
			for(String arg : arglist){
				if(param.get(arg) == null || param.get(arg).toString().equals("")){
					notValueList.add(arg);
				}
			}
		}
		return new HashSet<>(notValueList);
	}
	 static String fillArg(String sql,Map<String, Object> parameters){
		 sql = fillInArg(sql, parameters);
		 sql = fillSimpleArg(sql, parameters);
		 return sql;
	}

	private static String fillInArg(String sql, Map<String, Object> param) {
		String[] patterns = {"\\(\\'{0,1}\\$\\{(.*?)\\}\\'{0,1}\\)","\\(\\'{0,1}#\\{(.*?)\\}\\'{0,1}\\)"};
		List<String> inArgList = RegexUtils.matchListData(sql, patterns);
		if(! inArgList.isEmpty()){
			for(String arg : inArgList){
				String key = RegexUtils.extractData(arg, "\\{(.*?)\\}");
				Object value = param.get(key);
				if(value == null) continue;
				if (value.getClass().getName().contains("ArrayList")) {
					@SuppressWarnings("unchecked")
					List<String> valueList = (List<String>) value;
					if (arg.contains("'")) {
						sql = sql.replace(arg, getInCondition(valueList, "'"));
					} else {
						sql = sql.replace(arg, getInCondition(valueList, ""));
					}
				} else {
					if (arg.contains("'")) {
						sql = sql.replace(arg, "('" + value + "')");
					} else {
						sql = sql.replace(arg, "(" + value + ")");
					}
				}
			}
		}
		return sql;
	}

	private static String getInCondition(List<String> valueList, String singleQuote) {
		StringBuffer sb = new StringBuffer("(");
		int len = valueList.size();
		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				sb.append(singleQuote + valueList.get(i) + singleQuote);
			} else {
				sb.append(singleQuote + valueList.get(i) + singleQuote + ",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	private static String fillSimpleArg(String sql, Map<String, Object> param) {
		String[] patterns = {"\\$\\{(.*?)\\}","\\#\\{(.*?)\\}"};
		List<String> argList = RegexUtils.extractListData(sql, patterns);
		if (!argList.isEmpty()) {
			for (String arg : argList) {
				if(param.get(arg) == null) continue;
				sql = sql.replace("${" + arg + "}", String.valueOf(param.get(arg)));
				sql = sql.replace("#{" + arg + "}", String.valueOf(param.get(arg)));
			}
		}
		return sql;
	}
}
