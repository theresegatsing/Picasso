package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Plus;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.Sin;
import picasso.parser.language.expressions.T;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;

/**
 * Tests for the T (time variable) animation feature
 * 
 * @author Luis Coronel
 * @author Asya Yurkovskaya
 */
public class TTests {

	private static ExpressionTreeGenerator parser;
	private static final double EPSILON = 1e-9;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	@BeforeEach
	public void setUp() throws Exception {
		T.resetTime();
		T.setHasTime(false);
	}

	@Test
	public void testTEvaluationInitialValue() {
		T myTree = new T();
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));
	}

	@Test
	public void testTEvaluationAfterIncrement() {
		T myTree = new T();
		
		T.increaseTime();
		assertEquals(new RGBColor(0.02, 0.02, 0.02), myTree.evaluate(0, 0));
		
		T.increaseTime();
		assertEquals(new RGBColor(0.04, 0.04, 0.04), myTree.evaluate(0.5, 0.5));
	}

	@Test
	public void testResetTime() {
		T myTree = new T();
		
		T.increaseTime();
		T.increaseTime();
		
		T.resetTime();
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));
	}

	@Test
	public void tFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("t");
		assertEquals(new T(), e);

		T.setHasTime(false);
		e = parser.makeExpression("x + t");
		assertEquals(new Plus(new X(), new T()), e);
	}

	@Test
	public void testToString() {
		ExpressionTreeNode t = new T();
		assertEquals("t", t.toString());
	}

	@Test
	public void testEquals() {
		ExpressionTreeNode t1 = new T();
		ExpressionTreeNode t2 = new T();
		ExpressionTreeNode x = new X();

		assertEquals(t1, t1);
		assertEquals(t1, t2);

		assertNotEquals(t1, x);
	}

	@Test
	public void testTimeIsStatic() {
		T t1 = new T();
		T t2 = new T();
		
		T.increaseTime();
		
		RGBColor result1 = t1.evaluate(0, 0);
		RGBColor result2 = t2.evaluate(0, 0);
		
		assertEquals(result1.getRed(), result2.getRed(), EPSILON);
	}

	@Test
	public void testTReaches1After50Increments() {
		T myTree = new T();
		
		for (int i = 0; i < 50; i++) {
			T.increaseTime();
		}
		// from 0.2
		RGBColor result = myTree.evaluate(0, 0);
		assertEquals(1.0, result.getRed(), EPSILON);
	}
}