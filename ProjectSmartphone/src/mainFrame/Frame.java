/*
 * Menu principal
 * Author: Brice Berclaz
 * Date creation: 16.04.2019
 * Date last modification: 07.05.2019
 */

package mainFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import calculator.CalculatorPanel;
import clock.ClockPanel;
import contact.ContactPanel;
import gallery.Galerie;
import images.Background;
import images.Icon;
import musicPlayer.MusicPlayerPanel;
import panels.BannerPanel;
import panels.ContouringPanel;
import panels.ScreenPanel;
import settings.SettingsPanel;
import settings.WLAN;

public class Frame extends JFrame {

	// Panels
	ContactPanel contactpanel = new ContactPanel();
	MusicPlayerPanel musicpanel = new MusicPlayerPanel();
	ClockPanel clockpanel = new ClockPanel();
	SettingsPanel settingspanel = new SettingsPanel();
	CalculatorPanel calculatorpanel = new CalculatorPanel();
	Galerie gallerypanel = new Galerie();

	// Panels emplacement
	BannerPanel bannerPanel = new BannerPanel();

	// Panels classiques emplacement
	JPanel backPanel = new JPanel();
	// JPanel northPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();

	JPanel panS = new JPanel();

	JPanel bgCenter = new JPanel();

	// Panels classiques emplacement (design)
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();

	JPanel panL = new JPanel();
	JPanel panR = new JPanel();

	JPanel southMenu = new JPanel();

	// Panels principaux
	ContouringPanel smartphone = new ContouringPanel();
	ScreenPanel screen = new ScreenPanel();
	Background background = new Background();

	// Icons applications
	Icon iconContact = new Icon("images/icons/Contacts-48.png", 48, 48);
	Icon iconGallery = new Icon("images/icons/Gallery-48.png", 48, 48);
	Icon iconCalculator = new Icon("images/icons/Calculator-48.png", 48, 48);
	Icon iconClock = new Icon("images/icons/Clock-48.png", 48, 48);
	Icon iconMusic = new Icon("images/icons/Music-48.png", 48, 48);
	Icon iconSettings = new Icon("images/icons/Settings-48.png", 48, 48);

	Icon iconEmpty = new Icon("images/icons/Empty.png", 48, 48);
	Icon iconEmpty2 = new Icon("images/icons/Empty.png", 48, 48);

	// Icons smartphone - fonctions
	// Icon iconBack = new Icon("images/icons/Back-48.png", 48, 48);
	Icon iconHome = new Icon("images/icons/Home-48.png", 48, 48);
	Icon iconLock = new Icon("images/icons/Lock-48.png", 48, 48);
	Icon iconShutdown = new Icon("images/icons/Shutdown-48.png", 48, 48);

	// tri des panels static pour avoir accès dans le panel d'accueil
	private CardLayout cardLayout = new CardLayout();
	private JPanel switchPanel = new JPanel(cardLayout);

	/*
	 * Constructeur de la classe Frame
	 */

