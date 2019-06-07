/*
 * Contact Panel
 * Author : B. Berclaz
 * Date creation : 29.04.2019
 * Date last modification : 03.06.2019 
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
import java.util.Collections;
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

import gallery.Galerie;
//import Contacts.ContactData;
import images.Icon;

/**
 * The ContactPanel class displays a panel with all contacts and offers the
 * possibilities of adding, modifying and deleting.
 * 
 * @author Brice Berclaz
 * @see java.io
 * @see java.util.concurrent.Executors
 * @see java.util.concurrent.ExecutorService
 * @see ContactData
 */
public class ContactPanel extends JPanel {

	private Galerie gallery = new Galerie();

	private int id = 0;

	private JLabel title = new JLabel("ALL CONTACTS");

	private Icon newContact = new Icon("images/icons/AddUser-24.png", 24, 24);
	private Icon iconBack = new Icon("images/icons/Back-48.png", 24, 24);

	private final String urlStatic = "images/icons/Unknown.jpg";
	private final String imageNotFound = "images/icons/ImageNotFound.jpg";
	private String urlPicture = "images/icons/Unknown.jpg";
	private String currentPanel = "center";

	private Icon pictureContact = new Icon(urlPicture, 96, 96);

	private ArrayList<ContactData> allcontact = new ArrayList<ContactData>();

	/* JPanels for the main menu */
	private JPanel north = new JPanel();
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();

	/* JButtons */
	private JButton show = new JButton("Show");
	private JButton edit = new JButton("Edit");
	private JButton remove = new JButton("Remove");

	/* External panels */
	private ContactAdd contactAdd = new ContactAdd();
	private ContactShow contactShow;
	private ContactEdit contactEdit;

	private JList contactinfo;

	private JScrollPane listScroller;

	private int selectedIndex = 0;

	/*
	 * This will allow to display the different panels
	 * 
	 * @see java.awt.CardLayout
	 */
	private CardLayout cardLayout = new CardLayout();
	private JPanel showPanel = new JPanel(cardLayout);

	/** Constructor of the ContactPanel class */
	public ContactPanel() {

		panelProperties(this);

		deserialize();

		/*
		 * We cannot create the JList outside the manufacturer because in this case we
		 * have added base contacts.
		 */
		contactinfo = new JList(allcontact.toArray());
		contactinfo.setBackground(Color.WHITE);
		contactinfo.setFont(new Font("Arial", Font.BOLD, 15));

		/*
		 * Let's say on which object added the elevator, added the listScroller in the
		 * constructor.
		 */
		listScroller = new JScrollPane(contactinfo);

		listScroller.setPreferredSize(new Dimension(300, 300));

		/*
		 * It is necessary to initialize in the constructor otherwise the dynamic array
		 * will be empty
		 */
		contactShow = new ContactShow();
		contactEdit = new ContactEdit();

		/* Adding panels to the cardlayout */
		showPanel.add(contactAdd, "contactAdd");
		showPanel.add(contactEdit, "contactEdit");
		showPanel.add(contactShow, "contactShow");
		showPanel.add(center, "center");

		showPanel.add(gallery, "gallery");

		/* Show the basic panel contact menu contact */
		cardLayout.show(showPanel, "center");

		/* Parameters of the north panel */
		north.add(iconBack);
		iconBack.setVisible(false);

		north.add(title);
		title.setFont(new Font("Serif", Font.BOLD, 20));
		title.setForeground(Color.WHITE);
		north.setBackground(Color.DARK_GRAY);
		north.add(newContact);

		/* Parameters of the south panel */
		south.add(show);
		south.add(edit);
		south.add(remove);

		south.setBackground(Color.GRAY);

		/* Parameters of the JButtons */
		buttonProperties();

		/* Parameters of the center panel */
		center.add(pictureContact, BorderLayout.NORTH);

		center.add(listScroller, BorderLayout.CENTER);

		center.add(south, BorderLayout.SOUTH);
		center.setBackground(Color.GRAY);

		/* Add the cardlayout to panel ContactPanel */
		this.add(showPanel, BorderLayout.CENTER);

		this.add(north, BorderLayout.NORTH);

		/* Listeners for the different events */
		newContact.addActionListener(new RunContactAdd());
		iconBack.addActionListener(new GetBack());
		show.addActionListener(new RunContactShow());
		edit.addActionListener(new RunContactEdition());
		remove.addActionListener(new ContactRemove());

		contactinfo.addListSelectionListener(new Displayer());
	}

