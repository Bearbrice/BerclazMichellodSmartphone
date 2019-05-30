/*
 * Background picture
 * Author: Brice Berclaz & Numeric1 (forum code source CCM)
 * Date creation: 
 * Date last modification:
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

public class Icon extends JButton {

	// private static final long serialVersionUID = 1L;

	private String location = "";

	private int large = 10;
	private int length = 10;
	// private String fileLocation = "";

	// Constructeur
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

	// Bien que la methode soit appellée d'elle même, il faut prévoir une issue de
	// secours : rafraichîr l'affichage.
	// Car cetains composants la gèrent mal.
	public void refresh() {
		this.repaint();
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationURL() {
		return location;
	}

	/**
	 * method for painting a component
	 * 
	 * @param ActionEvent e
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Ajouter Graphics 2D afin de jouir de toutes les performances de la class
		// graphic
		Graphics2D g2 = (Graphics2D) g;

		// Gérer l'exception si le fichier n'existe pas ou plus
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