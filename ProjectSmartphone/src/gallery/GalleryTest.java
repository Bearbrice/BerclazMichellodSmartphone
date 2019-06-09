package gallery;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GalleryTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame test = new JFrame();
		
		test.setVisible(true);
		
		
		GalleryPanel galerie = new GalleryPanel();
		
		//galerie.setVisible(true);
		
		test.add(galerie);
		
		test.setSize(new Dimension(400,720));
		test.setResizable(false);
	}

}
