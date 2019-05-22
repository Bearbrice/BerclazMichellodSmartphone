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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel {

	private String defaultBackground = "images/backgrounds/Background01.jpg";

	// Constructeur
	public Background() {

	}

	@Override
	public void paintComponent(Graphics g) {

		try {
			Image img = ImageIO.read(new File(defaultBackground));
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

	public String getDefaultBackground() {
		return defaultBackground;
	}

	public void setDefaultBackground(String defaultBackground) {
		this.defaultBackground = defaultBackground;
	}

}
