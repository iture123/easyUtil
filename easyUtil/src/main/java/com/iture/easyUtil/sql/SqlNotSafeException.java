package com.iture.easyUtil.sql;

public class SqlNotSafeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code=null;
	public SqlNotSafeException(String code){
		this.code = code;
	}
	@Override
	public String getMessage() {
		return "sql 参数的值可能存在注入漏洞："+code;
	}
}
