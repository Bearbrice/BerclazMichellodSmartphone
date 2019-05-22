package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Calculatrice extends JPanel{
	
	private JPanel container = new JPanel();
	private boolean clicOperateur= false, update = false;
	private JLabel ecran = new JLabel();
	private double chiffre;
	private int sol;
	private String operateur="";
	// dim pour les chiffres dim2 pour les operations
	 private Dimension dim = new Dimension(50, 40);
	 private Dimension dim2 = new Dimension(50, 30);
	
	public Calculatrice() {
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JFrame all = new JFrame("Calculatrice");
		//setTitle("Calculette");
		this.setSize(300,320);
		//this.setLocationRelativeTo(null);
		//this.setResizable(false);
		initComposant();
		
		//this.setContentPane(container);
		//this.setVisible(true);
		
	
	}
	
	
	private void initComposant(){
	    Font police = new Font("Arial", Font.BOLD, 15);
	    Font f = new Font("Arial", Font.BOLD,16);
	    ecran = new JLabel("0");
	    ecran.setFont(police);
	    //On aligne les informations à droite dans le JLabel
	    ecran.setHorizontalAlignment(JLabel.RIGHT);
	    ecran.setPreferredSize(new Dimension(220, 20));
	    JPanel operation = new JPanel();      
	    operation.setPreferredSize(new Dimension(55, 225));
	    JPanel clavier = new JPanel();
	    clavier.setPreferredSize(new Dimension(165, 225));
	    JPanel panEcran = new JPanel();
	    panEcran.setPreferredSize(new Dimension(220, 30));
	    
	    panEcran.setLayout(new BorderLayout());
		clavier.setLayout(new GridLayout(4,5));
		operation.setLayout(new GridLayout(5,1));
	    
		
	
		
	   
		
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
		point.addActionListener(new ChiffreListener());
		point.setPreferredSize(dim);
		JButton egale = new JButton("=");
		egale.setPreferredSize(dim2);
		clavier.add(egale);
		egale.addActionListener(new EgalListener());
		egale.setFont(f);
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

		
		 panEcran.add(ecran);
		    panEcran.setBorder(BorderFactory.createLineBorder(Color.black));
		    container.add(panEcran, BorderLayout.NORTH);
		    container.add(clavier, BorderLayout.CENTER);
		    container.add(operation, BorderLayout.EAST);
		    
		    
		    
		
		   
	}
	
	private void calcul(){
        if(operateur.equals("+")){
        	chiffre = chiffre + Double.valueOf(ecran.getText().toString()).doubleValue();
            ecran.setText(String.valueOf(chiffre));
        }
        if(operateur.equals("-")){
        	chiffre = chiffre - Double.valueOf(ecran.getText().toString()).doubleValue();
            ecran.setText(String.valueOf(chiffre));
        }
        if(operateur.equals("*")){
                chiffre = chiffre * Double.valueOf(ecran.getText().toString()).doubleValue();
                ecran.setText(String.valueOf(chiffre));
        }
        if(operateur.equals("/")){
        	try{
            	chiffre = chiffre / Double.valueOf(ecran.getText().toString()).doubleValue();
                ecran.setText(String.valueOf(chiffre));
            }catch(ArithmeticException e){
               ecran.setText("0");
            }
        }
	}
		class ChiffreListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String str = ((JButton)e.getSource()).getText();
			if(update) 
			{
				update=false;
			}
			else 
			{
				if(!ecran.getText().equals("0")) 
					str=ecran.getText() + str;
			}
			ecran.setText(str);
		}
	}
	class EgalListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			update=true;
			calcul();
			clicOperateur=false;
		}
	}
	class PlusListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
		if(clicOperateur) {
			calcul();
		
			ecran.setText(String.valueOf(chiffre));
		}
		else{
			chiffre = Double.valueOf(ecran.getText()).doubleValue();
			clicOperateur= true;
		}
		operateur = "+";
		update = true;
		}
	}
	class MoinsListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
		if(clicOperateur)
		{
			calcul();
			ecran.setText(String.valueOf(chiffre));
		}
		else {
			chiffre = Double.valueOf(ecran.getText()).doubleValue();
			clicOperateur=true;
		}
		operateur="-";
		update=true;
		}
	}
	class MultiListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(clicOperateur) 
			{
				calcul();
				ecran.setText(String.valueOf(chiffre));
			}
			else {
				chiffre = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur=true;
			}
			update=true;
			operateur="*";
		}
	}
	class DivListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(clicOperateur) 
			{
				calcul();
				ecran.setText(String.valueOf(chiffre));
			}
			else {
				chiffre = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur=true;
			}
			operateur="/";
			update=true;
		}
	}
	class ResetListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			clicOperateur = false;
			update=true;
			chiffre=0;
			operateur="C";
			ecran.setText("0");
		}
	}
	}


