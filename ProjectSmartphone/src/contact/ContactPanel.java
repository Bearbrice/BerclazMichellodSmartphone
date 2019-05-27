/*
 * Author : Brice Berclaz
 * Date creation : 29 avril 2019
 */

package contact;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import contact.ContactData;
import gallery.Galerie;
//import Contacts.ContactData;
import images.Icon;

public class ContactPanel extends JPanel {

	Galerie gallery = new Galerie();

	int id = 0;

	JLabel title = new JLabel("ALL CONTACTS");

	Icon newContact = new Icon("images/icons/AddUser-24.png", 24, 24);
	Icon iconBack = new Icon("images/icons/Back-48.png", 24, 24);

	private final String urlStatic = "images/icons/Unknown.jpg";
	private final String imageNotFound = "images/icons/ImageNotFound.jpg";
	private String urlPicture = "images/icons/Unknown.jpg";
	private String currentPanel = "center";
	
	Icon pictureContact = new Icon(urlPicture, 96, 96);

	ArrayList<ContactData> allcontact = new ArrayList<ContactData>();

	// PANELS DE MENU
	JPanel north = new JPanel();
	JPanel center = new JPanel();
	JPanel south = new JPanel();
	JPanel left = new JPanel();
	JPanel right = new JPanel();

	// JBUTTONS
	JButton show = new JButton("Show");
	JButton edit = new JButton("Edit");
	JButton remove = new JButton("Remove");

	// PANELS EXTERNES
	ContactAdd contactAdd = new ContactAdd();
	ContactShow contactShow;
	ContactEdit contactEdit;

	JList contactinfo;

	JScrollPane listScroller;

	int selectedIndex = 0;

	// Permettra d'afficher les différents panels
	private CardLayout cardLayout = new CardLayout();
	private JPanel showPanel = new JPanel(cardLayout);

	// Constructor
	public ContactPanel() {

		panelProperties();

		deserialize();

		/*
		 * allcontact.add(new ContactData(0, "Brice", "Berclaz", "0798668993",
		 * "0274559604", "Ch. des Anémones 6", "HES-SO Valais-Wallis"));
		 * allcontact.add(new ContactData(1, "Samuel", "Michellod", "0761234567",
		 * "0271234567", "Ch. de l'Arsenal 45", "HES-SO Valais-Wallis"));
		 */

		// On ne peut pas créé la JList en dehors du constructeur car dans ce cas ci
		// nous avons ajouté des contacts de base
		// contactinfo = new JList(allcontact.toArray());
		contactinfo = new JList(allcontact.toArray());
		contactinfo.setBackground(Color.WHITE);
		// contactinfo.setForeground(Color.WHITE);
		contactinfo.setFont(new Font("Arial", Font.BOLD, 15));

		listScroller = new JScrollPane(contactinfo); // dire sur quel objet ajouté l'ascenceur, il suffira
														// d'ajouter listScroller dans le constructeur
														// ensuite

		listScroller.setPreferredSize(new Dimension(300, 300));
		
		

		// Initialiser dans le constructeur sinon le tableau est vide
		contactShow = new ContactShow();
		contactEdit = new ContactEdit();

		// Ajouter les panels au cardlayout
		showPanel.add(contactAdd, "contactAdd");
		showPanel.add(contactEdit, "contactEdit");
		showPanel.add(contactShow, "contactShow");
		showPanel.add(center, "center");

		showPanel.add(gallery, "gallery");

		// Montrer le panel de base contact menu
		cardLayout.show(showPanel, "center");

		// panel north
		north.add(iconBack);
		iconBack.setVisible(false);

		north.add(title);
		title.setFont(new Font("Serif", Font.BOLD, 20));
		title.setForeground(Color.WHITE);
		north.setBackground(Color.DARK_GRAY);
		north.add(newContact);

		// panel south
		south.add(show);
		south.add(edit);
		south.add(remove);

		south.setBackground(Color.GRAY);

		buttonProperties();

		// panel center
		// center.add(test);
		center.add(pictureContact, BorderLayout.NORTH);

		center.add(listScroller, BorderLayout.CENTER);
		// center.add(contactinfo);

		center.add(south, BorderLayout.SOUTH);
		center.setBackground(Color.GRAY);

		// ajouter le cardlayout à Contactpanel
		this.add(showPanel, BorderLayout.CENTER);
		this.add(north, BorderLayout.NORTH);
		// this.add(right, BorderLayout.EAST);
		// this.add(left, BorderLayout.WEST);
		// this.add(south, BorderLayout.SOUTH);

		newContact.addActionListener(new RunContactAdd());
		iconBack.addActionListener(new GetBack());
		show.addActionListener(new RunContactShow());
		edit.addActionListener(new RunContactEdition());
		remove.addActionListener(new ContactRemove());
		
		contactinfo.addListSelectionListener(new Displayer());
	}

