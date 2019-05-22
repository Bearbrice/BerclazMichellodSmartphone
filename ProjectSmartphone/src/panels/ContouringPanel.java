/*
 * Contouring panel - Hardware view (coutouring) of a typic smartphone
 * Author: Brice Berclaz
 * Date creation: 16.04.2019
 * Date last modification: 07.05.2019
 */

package panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ContouringPanel extends JPanel {
	
	//Constructeur
	public ContouringPanel() 
	{		
		this.setLayout(new BorderLayout());
	}
	
	
	public void paintComponent(Graphics g)
	{
		try {

			Image img = ImageIO.read(new File("images/smartphone/smartphone-contouring_960_720.png"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

		} catch (IOException e) {
			e.printStackTrace();
		}                

	} 
}