	/**
	 * Modifies the parameters of a defined panel
	 * 
	 * @param pan the panel whose parameters you want to modify
	 */
	private void panelProperties(JPanel pan) {
		pan.setPreferredSize(new Dimension(480, 40));
		pan.setLayout(new BorderLayout());
		pan.setOpaque(false);
		pan.setVisible(true);
	}

	/**
	 * Modifies the parameters for the different JButtons.
	 */
	public void buttonProperties() {
		show.setBackground(Color.BLACK);
		edit.setBackground(Color.BLACK);
		remove.setBackground(Color.BLACK);

		show.setForeground(Color.WHITE);
		edit.setForeground(Color.WHITE);
		remove.setForeground(Color.WHITE);
	}

	/**
	 * Method that will read and retrieve the information from the serialized file
	 * for contacts.
	 */
	public void deserialize() {
		File f = new File("serialization/allcontacts.ser");
		if (f.isFile()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream("serialization/allcontacts.ser");

				ObjectInputStream ois = new ObjectInputStream(fis); // Allows you to write to the output stream

				while (fis.available() > 0) {
					allcontact.add((ContactData) ois.readObject());
				}
				ois.close();

				id = allcontact.size();

				/* Sort the array by alphabetic orders */
				allcontact = orderArray();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Updates the dynamic array.
	 */
	public void actualizeList() {
		allcontact = orderArray();
		contactinfo.setListData(allcontact.toArray());
	}

	/**
	 * Sorts the contact table according to the alphabetical order of first names
	 * 
	 * @return ArrayList<ContactData>
	 */
	public ArrayList<ContactData> orderArray() {

		ArrayList<ContactData> newContact = new ArrayList<ContactData>();

		/* The array will welcome all first names of contacts. */
		ArrayList<String> firstnames = new ArrayList<String>();

		/* We add the first names to the string array */
		for (int i = 0; i < allcontact.size(); i++) {
			firstnames.add(allcontact.get(i).getFirstName());
		}

		/* Sorts the array of string in alphabetical order of first names */
		Collections.sort(firstnames, String.CASE_INSENSITIVE_ORDER);

		/* We are preparing the new object array which will be ordered this time */
		for (int j = 0; j < firstnames.size(); j++) {
			for (int i = 0; i < allcontact.size(); i++) {
				if (firstnames.get(j) == allcontact.get(i).getFirstName()) {
					newContact.add(allcontact.get(i));
				}
			}
		}

		return newContact;
	}

	/**
	 * Remove all contacts from the dynamic array.
	 */
	public void removeAllContacts() {
		/*
		 * Important: contacts are removed from the largest to the smallest because we
		 * work with a dynamic table
		 */
		for (int i = allcontact.size() - 1; i > -1; i--) {
			allcontact.remove(i);
		}

		actualizeList();
		serialize();

	}

	/**
	 * Method that will serialize contacts in a .ser file, it will be used when a
	 * user is deleted, modified or added.
	 */
	private void serialize() {
		/* Serialization of the contact */
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("serialization/allcontacts.ser");

			ObjectOutputStream oos = new ObjectOutputStream(fos); // allows you to write to the output stream

			for (int i = 0; i < allcontact.size(); i++) {
				oos.writeObject(allcontact.get(i));
			}

			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launches the secondary panel from the class ContactAdd.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see ContactAdd
	 */
	public class RunContactAdd implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "contactAdd");
			currentPanel = "contactAdd";

			iconBack.setVisible(true);
			newContact.setVisible(false);
			title.setText("ADD A NEW CONTACT");
		}

	}

	/**
	 * Launches the secondary panel from the class ContactEdition.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see ContactEdition
	 */
	public class RunContactEdition implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedIndex = contactinfo.getSelectedIndex();

