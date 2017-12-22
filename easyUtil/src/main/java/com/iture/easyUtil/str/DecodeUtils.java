package com.iture.easyUtil.str;

import java.io.Serializable;

public class DecodeUtils {
	public static Serializable decode(Serializable input,Serializable out){
		if(input == null || "".equals(input.toString())){
			return out;
		}else{
			return input;
		}
	}
}
