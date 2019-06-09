/*
 * Settings Panel
 * Author: B. Berclaz
 * Date creation: 03.05.2019
 * Date last modification: 31.05.2019
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
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import images.Icon;

/**
 * The SettingsPanel class displays a panel who show information about the PC
 * and the project and allow you to delete all contacts in one time
 * 
 * @author Brice Berclaz
 */
public class SettingsPanel extends JPanel {

	private boolean reset = false;

	private JPanel north = new JPanel();
	private JPanel center = new JPanel();

	private JButton informations = new JButton("Informations");
	private JButton version = new JButton("Version");
	private JButton resetContact = new JButton("Remove all contacts");

	private Icon iconBack = new Icon("images/icons/Back-48.png", 36, 36);

	private JLabel title = new JLabel("SETTINGS");

	private Version versionpanel = new Version();
	private Information infoPanel = new Information();

	private CardLayout cardLayout = new CardLayout();
	private JPanel showPanel = new JPanel(cardLayout);

	/** Constructor of the SettingsPanel class */
	public SettingsPanel() {

		panelSettings();
		panelBackgrounds();
		labelProperties();
		buttonProperties();

		north.add(iconBack);
		iconBack.setVisible(false);

		north.add(title);

		center.setLayout(new GridLayout(3, 1));

		center.add(informations);
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
	}

	public boolean isReset() {
		return reset;
	}

	public void setResetToFalse() {
		reset = false;
	}

	/** Modifies the parameters of the panel */
	public void panelSettings() {
		this.setPreferredSize(new Dimension(638, 188));
		this.setVisible(true);
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
	}

	/** Modifies the parameters of the JLabel title */
	private void labelProperties() {
		title.setFont(new Font("Serif", Font.BOLD, 35));
		title.setForeground(Color.WHITE);
	}

	/** Modifies the parameters of the choosen JButtons */
	private void buttonProperties() {
		informations.setBackground(Color.BLACK);
		informations.setForeground(Color.WHITE);
		informations.setFont(new Font("Algerian", Font.BOLD, 20));

		version.setBackground(Color.BLACK);
		version.setForeground(Color.WHITE);
		version.setFont(new Font("Algerian", Font.BOLD, 20));

		resetContact.setBackground(Color.BLACK);
		resetContact.setForeground(Color.WHITE);
		resetContact.setFont(new Font("Algerian", Font.BOLD, 20));
	}

	/** Modifies the parameters of the north panel */
	private void panelBackgrounds() {
		north.setBackground(Color.GRAY);
	}

	/**
	 * Launches the secondary panel from the class ShowVersion.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 */
	private class ShowVersion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "versionpanel");
			iconBack.setVisible(true);
			title.setText("VERSION");

		}

	}

	/**
	 * Launches the secondary panel from the class ShowNetwork.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 */
	private class ShowNetwork implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(showPanel, "infoPanel");
			iconBack.setVisible(true);
			title.setText("INFORMATIONS");

		}

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
			title.setText("SETTINGS");

			cardLayout.show(showPanel, "center");

		}

	}

	/**
	 * This class shows a JDialog to make sure the user is ready and sure to remove
	 * all contacts.
	 * 
	 * @author Brice Berclaz
	 * @see java.awt.event.ActionEvent
	 */
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

		/**
		 * This class allows to remove (all) contacts (R.C.)
		 * 
		 * @author Brice Berclaz
		 * @see java.awt.event.ActionEvent
		 */
		public class ResetAllContacts implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * The reset will be done when the contacts are opened from the menu (main
				 * frame)
				 */
				reset = true;
				confirmation.dispose();
			}
		}

		/**
		 * This class closes the JDialog
		 * 
		 * @author Brice Berclaz
		 * @see java.awt.event.ActionEvent
		 */
		public class CloseDialog implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmation.dispose();
			}
		}

	}

	// ******************************************************************** //
	// ********************** NEW SECONDARY CLASS ************************* //
	// ******************************************************************** //

	/**
	 * The Version class displays the version of the project.
	 * 
	 * @author Brice Berclaz
	 */
	private class Version extends JPanel {
		private JLabel version = new JLabel("Version 1.44");
		private JLabel credits = new JLabel("©Brice Berclaz & Samuel Michellod - 2019");

		private JPanel center = new JPanel();

		/** Constructor of the Version class */
		public Version() {

			center.setLayout(new GridLayout(3, 1));

			center.add(version);
			center.add(credits);

			this.add(center, BorderLayout.CENTER);
		}
	}

	/**
	 * The Information class displays some information of the user PC.
	 * 
	 * @author Brice Berclaz
	 */
	private class Information extends JPanel {

		private String osName = System.getProperty("os.name");
		private Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		private double width = screenDim.getWidth();
		private double height = screenDim.getHeight();
		private String javaVersion = System.getProperty("java.version");
		private String pathJava = System.getProperty("java.home");
		private String hostName = getHostName();
		private String IPAddress = getIP();
		private String userName = System.getProperty("user.name");

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

		private JPanel center = new JPanel();

		/** Constructor of the ContactPanel class */
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

		/** Modifies the parameters of the JLabels titles */
		private void titleProperties() {
			title1.setForeground(Color.BLACK);
			title1.setFont(new Font("Lucida Console", Font.BOLD, 15));

			title2.setForeground(Color.BLACK);
			title2.setFont(new Font("Lucida Console", Font.BOLD, 15));

			title3.setForeground(Color.BLACK);
			title3.setFont(new Font("Lucida Console", Font.BOLD, 15));

			title4.setForeground(Color.BLACK);
			title4.setFont(new Font("Lucida Console", Font.BOLD, 15));
		}

		/** Modifies the parameters of the panel center */
		private void panelProperties() {
			center.setLayout(new GridLayout(15, 1));
		}

		/**
		 * Get the HostName of the user
		 * 
		 * @return hostname of the computer
		 */
		private String getHostName() {
			try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return hostName;
		}

		/**
		 * Get the IP of the local host
		 * 
		 * @return IPAddress from the local host
		 */
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

}
