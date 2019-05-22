package clock;

import java.awt.Dimension;

import javax.swing.JFrame;



public class ClockPanelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame test = new JFrame();
		test.setVisible(true);
		
		
		
		
		ClockPanel clock = new ClockPanel();
		test.add(clock);
		
		test.setSize(new Dimension(800, 500));
	}

}
