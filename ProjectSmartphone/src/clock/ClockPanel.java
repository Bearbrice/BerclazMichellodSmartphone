/*
 * Clock App
 * Author: Brice Berclaz
 * Date creation: 24.04.2019
 * Date last modification: 26.04.2019
 */

package clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClockPanel extends JPanel {
	
	JLabel heure = new JLabel();
	DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	Timer timer = new Timer(0, new SetTime());
	
	JPanel banner = new JPanel();
	JPanel center = new JPanel();
	
	JLabel title = new JLabel("HEURE SUISSE UTC+2");
	
	//Constructeur
	public ClockPanel() {
		clockSettings();
		
		timer.start();
		
		//Ajout du titre
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setForeground(Color.WHITE);
		
	    banner.add(title);
	    banner.setBackground(Color.GRAY);
	    
	    //Ajout de l'heure
	    heure.setFont(new Font("Serif", Font.BOLD, 50));
	    
	    center.add(heure);
	    center.setBackground(Color.WHITE);
		
	    
	    //Ajout des panels
	    this.add(banner, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
	}

	private void clockSettings() {
		this.setPreferredSize(new Dimension(480, 40));
		this.setLayout(new BorderLayout());
	}
	
	class SetTime implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Calendar now = Calendar.getInstance();

			heure.setText(sdf.format(now.getTime()));
		}
	}
}
