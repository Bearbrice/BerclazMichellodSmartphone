/*
 * Author : Brice Berclaz
 * Date creation : 30 mai 2019
 */

/* CLASSE DEDIE A LA SERIALISATION*/

package contact;

import java.io.Serializable;

public class ContactData implements Serializable {

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

	@Override
	public String toString() {
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
