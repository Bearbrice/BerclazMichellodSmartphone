/*
 * Music App
 * Author: Brice Berclaz
 * Date creation: 23.04.2019
 * Date last modification: 07.05.2019
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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import images.Icon;



public class MusicPlayerPanel extends JPanel {

	boolean inProgress = false;

	Icon iconPlay = new Icon("images/icons/Play-48.png", 48, 48);
	Icon iconPause = new Icon("images/icons/Pause-48.png", 48, 48);
	Icon iconStop = new Icon("images/icons/Stop-48.png", 48, 48);

	JLabel title = new JLabel("MUSIC PLAYER");
	JLabel nameMusic1 = new JLabel("Hardwell & Wildstylez feat. KiFi - \n Shine A Light");
	JLabel nameMusic2 = new JLabel("Ti�sto - WOW");
	JLabel nameMusic3 = new JLabel("Mike Williams - The Beat (Hardwell Edit)");
	
	Icon addMusic = new Icon("images/icons/AddMusic-48.png", 24, 24);
	
	ArrayList<Track> alltracks = new ArrayList<Track>();

	private String locationM1 = "music/Shine a Light (feat. KiFi).wav";
	private String locationM2 = "music/WOW.wav";
	private String locationM3 = "music/The Beat (Hardwell Edit).wav";

	ButtonGroup BG = new ButtonGroup();
	JRadioButton JRBmusic1 = new JRadioButton();
	JRadioButton JRBmusic2 = new JRadioButton();
	JRadioButton JRBmusic3 = new JRadioButton();
	
	JRadioButton select;
	JPanel content = new JPanel(new GridLayout(0,1));
	
	JScrollPane scrollPane;
	
	
    

	// PANELS
	JPanel track1 = new JPanel();
	JPanel track2 = new JPanel();
	JPanel track3 = new JPanel();
	JPanel tracks = new JPanel(); // CENTER
	JPanel manager = new JPanel(); // SOUTH
	JPanel banner = new JPanel(); // headline NORTH

	String location;

	Clip clip;
	
	public void actualize() {
		String location;
		String musicTitle;
		
		File folder = new File("music");

		if (!folder.exists()) {
			return;
		}
	
		//Tableau temporaire avec tous les fichiers du dossier music
		File[] all = folder.listFiles();
		
		//On vide le tableau dynamique
		alltracks.removeAll(alltracks);
		
		
		for(int i=0; i<all.length; i++) {
			//On recupere le nom du fichier et on enleve l'extension .wav (4 char)
			musicTitle=all[i].getName().substring(0, all[i].getName().length()-4);
			
			//On recupere le chemin relatif
			location=all[i].toString();
			
			//On ajoute les tracks dans le tableau dynamique
			alltracks.add(new Track(musicTitle, location));
		}
		
		//Supprime tous les JRADIOBUTTONs
		content.removeAll();
	    
		//Ajoute tous les JRADIOBUTTONs
	    for(int i=0; i<alltracks.size(); i++){
	        select = new JRadioButton(alltracks.get(i).getTitle());
	        select.setFont(new Font("Serif", Font.CENTER_BASELINE, 14));
			select.setForeground(Color.DARK_GRAY);
	        content.add(select);
	        
	        //permet la s�lection d'un seul bouton � la fois
	        BG.add(select);
	        
	       	        
	     }
	    
	    scrollPane = new JScrollPane(content);
	}

	// Constructor
	public MusicPlayerPanel() {
		actualize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setTitle("Test Sound Clip");
		this.setPreferredSize(new Dimension(638, 188));
		this.setVisible(true);
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		

		labelProperties();

		// Ajout des boutons au groupe de bouton BG afin de pouvoir en s�lectionner un
		// seul � la fois
		BG.add(JRBmusic1);
		BG.add(JRBmusic2);
		BG.add(JRBmusic3);

		JRBmusic1.setOpaque(false);
		JRBmusic2.setOpaque(false);
		JRBmusic3.setOpaque(false);

		// Ajout du titre
		banner.add(addMusic);
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
		addMusic.addActionListener(new AddMusic());

		this.add(banner, BorderLayout.NORTH);
		//this.add(tracks, BorderLayout.CENTER);
		this.add(scrollPane, BorderLayout.CENTER);
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
		String name="";
		String emplacement="";
		
		
		//source : https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
		for (Enumeration<AbstractButton> buttons = BG.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                name = button.getText();
            }
        }
		
		for(int i=0; i<alltracks.size(); i++) {
			if(alltracks.get(i).getTitle()==name) {
				emplacement=alltracks.get(i).getPath();
				return emplacement;
			}
		}

        return null;
	}

	// methode play (start)
	public void play(Clip clip) {
		// Permet de g�rer l'erreur java si le son est introuvable
		if(findLocation()==null) {
			return;
		}
		
		//On test si aucun bouton n'est s�lectionn� alors rien ne se passe
		int i=-1;
		for (Enumeration<AbstractButton> buttons = BG.getElements(); buttons.hasMoreElements();) {
			
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                i=1;
            }
        }
		
		if(i==-1) {
			System.out.println("Aucun bouton s�lectionn�");
			return;
		}
		
		
		/*if (!JRBmusic1.isSelected())
			if (!JRBmusic2.isSelected())
				if (!JRBmusic3.isSelected())
					return;*/
		
		System.out.print(findLocation());

		
		// met sur pause avant de d�marrer une nouvelle music
		pause(clip);

		// test si le son �tait en train de jouer, alors il recommence l� o� il s'est
		// arr�t�
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
	
	private class AddMusic implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*JFileChooser choisir = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("wav");
			choisir.setAcceptAllFileFilterUsed(false);
			choisir.setFileFilter(filter);*/
			
			/*JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			
			//jfc.showOpenDialog(content);
			
			int returnValue = jfc.showOpenDialog(content);
			// int returnValue = jfc.showSaveDialog(null);
			File selectedFile=new File("");
			
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = jfc.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());
			}
			
			String z=selectedFile.getName();
			
			Path source = Paths.get(selectedFile.getAbsolutePath());
		    Path destination = Paths.get("/music/"+z);
		 
		    try {
				Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			JFileChooser choisir = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV files", "wav");
			choisir.setAcceptAllFileFilterUsed(false);
			choisir.setFileFilter(filter);

			int returnVal = choisir.showOpenDialog(content);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = choisir.getSelectedFile();
				String temp = file.getName();

				String newName = Track.copy(file);

				actualize();
				content.repaint();
				scrollPane.repaint();
				//revalidate();
				//repaint();
			}
			
			
			

//			int returnVal = choisir.showOpenDialog(this);
//
//			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				File file = choisir.getSelectedFile();
//				String temp = file.getName();
			
			actualize();
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

		// remet l'emplacement � 0 pour recommencer la lecture
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

	// Permet de checker quand la musique est termin�e
	public void checkProgress() {
		// Test si clip est vide
		if (clip == null) {
			return;
		}

		// Test qui regarde si la musique est termin�e
		if (clip.isRunning() == false) {
			setInProgress(false);
		}

	}
}