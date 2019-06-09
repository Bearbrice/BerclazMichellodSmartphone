/*
 * Contouring panel - Hardware view (coutouring) of a typic smartphone
 * Author: B. Berclaz
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

/**
 * The ContactPanel class displays a panel with all contacts and offers the
 * possibilities of adding, modifying and deleting.
 * 
 * @author Brice Berclaz
 * @see javax.imageio.ImageIO
 * @see java.awt.Graphics
 * @see java.awt.Image
 * @see javax.imageio.ImageIO
 */
public class ContouringPanel extends JPanel {

	/** Constructor of the ContactPanel class */
	public ContouringPanel() {
		this.setLayout(new BorderLayout());
	}

	/** Paint a component (image) */
	@Override
	public void paintComponent(Graphics g) {
		try {

			Image img = ImageIO.read(new File("images/smartphone/smartphone-contouring_960_720.png"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
