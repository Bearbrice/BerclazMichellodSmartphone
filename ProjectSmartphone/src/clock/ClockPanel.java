/*
 * Clock App
 * Author: B. Berclaz
 * Date creation: 24.04.2019
 * Date last modification: 26.04.2019
 */

package clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The ClockPanel class displays the computer time in real time in a panel
 *
 * @author Brice Berclaz
 * @see java.text.DateFormat
 */

public class ClockPanel extends JPanel {

	private DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private Timer timer = new Timer(0, new SetTime());

	private JPanel banner = new JPanel();
	private JPanel center = new JPanel();

	private JLabel title = new JLabel("SWISS TIME UTC+2");
	private JLabel hour = new JLabel();

	/**
	 * Constructor of the ClockPanel class
	 */
	public ClockPanel() {

		setClockPanel();

		timer.start();

		/* title parameters */
		title.setFont(new Font("Serif", Font.BOLD, 30));
		title.setForeground(Color.WHITE);

		/* hour parameters */
		hour.setFont(new Font("Serif", Font.BOLD, 50));

		/* Adding elements to panels */
		banner.add(title);
		banner.setBackground(Color.GRAY);

		center.add(hour);
		center.setBackground(Color.WHITE);

		/* Adding panels to the main panel */
		this.add(banner, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Sets up the panel of the class
	 */
	private void setClockPanel() {
		this.setPreferredSize(new Dimension(480, 40));
		this.setLayout(new BorderLayout());
	}

	/**
	 * The SetTime class implements an action listener that displays the time in
	 * real time
	 * 
	 * @author Brice Berclaz
	 * @see java.util.Calendar
	 */

	private class SetTime implements ActionListener {
		/**
		 * Sets up the label hour
		 * 
		 * @param ActionEvent e
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar now = Calendar.getInstance();

			hour.setText(sdf.format(now.getTime()));
		}
	}
}
