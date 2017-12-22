package com.iture.easyUtil.sql;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.update.Update;

class NullExpressionUtils {
	static final String IDENTIFYLING = "-000";
	public static String dealNullArg(String sql) throws JSQLParserException{
		sql = preDealSql(sql);
		CCJSqlParserManager pm = new CCJSqlParserManager();
		Statement statement = pm.parse(new StringReader(sql));
		List<Expression> exList = new ArrayList<>();
		if(statement instanceof Select){
			Select select = (Select) statement;
			addExBySelectBody(select.getSelectBody(),exList);
		}else if(statement instanceof Update){
			Expression update = ((Update) statement).getWhere();
			addExByFromExpression(update, exList);
		}else if(statement instanceof Delete){
			Expression expression = ((Delete) statement).getWhere();
			addExByFromExpression(expression, exList);
		}else if(statement instanceof Insert){
			ItemsList itemList = ((Insert) statement).getItemsList();
			if(itemList instanceof SubSelect){
				addExFromSubSelect((SubSelect)itemList,exList);
			}
		}
		sql = statement.toString();
		for(Expression ex : exList){
			if(ex.toString().contains(IDENTIFYLING)){
				sql = sql.replace(ex.toString(), "1 = 1");
			}
		}
		sql = sql.replace("AND 1 = 1", "").replace("OR 1 = 1", "");
		return sql;
	}
	private static void addExBySelectBody(SelectBody selectBody,List<Expression> exList){
		if(selectBody instanceof PlainSelect){
			addExByPlainSelect((PlainSelect)selectBody,exList);
		}else if(selectBody instanceof SetOperationList){
			List<PlainSelect> plainSelects = ((SetOperationList) selectBody).getPlainSelects();
			for(PlainSelect plainSelect:plainSelects){
				addExByPlainSelect(plainSelect, exList);
			}
		}
	}
	private static void addExByPlainSelect(PlainSelect plainSelect,List<Expression> exList){
		FromItem fromItem = plainSelect.getFromItem();
		addExByFromItem(fromItem,exList);
		Expression expression = plainSelect.getWhere();
		addExByFromExpression(expression, exList);
		List<Join> joins = plainSelect.getJoins();
		if(joins !=null){
			for(Join join:joins){
				FromItem rightItem = join.getRightItem();
				addExByFromItem(rightItem, exList);
			}
		}
	}
	private static void addExByFromItem(FromItem fromItem,List<Expression> exList){
		if(fromItem instanceof SubSelect){
			addExFromSubSelect( ((SubSelect) fromItem),exList);
		}
	}
	private static void addExFromSubSelect(SubSelect subSelect,List<Expression> exList){
		SelectBody selectBody = subSelect.getSelectBody();
		addExBySelectBody(selectBody,exList);
	}
	private static void addExByFromExpression(Expression expression,List<Expression> exList){
		if(expression==null)return;
		if(expression instanceof BinaryExpression){
			Expression leftExpression = ((BinaryExpression) expression).getLeftExpression();
			Expression rightExpression = ((BinaryExpression) expression).getRightExpression();
			if(!(expression instanceof AndExpression || expression instanceof OrExpression)){
				if(rightExpression instanceof StringValue  ){
					exList.add(expression);
				}
			}
			addExByFromExpression(leftExpression,exList);
			addExByFromExpression(rightExpression,exList);
		
		}else if(expression instanceof Column || expression instanceof StringValue||expression instanceof LongValue
				||expression instanceof Function){ 
			
		}else if(expression instanceof Parenthesis){
			addExByFromExpression(((Parenthesis) expression).getExpression(), exList);;
		}else if(expression instanceof IsNullExpression){
			//exList.add(expression);
		}else if(expression instanceof SubSelect){
			SelectBody selectBody = ((SubSelect) expression).getSelectBody();
			addExBySelectBody(selectBody, exList);
		}else if(expression instanceof InExpression){
			exList.add(expression);
		}
	}
	private  static String preDealSql(String sql){
		String p = "\\$\\{.*?\\}";
		Pattern pattern = Pattern.compile(p , Pattern.DOTALL);
		Matcher m = pattern.matcher(sql);
		while (m.find()) {
			sql = sql.replace(m.group(0), IDENTIFYLING);
		}
		return sql;
	}
}
