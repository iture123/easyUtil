package com.iture.easyUtil.sql;

import java.util.Set;

public class SqlArgNotSatisfiedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Set<String> NotValueRequiredArgs = null;
	public SqlArgNotSatisfiedException(Set<String> NotValueRequiredArgs) {
		this.NotValueRequiredArgs = NotValueRequiredArgs;
	}
	@Override
	public String getMessage() {
		return "以下必填参数为空:"+this.NotValueRequiredArgs;
	}
}
