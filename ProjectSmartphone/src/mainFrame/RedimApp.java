package mainFrame;

import java.awt.Dimension;
import java.awt.Toolkit;

public class RedimApp {
	
	//La resolution d'�cran de la machine h�te
	// ayant servit � coder et tester le code.
	private final double eWidth = 1920.0;
	private final double eHeight = 1200.0;
	
	// La future nouvelle dimension
	private Dimension newDim;
	
	/*
	 * Methode servant � calculer la nouvelle dimension
	 * de la GUI. en fonction d'une dimension fonctionnelle
	 * connue de l'�cran; et de la dimension de la GUI voulue
	 * par le progammeur et m�me par l'utilisateur (onResize....fait l'affaire)
	 */
	public Dimension dimCalculating(Dimension myAppDim) {
		// On r�cup�re la dim de l"ecran: machine h�te
	   Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	   
	   // On calcule la dimension appropri�e
	   double newWidth = Math.abs((eWidth-screenDim.getWidth())+ myAppDim.getWidth());
	   double newHeight = Math.abs((eHeight-screenDim.getHeight())+ myAppDim.getHeight());
	   
	   // On r�tourne cette nvlle dimension
	   //(ex: utilis�e dans la JFrame comme suit:
	   //this.setSize(newDim))
	   
	   newDim.setSize(newWidth, newHeight);
	   return newDim;
	}

}