package images;

import java.io.Serializable;

public class BackgroundData implements Serializable {
	
	private String defaultBackground = "images/backgrounds/Background01.jpg";
	private String backgroundPath = defaultBackground;
	
	//Constructeur
	public BackgroundData(String backgroundPath) {
		this.backgroundPath=backgroundPath;
	}
	
	public BackgroundData() {
		
	}
	
	//METHODES
	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}
}
