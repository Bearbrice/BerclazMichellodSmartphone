/*
 * Calculator App
 * Author: S. Michellod & B. Berclaz
 * Date creation: 27.04.2019
 * Date last modification: 31.05.2019
 */

package gallery;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import images.Background;
import images.Icon;

/**
 * Class that will manage the gallery app
 * 
 * @author Samuel Michellod
 */

public class GalleryPanel extends JPanel {
	// path for background
	private String pathbg;
	private boolean activeBGserialization = false;

	public boolean isActiveBGserialization() {
		return activeBGserialization;
	}

	public void setActiveBGserialization(boolean activeBGserialization) {
		this.activeBGserialization = activeBGserialization;
	}

	Background change = new Background();

	/* Boolean to know if the gallery is running normally or in contacts */
	private boolean activContact = false;

	// getter for activContact
	public boolean isActivContact() {
		return activContact;
	}

	// setter for activContact
	public void setActivContact(boolean activContact) {
		this.activContact = activContact;
	}

	// url of the contact image
	private String urlContact = "";

	// Creation of a picture object array
	private ArrayList<Photo> listPhoto = new ArrayList<Photo>();

	PanelGallery panelgallery = new PanelGallery("Galerie", "Galerie");

	// Panels that will contains the pictures
	JPanel center = new JPanel();
	JPanel photo = new JPanel();

	private CardLayout cardlayout = new CardLayout();
	private JPanel tripanel = new JPanel(cardlayout);

	// for scrolling
	JScrollPane scroll = new JScrollPane(center);

	/** Constructor of the GaleriePanel class */
	public GalleryPanel() {

		this.setPreferredSize(new Dimension(480, 40));
		this.setLayout(new BorderLayout());
		this.add(panelgallery, BorderLayout.NORTH);

		center.setLayout(new GridLayout(10, 10, 2, 2));

		this.add(tripanel, BorderLayout.CENTER);

		tripanel.add(scroll, "scroll");
		tripanel.add(photo, "photo");
		panelgallery.setBackground(Color.DARK_GRAY);
		actualisePhoto();

	}

	// The path of the photo for the background
	public String getPathbg() {
		return pathbg;
	}

	public void setPathbg(String pathbg) {
		this.pathbg = pathbg;
	}

	/**
	 * Class that will manage the click on the image button of the gallery
	 * 
	 * @author Samuel Michellod
	 */
	public class ClickPhoto implements ActionListener {

		private String path;

		public ClickPhoto(String path) {
			this.path = path;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			// START CODING BRICE
			if (activContact == true) {
				String temp = "";
				String temp2 = "";

				/*
				 * Loop that executes an inverted substrate to obtain the absolute path of the
				 * image
				 */
				for (int i = path.length() - 1; i > 0; i--) {
					if (path.charAt(i) == '\\') {
						break;
					}
					temp += path.charAt(i);
				}

				/* Loop that returns the absolute path obtained to the place */
				for (int i = temp.length() - 1; i >= 0; i--) {
					temp2 += temp.charAt(i);
				}

				setUrlContact("imagesgallery/" + temp2);

				activContact = false;
			}
			// END CODING BRICE

			/* Else = normal use of the gallery by Samuel */
			else {
				panelgallery.setVisible(false);
				cardlayout.show(gettripanel(), "photo");

				// Look for the source of the image click
				MiniPhoto minsource = (MiniPhoto) e.getSource();
				PhotoPanel photoPanel = new PhotoPanel(GalleryPanel.this, minsource.pic);

				// Creation of a panel for photos in large
				photo.setLayout(new BorderLayout());

				// We do a removeAll to reset the instantiated object to zero
				photo.removeAll();
				photo.add(photoPanel, BorderLayout.CENTER);
			}

		}

	}

	/**
	 * Class that will manage the panel when you click on the picture
	 * 
	 * @author Samuel Michellod
	 *
	 */
	public class PhotoPanel extends JPanel {
		private GalleryPanel photo;
		private Photo image;
		private MouseAdapter m;
		private String changeImage;
		// private Background fondEcran;

		JPanel up = new JPanel();

		JPanel backgroundIcon = new JPanel();

		Icon delete = new Icon("images/icons/delete.png", 48, 48);
		Icon previous = new Icon("images/icons/left-arrow.png", 48, 48);
		Icon background = new Icon("images\\icons\\icons8-fond-d'écran-filled-50.png", 48, 48);

		/**
		 * Constructor of the class PhotoPanel
		 * 
		 * @param photo : Photo in the gallery
		 * @param image : The miniPicture
		 * @author Samuel Michellod
		 */

