package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description for Bill Payment
 * contains get & set methods required for Bill Payment
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class BillPayment implements Serializable{
	private static int maxID = 1;
	private int billPayment_ID;
	private CheckInCheckOut cico;
	private ArrayList<RoomService> statusList = new ArrayList<>();
	private double roomTPrice;
	private double roomServiceTPrice;
	private double totalPrice;
	private double discountAmt;
	private double taxAmt;
	private double finalTotal;
	private String paymentMode;
	private Card card;
	private String paymentStatus;
	
	public BillPayment(int billPayment_ID, CheckInCheckOut cico, ArrayList<RoomService> statusList, double roomTPrice,
			double roomServiceTPrice, double totalPrice, double discountAmt, double taxAmt, double finalTotal,
			String paymentMode, Card card, String paymentStatus) {
		super();
		this.billPayment_ID = billPayment_ID;
		this.cico = cico;
		this.statusList = statusList;
		this.roomTPrice = roomTPrice;
		this.roomServiceTPrice = roomServiceTPrice;
		this.totalPrice = totalPrice;
		this.discountAmt = discountAmt;
		this.taxAmt = taxAmt;
		this.finalTotal = finalTotal;
		this.paymentMode = paymentMode;
		this.card = card;
		this.paymentStatus = paymentStatus;
	}
	
	
	public BillPayment(CheckInCheckOut cico, ArrayList<RoomService> statusList, double roomTPrice,
			double roomServiceTPrice, double totalPrice, double discountAmt, double taxAmt, double finalTotal,
			String paymentMode, Card card, String paymentStatus) {
		super();
		this.billPayment_ID = maxID;
		this.cico = cico;
		this.statusList = statusList;
		this.roomTPrice = roomTPrice;
		this.roomServiceTPrice = roomServiceTPrice;
		this.totalPrice = totalPrice;
		this.discountAmt = discountAmt;
		this.taxAmt = taxAmt;
		this.finalTotal = finalTotal;
		this.paymentMode = paymentMode;
		this.card = card;
		this.paymentStatus = paymentStatus;
		maxID++;
	}

	public static int getMaxID() { return maxID; }

	public static void setMaxID(int maxID) { BillPayment.maxID = maxID; }

	public int getBillPayment_ID() { return billPayment_ID; }

	public void setBillPayment_ID(int billPayment_ID) { this.billPayment_ID = billPayment_ID; }

	public CheckInCheckOut getCico() { return cico; }

	public void setCico(CheckInCheckOut cico) { this.cico = cico; }

	public ArrayList<RoomService> getStatusList() { return statusList; }

	public void setStatusList(ArrayList<RoomService> statusList) { this.statusList = statusList; }

	public double getRoomTPrice() { return roomTPrice; }

	public void setRoomTPrice(double roomTPrice) { this.roomTPrice = roomTPrice; }

	public double getRoomServiceTPrice() { return roomServiceTPrice; }

	public void setRoomServiceTPrice(double roomServiceTPrice) { this.roomServiceTPrice = roomServiceTPrice; }

	public double getTotalPrice() { return totalPrice; }

	public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

	public double getDiscountAmt() { return discountAmt; }

	public void setDiscountAmt(double discountAmt) { this.discountAmt = discountAmt; }

	public double getTaxAmt() { return taxAmt; }

	public void setTaxAmt(double taxAmt) { this.taxAmt = taxAmt; }

	public double getFinalTotal() { return finalTotal; }

	public void setFinalTotal(double finalTotal) { this.finalTotal = finalTotal; }

	public String getPaymentMode() { return paymentMode; }

	public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

	public Card getCard() { return card; }

	public void setCard(Card card) { this.card = card; }

	public String getPaymentStatus() { return paymentStatus; }

	public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
