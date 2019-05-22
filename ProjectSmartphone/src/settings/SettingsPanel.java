/*
 * Settings App
 * Author: Brice Berclaz
 * Date creation: 03.05.2019
 * Date last modification: 13.05.2019
 */

package settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import images.Icon;

public class SettingsPanel extends JPanel {
	private boolean reset = false;

	// PANELS
	JPanel north = new JPanel();
	JPanel center = new JPanel();
	JPanel left = new JPanel();
	JPanel right = new JPanel();

	JButton informations = new JButton("Informations");
	JButton changeBG = new JButton("Change background");
	JButton version = new JButton("Version");
	JButton resetContact = new JButton("Remove all contacts");

	Icon iconBack = new Icon("images/icons/Back-48.png", 36, 36);

	JLabel title = new JLabel("SETTINGS");

	Version versionpanel = new Version();
	Information infoPanel = new Information();

	// Permettra d'afficher les différents panels
	private CardLayout cardLayout = new CardLayout();
	private JPanel showPanel = new JPanel(cardLayout);

	// Constructeur
	public SettingsPanel() {

		panelSettings();
		panelBackgrounds();
		labelProperties();
		buttonProperties();

		north.add(iconBack);
		iconBack.setVisible(false);

		north.add(title);

		center.setLayout(new GridLayout(4, 1));

		center.add(informations);
		center.add(changeBG);
		center.add(version);
		center.add(resetContact);

		version.addActionListener(new ShowVersion());
		iconBack.addActionListener(new GetBack());
		informations.addActionListener(new ShowNetwork());
		resetContact.addActionListener(new ConfirmationRC());

		showPanel.add(center, "center");
		cardLayout.show(showPanel, "center");

		showPanel.add(versionpanel, "versionpanel");
		showPanel.add(infoPanel, "infoPanel");

		this.add(north, BorderLayout.NORTH);
		this.add(showPanel, BorderLayout.CENTER);
		// this.add(left, BorderLayout.WEST);
		// this.add(right, BorderLayout.EAST);
	}

	public boolean isReset() {
		return reset;
	}

	public void setResetToFalse() {
		reset = false;
	}

	public void panelSettings() {
		this.setPreferredSize(new Dimension(638, 188));
		this.setVisible(true);
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
	}

	private void labelProperties() {
		title.setFont(new Font("Serif", Font.BOLD, 35));
		title.setForeground(Color.WHITE);
	}

	private void buttonProperties() {
		informations.setBackground(Color.BLACK);
		informations.setForeground(Color.WHITE);
		informations.setFont(new Font("Algerian", Font.BOLD, 20));

		changeBG.setBackground(Color.BLACK);
		changeBG.setForeground(Color.WHITE);
		changeBG.setFont(new Font("Algerian", Font.BOLD, 20));

		version.setBackground(Color.BLACK);
		version.setForeground(Color.WHITE);
		version.setFont(new Font("Algerian", Font.BOLD, 20));

		resetContact.setBackground(Color.BLACK);
		resetContact.setForeground(Color.WHITE);
		resetContact.setFont(new Font("Algerian", Font.BOLD, 20));
	}

	private void panelBackgrounds() {
		north.setBackground(Color.GRAY);
	}

	/*
	 * LISTENERS START
	 * 
	 */

