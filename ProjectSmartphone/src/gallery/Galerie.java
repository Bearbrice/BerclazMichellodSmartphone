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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Galerie extends JPanel{
	
	private ArrayList<Photo> listPhoto = new ArrayList<Photo>();
	
	
	PanelGallery panelgallery = new PanelGallery("Galerie", "Galerie");
	
	// Panels pour mettre les photos
	JPanel center = new JPanel();
	JPanel photo = new JPanel();
	
	// fond d'écran Background
	
	// Gestion des panels dans la galerie
	private CardLayout cardlayout = new CardLayout();
	private JPanel tripanel = new JPanel(cardlayout);
	
	
	//pour le scroll
	JScrollPane scroll = new JScrollPane(center);
	
	public Galerie () {
		
		
		
		
		this.setPreferredSize(new Dimension (480,40));
		this.setLayout(new BorderLayout());
		this.add(panelgallery, BorderLayout.NORTH);
		
		center.setLayout(new GridLayout(10,10,2,2));
		
		this.add(tripanel, BorderLayout.CENTER);
		
		tripanel.add(scroll,"scroll");
		tripanel.add(photo, "photo");
		panelgallery.setBackground(Color.DARK_GRAY);
		actualisePhoto();
		
		
	}
	
	
	// Classe qui va gérer le cique sur le bouton image de la galerie et le changement de fond d'écran
	
	class ClickPhoto implements ActionListener{
		String path;
		public ClickPhoto(String path) {
			this.path=path;
		}
		public void actionPerformed(ActionEvent e) {
			panelgallery.setVisible(false);
			cardlayout.show(gettripanel(), "photo");

			//On va chercher la source du clique de l'image
			MiniPhoto minsource = (MiniPhoto) e.getSource();
			PhotoPanel photoPanel = new PhotoPanel(Galerie.this, minsource.pic);

			//Création d'un panel pour la photo en grand
			photo.setLayout(new BorderLayout());

			//on fait un removeAll pour remettre à zéro l'objet instancié
			photo.removeAll();
			photo.add(photoPanel, BorderLayout.CENTER);
		}
	}
	
	public class PhotoPanel extends JPanel{
		private Galerie photo;
		private Photo image;
		private MouseAdapter m;
		private String changeImage;
		//private Background fondEcran;
		
		JPanel up = new JPanel();
		
	
	Icon delete = new Icon("Projet\\delete.png",48,48);
	Icon previous = new Icon("Projet\\left-arrow.png",48,48);
	//Icon background = new Icon ("Projet\\icons8-fond-d'écran-filled-50.png", 48,48);

		
		
		
		public PhotoPanel(Galerie photo, Photo image) {
			this.photo=photo;
			this.image=image;
			
			
			
			
			BorderLayout borderlayout = new BorderLayout();
			this.setLayout(borderlayout);
			this.setBackground(Color.BLACK);
			
			up.setLayout(new BorderLayout());
			up.setBackground(Color.BLACK);
			this.add(up, BorderLayout.NORTH);
			
			
			
			up.add(previous, BorderLayout.WEST);
			up.add(delete, BorderLayout.EAST);
			//up.add(background, BorderLayout.CENTER);
			
		
			
			previous.addActionListener(new Previous());
			delete.addActionListener(new Delete(image));
			//background.addActionListener(new Background());
			
			
			
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
						if(deltaX>=200){
							changeImage(1);
							origin=null;
						}
						else if(deltaX<=-200){
							changeImage(-1);
							origin=null;
						}
					}
				}


			};
			this.addMouseListener(m);
			this.addMouseMotionListener(m);
		}
		
		public void paintComponent(Graphics g) 
		{
			
			super.paintComponent(g);

			BufferedImage pic = image.getPicture();

			int nH = (int) (pic.getHeight() / ((double) pic.getWidth() / getWidth()));

			if (nH > getHeight()) 
			{
				int nW = (int) (pic.getWidth() / ((double) pic.getHeight() / getHeight()));
				int x = (getWidth() - nW) / 2;
				g.drawImage(pic, x, 0, nW, getHeight(), this);
			} 
			else 
			{

				int y = (getHeight() - nH) / 2;
				g.drawImage(pic, 0, y, getWidth(), nH, this);
			}

		}
		// méthode qui permet de changer d'image par apport au tableau d'objets
		public void changeImage(int i) {
			int idImage = photo.getNextPhoto(image)+i;
			ArrayList<Photo> liste = photo.getListImg();
			
			if(idImage==-1) {
				idImage=liste.size()-1;
			}
			else if(idImage==liste.size()) {
				idImage=0;
			}
			image = liste.get(idImage);
			PhotoPanel.this.revalidate();
			PhotoPanel.this.repaint();
		}
		public String getChangeImage() {
			return changeImage;
		}
		public class Delete implements ActionListener{
			
		
			
		
			Photo image;
			
			public Delete(Photo image) {
				this.image=image;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String [] options = {"supprimer", "annuler"};
				
				
				
				if(e.getSource()==delete) {
					int reply = JOptionPane.showOptionDialog(null, "Vous êtes sûr de vouloir supprimer cette photo?",
				                "Attention",
				                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					
					
					
				if(reply ==JOptionPane.YES_OPTION){
					
					
					
					image.delete();
					
					removeChild(Galerie.this);
					
					JOptionPane.showMessageDialog(null, "photo supprimée avec succès");
					
				}
				else{
					remove(reply);
				}
					 {
						
					}
				}
				
				
			
			}
			
		}
	}

	
	private void removeChild (JPanel PaneltoRemove) {
		panelgallery.setVisible(true);
		cardlayout.show(tripanel,"scroll");
		
		actualisePhoto();
		this.revalidate();
		this.repaint();
	}
	
	
	private class PanelGallery extends JPanel{
		JLabel titrePanel;
		
		Font globalFont = new Font ("2.TimesRoman", Font.BOLD,50);
		
		
		
		Icon create = new Icon ("Projet\\icons8-plus-2-mathématique-filled-50.png",48,48);
		
		
		
		
		
		private String nomPhoto;
		
		
		public PanelGallery (String titre, String nomClass) {
			titrePanel = new JLabel(titre);
			titrePanel.setFont(globalFont);
			titrePanel.setForeground(Color.WHITE);
			
			this.setPreferredSize(new Dimension (480,78));
			this.setLayout(new FlowLayout(FlowLayout.CENTER,10,8));
			
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
	
	public class Create implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		JFileChooser choisir = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("photo", "jpg", "png", "gif");
		choisir.setAcceptAllFileFilterUsed(false);
		choisir.setFileFilter(filter);
		
		int returnVal= choisir.showOpenDialog(panelgallery);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = choisir.getSelectedFile();
			String temp = file.getName();
			
			String newName = Photo.copy(file);
			
			MiniPhoto newpic = new MiniPhoto ("imagesgallery/" + newName);
			revalidate();
			repaint();
		}
		}
		
	}
	
	public class Previous implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			panelgallery.setVisible(true);
			
			cardlayout.show(gettripanel(),"scroll");
		}
		
	}
	
	public class Background implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			JOptionPane.showConfirmDialog(null, "Définir en tant que fon d'écran?",
	                "",
	                JOptionPane.YES_NO_OPTION);
			
		}
		
	}

	public int getNextPhoto(Photo actuelle) {
		return listPhoto.indexOf(actuelle);
	}
	
	public ArrayList<Photo> getListImg(){
		return listPhoto;
	}
	
	public JPanel gettripanel() {
		return tripanel;
	}
	
	private void actualisePhoto() {
		center.removeAll();
		listPhoto.clear();
		
		File folder = new File ("imagesgallery");
		
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		File[] photos = folder.listFiles();
		for(File photo : photos) {
			new MiniPhoto (photo.getAbsolutePath());
		}
	}
	
	private class MiniPhoto extends JButton {
		private Photo pic;
		private String path;
		
		
		public MiniPhoto (String path) {
			super();
			this.path=path;
			this.setOpaque(false);
			this.setBorderPainted(false);
			this.setContentAreaFilled(false);
			this.setFocusPainted(false);

			try {
				this.pic = new Photo(path, ImageIO.read(new File(path)));
			} catch (IOException e) 
			{

				e.printStackTrace();
			}

			//Choix de la dimension de l'image
			BufferedImage img = pic.getPicture();
			int nW = img.getWidth() / (img.getHeight() / 100);
			this.setPreferredSize(new Dimension(nW, 200));

			//Affichage du panel de l'image au moment du clique
			this.addActionListener(new ClickPhoto(path));

			//Ajout de la minipicture dans le panel center
			center.add(this);
			


			listPhoto.add(pic);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(pic.getPicture(),0,0,getWidth(),getHeight(),this);
		}
	}
	}