		public PhotoPanel(GalleryPanel photo, Photo image) {
			this.photo = photo;
			this.image = image;

			BorderLayout borderlayout = new BorderLayout();
			this.setLayout(borderlayout);
			this.setBackground(Color.BLACK);

			backgroundIcon.add(background);

			backgroundIcon.setBackground(Color.BLACK);

			up.add(backgroundIcon, BorderLayout.CENTER);
			up.setLayout(new BorderLayout());
			up.setBackground(Color.BLACK);
			this.add(up, BorderLayout.NORTH);

			up.add(previous, BorderLayout.WEST);
			up.add(delete, BorderLayout.EAST);
			up.add(backgroundIcon, BorderLayout.CENTER);

			previous.addActionListener(new Previous());
			delete.addActionListener(new ConfirmationDelete());
			background.addActionListener(new BackgroundListener());

			up.setVisible(true);
			// Swipe with the click
			m = new MouseAdapter() {
				private Point origin;

				@Override
				public void mousePressed(MouseEvent e) {
					origin = new Point(e.getPoint());

				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					if (origin != null) {
						int deltaX = origin.x - e.getX();
						if (deltaX >= 200) {
							changeImage(1);
							origin = null;
						} else if (deltaX <= -200) {
							changeImage(-1);
							origin = null;
						}
					}
				}

			};
			this.addMouseListener(m);
			this.addMouseMotionListener(m);
		}

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			BufferedImage pic = image.getPicture();

			int nH = (int) (pic.getHeight() / ((double) pic.getWidth() / getWidth()));

			if (nH > getHeight()) {
				int nW = (int) (pic.getWidth() / ((double) pic.getHeight() / getHeight()));
				int x = (getWidth() - nW) / 2;
				g.drawImage(pic, x, 0, nW, getHeight(), this);
			} else {

				int y = (getHeight() - nH) / 2;
				g.drawImage(pic, 0, y, getWidth(), nH, this);
			}

		}

		/**
		 * Method that allows the image swipe in relation to the object array
		 * 
		 * @param i : The position in the table
		 */
		public void changeImage(int i) {
			int idImage = photo.getNextPhoto(image) + i;
			ArrayList<Photo> liste = photo.getListImg();

			if (idImage == -1) {
				idImage = liste.size() - 1;
			} else if (idImage == liste.size()) {
				idImage = 0;
			}
			image = liste.get(idImage);
			PhotoPanel.this.revalidate();
			PhotoPanel.this.repaint();
		}

		public String getChangeImage() {
			return changeImage;
		}

		/**
		 * Class that will display a dialog when you click on the button delete
		 * 
		 * @author Samuel Michellod
		 *
		 */
		public class ConfirmationDelete implements ActionListener {

			private JDialog confirmation = new JDialog();

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JLabel warning = new JLabel("Delete this photo?");
				JButton yes = new JButton("Yes");
				JButton no = new JButton("No");
				JPanel north = new JPanel();
				JPanel center = new JPanel();

				north.add(warning);
				center.add(yes);
				center.add(no);

				yes.addActionListener(new Delete());
				no.addActionListener(new CloseDialog());

				confirmation.add(north, BorderLayout.NORTH);
				confirmation.add(center, BorderLayout.CENTER);

				confirmation.setLocation(620, 400);
				confirmation.setTitle("Warning");

				confirmation.pack();

				confirmation.setVisible(true);
				confirmation.setAlwaysOnTop(true);

			}

