package gallery;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import contact.ContactPanel;
import images.Icon;
import images.Background;

public class Galerie extends JPanel {
	//path pour background
	private String pathbg;
	private boolean activeBGserialization=false;
	
	public boolean isActiveBGserialization() {
		return activeBGserialization;
	}

	public void setActiveBGserialization(boolean activeBGserialization) {
		this.activeBGserialization = activeBGserialization;
	}

	Background change = new Background();

	// boolean pour savoir si la galerie s'exécute normalement ou dans les contacts
	private boolean activContact = false;
	
	//getter pour activContact
	public boolean isActivContact() {
		return activContact;
	}

	//setter pour activContact
	public void setActivContact(boolean activContact) {
		this.activContact = activContact;
	}
	
	//urldelimagecontact
	private String urlContact = "";

	private ArrayList<Photo> listPhoto = new ArrayList<Photo>();

	PanelGallery panelgallery = new PanelGallery("Galerie", "Galerie");

	// Panels pour mettre les photos
	JPanel center = new JPanel();
	JPanel photo = new JPanel();


	// Gestion des panels dans la galerie
	private CardLayout cardlayout = new CardLayout();
	private JPanel tripanel = new JPanel(cardlayout);

	// pour le scroll
	JScrollPane scroll = new JScrollPane(center);
	
	
	
	//Constructeur
	public Galerie() {

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
	
	public String getPathbg() {
		return pathbg;
	}

	public void setPathbg(String pathbg) {
		this.pathbg = pathbg;
	}

	// Classe qui va gerer le cique sur le bouton image de la galerie
	

	class ClickPhoto implements ActionListener {
		
		String path;

		public ClickPhoto(String path) {
			this.path = path;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//START CODING BRICE (EN TEST)
			
			if(activContact==true) {
				String temp = "";
				String temp2 = "";
				
				//boucle qui exécute un substring inversé pour obtenir le chemin absolu de l'image
				for(int i=path.length()-1; i>0; i--) {
					if(path.charAt(i)=='\\') {
						break;
					}
					temp+=path.charAt(i);
					//System.out.println(path.charAt(i));
					//System.out.println(temp);
					}
				
				//boucle qui remet à l'endroit le chemin absolu obtenu
				for(int i=temp.length()-1; i>=0; i--) {
					temp2+=temp.charAt(i);
				}
				
				setUrlContact("imagesgallery/" + temp2);
			
				//setUrlContact(path);
				System.out.println(path);
				
				System.out.println("TEMP2 = " + temp2);
				
				
				
				activContact=false;
			}
			//END CODING BRICE
			
			//else = utilisation normale de la galerie by SAMUEL
			else {
				panelgallery.setVisible(false);
				cardlayout.show(gettripanel(), "photo");

				// On va chercher la source du clique de l'image
				MiniPhoto minsource = (MiniPhoto) e.getSource();
				PhotoPanel photoPanel = new PhotoPanel(Galerie.this, minsource.pic);

				// Creation d'un panel pour la photo en grand
				photo.setLayout(new BorderLayout());

				// on fait un removeAll pour remettre a  zero l'objet instancié
				photo.removeAll();
				photo.add(photoPanel, BorderLayout.CENTER);
			}
			
		}
		
	}
	


	public class PhotoPanel extends JPanel {
		private Galerie photo;
		private Photo image;
		private MouseAdapter m;
		private String changeImage;
		// private Background fondEcran;

		JPanel up = new JPanel();
		
		JPanel backgroundIcon = new JPanel();

		Icon delete = new Icon("images/icons/delete.png", 48, 48);
		Icon previous = new Icon("images/icons/left-arrow.png", 48, 48);
		Icon background = new Icon ("images\\icons\\icons8-fond-d'écran-filled-50.png", 48,48);

		public PhotoPanel(Galerie photo, Photo image) {
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

		// mÃ©thode qui permet de changer d'image par apport au tableau d'objets
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
		            
		            
		            confirmation.setLocation(620,400);
		            confirmation.setTitle("Warning");
		            
		            confirmation.pack();
		            
		            
		            confirmation.setVisible(true);
		            confirmation.setAlwaysOnTop(true);
		       
		            
			}
			
			public class Delete implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					image.delete();
					removeChild(panelgallery);
					confirmation.dispose();
				}
				
			}
			   public class CloseDialog implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					confirmation.dispose();
				
			   }
			   }
		}
		
		
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
		            
		            
		            confirmation.setLocation(620,400);
		            confirmation.setTitle("Warning");
		            
		            confirmation.pack();
		            
		            
		            confirmation.setVisible(true);
		            confirmation.setAlwaysOnTop(true);
		       
		            
			}
			

            public class Background implements ActionListener {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				confirmation.dispose();
    				//setPathbg(image.getPath());
    				//change.changeBackground(image.getPath());
    				String tempPath;
    				tempPath=image.getPath();
    				
    				System.out.println("imagegetpath: " + tempPath);
    				
    				tempPath=findRelativePath(tempPath);
    				
    				System.out.println("tempPath: " + tempPath);
    				
    				setPathbg(tempPath);
    				
    				//activer le boolean pour que la serialization se fasse dans la frame
    				setActiveBGserialization(true);
    			}

    		}
			public class CloseDialog implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					confirmation.dispose();
				
			   }
			}
			
			//Permet de retrouver le chemin relatif
			private String findRelativePath(String x) {
				String newPath = null;
				int z=0;
				int profondeur=0;
				
				x = x.replace("\\", "/");
								
				for(int i=x.length()-1; i>0; i--) {
					if(x.charAt(i)=='/') {
						//x.charAt(i).
						newPath=x.substring(x.length()-z, x.length());
						profondeur++;
						
						//on veut obtenir 2 de profondeur
						if(profondeur==2) {
							break;
						}
						
					}
					z++;
				}
				
				return newPath;
			}
			  
			
			
			
			
			
		}
	
	}	
	

		

	private void removeChild(JPanel PaneltoRemove) {
		panelgallery.setVisible(true);
		cardlayout.show(tripanel, "scroll");

		actualisePhoto();
		this.revalidate();
		this.repaint();
	}

	private class PanelGallery extends JPanel {
		JLabel titrePanel;

		Font globalFont = new Font("2.TimesRoman", Font.BOLD, 50);

		Icon create = new Icon("images/icons/icons8-plus-2.png", 48, 48);

		private String nomPhoto;

		public PanelGallery(String titre, String nomClass) {
			titrePanel = new JLabel(titre);
			titrePanel.setFont(globalFont);
			titrePanel.setForeground(Color.WHITE);

			this.setPreferredSize(new Dimension(480, 78));
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));

			this.add(titrePanel, BorderLayout.CENTER);

			this.add(create, BorderLayout.CENTER);

			create.addActionListener(new Create());

		}

		public ArrayList<Photo> getListImg() {
			// TODO Auto-generated method stub
			return null;
		}

		public int getNextPhoto(Photo image) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

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

	private class MiniPhoto extends JButton {
		private Photo pic;
		private String path;

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

			// Choix de la dimension de l'image
			BufferedImage img = pic.getPicture();
			int nW = img.getWidth() / (img.getHeight() / 100);
			this.setPreferredSize(new Dimension(nW, 200));

			// Affichage du panel de l'image au moment du clique
			this.addActionListener(new ClickPhoto(path));

			// Ajout de la minipicture dans le panel center
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
