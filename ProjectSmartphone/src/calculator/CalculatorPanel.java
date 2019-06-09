/*
 * Calculator App
 * Author: S. Michellod
 * Date creation: 20.04.2019
 * Date last modification: 07.06.2019
 */

package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalculatorPanel extends JPanel {

	private JPanel container = new JPanel();
	private boolean clicOperator = false, update = false;
	private JLabel screen = new JLabel();
	private double number;
	private String operator = "";

	private JPanel banner = new JPanel(); // headline NORTH
	private JLabel title = new JLabel("CALCULATOR");

	// dim for the numbers dim2 for the operations
	private Dimension dim = new Dimension(50, 40);
	private Dimension dim2 = new Dimension(50, 30);

	/** Constructor of the CalculatorPanel class */
	public CalculatorPanel() {

		this.setPreferredSize(new Dimension(638, 188));
		this.setVisible(true);
		this.setOpaque(false);

		// Adding the title
		banner.add(title);
		banner.setBackground(Color.GRAY);

		title.setFont(new Font("Serif", Font.BOLD, 40));
		title.setForeground(Color.WHITE);

		initComposant();

		this.add(banner, BorderLayout.NORTH);
		this.add(container, BorderLayout.CENTER);

	}

	/**
	 * The component of the picture and the buttons
	 */

	private void initComposant() {
		Font police = new Font("Arial", Font.BOLD, 35);
		Font f = new Font("Arial", Font.BOLD, 16);

		screen = new JLabel("0");
		screen.setFont(police);

		/* We align the information on the right in the JLabel */
		screen.setHorizontalAlignment(JLabel.RIGHT);
		screen.setPreferredSize(new Dimension(220, 20));

		JPanel operation = new JPanel();
		operation.setPreferredSize(new Dimension(105, 265));

		JPanel keyboard = new JPanel();

		keyboard.setPreferredSize(new Dimension(165, 225));

		JPanel panEcran = new JPanel();

		panEcran.setPreferredSize(new Dimension(260, 70));

		panEcran.setLayout(new BorderLayout());
		keyboard.setLayout(new GridLayout(4, 5));
		operation.setLayout(new GridLayout(5, 1));

		JButton t1 = new JButton("1");
		keyboard.add(t1);
		t1.addActionListener(new NumberListener());
		t1.setPreferredSize(dim);

		JButton t2 = new JButton("2");
		keyboard.add(t2);
		t2.setPreferredSize(dim);
		t2.addActionListener(new NumberListener());

		JButton t3 = new JButton("3");
		keyboard.add(t3);
		t3.setPreferredSize(dim);
		t3.addActionListener(new NumberListener());

		JButton t4 = new JButton("4");
		keyboard.add(t4);
		t4.setPreferredSize(dim);
		t4.addActionListener(new NumberListener());

		JButton t5 = new JButton("5");
		keyboard.add(t5);
		t5.setPreferredSize(dim);
		t5.addActionListener(new NumberListener());

		JButton t6 = new JButton("6");
		keyboard.add(t6);
		t6.setPreferredSize(dim);
		t6.addActionListener(new NumberListener());

		JButton t7 = new JButton("7");
		keyboard.add(t7);
		t7.addActionListener(new NumberListener());
		t7.setPreferredSize(dim);

		JButton t8 = new JButton("8");
		keyboard.add(t8);
		t8.setPreferredSize(dim);
		t8.addActionListener(new NumberListener());

		JButton t9 = new JButton("9");
		keyboard.add(t9);
		t9.setPreferredSize(dim);
		t9.addActionListener(new NumberListener());

		JButton zero = new JButton("0");
		keyboard.add(zero);
		zero.setPreferredSize(dim);
		zero.addActionListener(new NumberListener());

		JButton point = new JButton(".");
		keyboard.add(point);
		point.addActionListener(new PointListener());
		point.setPreferredSize(dim2);

		JButton egale = new JButton("=");
		egale.setPreferredSize(dim2);
		keyboard.add(egale);
		egale.addActionListener(new EgalListener());

		JButton c = new JButton("C");
		operation.add(c);
		c.addActionListener(new ResetListener());
		c.setPreferredSize(dim2);
		c.setForeground(Color.RED);

		JButton plus = new JButton("+");
		operation.add(plus);
		plus.addActionListener(new PlusListener());
		plus.setPreferredSize(dim2);

		JButton moins = new JButton("-");
		operation.add(moins);
		moins.addActionListener(new MinusListener());
		moins.setPreferredSize(dim2);

		JButton fois = new JButton("*");
		operation.add(fois);
		fois.addActionListener(new MultiListener());
		fois.setPreferredSize(dim2);

		JButton divise = new JButton("/");
		operation.add(divise);
		divise.addActionListener(new DivListener());
		divise.setPreferredSize(dim2);

		Font touche = new Font("Arial", Font.BOLD, 30);
		t1.setFont(touche);
		t2.setFont(touche);
		t3.setFont(touche);
		t4.setFont(touche);
		t5.setFont(touche);
		t6.setFont(touche);
		t7.setFont(touche);
		t8.setFont(touche);
		t9.setFont(touche);
		zero.setFont(touche);
		plus.setFont(touche);
		moins.setFont(touche);
		fois.setFont(touche);
		divise.setFont(touche);
		c.setFont(touche);
		point.setFont(touche);
		egale.setFont(touche);

		Color fg = Color.WHITE;
		t1.setForeground(fg);
		t2.setForeground(fg);
		t3.setForeground(fg);
		t4.setForeground(fg);
		t5.setForeground(fg);
		t6.setForeground(fg);
		t7.setForeground(fg);
		t8.setForeground(fg);
		t9.setForeground(fg);
		zero.setForeground(fg);
		plus.setForeground(fg);
		moins.setForeground(fg);
		divise.setForeground(fg);
		fois.setForeground(fg);
		point.setForeground(fg);
		egale.setForeground(fg);

		Color background = Color.BLACK;
		t1.setBackground(background);
		t2.setBackground(background);
		t3.setBackground(background);
		t4.setBackground(background);
		t5.setBackground(background);
		t6.setBackground(background);
		t7.setBackground(background);
		t8.setBackground(background);
		t9.setBackground(background);
		zero.setBackground(background);
		plus.setBackground(background);
		moins.setBackground(background);
		fois.setBackground(background);
		divise.setBackground(background);
		c.setBackground(background);
		point.setBackground(background);
		egale.setBackground(background);

		panEcran.add(screen);
		panEcran.setBorder(BorderFactory.createLineBorder(Color.black));

		container.setLayout(new BorderLayout());
		container.add(panEcran, BorderLayout.NORTH);
		container.add(keyboard, BorderLayout.CENTER);
		container.add(operation, BorderLayout.EAST);

	}

	/**
	 * Method that will make the calcul according to the operateur
	 */

	public void calcul() {
		if (operator.equals("+")) {
			number = number + Double.valueOf(screen.getText().toString()).doubleValue();
			screen.setText(String.valueOf(number));
		}
		if (operator.equals("-")) {
			number = number - Double.valueOf(screen.getText().toString()).doubleValue();
			screen.setText(String.valueOf(number));
		}
		if (operator.equals("*")) {
			number = number * Double.valueOf(screen.getText().toString()).doubleValue();
			screen.setText(String.valueOf(number));
		}
		if (operator.equals("/")) {
			try {
				number = number / Double.valueOf(screen.getText().toString()).doubleValue();
				screen.setText(String.valueOf(number));
			} catch (ArithmeticException e) {
				screen.setText("0");
			}
		}
		/**
		 * Set the screen to an int if the number finish by .0
		 */

		double y = Math.round(number);
		if (number - y == 0) {
			screen.setText(String.valueOf((int) number));
		} else
			screen.setText(String.valueOf(number));
	}

	/**
	 * Listener for the numbers in the keyboard
	 */

	class NumberListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String str = ((JButton) e.getSource()).getText();
			if (update) {
				update = false;
			} else {
				if (!screen.getText().equals("0"))
					str = screen.getText() + str;
			}
			screen.setText(str);
		}
	}

	/**
	 * Listener of the equals button
	 */

	class EgalListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			update = true;
			calcul();
			clicOperator = false;
		}
	}

	/**
	 * Listener of the plus button
	 */

	class PlusListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (clicOperator) {
				calcul();

				screen.setText(String.valueOf(number));
			} else {
				number = Double.valueOf(screen.getText()).doubleValue();
				clicOperator = true;
			}
			operator = "+";
			update = true;
		}
	}

	/**
	 * Listener of the minus button
	 */

	class MinusListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (clicOperator) {
				calcul();
				screen.setText(String.valueOf(number));
			} else {
				number = Double.valueOf(screen.getText()).doubleValue();
				clicOperator = true;
			}
			operator = "-";
			update = true;
		}
	}

	/**
	 * Listener of the multiplication button
	 */

	class MultiListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (clicOperator) {
				calcul();
				screen.setText(String.valueOf(number));
			} else {
				number = Double.valueOf(screen.getText()).doubleValue();
				clicOperator = true;
			}
			update = true;
			operator = "*";
		}
	}

	/**
	 * Listener of the division button
	 */

	class DivListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (clicOperator) {
				calcul();
				screen.setText(String.valueOf(number));
			} else {
				number = Double.valueOf(screen.getText()).doubleValue();
				clicOperator = true;
			}
			operator = "/";
			update = true;
		}
	}

	/**
	 * Listener of the reset button
	 */

	class ResetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			clicOperator = false;
			update = true;
			number = 0;
			operator = "C";
			screen.setText("0");
		}
	}

	/**
	 * Listener of the point button
	 */

	class PointListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String str = ((JButton) e.getSource()).getText();
			if (update) {
				update = false;
			} else {
				if (screen.getText().equals("0"))
					str = screen.getText() + str;
			}
			if (!screen.getText().equals("0")) {
				str = screen.getText() + str;
			}
			screen.setText(str);
		}
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public JLabel getScreen() {
		return screen;
	}

	public void setScreen(JLabel screen) {
		this.screen = screen;
	}
}
