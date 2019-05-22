import java.awt.Dimension;

import javax.swing.JFrame;

public class Gallery {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame test = new JFrame();
		
		test.setVisible(true);
		
		
		Galerie galerie = new Galerie();
		
		//galerie.setVisible(true);
		
		test.add(galerie);
		
		test.setSize(new Dimension(400,720));
		test.setResizable(false);
	}

}
