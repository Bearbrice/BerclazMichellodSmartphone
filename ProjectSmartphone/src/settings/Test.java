package settings;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame test = new JFrame();
		test.setVisible(true);
		
		SettingsPanel settings = new SettingsPanel();
		test.add(settings);
		
		test.setSize(new Dimension(400, 720));
			   
	}
}
