/*
 * Author : Brice Berclaz
 * Date creation : 30 mai 2019
 */

/* CLASSE DEDIE A LA SERIALISATION*/

package contact;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class ContactData implements Serializable {

	// Variable qui stock l'id du contact en création
	// int idContact=Integer.parseInt(read());

	// Initialisation des variables
	private int id;
	private String prenom;
	private String nom;
	private String telephonePrive;
	private String telephoneFixe;
	private String adresse;
	private String organisation;
	private String locPicture;


	// Constructeur
	public ContactData(int id, String prenom, String nom, String telephonePrive, String telephoneFixe, String adresse,
			String organisation, String locPicture) {
		this.id = id;
		setPrenom(prenom);
		setNom(nom);
		setTelephonePrive(telephonePrive);
		setTelephoneFixe(telephoneFixe);
		setAdresse(adresse);
		setOrganisation(organisation);
		setLocPicture(locPicture);
	}

	public ContactData() {

	}

	// Methode de lecture du fichier
	/*
	 * private String read() { String retour="";
	 * 
	 * File dossier = new File("src/Contact"); dossier.mkdir();
	 * 
	 * File fichier = new File(dossier, "data.txt");
	 * 
	 * try { FileReader read = new FileReader(fichier); BufferedReader bfread = new
	 * BufferedReader(read); retour = bfread.readLine(); read.close();
	 * bfread.close();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * return retour; }
	 */

	// Methode d'écriture du fichier
	/*
	 * private void write(int idContact) { this.idContact=idContact;
	 * 
	 * File dossier = new File("src/Contact"); dossier.mkdir();
	 * 
	 * File fichier = new File(dossier, "data.txt");
	 * 
	 * try { fichier.createNewFile();
	 * 
	 * FileWriter ecriture = new FileWriter(fichier); BufferedWriter bfwrite = new
	 * BufferedWriter(ecriture); bfwrite.write(Integer.toString(idContact));
	 * bfwrite.close();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } }
	 */

	// Méthode getters & setters
	/*
	 * public int getIdContact() { return idContact; }
	 */

	@Override
	public String toString() {
		// return String.format("ContactData X ~ [id: #%d, name: %s]", id, prenom, nom);
		return prenom + " " + nom + " - " + telephonePrive + " - " + organisation;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephonePrive() {
		return telephonePrive;
	}

	public void setTelephonePrive(String telephonePrive) {
		this.telephonePrive = telephonePrive;
	}

	public String getTelephoneFixe() {
		return telephoneFixe;
	}

	public void setTelephoneFixe(String telephoneFixe) {
		this.telephoneFixe = telephoneFixe;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
	public String getLocPicture() {
		return locPicture;
	}

	public void setLocPicture(String locPicture) {
		this.locPicture = locPicture;
	}

	

}
