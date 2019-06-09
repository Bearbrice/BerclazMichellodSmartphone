/*
 * Photo
 * Author: S. Michellod
 * Date creation: 27.04.2019
 * Date last modification: 31.05.2019
 */

package gallery;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

/**
 * Class that will manage the picture in the gallery
 * 
 * @author Samuel Michellod
 *
 */

public class Photo implements Serializable {

	private String path;
	private BufferedImage picture;

	/** Constructor of the Photo class */
	public Photo(String path, BufferedImage picture) {
		this.path = path;
		this.picture = picture;

	}

	/**
	 * Method that will delete the image in the gallery
	 */

	public void delete() {
		picture.flush();
		File file = new File(path);
		file.delete();

	}

	/**
	 * Method that will copy the picture in the folder imagesgallery
	 * 
	 * @param File the file you want to copy
	 * @return The name of the picture
	 */
	public static String copy(File file) {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String Name = file.getName() + " " + timestamp.getTime();

		Path path2 = Paths.get("imagesgallery/" + Name);
		try {
			Files.copy(file.toPath(), path2, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public BufferedImage getPicture() {
		return picture;
	}

	public void setImg(BufferedImage picture) {
		this.picture = picture;
	}

}
