package contact;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import clock.ClockPanel;

public class TryContactPanel {
	public static void main(String[] args) {
		JFrame test = new JFrame();
		test.setVisible(true);

		ContactPanel contact = new ContactPanel();

		test.add(contact);

		test.setSize(new Dimension(800, 500));

	}
}
