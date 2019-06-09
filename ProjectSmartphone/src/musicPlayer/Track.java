/*
 * Music App
 * Author: B. Berclaz
 * Date creation: 21.05.2019
 * Date last modification: 30.05.2019
 */

package musicPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

/**
 * Class that will manage the tracks in the music player
 * 
 * @author Brice Berclaz
 *
 */
public class Track {

	private String title;

	private String path;

	/**
	 * Constructor of the Track class
	 * 
	 * @param title the title of the music
	 * @param path  the location where the music can be found
	 */
	public Track(String title, String path) {
		this.title = title;
		this.setPath(path);
	}

	/**
	 * Method that will copy the track in the music folder
	 * 
	 * @param file the file you want to copy
	 * @return the path of the track
	 */
	public static String copy(File file) {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String newName = file.getName() + " " + timestamp.getTime();

		Path path2 = Paths.get("music/" + newName);
		try {
			Files.copy(file.toPath(), path2, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
