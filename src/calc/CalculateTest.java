package calc;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculateTest {

	@Test
	public void test() {
		assertEquals("22.0+5", Calculate.replaceInnerBrackets("(3+6+8+5)+5"));
		assertEquals("-20.0+5", Calculate.replaceInnerBrackets("(5-5*5)+5"));
		assertEquals("10.0+10.0+5", Calculate.replaceInnerBrackets("(5+5)+(5+5)+5"));
		assertEquals("12.0+5", Calculate.replaceInnerBrackets("(2+(7+3))+5"));
		assertEquals("-3.0+5", Calculate.replaceInnerBrackets("(2+((-7+6)*5))+5"));
		assertEquals("-3.0+5", Calculate.replaceInnerBrackets("(2+(5/(-7+6)))+5"));
		assertEquals("-3.0+5", Calculate.replaceInnerBrackets("(6/(-2))+5"));
		assertEquals("1.0+5", Calculate.replaceInnerBrackets("((-5)+6)+5"));
//		Calculate c = new Calculate("(2+(5*(-7+6)))+5*(-1)");
		Calculate c = new Calculate("-3.0+5*-1.0");
		assertEquals("-8.0", String.valueOf(c.evaluateExpression()));
		
	}

}