			if (!(selectedIndex == -1)) {

				iconBack.setVisible(true);
				newContact.setVisible(false);
				title.setText("CONTACT EDITION");

				title.setText("Editer : " + allcontact.get(selectedIndex).getFirstName() + " "
						+ allcontact.get(selectedIndex).getLastName());
				contactEdit.actualize();

				cardLayout.show(showPanel, "contactEdit");
				currentPanel = "contactEdit";
			}
		}

	}

	/**
	 * Remove a contact from the dynamic array
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 */
	public class ContactRemove implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedIndex = contactinfo.getSelectedIndex();

			/* Avoids the 'out of bounds' error */
			if (!(selectedIndex == -1)) {
				allcontact.remove(selectedIndex);
				serialize();
				actualizeList();
			}

			/* The image of the deleted contact is no longer displayed */
			pictureContact.setLocation(urlStatic);
			pictureContact.refresh();

		}

	}

	/**
	 * Launches the secondary panel from the class ContactShow.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see ContactShow
	 */
	public class RunContactShow implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedIndex = contactinfo.getSelectedIndex();

			if (!(selectedIndex == -1)) {
				cardLayout.show(showPanel, "contactShow");
				currentPanel = "contactShow";

				iconBack.setVisible(true);
				newContact.setVisible(false);

				title.setText("Contact : " + allcontact.get(selectedIndex).getFirstName() + " "
						+ allcontact.get(selectedIndex).getLastName());
				contactShow.actualize();

			}
		}

	}

	/**
	 * Remove all contacts from the dynamic array.
	 */
	public class Displayer implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			selectedIndex = contactinfo.getSelectedIndex();

			// Change the path of the contact icon/image
			if (!(selectedIndex == -1)) {
				pictureContact.setLocation(allcontact.get(selectedIndex).getLocPicture());
			}

			pictureContact.refresh();
		}

	}

	/**
	 * Method for updating the Gallery panel included in our ContactPanel
	 */
	public void actualiseGalleryC() {
		gallery.actualisePhoto();
	}

	/**
	 * Launches the main panel center.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 */
	public class GetBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			iconBack.setVisible(false);
			newContact.setVisible(true);
			title.setText("ALL CONTACTS");

			actualizeList();

			cardLayout.show(showPanel, "center");
			currentPanel = "center";

			/*
			 * We do not display an image as nothing will be selected when we return to the
			 * panel
			 */
			pictureContact.setLocation(urlStatic);
			pictureContact.refresh();

		}

	}

	/**
	 * Allows you to choose an image from the gallery.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see #run()
	 * @see #executeRunnable()
	 */
	public class ChoosePicture implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "gallery");
			gallery.setActivContact(true);

			/* Method that will execute a runnable */
			executeRunnable();
			/*
			 * Do not put instructions after the runnable otherwise they will be executed
			 * BEFORE its end
			 */

		}
	}

	/* Runnable that allows you to know when to return to the previous panel */
	private Runnable r = new Runnable() {
		@Override
		public void run() {
			for (;;) {
				if (gallery.isActivContact() == false) {

					cardLayout.show(showPanel, currentPanel);

					break;
				}

				/* Allows you not to overload the runnable */
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			/* Defines the path to the contact's photo */
			urlPicture = gallery.getUrlContact();

			if (currentPanel == "contactEdit") {
				/* Updates the image of the contactEdit panel */
				contactEdit.repaintEdit();
			}

			if (currentPanel == "contactAdd") {
				/* Updates the image of the contactAdd panel */
				contactAdd.repaintAdd();
			}

			if (currentPanel == "contactShow") {
				/* Updates the image of the contactShow panel */
				contactShow.repaintShow();
			}

			return;
		}
	};

	/**
	 * Method that allows to execute the runnable.
	 */
	private void executeRunnable() {
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(r);
		/*
		 * you shouldn't put anything here because this line will execute immediately,
		 * not waiting for your task to complete
		 */
	}

	// ******************************************************************** //
	// ********************** NEW SECONDARY CLASS ************************* //
	// ******************************************************************** //
	/**
	 * The ContacAdd class displays a panel which allows you to add a contact.
	 * 
	 * @author Brice Berclaz
	 */

	class ContactAdd extends JPanel {

		private JPanel north = new JPanel();
		private JPanel south = new JPanel();
		private JPanel center = new JPanel();
		private JPanel left = new JPanel();
		private JPanel right = new JPanel();

		private JLabel jlprenom = new JLabel("Prenom");
		private JLabel jlnom = new JLabel("Nom");
		private JLabel jltelephoneprive = new JLabel("Téléphone privé");
		private JLabel jltelephonefixe = new JLabel("Téléphone fixe");
		private JLabel jladresse = new JLabel("Adresse");
		private JLabel jlorganisation = new JLabel("Organisation");

		private JTextField jtfprenom = new JTextField();
		private JTextField jtfnom = new JTextField();
		private JTextField jtftelephoneprive = new JTextField();
		private JTextField jtftelephonefixe = new JTextField();
		private JTextField jtfadresse = new JTextField();
		private JTextField jtforganisation = new JTextField();

		private JButton save = new JButton("Save");

		private Icon pictureContactAdd = new Icon(urlPicture, 96, 96);

		/** Constructor of the ContactAdd class. */
		public ContactAdd() {
			panelProperties(this);

			north.add(pictureContactAdd);
			pictureContactAdd.addActionListener(new ChoosePicture());

			center.setLayout(new GridLayout(6, 2));

			/* Adding elements to the center panel */
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

			/* Adding elements to the south panel */
			south.add(save);
			south.setVisible(true);

			save.addActionListener(new SaveContact());

			this.add(north, BorderLayout.NORTH);
			this.add(center, BorderLayout.CENTER);
			this.add(south, BorderLayout.SOUTH);
			this.add(left, BorderLayout.WEST);
			this.add(right, BorderLayout.EAST);
		}

		/**
		 * Method that refreshes the panel's contact photo.
		 */
		public void repaintAdd() {
			pictureContactAdd.setLocation(urlPicture);
			pictureContactAdd.refresh();
		}

		/**
		 * Allows to save a contact when pressing a specific JButton.
		 * 
		 * @author Brice Berclaz
		 * @see java.awt.event.ActionEvent
		 */
		public class SaveContact implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean readyToSave = isReadyToSave();
				if (readyToSave == false) {
					return;
				}

				/* T = TYPED, S = SELECTED */
				String prenomT = jtfprenom.getText();
				String nomT = jtfnom.getText();
				String telPriveT = jtftelephoneprive.getText();
				String telFixeT = jtftelephonefixe.getText();
				String adresseT = jtfadresse.getText();
				String organisationT = jtforganisation.getText();
				String pictureS = urlPicture;

				/* Adding the contact to the dynamic table */
				allcontact.add(
						new ContactData(id, prenomT, nomT, telPriveT, telFixeT, adresseT, organisationT, pictureS));

				serialize();

				id += 1;

				/* Reset the JTextFields to their initial state */
				jtfprenom.setText("");
				jtfnom.setText("");
				jtftelephoneprive.setText("");
				jtftelephonefixe.setText("");
				jtfadresse.setText("");
				jtforganisation.setText("");

				pictureContactAdd.setLocation(urlStatic);
				pictureContactAdd.refresh();

			}

			/**
			 * Check if the required fields have been filled in and if the contact can
			 * therefore be saved
			 * 
			 * @return a boolean if it is ready or not
			 */
			public boolean isReadyToSave() {
				boolean ready = false;

				/* Don't save the contact if the prenom field is empty */
				if (jtfprenom.getText().isBlank()) {
					jlprenom.setForeground(Color.RED);
					jlprenom.setText("Prenom*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jlprenom.setForeground(Color.BLACK);
					jlprenom.setText("Prenom");
					ready = true;
				}

				/* Don't save the contact if the nom field is empty */
				if (jtfnom.getText().isBlank()) {
					jlnom.setForeground(Color.RED);
					jlnom.setText("Nom*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jlnom.setForeground(Color.BLACK);
					jlnom.setText("Nom");
				}

				/* Don't save the contact if the telephoneprive field is empty */
				if (jtftelephoneprive.getText().isBlank()) {
					jltelephoneprive.setForeground(Color.RED);
					jltelephoneprive.setText("Téléphone privé*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jltelephoneprive.setForeground(Color.BLACK);
					jltelephoneprive.setText("Téléphone privé");
				}

				/* Don't save the contact if the organisation field is empty */
				if (jtforganisation.getText().isBlank()) {
					jlorganisation.setForeground(Color.RED);
					jlorganisation.setText("Organisation*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jlorganisation.setForeground(Color.BLACK);
					jlorganisation.setText("Organisation");
				}

				return ready;
			}

		}

	}

	// ******************************************************************** //
	// ********************** NEW SECONDARY CLASS ************************* //
	// ******************************************************************** //

	/**
	 * The ContacEdit class displays a panel which allows you to edit a contact.
	 * 
	 * @author Brice Berclaz
	 */

	public class ContactEdit extends JPanel {

		private JPanel north = new JPanel();
		private JPanel left = new JPanel();
		private JPanel right = new JPanel();
		private JPanel south = new JPanel();

		private JPanel center = new JPanel();

		private JLabel jlprenom = new JLabel("Prenom");
		private JLabel jlnom = new JLabel("Nom");
		private JLabel jltelephoneprive = new JLabel("Téléphone privé");
		private JLabel jltelephonefixe = new JLabel("Téléphone fixe");
		private JLabel jladresse = new JLabel("Adresse");
		private JLabel jlorganisation = new JLabel("Organisation");

		private JTextField jtfprenom = new JTextField();
		private JTextField jtfnom = new JTextField();
		private JTextField jtftelephoneprive = new JTextField();
		private JTextField jtftelephonefixe = new JTextField();
		private JTextField jtfadresse = new JTextField();
		private JTextField jtforganisation = new JTextField();

		private Icon pictureContactEdit = new Icon(urlPicture, 96, 96);

		private JButton save = new JButton("Save");

		/** Constructor of the ContactEdit class. */
		public ContactEdit() {
			panelProperties(this);

			north.add(pictureContactEdit);
			pictureContactEdit.addActionListener(new ChoosePicture());

			center.setLayout(new GridLayout(6, 2));

			/* Adding elements to the center panel */
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

			/* Adding an element to the south panel */
			south.add(save);

			save.addActionListener(new SaveContact());

			this.add(north, BorderLayout.NORTH);
			this.add(center, BorderLayout.CENTER);
			this.add(left, BorderLayout.WEST);
			this.add(right, BorderLayout.EAST);
			this.add(south, BorderLayout.SOUTH);
		}

		/** Method that refreshes the panel's contact photo. */
		public void repaintEdit() {
			pictureContactEdit.setLocation(urlPicture);
			pictureContactEdit.refresh();
		}

		/**
		 * Allows you to retrieve the information of the selected contact and modify it.
		 */
		public void actualize() {
			jtfprenom.setText(allcontact.get(selectedIndex).getFirstName());
			jtfnom.setText(allcontact.get(selectedIndex).getLastName());
			jtftelephoneprive.setText(allcontact.get(selectedIndex).getPrivatePhone());
			jtftelephonefixe.setText(allcontact.get(selectedIndex).getLandlinePhone());
			jtfadresse.setText(allcontact.get(selectedIndex).getAddress());
			jtforganisation.setText(allcontact.get(selectedIndex).getOrganisation());

			pictureContactEdit.setLocation(allcontact.get(selectedIndex).getLocPicture());
		}

		/**
		 * Allows to save a contact when pressing a specific JButton.
		 * 
		 * @author Brice Berclaz
		 * @see java.awt.event.ActionEvent
		 */
		public class SaveContact implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean readyToSave = isReadyToSave();
				if (readyToSave == false) {
					return;
				}

				/* T = TYPED, S = SELECTED */
				String prenomT = jtfprenom.getText();
				String nomT = jtfnom.getText();
				String telPriveT = jtftelephoneprive.getText();
				String telFixeT = jtftelephonefixe.getText();
				String adresseT = jtfadresse.getText();
				String organisationT = jtforganisation.getText();
				String pictureS = urlPicture;

				allcontact.get(selectedIndex).setFirstName(prenomT);
				allcontact.get(selectedIndex).setLastName(nomT);
				allcontact.get(selectedIndex).setPrivatePhone(telPriveT);
				allcontact.get(selectedIndex).setLandlinePhone(telFixeT);
				allcontact.get(selectedIndex).setAddress(adresseT);
				allcontact.get(selectedIndex).setOrganisation(organisationT);
				allcontact.get(selectedIndex).setOrganisation(organisationT);
				allcontact.get(selectedIndex).setLocPicture(pictureS);

				serialize();
			}

			/**
			 * Check if the required fields have been filled in and if the contact can
			 * therefore be saved
			 * 
			 * @return a boolean if it is ready or not
			 */
			public boolean isReadyToSave() {
				boolean ready = false;

				/* Don't save the contact if the prenom field is empty */
				if (jtfprenom.getText().isBlank()) {
					jlprenom.setForeground(Color.RED);
					jlprenom.setText("Prenom*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jlprenom.setForeground(Color.BLACK);
					jlprenom.setText("Prenom");
					ready = true;
				}

				/* Don't save the contact if the nom field is empty */
				if (jtfnom.getText().isBlank()) {
					jlnom.setForeground(Color.RED);
					jlnom.setText("Nom*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jlnom.setForeground(Color.BLACK);
					jlnom.setText("Nom");
				}

				/* Don't save the contact if the telephoneprive field is empty */
				if (jtftelephoneprive.getText().isBlank()) {
					jltelephoneprive.setForeground(Color.RED);
					jltelephoneprive.setText("Téléphone privé*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jltelephoneprive.setForeground(Color.BLACK);
					jltelephoneprive.setText("Téléphone privé");
				}

				/* Don't save the contact if the organisation field is empty */
				if (jtforganisation.getText().isBlank()) {
					jlorganisation.setForeground(Color.RED);
					jlorganisation.setText("Téléphone privé*");
					ready = false;
				} else {
					/* We make sure that the colors and texts are black if it isn't empty */
					jlorganisation.setForeground(Color.BLACK);
					jlorganisation.setText("Organisation*");
				}

				return ready;
			}
		}

	}

	// ******************************************************************** //
	// ********************** NEW SECONDARY CLASS ************************* //
	// ******************************************************************** //

	/**
	 * The ContacAdd class displays a panel which allows you to show a contact.
	 * 
	 * @author Brice Berclaz
	 */

	private class ContactShow extends JPanel {

		private JPanel north = new JPanel();
		private JPanel left = new JPanel();
		private JPanel south = new JPanel();

		private JPanel center = new JPanel();

		private JLabel jlprenom = new JLabel("Prenom");
		private JLabel jlnom = new JLabel("Nom");
		private JLabel jltelephoneprive = new JLabel("Téléphone privé");
		private JLabel jltelephonefixe = new JLabel("Téléphone fixe");
		private JLabel jladresse = new JLabel("Adresse");
		private JLabel jlorganisation = new JLabel("Organisation");

		/* O for object. */
		private JLabel jlprenomO = new JLabel();
		private JLabel jlnomO = new JLabel();
		private JLabel jltelephonepriveO = new JLabel();
		private JLabel jltelephonefixeO = new JLabel();
		private JLabel jladresseO = new JLabel();
		private JLabel jlorganisationO = new JLabel();

		private Icon pictureContactShow = new Icon(urlPicture, 96, 96);

		/** Constructor of the ContactShow class. */
		public ContactShow() {
			panelProperties(this);

			north.add(pictureContactShow);

			center.setLayout(new GridLayout(6, 2));

			/* Adding elements to the center panel */
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

			this.add(center, BorderLayout.CENTER);
			this.add(left, BorderLayout.WEST);
			this.add(south, BorderLayout.SOUTH);
			this.add(north, BorderLayout.NORTH);
		}

		/** Method that refreshes the panel's contact photo. */
		public void repaintShow() {
			pictureContactShow.setLocation(urlPicture);
			pictureContactShow.refresh();
		}

		/**
		 * Allows you to retrieve the information of the selected contact and show it
		 */
		public void actualize() {
			jlprenomO.setText(allcontact.get(selectedIndex).getFirstName());
			jlnomO.setText(allcontact.get(selectedIndex).getLastName());
			jltelephonepriveO.setText(allcontact.get(selectedIndex).getPrivatePhone());
			jltelephonefixeO.setText(allcontact.get(selectedIndex).getLandlinePhone());
			jladresseO.setText(allcontact.get(selectedIndex).getAddress());
			jlorganisationO.setText(allcontact.get(selectedIndex).getOrganisation());

			pictureContactShow.setLocation(allcontact.get(selectedIndex).getLocPicture());
		}
	}
}// End of the class ContactPanel
