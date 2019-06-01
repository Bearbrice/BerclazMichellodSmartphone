/*
 * Calculator App
 * Author: Samuel Michellod
 * Date creation: 
 * Date last modification: 14.05.2019
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
	private boolean clicOperateur = false, update = false;
	private JLabel ecran = new JLabel();
	private double chiffre;
	private int sol;
	private String operateur = "";

	private JPanel banner = new JPanel(); // headline NORTH
	private JLabel title = new JLabel("CALCULATOR");

	// dim pour les chiffres dim2 pour les operations
	private Dimension dim = new Dimension(50, 40);
	private Dimension dim2 = new Dimension(50, 30);

	public CalculatorPanel() {
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JFrame all = new JFrame("Calculatrice");
		// setTitle("Calculette");
		// this.setSize(300,320);
		// this.setLocationRelativeTo(null);
		// this.setResizable(false);
		this.setPreferredSize(new Dimension(638, 188));
		this.setVisible(true);
		this.setOpaque(false);
		// this.setLayout(new BorderLayout());

		// Ajout du titre
		banner.add(title);
		banner.setBackground(Color.GRAY);

		title.setFont(new Font("Serif", Font.BOLD, 40));
		title.setForeground(Color.WHITE);

		initComposant();

		this.add(banner, BorderLayout.NORTH);
		this.add(container, BorderLayout.CENTER);

		// this.setContentPane(container);
		// this.setVisible(true);
	}

	private void initComposant() {
		Font police = new Font("Arial", Font.BOLD, 35);
		Font f = new Font("Arial", Font.BOLD, 16);

		ecran = new JLabel("0");
		ecran.setFont(police);

		// On aligne les informations à droite dans le JLabel
		ecran.setHorizontalAlignment(JLabel.RIGHT);
		ecran.setPreferredSize(new Dimension(220, 20));

		JPanel operation = new JPanel();
		operation.setPreferredSize(new Dimension(105, 265));

		JPanel clavier = new JPanel();

		// original +40 +40
		clavier.setPreferredSize(new Dimension(165, 225));

		// clavier.setPreferredSize(new Dimension(205, 265));

		JPanel panEcran = new JPanel();
		// original -40 -40
		panEcran.setPreferredSize(new Dimension(260, 70));

		panEcran.setLayout(new BorderLayout());
		clavier.setLayout(new GridLayout(4, 5));
		operation.setLayout(new GridLayout(5, 1));

		JButton t1 = new JButton("1");
		clavier.add(t1);
		t1.addActionListener(new ChiffreListener());
		t1.setPreferredSize(dim);

		JButton t2 = new JButton("2");
		clavier.add(t2);
		t2.setPreferredSize(dim);
		t2.addActionListener(new ChiffreListener());

		JButton t3 = new JButton("3");
		clavier.add(t3);
		t3.setPreferredSize(dim);
		t3.addActionListener(new ChiffreListener());

		JButton t4 = new JButton("4");
		clavier.add(t4);
		t4.setPreferredSize(dim);
		t4.addActionListener(new ChiffreListener());

		JButton t5 = new JButton("5");
		clavier.add(t5);
		t5.setPreferredSize(dim);
		t5.addActionListener(new ChiffreListener());

		JButton t6 = new JButton("6");
		clavier.add(t6);
		t6.setPreferredSize(dim);
		t6.addActionListener(new ChiffreListener());

		JButton t7 = new JButton("7");
		clavier.add(t7);
		t7.addActionListener(new ChiffreListener());
		t7.setPreferredSize(dim);

		JButton t8 = new JButton("8");
		clavier.add(t8);
		t8.setPreferredSize(dim);
		t8.addActionListener(new ChiffreListener());

		JButton t9 = new JButton("9");
		clavier.add(t9);
		t9.setPreferredSize(dim);
		t9.addActionListener(new ChiffreListener());

		JButton zero = new JButton("0");
		clavier.add(zero);
		zero.setPreferredSize(dim);
		zero.addActionListener(new ChiffreListener());

		JButton point = new JButton(".");
		clavier.add(point);
		point.addActionListener(new PointListener());
		point.setPreferredSize(dim);

		JButton egale = new JButton("=");
		egale.setPreferredSize(dim2);
		clavier.add(egale);
		egale.addActionListener(new EgalListener());
		// egale.setFont(f);

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
		moins.addActionListener(new MoinsListener());
		moins.setPreferredSize(dim2);

		JButton fois = new JButton("*");
		operation.add(fois);
		fois.addActionListener(new MultiListener());
		fois.setPreferredSize(dim2);

		JButton divise = new JButton("/");
		operation.add(divise);
		divise.addActionListener(new DivListener());
		divise.setPreferredSize(dim2);

		// SET FONTS
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

		// SET FOREGROUNDS
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
		// c.setForeground(fg);
		fois.setForeground(fg);
		point.setForeground(fg);
		egale.setForeground(fg);

		// SET BACKGROUNDS
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

		// panEcran.setBackground(background);
		// clavier.setBackground(background);
		// operation.setBackground(background);
		// container.setBackground(background);

		panEcran.add(ecran);
		panEcran.setBorder(BorderFactory.createLineBorder(Color.black));

		container.setLayout(new BorderLayout());
		container.add(panEcran, BorderLayout.NORTH);
		container.add(clavier, BorderLayout.CENTER);
		container.add(operation, BorderLayout.EAST);

	}

	private void calcul() {
		if (operateur.equals("+")) {
			chiffre = chiffre + Double.valueOf(ecran.getText().toString()).doubleValue();
			ecran.setText(String.valueOf(chiffre));
		}
		if (operateur.equals("-")) {
			chiffre = chiffre - Double.valueOf(ecran.getText().toString()).doubleValue();
			ecran.setText(String.valueOf(chiffre));
		}
		if (operateur.equals("*")) {
			chiffre = chiffre * Double.valueOf(ecran.getText().toString()).doubleValue();
			ecran.setText(String.valueOf(chiffre));
		}
		if (operateur.equals("/")) {
			try {
				chiffre = chiffre / Double.valueOf(ecran.getText().toString()).doubleValue();
				ecran.setText(String.valueOf(chiffre));
			} catch (ArithmeticException e) {
				ecran.setText("0");
			}
		}
		double y = Math.round(chiffre);
		if (chiffre - y == 0) {
			ecran.setText(String.valueOf((int) chiffre));
		} else
			ecran.setText(String.valueOf(chiffre));
	}

	class ChiffreListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String str = ((JButton) e.getSource()).getText();
			if (update) {
				update = false;
			} else {
				if (!ecran.getText().equals("0"))
					str = ecran.getText() + str;
			}
			ecran.setText(str);
		}
	}

	class EgalListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			update = true;
			calcul();
			clicOperateur = false;
		}
	}

	class PlusListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (clicOperateur) {
				calcul();

				ecran.setText(String.valueOf(chiffre));
			} else {
				chiffre = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			operateur = "+";
			update = true;
		}
	}

	class MoinsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (clicOperateur) {
				calcul();
				ecran.setText(String.valueOf(chiffre));
			} else {
				chiffre = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			operateur = "-";
			update = true;
		}
	}

	class MultiListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (clicOperateur) {
				calcul();
				ecran.setText(String.valueOf(chiffre));
			} else {
				chiffre = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			update = true;
			operateur = "*";
		}
	}

	class DivListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (clicOperateur) {
				calcul();
				ecran.setText(String.valueOf(chiffre));
			} else {
				chiffre = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			operateur = "/";
			update = true;
		}
	}

	class ResetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			clicOperateur = false;
			update = true;
			chiffre = 0;
			operateur = "C";
			ecran.setText("0");
		}
	}

	class PointListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String str = ((JButton) e.getSource()).getText();
			if (update) {
				update = false;
			} else {
				if (ecran.getText().equals("0"))
					str = ecran.getText() + str;
			}
			if (!ecran.getText().equals("0")) {
				str = ecran.getText() + str;
			}
			ecran.setText(str);
		}
	}

}
