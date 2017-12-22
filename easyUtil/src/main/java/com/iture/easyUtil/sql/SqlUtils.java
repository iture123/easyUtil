package com.iture.easyUtil.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.iture.easyUtil.regex.RegexUtils;

import net.sf.jsqlparser.JSQLParserException;

public class SqlUtils {
	public static void main(String[] args) throws JSQLParserException {
		Map<String, Object> param = new HashMap<>();
		param.put("age", 12);
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		//param.put("addr", "广州");
		param.put("name", list);
		String sql = "select t1.* from  t1, t2 where t1.id=t2.id and t1.city = '${city}' "
				+ "and age=#{age} and t2.name in ('${name}') and addr like '%#{addr}%'";
		//sql ="insert into stu(name,age)  select * from t2 where age=#{age} and t2.name in ('${name}') and addr like '%${addr}%'";
		//sql = "delete from stu where  age=#{age} and t2.name in ('${name}') and addr like '%${addr}%'";
		sql = SqlUtils.fillParam(sql, param);
		System.out.println("填充参数后sql："+sql);
	}
	/**
	 * 检验是否有sql 注入问题
	 * **/
	public static boolean IsSafeSql(String sql,Map<String, Object> param){
		String[] patterns = {"\\$\\{(.*?)\\}","\\#\\{(.*?)\\}"};
		List<String> argList = RegexUtils.extractListData(sql, patterns);
		if(! argList.isEmpty()){
			for(String arg : argList){
				String value = String.valueOf(param.get(arg));
				if(value.contains(" or ")){
					return false;
				}
				value = value.replaceAll("\\s","");
				if(value.contains(";drop") || value.contains(";delete") || value.contains(";truncate")
						||value.contains(";insert")||value.contains(";update")
						||value.contains(";select")||value.contains(";exec")||value.contains(";call")){
					return false;
				}
			}
		}
		return true;
	}
	/***
	 * 输入需要补充参数的sql
	 * 返回处理后的sql
	 * sql 中的参数 ${arg}表示可选，#{arg}表示必填
	 * @throws JSQLParserException 
	 * ***/
	public static String fillParam(String sql,Map<String,Object> param) throws JSQLParserException{
		if(IsSafeSql(sql, param)){
			Set<String> notValueArgs = SqlArgUtils.getNotValueRequiredArgs(sql, param);
			if(! notValueArgs.isEmpty()){
				throw new SqlArgNotSatisfiedException(notValueArgs);
			}
			sql = SqlArgUtils.fillArg(sql, param);
			sql  = NullExpressionUtils.dealNullArg(sql);
			return sql;
		}else{
			throw new SqlNotSafeException(param.toString());
		}
	}
	
}
