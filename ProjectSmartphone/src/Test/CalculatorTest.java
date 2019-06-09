/*
 * CalculatorTest
 * Author: S. Michellod
 * Date creation: 27.05.2019
 * Date last modification: 07.06.2019
 */

package Test;

import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import calculator.CalculatorPanel;

/**
 * JUnit test case class Calculator
 * 
 * @author Samuel Michellod
 *
 */

class CalculatorTest {

	CalculatorPanel c = new CalculatorPanel();
	JLabel screen = new JLabel();
	String operator;
	double number;

	/**
	 * Test of the method calul
	 * 
	 * @author Samuel Michellod
	 */

	@Test
	void testCalcul() {

		/*
		 **********************************
		 * Test for the addition
		 **********************************
		 */

		// Set the first number to 5
		number = 5.0;
		c.setNumber(number);

		// Set the second number to 15
		screen.setText("15.0");
		c.setScreen(screen);

		// Set the operator to +
		operator = "+";
		c.setOperator(operator);

		c.calcul();

		if (!c.getScreen().getText().equals("20")) {
			fail("The addition is false");
		}

		/*
		 **********************************
		 * Test for the substraction
		 **********************************
		 */
		// Set the first number to 15
		number = 15.0;
		c.setNumber(number);

		// Set the second number to 5
		screen.setText("5.0");
		c.setScreen(screen);

		// Set the operator to -
		operator = "-";
		c.setOperator(operator);

		c.calcul();

		if (!c.getScreen().getText().equals("10")) {
			fail("The substration is false");
		}

		/*
		 **********************************
		 * Test for the multiplication
		 **********************************
		 */

		// Set the first number to 10
		number = 10.0;
		c.setNumber(number);

		// Set the second number to 10
		screen.setText("10.0");
		c.setScreen(screen);

		// Set the operator to *
		operator = "*";
		c.setOperator(operator);

		c.calcul();

		if (!c.getScreen().getText().equals("100")) {
			fail("The multiplication is false");
		}

		/*
		 **********************************
		 * Test of the division
		 **********************************
		 */

		// Set the first number to 20
		number = 20.0;
		c.setNumber(number);

		// Set the second number to 2
		screen.setText("2.0");
		c.setScreen(screen);

		// Set the operator to /
		operator = "/";
		c.setOperator(operator);

		c.calcul();

		if (!c.getScreen().getText().equals("10")) {
			fail("The division is false");
		}
	}
}
