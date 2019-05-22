package mainFrame;

import java.awt.Dimension;
import java.awt.Toolkit;

public class RedimApp {
	
	//La resolution d'écran de la machine hôte
	// ayant servit à coder et tester le code.
	private final double eWidth = 1920.0;
	private final double eHeight = 1200.0;
	
	// La future nouvelle dimension
	private Dimension newDim;
	
	/*
	 * Methode servant à calculer la nouvelle dimension
	 * de la GUI. en fonction d'une dimension fonctionnelle
	 * connue de l'écran; et de la dimension de la GUI voulue
	 * par le progammeur et même par l'utilisateur (onResize....fait l'affaire)
	 */
	public Dimension dimCalculating(Dimension myAppDim) {
		// On récupère la dim de l"ecran: machine hôte
	   Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	   
	   // On calcule la dimension appropriée
	   double newWidth = Math.abs((eWidth-screenDim.getWidth())+ myAppDim.getWidth());
	   double newHeight = Math.abs((eHeight-screenDim.getHeight())+ myAppDim.getHeight());
	   
	   // On rétourne cette nvlle dimension
	   //(ex: utilisée dans la JFrame comme suit:
	   //this.setSize(newDim))
	   
	   newDim.setSize(newWidth, newHeight);
	   return newDim;
	}

}