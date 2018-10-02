package hotelsystem.entity;

import java.io.Serializable;

/**
 * Description for Card
 * contains get & set methods required for Card
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class Card implements Serializable{
	private static int maxID = 1;
	private int card_ID;
	private String ccname;
	private long card_no;
	private String expiry_date;
	private int cvv;
	
	public Card(String ccname, long card_no, String expiry_date, int cvv) {
		this.card_ID = maxID;
		this.ccname = ccname;
		this.card_no = card_no;
		this.expiry_date = expiry_date;
		this.cvv = cvv;
		maxID++;
	}
	
	public static int getMaxID() { return maxID; }

    public static void setMaxID(int ID) { maxID = ID; }

	public int getCard_ID() { return card_ID; }

	public void setCard_ID(int card_ID) { this.card_ID = card_ID; }

	public String getCcname() { return ccname; }

	public void setCcname(String ccname) { this.ccname = ccname; }

	public long getCard_no() { return card_no; }

	public void setCard_no(long card_no) { this.card_no = card_no; }

	public String getExpiry_date() { return expiry_date; }

	public void setExpiry_date(String expiry_date) { this.expiry_date = expiry_date; }

	public int getCvv() { return cvv; }

	public void setCvv(int cvv) { this.cvv = cvv; } 	
}
