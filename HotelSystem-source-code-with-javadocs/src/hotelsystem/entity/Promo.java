package hotelsystem.entity;

import java.util.Date;

/**
 * Descriptions for Promotions
 * contains get & set methods required for Promotions
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
public class Promo {
	private static int maxID = 1;
	private int promo_ID;
	private int roomType_ID;
	private String promo_desc;
	private double discount_amt;
	private Date promo_from;
	private Date promo_to;
	
	
	public Promo() {}

	public Promo(int promo_ID, int roomType_ID, String promo_desc, double discount_amt, Date promo_from,
			Date promo_to) {
		super();
		this.promo_ID = maxID;
		this.roomType_ID = roomType_ID;
		this.promo_desc = promo_desc;
		this.discount_amt = discount_amt;
		this.promo_from = promo_from;
		this.promo_to = promo_to;
		maxID++;
	}

	public Promo(int roomType_ID, String promo_desc, double discount_amt, Date promo_from,
			Date promo_to) {
		super();
		this.promo_ID = maxID;
		this.roomType_ID = roomType_ID;
		this.promo_desc = promo_desc;
		this.discount_amt = discount_amt;
		this.promo_from = promo_from;
		this.promo_to = promo_to;
		maxID++;
	}

	public static int getMaxID() { return maxID; }

	public static void setMaxID(int maxID) { Promo.maxID = maxID; }

	public int getPromo_ID() { return promo_ID; }

	public void setPromo_ID(int promo_ID) { this.promo_ID = promo_ID; }

	public int getRoomType_ID() { return roomType_ID; }

	public void setRoomType_ID(int roomType_ID) { this.roomType_ID = roomType_ID; }

	public String getPromo_desc() { return promo_desc; }

	public void setPromo_desc(String promo_desc) { this.promo_desc = promo_desc; }

	public double getDiscount_amt() { return discount_amt; }

	public void setDiscount_amt(double discount_amt) { this.discount_amt = discount_amt; }

	public Date getPromo_from() { return promo_from; }

	public void setPromo_from(Date promo_from) { this.promo_from = promo_from; }

	public Date getPromo_to() { return promo_to; }

	public void setPromo_to(Date promo_to) { this.promo_to = promo_to; }
}