	private void panelProperties() {
		this.setPreferredSize(new Dimension(480, 40));
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		this.setVisible(true);
	}

	public void buttonProperties() {
		show.setBackground(Color.BLACK);
		edit.setBackground(Color.BLACK);
		remove.setBackground(Color.BLACK);

		show.setForeground(Color.WHITE);
		edit.setForeground(Color.WHITE);
		remove.setForeground(Color.WHITE);
	}

	public void changeCardLayout(String panel) {
		cardLayout.show(showPanel, panel);
	}

	public void deserialize() {
		File f = new File("serialization/allcontacts.ser");
		if (f.isFile()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream("serialization/allcontacts.ser");

				ObjectInputStream ois = new ObjectInputStream(fis); // permet d'écrire au niveau du flux de sortie

				while (fis.available() > 0) {
					allcontact.add((ContactData) ois.readObject());
				}
				ois.close();

				/*
				 * for(int i=0; i<allcontact.size(); i++) {
				 * allcontact.add((ContactData)ois.readObject()); }
				 */
				/*
				 * CHECK SERIALIZATION IS WORKING OR NOT for(int i=0; i<allcontact.size(); i++)
				 * { System.out.println(allcontact.get(i)); }
				 */
				id = allcontact.size();
				
				
				for(int i=0; i<allcontact.size(); i++) {
				System.out.println(allcontact.get(i));
				}
				

				// System.out.println(allcontact.get(0).getPrenom());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void actualizeList() {
	
		contactinfo.setListData(allcontact.toArray());
		
		// this.updateUI();
		// center.remove(contactinfo);
		// center.add(contactinfo);
		// contactinfo.repaint();
		// contactinfo.updateUI();
		// contactinfo.revalidate();
		// center.updateUI();
		// center.revalidate();
		// center.repaint();

		/*
		 * test contacts for(int i=0; i<allcontact.size(); i++) {
		 * System.out.print(allcontact.get(i)); }
		 */

	}

	public void removeAllContacts() {
		// Important : on enlève les contacts du plus grand au plus petit car on
		// travaille avec un tableau dynamique
		for (int i = allcontact.size() - 1; i > -1; i--) {
			allcontact.remove(i);
		}

		actualizeList();
		serialize();

	}

	// UTILISER QUAND UN UTILISATEUR EST SUPPRIME (REMOVE), ADD et EDIT
	private void serialize() {
		// Sérialisation du contact
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("serialization/allcontacts.ser");

			ObjectOutputStream oos = new ObjectOutputStream(fos); // permet d'écrire au niveau du flux de sortie

			for (int i = 0; i < allcontact.size(); i++) {
				oos.writeObject(allcontact.get(i));
			}

			oos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class RunContactAdd implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "contactAdd");
			currentPanel="contactAdd";
			
			iconBack.setVisible(true);
			newContact.setVisible(false);
			title.setText("ADD A NEW CONTACT");
		}
		
	}

	public class RunContactEdition implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedIndex = contactinfo.getSelectedIndex();

			if (!(selectedIndex == -1)) {

				iconBack.setVisible(true);
				newContact.setVisible(false);
				title.setText("CONTACT EDITION");

				// System.out.println(selectedIndex);
				title.setText("Editer : " + allcontact.get(selectedIndex).getPrenom() + " "
						+ allcontact.get(selectedIndex).getNom());
				contactEdit.actualize();

				cardLayout.show(showPanel, "contactEdit");
				currentPanel="contactEdit";
			}
		}

	}

	public class ContactRemove implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			selectedIndex = contactinfo.getSelectedIndex();
			
			//permet d'éviter l'erreur outofbounds
			if (!(selectedIndex == -1)) {
				allcontact.remove(selectedIndex);
				serialize();
				actualizeList();
			}
			
			//on n'affiche plus l'image du contact supprimé
			pictureContact.setLocation(urlStatic);
			pictureContact.refresh();
					
		}

	}

	public class RunContactShow implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			selectedIndex = contactinfo.getSelectedIndex();

			if (!(selectedIndex == -1)) {
				cardLayout.show(showPanel, "contactShow");
				currentPanel="contactShow";
				
				iconBack.setVisible(true);
				newContact.setVisible(false);

				title.setText("Contact : " + allcontact.get(selectedIndex).getPrenom() + " "
						+ allcontact.get(selectedIndex).getNom());
				contactShow.actualize();
				
				
			}
		}

	}
	
	public class Displayer implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			selectedIndex = contactinfo.getSelectedIndex();
						
			//Change le chemin de l'icone contact
			if (!(selectedIndex == -1)) {
				pictureContact.setLocation(allcontact.get(selectedIndex).getLocPicture());
			}
			
