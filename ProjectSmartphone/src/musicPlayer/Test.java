package musicPlayer;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame test = new JFrame();
		test.setVisible(true);
		
		
		
		MusicPlayerPanel mpn = new MusicPlayerPanel();
		test.add(mpn);
		
		test.setSize(new Dimension(800, 500));
		
		   
	}

}
