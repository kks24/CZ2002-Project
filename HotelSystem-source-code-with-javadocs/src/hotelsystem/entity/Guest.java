package hotelsystem.entity;

import java.io.Serializable;

/**
 * Description for Guest
 * contains get & set methods required Guest
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class Guest implements Serializable{
	private static int maxID = 1;
	private int guest_ID;
	private String identity_no;
	private String name;
	private String address;
	private long contact_no;
	private String country;
	private char gender;
	private String nationality;
	private Card cardDetails;
	
	public Guest(String identity_no, String name, String address, long contact_no, String country,
			char gender, String nationality, Card cardDetails) {
		this.guest_ID = maxID;
		this.identity_no = identity_no;
		this.name = name;
		this.address = address;
		this.contact_no = contact_no;
		this.country = country;
		this.gender = gender;
		this.nationality = nationality;
		this.cardDetails = cardDetails;
		maxID++;
	}
	
	public Guest(String name) { this.name = name; }
	
	public static int getMaxID() { return maxID; }

    public static void setMaxID(int ID) { maxID = ID; }

	public int getGuest_ID() { return guest_ID; }

	public void setGuest_ID(int guest_ID) { this.guest_ID = guest_ID; }

	public String getIdentity_no() { return identity_no; }

	public void setIdentity_no(String identity_no) { this.identity_no = identity_no; }

	public String getName() { return name;}

	public void setName(String name) { this.name = name; }

	public String getAddress() { return address; }

	public void setAddress(String address) { this.address = address; }

	public long getContact_no() { return contact_no; }

	public void setContact_no(long contact_no) { this.contact_no = contact_no; }

	public String getCountry() { return country; }

	public void setCountry(String country) { this.country = country; }

	public char getGender() { return gender; }

	public void setGender(char gender) { this.gender = gender; }

	public String getNationality() { return nationality; }

	public void setNationality(String nationality) { this.nationality = nationality; }

	public Card getCardDetails() { return cardDetails; }

	public void setCardDetails(Card cardDetails) { this.cardDetails = cardDetails; }

}




