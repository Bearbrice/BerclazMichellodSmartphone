/*
 * Screen panel - Screen of the smartphone
 * Author: Brice Berclaz
 * Date creation: 
 * Date last modification: 
 */

package panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ScreenPanel extends JPanel {
	
	//Constructeur
	public ScreenPanel() 
	{		
		this.setLayout(new BorderLayout());
	}
	
	
	public void paintComponent(Graphics g)
	{
		try {

			Image img = ImageIO.read(new File("images/screen/Screen_350x580.jpg"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

		} catch (IOException e) {
			e.printStackTrace();
		}                

	} 
}

