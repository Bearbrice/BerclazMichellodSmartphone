/*
 * Contact Data
 * Author : B. Berclaz
 * Date creation : 30.04.2019
 * Date last modification : 03.06.2019 
 */

package contact;

import java.io.Serializable;

/**
 * This class is dedicated to the serialization of contact data
 * 
 * @author Brice Berclaz
 * @see java.io.Serializable
 */
public class ContactData implements Serializable {

	/* Initialization of variables */
	private int id;
	private String firstName;
	private String lastName;
	private String privatePhone;
	private String landlinePhone;
	private String address;
	private String organisation;
	private String locPicture;

	/**
	 * Constructor of the ContactData class
	 * 
	 * @param id            the panel whose parameters you want to modify
	 * @param firstname     the firstname of the contact
	 * @param name          the name of the contact
	 * @param privatePhone  the privatePhone of the contact
	 * @param landlinePhone the landlinePhone of the contact
	 * @param address       the address of the contact
	 * @param organisation  the organisation of the contact
	 * @param locPicture    the picture's location of the contact
	 */
	public ContactData(int id, String firstname, String name, String privatePhone, String landlinePhone, String address,
			String organisation, String locPicture) {
		this.id = id;
		setFirstName(firstname);
		setLastName(name);
		setPrivatePhone(privatePhone);
		setLandlinePhone(landlinePhone);
		setAddress(address);
		setOrganisation(organisation);
		setLocPicture(locPicture);
	}

	/** Secondary constructor of the ContactData class */
	public ContactData() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return firstName + " " + lastName + " - " + privatePhone + " - " + organisation;
	}

	// ******************************************************************** //
	// ************************ GETTERS & SETTERS ************************* //
	// ******************************************************************** //
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPrivatePhone() {
		return privatePhone;
	}

	public void setPrivatePhone(String privatePhone) {
		this.privatePhone = privatePhone;
	}

	public String getLandlinePhone() {
		return landlinePhone;
	}

	public void setLandlinePhone(String landlinePhone) {
		this.landlinePhone = landlinePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
