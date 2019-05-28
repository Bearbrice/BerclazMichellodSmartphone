/*
 * Background picture
 * Author: Brice Berclaz
 * Date creation: 
 * Date last modification: 
 */

package images;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import contact.ContactData;

public class Background extends JPanel {
	
	private String backgroundPath = "images/backgrounds/Background01.jpg";

	// Constructeur
	public Background() {
		
	}
	
	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}
	
	

	@Override
	public void paintComponent(Graphics g) {

		try {
			Image img = ImageIO.read(new File(backgroundPath));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (IOException e) {
			Image img;
			/*
			 * try { img = ImageIO.read(new File(urlImageBase)); g.drawImage(img, 0, 0,
			 * this.getWidth(), this.getHeight(), this); } catch (IOException e1) {
			 * e1.printStackTrace(); }
			 */
		}
	}
	


//	public String getDefaultBackground() {
//		return defaultBackground;
//	}
//
//	public void setDefaultBackground(String defaultBackground) {
//		this.defaultBackground = defaultBackground;
//	}

}
