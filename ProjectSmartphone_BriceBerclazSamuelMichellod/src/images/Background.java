/*
 * Background
 * Author : B. Berclaz
 * Date creation : 
 * Date last modification : 
 */

package images;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The Background class manage the background/wallpaper of the smartphone
 * 
 * @author Brice Berclaz
 * @see java.awt.Graphics
 * @see java.awt.Image
 * @see javax.imageio.ImageIO
 */
public class Background extends JPanel {

	private String backgroundPath = "images/backgroundDefault/Background01.jpg";

	/** Constructor of the Background class */
	public Background() {

	}

	@Override
	public void paintComponent(Graphics g) {

		try {
			Image img = ImageIO.read(new File(backgroundPath));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (IOException e) {
			Image img;
		}
	}

	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}
}