	public Frame(String title) {
		frameSettings(title);

		// PANEL DU HAUT
		smartphone.add(bannerPanel, BorderLayout.NORTH);

//BRICOLAGE START
		// PANELS LEFT & RIGHT - DEFINITION DES MARGES DU SCREEN (BACKGROUND)

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
//BRICOLAGE END			

		// PANEL SOUTH
		smartphone.add(southPanel, BorderLayout.SOUTH);
		southPanel.setOpaque(false);

		// southPanel.setLayout(new FlowLayout(0,62,0));
		// background.setLayout(new FlowLayout(30,50,70));
		southPanel.setLayout(new BorderLayout());

		southPanel.add(southMenu);

		southMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 11));
		southMenu.setOpaque(false);
		southMenu.add(iconEmpty);
		southMenu.add(iconHome);
		southMenu.add(iconEmpty2);

		southPanel.add(southMenu);

		/*
		 * panS.setLayout(new FlowLayout(0, 0, 35)); southPanel.add(panS);
		 * panS.setOpaque(false);
		 */
		// PANEL NORTH
		/*
		 * smartphone.add(northPanel, BorderLayout.NORTH); northPanel.setOpaque(false);
		 * 
		 * northPanel.setLayout(new FlowLayout(100,60,0));
		 * 
		 * northPanel.add(iconEmpty); northPanel.add(iconHome);
		 * northPanel.add(iconBack);
		 */
		/*
		 * panS.setLayout(new FlowLayout(0, 0, 30)); southPanel.add(panS);
		 * panS.setOpaque(false);
		 */
		// SCREEN PRINCIPAL
		smartphone.add(switchPanel);
		switchPanel.add(centerPanel, "mainpanel");

		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(480, 40));

		// AJOUT BACKGROUND AU PANEL PRINCIPAL
		// centerPanel.add(bannerPanel, BorderLayout.NORTH);
		centerPanel.add(background, BorderLayout.CENTER);

		// LAYOUT BACKGROUND - PERMET DE RAJOUTER LES CASES POUR APPLICATIONS
		// background.add(bannerPanel, BorderLayout.NORTH);

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

		// switchPanel.add(background, "mainpanel");

		// background.add(bgCenter);

		// gapLayout();

		// LISTENERS
		// iconGallery.addActionListener(new RunGallery());
		// iconCalculator.addActionListener(new RunCalculator());
		// iconBack.addActionListener(new ComeBack());

		iconHome.addActionListener(new Home());
		iconShutdown.addActionListener(new Shutdown());

		iconGallery.addActionListener(new RunGallery());
		iconClock.addActionListener(new RunClock());
		iconMusic.addActionListener(new RunMusic());
		iconContact.addActionListener(new RunContact());
		iconSettings.addActionListener(new RunSettings());
		iconCalculator.addActionListener(new RunCalculator());

		// ajout des applis
		switchPanel.add(musicpanel, "musicpanel");
		switchPanel.add(clockpanel, "clockpanel");
		switchPanel.add(contactpanel, "contactpanel");
		switchPanel.add(settingspanel, "settingspanel");
		switchPanel.add(calculatorpanel, "calculatorpanel");
		switchPanel.add(gallerypanel, "gallerypanel");

		// rajouter le smartphone dans la frame principale
		this.add(smartphone);
	}

	// Paramètre de la frame
	private void frameSettings(String title) {
		setTitle(title);

		// setSize(480, 860);
		setSize(400, 720);
		// setPreferredSize(new Dimension(400, 720));

		setLocationRelativeTo(null);
		setUndecorated(true); // Ne pas afficher les boutons de la frame
		setAlwaysOnTop(true);
		setResizable(false);
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// pack();
	}

	// Permet de laisser une ligne vide
	private void gapLayout() {
		background.add(iconEmpty);
		background.add(iconEmpty);
		background.add(iconEmpty);
	}

	/*
	 * METHODES ACTION LISTENER
	 */

	// RUNGALLERY
	private class RunGallery implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "gallerypanel");
			
			
		}
	}
	
	
	// SHUTDOWN
	private class Shutdown implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	// RUNCLOCK
	private class RunClock implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "clockpanel");
		}
	}

	// RUNMUSIC
	private class RunMusic implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "musicpanel");
			musicpanel.checkProgress();
			bannerPanel.setVisibleIconMusic(musicpanel.isInProgress());
		}
	}

	// HOME
	private class Home implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "mainpanel");
			musicpanel.checkProgress();
			bannerPanel.setVisibleIconMusic(musicpanel.isInProgress());
			bannerPanel.refreshNetwork();
		}
	}

	// RUNCONTACT
	private class RunContact implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// SI LES CONTACTS ONT ETE REINTIALISER DANS LE MENU SETTINGS
			if (settingspanel.isReset() == true) {
				contactpanel.removeAllContacts();
			}

			// REMETTRE A FALSE SINON A CHAQUE FOIS QU'ON LANCE CONTACT ON PERD TOUS LES
			// CONTACTS
			settingspanel.setResetToFalse();

			cardLayout.show(switchPanel, "contactpanel");
		}
	}

	// RUNSETTINGS
	private class RunSettings implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "settingspanel");
		}
	}

	// RUNCALCULATOR
	private class RunCalculator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(switchPanel, "calculatorpanel");
		}
	}

}
