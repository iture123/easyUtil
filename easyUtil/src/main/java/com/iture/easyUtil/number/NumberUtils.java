package com.iture.easyUtil.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {
	
    /**保留任意个小数位的方法(四舍五入)  
    * @param input 
    * @param scale要保留的小数位
    * @return
    */
    public static String round(Object input, int scale) {
        if(input == null || input.toString().equals("")){
            return "";
        }
        input =input.toString().replace(",", "");
        BigDecimal bd = new BigDecimal(Double.parseDouble(input.toString()));
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
        // 转换为非科学计算
        double temp = bd.doubleValue();
        StringBuilder sb = new StringBuilder("0");
        if(scale > 0){
            sb.append(".");
        }
        for (int i = 0; i < scale; i++) {
            sb.append("0");
        }
        DecimalFormat df = new DecimalFormat(sb.toString());
        return df.format(temp);
    }
}
