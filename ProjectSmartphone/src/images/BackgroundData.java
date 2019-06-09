/*
 * BackgroundData
 * Author : B. Berclaz
 * Date creation : 
 * Date last modification : 
 */

package images;

import java.io.Serializable;

/**
 * The BackgroundData class is a serializable class for retrieving the path of
 * the background in a text file
 * 
 * @author Brice Berclaz
 * @see java.io.Serializable
 */
public class BackgroundData implements Serializable {

	private String defaultBackground = "images/backgroundDefault/Background01.jpg";

	private String backgroundPath = defaultBackground;

	/** Constructor of the BackgroundData class */
	public BackgroundData(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	/** Secondary constructor of the BackgroundData class */
	public BackgroundData() {

	}

	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	public String getDefaultBackground() {
		return defaultBackground;
	}
}
