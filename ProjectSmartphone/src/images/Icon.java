/*
 * Background picture
 * Author : B. Berclaz
 * Date creation : 
 * Date last modification : 
 */

package images;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 * The Icon class allows a picture to be a JButton
 * 
 * @author Brice Berclaz
 * @see java.awt.Graphics2D
 */
public class Icon extends JButton {

	private String location = "";

	private int large = 10;
	private int length = 10;

	/** Constructor of the Icon class */
	public Icon(String location, int large, int length) {

		this.location = location;
		this.length = length;
		this.large = large;

		setPreferredSize(new Dimension(large, length));

		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);

		/*
		 * Assign a refresh task. NB: this task is severely added in this case. Change
		 * if not necessary
		 */
		this.addChangeListener((fx) -> {
			refresh();
		});
	}

	/** Repaint the icon */
	public void refresh() {
		/*
		 * Although the method is called by itself, an emergency exit must be provided:
		 * refresh the display. Because some components do not manage it well.
		 */
		this.repaint();
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationURL() {
		return location;
	}

	/**
	 * Method for painting a component.
	 * 
	 * @param ActionEvent e
	 */
	@Override
	public void paintComponent(Graphics g) {
		/* Add 2D Graphics to enjoy all the performance of the graphic class */
		Graphics2D g2 = (Graphics2D) g;

		/* Manage the exception if the file does not exist or no longer exists */
		File f = new File(location);
		if (!(f.isFile())) {
			location = "images/icons/ImageNotFound.jpg";
		}

		try {
			Image img = ImageIO.read(new File(location));
			g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (IOException e) {
			Image img;
			try {
				img = ImageIO.read(new File(location));
				g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}