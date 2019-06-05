/*
 * Frame
 * Author : B. Berclaz
 * Date creation : 16.04.2019
 * Date last modification : 31.05.2019
 */

package mainFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import calculator.CalculatorPanel;
import clock.ClockPanel;
import contact.ContactPanel;
import gallery.Galerie;
import images.Background;
import images.BackgroundData;
import images.Icon;
import musicPlayer.MusicPlayerPanel;
import panels.BannerPanel;
import panels.ContouringPanel;
import settings.SettingsPanel;

/**
 * The Frame class displays a frame with all necessary panel to show all the
 * virtual smartphone
 * 
 * @author Brice Berclaz
 * @see javax.swing.JFrame;
 * @see javax.swing.JPanel
 */
public class Frame extends JFrame {
	private String bgPath;

	/* Application panels from other classes */
	private ContactPanel contactpanel = new ContactPanel();
	private MusicPlayerPanel musicpanel = new MusicPlayerPanel();
	private ClockPanel clockpanel = new ClockPanel();
	private SettingsPanel settingspanel = new SettingsPanel();
	private CalculatorPanel calculatorpanel = new CalculatorPanel();
	private Galerie gallerypanel = new Galerie();

	/* Panel from another class */
	private BannerPanel bannerPanel = new BannerPanel();

	/* Location panels for design */
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	/* Location panels for design */
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();

	private JPanel panL = new JPanel();
	private JPanel panR = new JPanel();

	private JPanel southMenu = new JPanel();

	/* Main panels */
	private ContouringPanel smartphone = new ContouringPanel();
	private Background background = new Background();
	private BackgroundData bgdata = new BackgroundData();

	/* Icons for applications */
	private Icon iconContact = new Icon("images/icons/Contacts-48.png", 48, 48);
	private Icon iconGallery = new Icon("images/icons/Gallery-48.png", 48, 48);
	private Icon iconCalculator = new Icon("images/icons/Calculator-48.png", 48, 48);
	private Icon iconClock = new Icon("images/icons/Clock-48.png", 48, 48);
	private Icon iconMusic = new Icon("images/icons/Music-48.png", 48, 48);
	private Icon iconSettings = new Icon("images/icons/Settings-48.png", 48, 48);

	/* Icon for design only */
	private Icon iconEmpty = new Icon("images/icons/Empty.png", 48, 48);
	private Icon iconEmpty2 = new Icon("images/icons/Empty.png", 48, 48);

	/* Icon for access to features */
	private Icon iconHome = new Icon("images/icons/Home-48.png", 48, 48);
	private Icon iconShutdown = new Icon("images/icons/Shutdown-48.png", 48, 48);

	/*
	 * This will allow to display the different panels
	 * 
	 * @see java.awt.CardLayout
	 */
	private CardLayout cardLayout = new CardLayout();
	private JPanel switchPanel = new JPanel(cardLayout);