			/**
			 * Class that will delete the photo
			 * 
			 * @author Samuel Michellod
			 *
			 */
			public class Delete implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					image.delete();
					removeChild(panelgallery);
					confirmation.dispose();
				}

			}

			/**
			 * Class that will close the dialog
			 * 
			 * @author Samuel Michellod
			 *
			 */
			public class CloseDialog implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					confirmation.dispose();

				}
			}
		}

		/**
		 * Class that will display a dialog when you click on the button background
		 * 
		 * @author Samuel Michellod
		 *
		 */
		public class BackgroundListener implements ActionListener {

			private JDialog confirmation = new JDialog();

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JLabel warning = new JLabel("Set this photo as background?");
				JButton yes = new JButton("Yes");
				JButton no = new JButton("No");
				JPanel north = new JPanel();
				JPanel center = new JPanel();

				north.add(warning);
				center.add(yes);
				center.add(no);

				yes.addActionListener(new Background());
				no.addActionListener(new CloseDialog());
				confirmation.add(north, BorderLayout.NORTH);
				confirmation.add(center, BorderLayout.CENTER);

				confirmation.setLocation(620, 400);
				confirmation.setTitle("Warning");

				confirmation.pack();

				confirmation.setVisible(true);
				confirmation.setAlwaysOnTop(true);

			}

			/**
			 * Class that will change the background
			 * 
			 * @author Samuel Michellod
			 *
			 */
			public class Background implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					confirmation.dispose();
					// setPathbg(image.getPath());
					// change.changeBackground(image.getPath());
					String tempPath;
					tempPath = image.getPath();

					System.out.println("imagegetpath: " + tempPath);

					tempPath = findRelativePath(tempPath);

					System.out.println("tempPath: " + tempPath);

					setPathbg(tempPath);

					// enable the boolean so that serialization is done in the frame
					setActiveBGserialization(true);
				}

			}

			/**
			 * Class that will close the dialog
			 * 
			 * @author Samuel Michellod
			 *
			 */
			public class CloseDialog implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					confirmation.dispose();

				}
			}

			/** Find a relative path */
			private String findRelativePath(String x) {
				String newPath = null;
				int z = 0;
				int profondeur = 0;

				x = x.replace("\\", "/");

				for (int i = x.length() - 1; i > 0; i--) {
					if (x.charAt(i) == '/') {
						// x.charAt(i).
						newPath = x.substring(x.length() - z, x.length());
						profondeur++;

						if (profondeur == 2) {
							break;
						}

					}
					z++;
				}

				return newPath;
			}

		}

	}

	/**
	 * Method that will reactualise the gallery when you delete a photo
	 * 
	 * @param PaneltoRemove : the panel you use
	 * @author Samuel Michellod
	 */
	private void removeChild(JPanel PaneltoRemove) {
		panelgallery.setVisible(true);
		cardlayout.show(tripanel, "scroll");

		actualisePhoto();
		this.revalidate();
		this.repaint();
	}

	/**
	 * 
	 * Class that will manage the panel at the top of the gallery
	 * 
	 * @author Samuel Michellod
	 *
	 */

	private class PanelGallery extends JPanel {

		JLabel PanelTitle;

		Font globalFont = new Font("2.TimesRoman", Font.BOLD, 50);

		Icon create = new Icon("images/icons/icons8-plus-2.png", 48, 48);

		private String PhotoName;

		/**
		 * Constructor of the class PanelGallery
		 * 
		 * @param title     : the title
		 * @param className : name of the class
		 * 
		 * @author Samuel Michellod
		 */

		public PanelGallery(String title, String className) {
			PanelTitle = new JLabel(title);
			PanelTitle.setFont(globalFont);
			PanelTitle.setForeground(Color.WHITE);

			this.setPreferredSize(new Dimension(480, 78));
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));

			this.add(PanelTitle, BorderLayout.CENTER);

			this.add(create, BorderLayout.CENTER);

			create.addActionListener(new Create());

		}
	}

	/**
	 * 
	 * Class that will manage the addition of a picture
	 * 
	 * @author Samuel Michellod
	 *
	 */
	public class Create implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser choisir = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("photo", "jpg", "png", "gif");
			choisir.setAcceptAllFileFilterUsed(false);
			choisir.setFileFilter(filter);

			int returnVal = choisir.showOpenDialog(panelgallery);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = choisir.getSelectedFile();
				String temp = file.getName();

				String newName = Photo.copy(file);

				MiniPhoto newpic = new MiniPhoto("imagesgallery/" + newName);
				revalidate();
				repaint();
			}
		}

	}

	/**
	 * 
	 * Class that will come back to the gallery panel
	 * 
	 * @author Samuel Michellod
	 *
	 */

	public class Previous implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			panelgallery.setVisible(true);

			cardlayout.show(gettripanel(), "scroll");
		}

	}

	public int getNextPhoto(Photo actuelle) {
		return listPhoto.indexOf(actuelle);
	}

	public ArrayList<Photo> getListImg() {
		return listPhoto;
	}

	public JPanel gettripanel() {
		return tripanel;
	}

	/**
	 * Method that will update the recorded pictures
	 * 
	 */

	public void actualisePhoto() {
		center.removeAll();
		listPhoto.clear();

		File folder = new File("imagesgallery");

		if (!folder.exists()) {
			folder.mkdirs();
		}

		File[] photos = folder.listFiles();
		for (File photo : photos) {
			new MiniPhoto(photo.getAbsolutePath());
		}
	}

	public String getUrlContact() {
		return urlContact;
	}

	public void setUrlContact(String urlContact) {
		this.urlContact = urlContact;
	}

	/**
	 * Creation of "photo buttons" in small
	 * 
	 * @author Samuel Michellod
	 *
	 */
	private class MiniPhoto extends JButton {
		private Photo pic;
		private String path;

		/**
		 * Constructor of the class MiniPhoto
		 * 
		 * @param path : Recovering the image path
		 * @author Samuel Michellod
		 */
		public MiniPhoto(String path) {
			super();
			this.path = path;
			this.setOpaque(false);
			this.setBorderPainted(false);
			this.setContentAreaFilled(false);
			this.setFocusPainted(false);

			try {
				this.pic = new Photo(path, ImageIO.read(new File(path)));
			} catch (IOException e) {

				e.printStackTrace();
			}

			// Choice of the image size
			BufferedImage img = pic.getPicture();
			int nW = img.getWidth() / (img.getHeight() / 100);
			this.setPreferredSize(new Dimension(nW, 200));

			// Display of the image panel when you click on it
			this.addActionListener(new ClickPhoto(path));

			// Adding the miniphoto in the panel center
			center.add(this);

			listPhoto.add(pic);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(pic.getPicture(), 0, 0, getWidth(), getHeight(), this);
		}
	}
}
