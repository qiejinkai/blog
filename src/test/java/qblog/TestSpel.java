package qblog;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class TestSpel {

	
	public static void main(String[] args) {
		ExpressionParser eParser = new SpelExpressionParser();  
		
		String string= "T(com.qjk.qblog.util.RedisUtil).getKey('user',1)";
		String result = eParser.parseExpression(string).getValue(String.class);
		System.out.println(result);
	}
}
