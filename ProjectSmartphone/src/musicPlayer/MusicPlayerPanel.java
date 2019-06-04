/*
 * Music App
 * Author: B. Berclaz
 * Date creation: 23.04.2019
 * Date last modification: 30.05.2019
 */

package musicPlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import images.Icon;

/**
 * The MusicPlayerPanel class displays a music player
 * 
 * @author Brice Berclaz
 * @see javax.sound.sampled
 */

public class MusicPlayerPanel extends JPanel {

	private boolean inProgress = false;

	private Icon iconPlay = new Icon("images/icons/Play-48.png", 48, 48);
	private Icon iconPause = new Icon("images/icons/Pause-48.png", 48, 48);
	private Icon iconStop = new Icon("images/icons/Stop-48.png", 48, 48);

	private JLabel title = new JLabel("MUSIC PLAYER");

	private Icon addMusic = new Icon("images/icons/AddMusic-48.png", 48, 48);
	private Icon delMusic = new Icon("images/icons/delete.png", 48, 48);

	private ArrayList<Track> alltracks = new ArrayList<Track>();

	// SOUND BAR = SB
	private static final int SB_MIN = 0;
	private static final int SB_MAX = 100;
	private static final int SB_INIT = 80; // initial frames per second

	private JLabel valeurSound = new JLabel("Volume actuel : 80%");
	private float vol = (float) 0.8;
	private int currentVolume = 80;
	private int volumeTemp = 0;

	private String loud = "images/icons/soundLoud-48.png";
	private String medium = "images/icons/soundMedium-48.png";
	private String low = "images/icons/soundLow-48.png";
	private String mute = "images/icons/soundMute-48.png";

	private Icon sound = new Icon(loud, 24, 24);

	private JSlider soundBar = new JSlider(JSlider.HORIZONTAL, SB_MIN, SB_MAX, SB_INIT);

	private ButtonGroup BG = new ButtonGroup();

	private JRadioButton select;
	private JPanel content = new JPanel(new GridLayout(0, 1));

	private JScrollPane scrollPane;

	// PANELS
	private JPanel manager = new JPanel(); // SOUTH
	private JPanel south = new JPanel(); // SOUTH
	private JPanel slider = new JPanel();
	private JPanel valeur = new JPanel();
	private JPanel banner = new JPanel(); // headline NORTH
	private JPanel bannerN = new JPanel(); // headline NORTH
	private JPanel bannerS = new JPanel(); // headline NORTH

	private String location;

	private Clip clip;

	public void create() {
		String location;
		String musicTitle;

		File folder = new File("music");

		if (!folder.exists()) {
			return;
		}

		// Tableau temporaire avec tous les fichiers du dossier music
		File[] all = folder.listFiles();

		// On vide le tableau dynamique
		alltracks.removeAll(alltracks);

		for (int i = 0; i < all.length; i++) {

			musicTitle = all[i].getName();

			// on enlève l'extension pour obtenir uniquement le nom
			musicTitle = substrTitle(musicTitle);

			// .substring(0, all[i].getName().length()-4);

			// On recupere le chemin relatif
			location = all[i].toString();

			// On ajoute les tracks dans le tableau dynamique
			alltracks.add(new Track(musicTitle, location));
		}

		// Supprime tous les JRADIOBUTTONs
		content.removeAll();

		// Ajoute tous les JRADIOBUTTONs
		for (int i = 0; i < alltracks.size(); i++) {
			select = new JRadioButton(alltracks.get(i).getTitle());
			select.setFont(new Font("Serif", Font.CENTER_BASELINE, 14));
			select.setForeground(Color.DARK_GRAY);
			content.add(select);

			// permet la sélection d'un seul bouton à la fois
			BG.add(select);
		}

		scrollPane = new JScrollPane(content);
	}

	// Methode qui permet d'enlever l'extension pour obtenir uniquement le nom du
	// fichier
	public String substrTitle(String name) {
		int toDelete = 0;

		// Boucle qui prend la longueur du nom et qui va jusqu'au . (décroissant)
		for (int i = name.length() - 1; i > 0; i--) {
			if (name.charAt(i) == '.') {
				toDelete++;
				break;
			}
			toDelete++;
		}

		// Integer qui contient l'index de fin du substring
		int idxEnd = name.length() - toDelete;

		name = name.substring(0, idxEnd);

		return name;
	}

	// Constructor
	public MusicPlayerPanel() {
		create();

		this.setPreferredSize(new Dimension(638, 188));
		this.setVisible(true);
		this.setOpaque(false);
		this.setLayout(new BorderLayout());

		labelProperties();

		slidersettings();

		// panel banner parameters
		banner.setLayout(new GridLayout(2, 1));

		bannerN.add(title);

		bannerS.add(addMusic);
		bannerS.add(delMusic);

		bannerN.setBackground(Color.GRAY);
		bannerS.setBackground(Color.DARK_GRAY);

		banner.add(bannerN);
		banner.add(bannerS);

		// panel south parameters
		south.setLayout(new GridLayout(3, 1));

		valeur.add(sound);
		valeur.add(valeurSound);

		slider.add(soundBar);

		// Ajout des icones play, pause et stop au panel manager
		manager.add(iconPlay);
		manager.add(iconPause);
		manager.add(iconStop);
		manager.setBackground(Color.BLUE);

		south.add(valeur);
		south.add(slider);
		south.add(manager);

		iconPlay.addActionListener(new Play());
		iconPause.addActionListener(new Pause());
		iconStop.addActionListener(new Stop());
		addMusic.addActionListener(new AddMusic());
		delMusic.addActionListener(new DeleteMusic());
		sound.addActionListener(new Mute());

		this.add(banner, BorderLayout.NORTH);
		// this.add(tracks, BorderLayout.CENTER);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

	}