	private class ShowVersion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "versionpanel");
			iconBack.setVisible(true);
			title.setText("VERSION");

		}

	}

	private class ShowNetwork implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "infoPanel");
			iconBack.setVisible(true);
			title.setText("INFORMATIONS");

		}

	}

	public class GetBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			iconBack.setVisible(false);
			title.setText("SETTINGS");

			cardLayout.show(showPanel, "center");

		}

	}

	public class ConfirmationRC implements ActionListener {

		private JDialog confirmation = new JDialog();

		@Override
		public void actionPerformed(ActionEvent e) {

			JLabel warning = new JLabel("Are you sure you want to reset all contact ?");
			JButton yes = new JButton("Yes, remove all contacts");
			JButton no = new JButton("No, abort !");
			JPanel north = new JPanel();
			JPanel center = new JPanel();

			north.add(warning);
			center.add(yes);
			center.add(no);

			yes.addActionListener(new ResetAllContacts());
			no.addActionListener(new CloseDialog());

			confirmation.add(north, BorderLayout.NORTH);
			confirmation.add(center, BorderLayout.CENTER);

			confirmation.pack();

			confirmation.setTitle("Confirm your choice");
			confirmation.setLocation(620, 400);

			confirmation.setVisible(true);
			confirmation.setAlwaysOnTop(true);
		}

		public class ResetAllContacts implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// LE RESET SE FERA AU MOMENT OU ON OUVRIRA LES CONTACTS DEPUIS LE MENU (FRAME
				// PRINCIPALE)
				reset = true;
				confirmation.dispose();
			}
		}

		public class CloseDialog implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmation.dispose();
			}
		}

	}

	// CLASS VERSION
	private class Version extends JPanel {
		private JLabel version = new JLabel("Version 1.17");
		private JLabel credits = new JLabel("©Brice Berclaz & Samuel Michellod - 2019");

		private JPanel center = new JPanel();

		// Constructeur
		public Version() {

			center.setLayout(new GridLayout(3, 1));

			center.add(version);
			center.add(credits);

			this.add(center, BorderLayout.CENTER);
		}
	}

	// CLASS INFORMATION
	private class Information extends JPanel {

		// Variables
		private String osName = System.getProperty("os.name");
		private Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		private double width = screenDim.getWidth();
		private double height = screenDim.getHeight();
		private String javaVersion = System.getProperty("java.version");
		private String pathJava = System.getProperty("java.home");
		private String hostName = getHostName();
		private String IPAddress = getIP();
		private String userName = System.getProperty("user.name");

		// JLabels
		private JLabel os = new JLabel("Operating system : " + osName);
		private JLabel screenW = new JLabel("Screen width=" + width);
		private JLabel screenH = new JLabel("Screen height=" + height);
		private JLabel host = new JLabel("Name of the device : " + hostName);
		private JLabel javaV = new JLabel("JAVA version : " + javaVersion);
		private JLabel javaP = new JLabel("JRE directory : " + pathJava);
		private JLabel userN = new JLabel("User name : " + userName);
		private JLabel IP = new JLabel("IP local host : " + IPAddress);

		private JLabel title1 = new JLabel("Current machine informations");
		private JLabel title2 = new JLabel("Screen informations");
		private JLabel title3 = new JLabel("Java informations");
		private JLabel title4 = new JLabel("Network informations");

		private JLabel empty = new JLabel();
		private JLabel empty2 = new JLabel();
		private JLabel empty3 = new JLabel();

		// JPANEL center
		private JPanel center = new JPanel();

		// Constructeur
		public Information() {

			panelProperties();
			titleProperties();

			center.add(title1);
			center.add(host);
			center.add(os);
			center.add(userN);

			center.add(empty);

			center.add(title2);
			center.add(screenW);
			center.add(screenH);

			center.add(empty2);

			center.add(title3);
			center.add(javaV);
			center.add(javaP);

			center.add(empty3);

			center.add(title4);
			center.add(IP);

			this.add(center, BorderLayout.CENTER);
		}

		private void titleProperties() {
			// title1.setBackground(Color.BLACK);
			title1.setForeground(Color.BLACK);
			title1.setFont(new Font("Lucida Console", Font.BOLD, 15));

			// title2.setBackground(Color.BLACK);
			title2.setForeground(Color.BLACK);
			title2.setFont(new Font("Lucida Console", Font.BOLD, 15));

			// title3.setBackground(Color.BLACK);
			title3.setForeground(Color.BLACK);
			title3.setFont(new Font("Lucida Console", Font.BOLD, 15));

			// title3.setBackground(Color.BLACK);
			title4.setForeground(Color.BLACK);
			title4.setFont(new Font("Lucida Console", Font.BOLD, 15));
		}

		private void panelProperties() {
			center.setLayout(new GridLayout(15, 1));
		}

		private String getHostName() {
			try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return hostName;
		}

		public String getIP() {
			try {
				InetAddress inetadr = InetAddress.getLocalHost();
				IPAddress = inetadr.getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return IPAddress;
		}
	}

	// CLASS CHANGEBACKGROUND
	private class ChangeBackground extends JPanel {

		// A CONFIGURER AVEC LA GALERIE

		/*
		 * Icon iconBG1 = new Icon("images/backgrounds/Background01.jpg", 400, 711);
		 * Icon iconBG2 = new Icon("images/backgrounds/Background02.jpg", 400, 711);
		 * Icon iconBG3 = new Icon("images/backgrounds/Background03.jpg", 400, 711);
		 * Icon iconBG4 = new Icon("images/backgrounds/Background04.jpg", 400, 711);
		 */

		// Constructeur
		public ChangeBackground() {

		}

	}

}