/*			//Met à jour l'icone en fonction de la sélection
			try {
			pictureContact.refresh();
			}
			catch(Exception x) { //gérer l'exception d'une image supprimée
				pictureContact.setLocation("images/icons/ImageNotFound.jpg");
				pictureContact.refresh();
				
			}
*/
			//Met à jour l'icone en fonction de la sélection
//			File f = new File(pictureContact.getLocationURL());
//			if(f.isFile())
//			{ 
//				pictureContact.refresh();
//			}
//			
//			//gérer l'exception d'une image supprimée
//			else {
//				pictureContact.setLocation(imageNotFound);
//				pictureContact.refresh();
//			}
			
			pictureContact.refresh();
		}

	}
	
	
	public void getBackNow() {

		iconBack.setVisible(false);
		newContact.setVisible(true);
		title.setText("ALL CONTACTS");

		actualizeList();

		cardLayout.show(showPanel, "center");
		

	}
	
	//Methode d'actualisation du panel 'gallery' de contactpanel
	public void actualiseGalleryC() {
		gallery.actualisePhoto();
	}

	public class GetBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			iconBack.setVisible(false);
			newContact.setVisible(true);
			title.setText("ALL CONTACTS");

			actualizeList();

			cardLayout.show(showPanel, "center");
			currentPanel="center";
			
			//on n'affiche pas d'image comme rien ne sera sélectionner quand on retourne sur le panel
			pictureContact.setLocation(urlStatic);
			pictureContact.refresh();

		}

	}
	
	
	//PERMET DE CHOISIR UNE IMAGE DANS LA GALERIE
	public class ChoosePicture implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "gallery");
			gallery.setActivContact(true);
			
			//Methode qui va executer un runnable
			executeRunnable();
			//Ne pas mettre d'instruction après le runnable sinon elle s'exécuteront AVANT sa fin
			     
			     
			     
			//new System.Threading.ManualResetEvent(false).WaitOne(1000);
				
				
				/*
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
				
			/*     for(;;) {
						System.out.println("Actualisation en cours");
						if(gallery.isActivContact()==false) {
							break;
						}
			}*/
			
			// cardLayout.show(showPanel, "center");
			
			//gallery.addMouseListener(x);

			/*for (;;) {
				if (gallery.isActivContact() == false) {
					cardLayout.show(showPanel, "center");
				}*/
		}
	}
	
	//Runnable qui permet de savoir quand retourner au panel précédent
	private Runnable r = new Runnable() {
        public void run() {
       	 for (;;) {
				if (gallery.isActivContact() == false) {
					
					/*if(currentPanel=="contactEdit") {
						cardLayout.show(showPanel, "contactEdit");
					}
					
					if(currentPanel=="contactAdd") {
						cardLayout.show(showPanel, "contactAdd");
					}
					
					if(currentPanel=="contactShow") {
						cardLayout.show(showPanel, "contactAdd");
					}*/
					
					cardLayout.show(showPanel, currentPanel);
								 							 					
					break;
				}	
				
				//Permet de ne pas surcharger le runnable
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//System.out.println("TEST : " + gallery.getUrlContact());
				
       	 }
       	 //System.out.println("TEST : " + gallery.getUrlContact());
       	 
       	 //System.out.println(currentPanel);
       	 
       	 //Définit le chemin vers la photo du contact
       	 urlPicture=gallery.getUrlContact();
       	 
       	 if(currentPanel=="contactEdit") {          	 
           	 //actualise l'image du panel contactEdit
           	 contactEdit.repaintEdit();
       	 }
       	 
       	 if(currentPanel=="contactAdd") {
       		 //actualise l'image du panel contactAdd
           	 contactAdd.repaintAdd();
       	 }
       	 
       	 if(currentPanel=="contactShow") {
       		//actualise l'image du panel contactShow
           	contactShow.repaintShow();
       	 }
       	 
       	 //urlPicture=urlStatic;
       	 
       	 
       	 //System.out.println("urlPic : "+urlPicture);
       	 return;
        }
    };
    
    //Methode qui permet d'exécuter le runnable
    private void executeRunnable() {
    	ExecutorService executor = Executors.newCachedThreadPool();
	    executor.submit(r);
	    //this line will execute immediately, not waiting for your task to complete
    }

	// *****************************************************************************************************//
	// *****************************************************************************************************//
	// *****************************************************************************************************//

	/*
	 * Class : ContactAdd Author : Brice Berclaz Date creation : 29 avril 2019
	 */

	class ContactAdd extends JPanel {

		JLabel title = new JLabel();

		JPanel north = new JPanel();
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		JPanel south = new JPanel();

		JPanel center = new JPanel();

		JLabel jlprenom = new JLabel("Prenom");
		JLabel jlnom = new JLabel("Nom");
		JLabel jltelephoneprive = new JLabel("Téléphone privé");
		JLabel jltelephonefixe = new JLabel("Téléphone fixe");
		JLabel jladresse = new JLabel("Adresse");
		JLabel jlorganisation = new JLabel("Organisation");

		JTextField jtfprenom = new JTextField();
		JTextField jtfnom = new JTextField();
		JTextField jtftelephoneprive = new JTextField();
		JTextField jtftelephonefixe = new JTextField();
		JTextField jtfadresse = new JTextField();
		JTextField jtforganisation = new JTextField();

		JButton save = new JButton("Save");

		Icon pictureContactAdd = new Icon(urlPicture, 96, 96);
		// JLabel test = new JLabel("TEST");

		public ContactAdd() {
			panelProperties();

			// left.setLayout(new GridLayout(6, 1));

			north.add(pictureContactAdd);
			pictureContactAdd.addActionListener(new ChoosePicture());

			center.setLayout(new GridLayout(6, 2));

			// ADD TO PANEL CENTER
			center.add(jlprenom);
			center.add(jtfprenom);
			center.add(jlnom);
			center.add(jtfnom);
			center.add(jltelephoneprive);
			center.add(jtftelephoneprive);
			center.add(jltelephonefixe);
			center.add(jtftelephonefixe);
			center.add(jladresse);
			center.add(jtfadresse);
			center.add(jlorganisation);
			center.add(jtforganisation);

			// ADD TO PANEL SOUTH
			south.add(save);
			south.setVisible(true);

			save.addActionListener(new SaveContact());

			this.add(north, BorderLayout.NORTH);
			this.add(center, BorderLayout.CENTER);
			// this.add(left, BorderLayout.WEST);
			this.add(south, BorderLayout.SOUTH);

		}

		private void panelProperties() {
			this.setPreferredSize(new Dimension(480, 40));
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setVisible(true);
		}
		
		//Methode qui rafraichit la photo contact du panel
		public void repaintAdd() {
			pictureContactAdd.setLocation(urlPicture);
			pictureContactAdd.refresh();
		}
		
		

		// ACTION LISTENER SAVE
		public class SaveContact implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// T = TYPED
				// S = SELECTED
				
				String prenomT = jtfprenom.getText();
				String nomT = jtfnom.getText();
				String telPriveT = jtftelephoneprive.getText();
				String telFixeT = jtftelephonefixe.getText();
				String adresseT = jtfadresse.getText();
				String organisationT = jtforganisation.getText();
				String pictureS = urlPicture;

				// TESTS
				// Interdire les vides
				// Check numero telephone

				// Creation contact
				/*
				 * ContactData createContact = new ContactData(id, prenomT, nomT, telPriveT,
				 * telFixeT, adresseT, organisationT);
				 */

				// Ajout du contact dans le tableau dynamique
				allcontact.add(new ContactData(id, prenomT, nomT, telPriveT, telFixeT, adresseT, organisationT, pictureS));

				serialize();

				id += 1;

				// Remettre à l'état initial les JTextFields
				jtfprenom.setText("");
				jtfnom.setText("");
				jtftelephoneprive.setText("");
				jtftelephonefixe.setText("");
				jtfadresse.setText("");
				jtforganisation.setText("");

				//urlPicture=urlStatic;
				pictureContactAdd.setLocation(urlStatic);
				pictureContactAdd.refresh();
				//repaintAdd();
				
				

				// Serialisation de l'objet
				// this.serializeObject(createContact);

			}

			/*
			 * //Méthode de sérialisation private void serializeObject(ContactData
			 * createContact) { try { FileOutputStream fichier = new
			 * FileOutputStream("serialisation\\contact-"+createContact.getIdContact()+
			 * ".ser"); ObjectOutputStream oos = new ObjectOutputStream(fichier);
			 * oos.writeObject(createContact); oos.flush(); oos.close(); fichier.close(); }
			 * catch (java.io.IOException e) { e.printStackTrace(); }
			 * 
			 * }
			 */
		}

	}

	// *****************************************************************************************************//
	// *****************************************************************************************************//
	// *****************************************************************************************************//

	/*
	 * Class : Contact Edit Author : Brice Berclaz Date creation : 5 mai 2019
	 */

	class ContactEdit extends JLabel {

		JLabel title = new JLabel();

		JPanel north = new JPanel();
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		JPanel south = new JPanel();

		JPanel center = new JPanel();

		JLabel jlprenom = new JLabel("Prenom");
		JLabel jlnom = new JLabel("Nom");
		JLabel jltelephoneprive = new JLabel("Téléphone privé");
		JLabel jltelephonefixe = new JLabel("Téléphone fixe");
		JLabel jladresse = new JLabel("Adresse");
		JLabel jlorganisation = new JLabel("Organisation");

		JTextField jtfprenom = new JTextField();
		JTextField jtfnom = new JTextField();
		JTextField jtftelephoneprive = new JTextField();
		JTextField jtftelephonefixe = new JTextField();
		JTextField jtfadresse = new JTextField();
		JTextField jtforganisation = new JTextField();

		Icon pictureContactEdit = new Icon(urlPicture, 96, 96);

		/*
		 * JTextField jtfprenom = new
		 * JTextField(allcontact.get(selectedIndex).getPrenom());; JTextField jtfnom =
		 * new JTextField(allcontact.get(selectedIndex).getNom()); JTextField
		 * jtftelephoneprive = new
		 * JTextField(allcontact.get(selectedIndex).getTelephonePrive()); JTextField
		 * jtftelephonefixe = new
		 * JTextField(allcontact.get(selectedIndex).getTelephoneFixe()); JTextField
		 * jtfadresse = new JTextField(allcontact.get(selectedIndex).getAdresse());
		 * JTextField jtforganisation = new
		 * JTextField(allcontact.get(selectedIndex).getOrganisation());
		 */

		/*
		 * jtfprenom = new JTextField(allcontact.get(selectedIndex).getPrenom()); jtfnom
		 * = new JTextField(allcontact.get(selectedIndex).getNom()); jtftelephoneprive =
		 * new JTextField(allcontact.get(selectedIndex).getTelephonePrive());
		 * jtftelephonefixe = new
		 * JTextField(allcontact.get(selectedIndex).getTelephoneFixe()); jtfadresse =
		 * new JTextField(allcontact.get(selectedIndex).getAdresse()); jtforganisation =
		 * new JTextField(allcontact.get(selectedIndex).getOrganisation());
		 */

		JButton save = new JButton("Save");

		public ContactEdit() {
			panelProperties();

			// actualize();

			north.add(pictureContactEdit);
			pictureContactEdit.addActionListener(new ChoosePicture());

			center.setLayout(new GridLayout(6, 2));

			// ADD TO PANEL CENTER
			center.add(jlprenom);
			center.add(jtfprenom);

			center.add(jlnom);
			center.add(jtfnom);

			center.add(jltelephoneprive);
			center.add(jtftelephoneprive);

			center.add(jltelephonefixe);
			center.add(jtftelephonefixe);

			center.add(jladresse);
			center.add(jtfadresse);

			center.add(jlorganisation);
			center.add(jtforganisation);

			// ADD TO PANEL SOUTH
			south.add(save);

			save.addActionListener(new SaveContact());

			this.add(north, BorderLayout.NORTH);
			this.add(center, BorderLayout.CENTER);
			this.add(left, BorderLayout.WEST);
			this.add(south, BorderLayout.SOUTH);

		}
		
		public void repaintEdit() {
			pictureContactEdit.setLocation(urlPicture);
			pictureContactEdit.refresh();
			
			
			
//			System.out.println(pictureContactEdit.getLocationURL());
//			
//			//Met à jour l'icone en fonction de la sélection
//			File f = new File(pictureContactEdit.getLocationURL());
//			if(f.isFile())
//			{ 
//				pictureContactEdit.refresh();
//			}
//			
//			//gérer l'exception d'une image supprimée
//			else {
//				pictureContactEdit.setLocation(imageNotFound);
//				pictureContactEdit.refresh();
//			}
			
		}

		private void panelProperties() {
			this.setPreferredSize(new Dimension(480, 40));
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setVisible(true);
		}

		public void actualize() {
			jtfprenom.setText(allcontact.get(selectedIndex).getPrenom());
			jtfnom.setText(allcontact.get(selectedIndex).getNom());
			jtftelephoneprive.setText(allcontact.get(selectedIndex).getTelephonePrive());
			jtftelephonefixe.setText(allcontact.get(selectedIndex).getTelephoneFixe());
			jtfadresse.setText(allcontact.get(selectedIndex).getAdresse());
			jtforganisation.setText(allcontact.get(selectedIndex).getOrganisation());
		
			pictureContactEdit.setLocation(allcontact.get(selectedIndex).getLocPicture());
			
		}

		// ACTION LISTENER SAVE
		public class SaveContact implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// T = TYPED
				// S = SELECTED

				String prenomT = jtfprenom.getText();
				String nomT = jtfnom.getText();
				String telPriveT = jtftelephoneprive.getText();
				String telFixeT = jtftelephonefixe.getText();
				String adresseT = jtfadresse.getText();
				String organisationT = jtforganisation.getText();
				String pictureS = urlPicture;
				
				//System.out.println("SAVED : "+urlPicture);

				// TESTS
				// Interdire les vides
				// Check numero telephone

				// Creation contact
				// ContactData createContact = new ContactData(0, prenomT, nomT, telPriveT,
				// telFixeT, adresseT,
				// organisationT);

				// CREATION CONTACT
				// allcontact.add(new ContactData(id, prenomT, nomT, telPriveT, telFixeT,
				// adresseT, organisationT));
				
				//allcontact.get(selectedIndex).setPrenom(prenomT);
				
				allcontact.get(selectedIndex).setPrenom(prenomT);
				allcontact.get(selectedIndex).setNom(nomT);
				allcontact.get(selectedIndex).setTelephonePrive(telPriveT);
				allcontact.get(selectedIndex).setTelephoneFixe(telFixeT);
				allcontact.get(selectedIndex).setAdresse(adresseT);
				allcontact.get(selectedIndex).setOrganisation(organisationT);
				allcontact.get(selectedIndex).setOrganisation(organisationT);
				allcontact.get(selectedIndex).setLocPicture(pictureS);

				serialize();
			}
		}

	}

	// *****************************************************************************************************//
	// *****************************************************************************************************//
	// *****************************************************************************************************//

	/*
	 * Class : Contact Show Author : Brice Berclaz Date creation : 5 mai 2019
	 */

	class ContactShow extends JLabel {

		JLabel title = new JLabel();

		JPanel north = new JPanel();
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		JPanel south = new JPanel();

		JPanel center = new JPanel();

		JLabel jlprenom = new JLabel("Prenom");
		JLabel jlnom = new JLabel("Nom");
		JLabel jltelephoneprive = new JLabel("Téléphone privé");
		JLabel jltelephonefixe = new JLabel("Téléphone fixe");
		JLabel jladresse = new JLabel("Adresse");
		JLabel jlorganisation = new JLabel("Organisation");

		// o pour object
		JLabel jlprenomO = new JLabel();
		JLabel jlnomO = new JLabel();
		JLabel jltelephonepriveO = new JLabel();
		JLabel jltelephonefixeO = new JLabel();
		JLabel jladresseO = new JLabel();
		JLabel jlorganisationO = new JLabel();

		Icon pictureContactShow = new Icon(urlPicture, 96, 96);

		// JButton save = new JButton("Save");

		public ContactShow() {
			panelProperties();

			// actualize();
			north.add(pictureContactShow);
			// pictureContactShow.addActionListener(new ChoosePicture());

			center.setLayout(new GridLayout(6, 2));

			// ADD TO PANEL CENTER
			center.add(jlprenom);
			center.add(jlprenomO);

			center.add(jlnom);
			center.add(jlnomO);

			center.add(jltelephoneprive);
			center.add(jltelephonepriveO);

			center.add(jltelephonefixe);
			center.add(jltelephonefixeO);

			center.add(jladresse);
			center.add(jladresseO);

			center.add(jlorganisation);
			center.add(jlorganisationO);

			// ADD TO PANEL SOUTH
			this.add(center, BorderLayout.CENTER);
			this.add(left, BorderLayout.WEST);
			this.add(south, BorderLayout.SOUTH);
			this.add(north, BorderLayout.NORTH);

		}
		
		public void repaintShow() {
			pictureContactShow.setLocation(urlPicture);
			pictureContactShow.refresh();
		}

		public void actualize() {
			jlprenomO.setText(allcontact.get(selectedIndex).getPrenom());
			jlnomO.setText(allcontact.get(selectedIndex).getNom());
			jltelephonepriveO.setText(allcontact.get(selectedIndex).getTelephonePrive());
			jltelephonefixeO.setText(allcontact.get(selectedIndex).getTelephoneFixe());
			jladresseO.setText(allcontact.get(selectedIndex).getAdresse());
			jlorganisationO.setText(allcontact.get(selectedIndex).getOrganisation());
			
			pictureContactShow.setLocation(allcontact.get(selectedIndex).getLocPicture());
		}

		private void panelProperties() {
			this.setPreferredSize(new Dimension(480, 40));
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setVisible(true);
		}
	}
}// END CLASS CONTACTPANEL