	// RETRIEVED FROM
	// https://stackoverflow.com/questions/40514910/set-volume-of-java-clip
	public float getVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	// RETRIEVED FROM
	// https://stackoverflow.com/questions/40514910/set-volume-of-java-clip
	public void setVolume(float volume) {

		if (volume < 0f || volume > 1f)
			throw new IllegalArgumentException("Volume not valid: " + volume);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(20f * (float) Math.log10(volume));
	}

	// Parameters of the JSlider 'soundBar'
	private void slidersettings() {
		soundBar.setMinorTickSpacing(10);
		soundBar.setMajorTickSpacing(20);
		soundBar.setPaintTicks(true);
		soundBar.setPaintLabels(true);

		soundBar.addChangeListener(new ChangeVolume());
	}

	class ChangeVolume implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			valeurSound.setText("Volume actuel : " + ((JSlider) e.getSource()).getValue() + "%");

			currentVolume = ((JSlider) e.getSource()).getValue();

			// float z=(float) (x/100.0);
			vol = (float) (currentVolume / 100.0);

			String iconSoundLocation = null;

			if (currentVolume == 0) {
				iconSoundLocation = mute;
			}
			if (currentVolume > 0) {
				iconSoundLocation = low;
			}
			if (currentVolume > 29) {
				iconSoundLocation = medium;
			}
			if (currentVolume > 69) {
				iconSoundLocation = loud;
			}

			sound.setLocation(iconSoundLocation);
			sound.refresh();

			// execute only if there is an active clip
			if (isInProgress() == true) {
				setVolume(vol);
			}
		}
	}

	private class Mute implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (soundBar.getValue() == 0) {
				soundBar.setValue(volumeTemp);
			} else {
				volumeTemp = currentVolume;
				soundBar.setValue(0);
			}
		}
	}

	private void labelProperties() {
		title.setFont(new Font("Serif", Font.BOLD, 35));
		title.setForeground(Color.WHITE);
	}

	// findLocation
	private String findLocation() {
		String name = "";
		String emplacement = "";

		// source :
		// https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
		for (Enumeration<AbstractButton> buttons = BG.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				name = button.getText();
			}
		}

		for (int i = 0; i < alltracks.size(); i++) {
			if (alltracks.get(i).getTitle() == name) {
				emplacement = alltracks.get(i).getPath();
				return emplacement;
			}
		}

		return null;
	}

	// methode play (start)
	public void play(Clip clip) {
		// Permet de gérer l'erreur java si le son est introuvable
		if (findLocation() == null) {
			return;
		}

		// On test si aucun bouton n'est sélectionné alors rien ne se passe
		int i = -1;
		for (Enumeration<AbstractButton> buttons = BG.getElements(); buttons.hasMoreElements();) {

			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				i = 1;
			}
		}

		// si le i est toujours à -1 il n'y a pas de bouton sélectionné
		if (i == -1) {
			System.out.println("Aucun bouton sélectionné");
			return;
		}

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

			// starts the music at the volume chosen by the JSlider
			setVolume(vol);

		}
	}

	private class Play implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			play(clip);
		}
	}

	private class AddMusic implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser choisir = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV files", "wav");
			choisir.setAcceptAllFileFilterUsed(false);
			choisir.setFileFilter(filter);

			int returnVal = choisir.showOpenDialog(content);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = choisir.getSelectedFile();
				String temp = file.getName();

				String newName = Track.copy(file);

				String addPath = ("music/" + newName);

				// getting rid of extension .wav
				temp = substrTitle(temp);

				// adding music to the dynamic array
				alltracks.add(new Track(temp, addPath));

				// adding the new JRB to the panel
				addNewJRB();

			}
		}
	}

	// Method to add a the JRadioButton of the last index of the array dynamic
	public void addNewJRB() {
		// We are adding a new JRadioButton
		select = new JRadioButton(alltracks.get(alltracks.size() - 1).getTitle());
		select.setFont(new Font("Serif", Font.CENTER_BASELINE, 14));
		select.setForeground(Color.DARK_GRAY);
		content.add(select);

		// Allows the selection of only one button at a time
		BG.add(select);
	}

	private class DeleteMusic implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// On test si aucun bouton n'est sélectionné alors rien ne se passe
			int i = -1;
			String text = null;

			for (Enumeration<AbstractButton> buttons = BG.getElements(); buttons.hasMoreElements();) {

				AbstractButton button = buttons.nextElement();

				if (button.isSelected()) {
					i = 1;
					text = button.getName();
				}
			}

			// si le i est toujours à -1 il n'y a pas de bouton sélectionné
			if (i == -1) {
				System.out.println("Aucun bouton sélectionné");
				return;
			}

			// we stop the music before deleting the file
			if (!(clip == null)) {
				clip.stop();
			}

			String location = null;
			location = findLocation();

			// Remove the track from the array + from the panel
			for (int j = 0; j < alltracks.size(); j++) {
				if (alltracks.get(j).getPath() == location) {
					alltracks.remove(j);
					content.remove(j);
				}
			}

			// Delete the file from music folder
			File toDelete = new File(location);
			toDelete.delete();
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
	private void stop(Clip clip) {
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