/*
 * Music App
 * Author: Brice Berclaz
 * Date creation: 23.04.2019
 * Date last modification: 07.05.2019
 */

package musicPlayer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import images.Icon;
import panels.BannerPanel;

public class MusicPlayerPanel extends JPanel {

	boolean inProgress = false;

	Icon iconPlay = new Icon("images/icons/Play-48.png", 48, 48);
	Icon iconPause = new Icon("images/icons/Pause-48.png", 48, 48);
	Icon iconStop = new Icon("images/icons/Stop-48.png", 48, 48);

	JLabel title = new JLabel("MUSIC PLAYER");
	JLabel nameMusic1 = new JLabel("Hardwell & Wildstylez feat. KiFi - \n Shine A Light");
	JLabel nameMusic2 = new JLabel("Tiësto - WOW");
	JLabel nameMusic3 = new JLabel("Mike Williams - The Beat (Hardwell Edit)");

	private String locationM1 = "music/Shine a Light (feat. KiFi).wav";
	private String locationM2 = "music/WOW.wav";
	private String locationM3 = "music/The Beat (Hardwell Edit).wav";

	ButtonGroup BG = new ButtonGroup();
	JRadioButton JRBmusic1 = new JRadioButton();
	JRadioButton JRBmusic2 = new JRadioButton();
	JRadioButton JRBmusic3 = new JRadioButton();

	// PANELS
	JPanel track1 = new JPanel();
	JPanel track2 = new JPanel();
	JPanel track3 = new JPanel();
	JPanel tracks = new JPanel(); // CENTER
	JPanel manager = new JPanel(); // SOUTH
	JPanel banner = new JPanel(); // headline NORTH

	String location;

	Clip clip;

	// Constructor
	public MusicPlayerPanel() {

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setTitle("Test Sound Clip");
		this.setPreferredSize(new Dimension(638, 188));
		this.setVisible(true);
		this.setOpaque(false);
		this.setLayout(new BorderLayout());

		labelProperties();

		// Ajout des boutons au groupe de bouton BG afin de pouvoir en sélectionner un
		// seul à la fois
		BG.add(JRBmusic1);
		BG.add(JRBmusic2);
		BG.add(JRBmusic3);

		JRBmusic1.setOpaque(false);
		JRBmusic2.setOpaque(false);
		JRBmusic3.setOpaque(false);

		// Ajout du titre
		banner.add(title);
		banner.setBackground(Color.GRAY);

		// Ajout des icones play, pause et stop au panel manager
		manager.add(iconPlay);
		manager.add(iconPause);
		manager.add(iconStop);
		manager.setBackground(Color.BLUE);

		// Ajout des musiques dans leur panel
		track1.add(JRBmusic1);
		track1.add(nameMusic1);
		track1.setOpaque(false);

		track2.add(JRBmusic2);
		track2.add(nameMusic2);
		track2.setOpaque(false);

		track3.add(JRBmusic3);
		track3.add(nameMusic3);
		track3.setOpaque(false);

		// Ajout des musiques dans le panel tracks qui regroupes toutes les musiques
		tracks.setLayout(new GridLayout(3, 1));
		tracks.setBackground(Color.DARK_GRAY);
		tracks.add(track1);
		tracks.add(track2);
		tracks.add(track3);

		iconPlay.addActionListener(new Play());
		iconPause.addActionListener(new Pause());
		iconStop.addActionListener(new Stop());

		this.add(banner, BorderLayout.NORTH);
		this.add(tracks, BorderLayout.CENTER);
		this.add(manager, BorderLayout.SOUTH);

	}

	private void labelProperties() {
		title.setFont(new Font("Serif", Font.BOLD, 40));
		title.setForeground(Color.WHITE);

		nameMusic1.setFont(new Font("Serif", Font.BOLD, 14));
		nameMusic1.setForeground(Color.WHITE);

		nameMusic2.setFont(new Font("Serif", Font.BOLD, 14));
		nameMusic2.setForeground(Color.WHITE);

		nameMusic3.setFont(new Font("Serif", Font.BOLD, 14));
		nameMusic3.setForeground(Color.WHITE);
	}

	// findLocation
	private String findLocation() {
		String emplacement = "";
		if (JRBmusic1.isSelected())
			emplacement = locationM1;

		if (JRBmusic2.isSelected())
			emplacement = locationM2;

		if (JRBmusic3.isSelected())
			emplacement = locationM3;

		return emplacement;
	}

	// methode play (start)
	public void play(Clip clip) {
		// Permet d'enlever l'erreur java si aucun son n'est sélectionné
		/*
		 * if(location==null) { return; }
		 */
		if (!JRBmusic1.isSelected())
			if (!JRBmusic2.isSelected())
				if (!JRBmusic3.isSelected())
					return;

		// met sur pause avant de démarrer une nouvelle music
		pause(clip);

		// test si le son était en train de jouer, alors il recommence là où il s'est
		// arrêté
		if (location == findLocation()) {
			clip.start();
		} else {
			location = findLocation();

			try {
				File soundFile = new File(location);
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				// Get a sound clip resource.
				clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);

			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}

			clip.start();

			setInProgress(true);

			this.clip = clip;
		}
	}

	private class Play implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			play(clip);
		}
	}

	// methode pause (stop)
	public void pause(Clip clip) {
		try {
			// Block of code to try
			clip.stop();
		} catch (Exception e) {
			// Block of code to handle errors
		}

		setInProgress(false);

		this.clip = clip;
	}

	private class Pause implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			pause(clip);

		}

	}

	// methode stop (close)
	public void stop(Clip clip) {
		try {
			// Block of code to try
			if (clip.isRunning())
				clip.stop();
		} catch (Exception e) {
			// Block of code to handle errors
		}

		setInProgress(false);

		// remet l'emplacement à 0 pour recommencer la lecture
		location = null;
	}

	private class Stop implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			stop(clip);
		}
	}

	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	// Permet de checker quand la musique est terminée
	public void checkProgress() {
		// Test si clip est vide
		if (clip == null) {
			return;
		}

		// Test qui regarde si la musique est terminée
		if (clip.isRunning() == false) {
			setInProgress(false);
		}

	}
}