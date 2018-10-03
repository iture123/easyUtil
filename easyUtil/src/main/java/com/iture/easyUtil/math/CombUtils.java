package com.iture.easyUtil.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 组合问题
 * @author DELL
 *
 */
public class CombUtils {

    /** 
     * 组合选择（从列表中选择n个组合） 
     * @param dataList 待选列表 
     * @param n 选择个数 
     */  
    public static void combinationSelect(List<List<String>> outList ,String[] dataList, int n) {  
      //  System.out.println(String.format("C(%d, %d) = %d", dataList.length, n, combination(dataList.length, n)));  
        combinationSelect(outList,dataList, 0, new String[n], 0);  
    }  
  
    /** 
     * 组合选择 
     * @param dataList 待选列表 
     * @param dataIndex 待选开始索引 
     * @param resultList 前面（resultIndex-1）个的组合结果 
     * @param resultIndex 选择索引，从0开始 
     */  
    private static void combinationSelect(List<List<String>> outList,String[] dataList, int dataIndex, String[] resultList, int resultIndex) {  
        int resultLen = resultList.length;  
        int resultCount = resultIndex + 1;  
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果  
        	//System.out.println("res:"+Arrays.asList(resultList));  
        	outList.add(Arrays.asList(resultList.clone()));
            return;  
        }  
  
        // 递归选择下一个  
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {  
            resultList[resultIndex] = dataList[i];  
            combinationSelect(outList,dataList, i + 1, resultList, resultIndex + 1);  
        }  
    }  
  
    /** 
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1 
     * @param n 
     * @return 
     */  
    public static long factorial(int n) {  
        return (n > 1) ? n * factorial(n - 1) : 1;  
    }  
  
    /** 
     * 计算排列数，即A(n, m) = n!/(n-m)! 
     * @param n 
     * @param m 
     * @return 
     */  
    public static long arrangement(int n, int m) {  
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;  
    }  
  
    /** 
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!) 
     * @param n 
     * @param m 
     * @return 
     */  
    public static long combination(int n, int m) {  
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;  
    }  
	public static void main(String[] args) {
		/*arrangementSelect(new String[] {  
                "1", "2", "3", "4"  
        }, 2);  */
		List<List<String>> outList = new ArrayList<>();
		 combinationSelect(outList,new String[] {  
                "1", "2", "3","4"
        }, 2);  
		for(List<String> row :outList) {
			System.out.println(row);
		}
	}
	

}