	/**
	 * Constructor of the Frame class
	 * 
	 * @param title the name to give to the frame
	 */
	public Frame(String title) {
		frameSettings(title);

		deserializeBG();

		// PANEL DU HAUT
		smartphone.add(bannerPanel, BorderLayout.NORTH);

		/* Design but not visible - START */
		/* Panels left & right - Definition of screen margins (background) */
		smartphone.add(leftPanel, BorderLayout.WEST);
		smartphone.add(rightPanel, BorderLayout.EAST);
		leftPanel.setOpaque(false);
		rightPanel.setOpaque(false);

		panL.setLayout(new FlowLayout(0, 7, 0));
		leftPanel.add(panL);
		panL.setOpaque(false);

		panR.setLayout(new FlowLayout(0, 7, 0));
		rightPanel.add(panR);
		panR.setOpaque(false);
		/* Design but not visible - END */

		/* South panel */
		smartphone.add(southPanel, BorderLayout.SOUTH);
		southPanel.setOpaque(false);

		southPanel.setLayout(new BorderLayout());

		southPanel.add(southMenu);

		southMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 11));
		southMenu.setOpaque(false);
		southMenu.add(iconEmpty);
		southMenu.add(iconHome);
		southMenu.add(iconEmpty2);

		southPanel.add(southMenu);

		/* Main screen */
		smartphone.add(switchPanel);
		switchPanel.add(centerPanel, "mainpanel");

		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(480, 40));

		/* Adding background to the center panel */
		centerPanel.add(background, BorderLayout.CENTER);

		/* Allows you to add the correct spacing for applications */
		background.setLayout(new FlowLayout(30, 50, 80));

		// AJOUTER LES ICONS SUR LE BACKGROUND
		background.add(iconGallery);
		background.add(iconContact);
		background.add(iconCalculator);

		background.add(iconClock);
		background.add(iconMusic);
		background.add(iconShutdown);

		background.add(iconSettings);

		background.setOpaque(false);

		/* Listeners for functions */
		iconHome.addActionListener(new Home());
		iconShutdown.addActionListener(new Shutdown());

		/* Listeners for applications */
		iconGallery.addActionListener(new RunGallery());
		iconClock.addActionListener(new RunClock());
		iconMusic.addActionListener(new RunMusic());
		iconContact.addActionListener(new RunContact());
		iconSettings.addActionListener(new RunSettings());
		iconCalculator.addActionListener(new RunCalculator());

		/* Adding applications to the switchpanel */
		switchPanel.add(musicpanel, "musicpanel");
		switchPanel.add(clockpanel, "clockpanel");
		switchPanel.add(contactpanel, "contactpanel");
		switchPanel.add(settingspanel, "settingspanel");
		switchPanel.add(calculatorpanel, "calculatorpanel");
		switchPanel.add(gallerypanel, "gallerypanel");

		/* Adding the smartphone in the main frame */
		this.add(smartphone);
	}

	/**
	 * Modifies the parameters of a the frame
	 * 
	 * @param title set the title you want for the frame
	 */
	private void frameSettings(String title) {
		setTitle(title);

		setSize(400, 720);

		setLocationRelativeTo(null);
		setUndecorated(true); // Do not display the buttons of the frame
		setAlwaysOnTop(true);
		setResizable(false);
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Method that will serialize the path for the background
	 */
	private void serializeBG() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("serialization/background.ser");

			ObjectOutputStream oos = new ObjectOutputStream(fos); // allows you to write to the output stream

			oos.writeObject(bgdata);

			System.out.println("serialization effectuée : " + bgdata.getBackgroundPath());

			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that will read and retrieve the information from the serialized file
	 * for background
	 */
	public void deserializeBG() {
		File f = new File("serialization/background.ser");
		if (f.isFile()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream("serialization/background.ser");

				ObjectInputStream ois = new ObjectInputStream(fis);

				bgdata = (BackgroundData) ois.readObject();

				ois.close();

				System.out.println("Déserialization effectuée : " + bgdata.getBackgroundPath());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		/* Retrieves the path of the serialized file */
		File check = new File(bgdata.getBackgroundPath());

		/* Check if the file exists */
		if (check.isFile()) {
			background.setBackgroundPath(bgdata.getBackgroundPath());
		}
		/*
		 * If the serialize path no longer finds the file then it sets the default
		 * background
		 */
		else {
			background.setBackgroundPath(bgdata.getDefaultBackground());
		}

	}

	/**
	 * Launches the gallery panel
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see gallery.Galerie
	 */
	private class RunGallery implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "gallerypanel");

		}
	}

	/**
	 * Turn off the smartphone, it closes the frame and also stops the console
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see java.lang.System.exit
	 */
	private class Shutdown implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/**
	 * Launches the clock panel
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see clock.ClockPanel
	 */
	private class RunClock implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "clockpanel");
		}
	}

	/**
	 * Launches the music panel
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see musicPlayer.MusicPlayerPanel
	 */
	private class RunMusic implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "musicpanel");
			musicpanel.checkProgress();
			bannerPanel.setVisibleIconMusic(musicpanel.isInProgress());
		}
	}

	/**
	 * Launches the home screen panel
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 */
	private class Home implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "mainpanel");
			musicpanel.checkProgress();
			bannerPanel.setVisibleIconMusic(musicpanel.isInProgress());
			bannerPanel.refreshNetwork();

			if (gallerypanel.isActiveBGserialization() == true) {
				bgPath = gallerypanel.getPathbg();

				/* Changes the background immediately */
				background.setBackgroundPath(bgPath);

				/* Stock le chemin avant sérialisation */
				bgdata.setBackgroundPath(bgPath);

				serializeBG();

				gallerypanel.setActiveBGserialization(false);
			}
		}
	}

	/**
	 * Launches the contact panel
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see contact.ContactPanel
	 */
	private class RunContact implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			/* If the contacts have benn reinitialized in the settings panel */
			if (settingspanel.isReset() == true) {
				contactpanel.removeAllContacts();
			}

			/* Reset to false or on every time contact is started all contacts are lost */
			settingspanel.setResetToFalse();

			/* Update the contact gallery */
			contactpanel.actualiseGalleryC();

			cardLayout.show(switchPanel, "contactpanel");
		}
	}

	/**
	 * Launches the settings panel
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see settings.SettingsPanel
	 */
	private class RunSettings implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "settingspanel");
		}
	}

	/**
	 * Launches the calculator panel
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 * @see calculator.CalculatorPanel
	 */
	private class RunCalculator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "calculatorpanel");
		}
	}

}
