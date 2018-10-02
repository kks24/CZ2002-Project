package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description for Check In Check Out
 * contains get & set methods required for Check In Check Out
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class CheckInCheckOut implements Serializable{
	private static int maxID = 1;
	private int CheckInCheckOut_ID;
	private Guest guest;
	private int number_of_children;
	private int number_of_adults;
	private String status;
	private ArrayList<RoomStatus> roomStatus = new ArrayList<>();
	private BillPayment bill;

	public CheckInCheckOut(int checkInCheckOut_ID, Guest guest, int number_of_children, int number_of_adults,
			ArrayList<RoomStatus> roomStatus, String status, BillPayment bill) {
		CheckInCheckOut_ID = maxID;
		this.guest = guest;
		this.number_of_children = number_of_children;
		this.number_of_adults = number_of_adults;
		this.roomStatus = roomStatus;
		this.status = status;
		this.bill = bill;
		maxID++;
	}
	
	public CheckInCheckOut(Guest guest, int number_of_children, int number_of_adults,
			ArrayList<RoomStatus> roomStatus, String status, BillPayment bill) {
		CheckInCheckOut_ID = maxID;
		this.guest = guest;
		this.number_of_children = number_of_children;
		this.number_of_adults = number_of_adults;
		this.roomStatus = roomStatus;
		this.status = status;
		this.bill = bill;
		maxID++;
	}
	
	public static int getMaxID() { return maxID; }

	public static void setMaxID(int maxID) { CheckInCheckOut.maxID = maxID; }

	public int getCheckInCheckOut_ID() { return CheckInCheckOut_ID; }

	public void setCheckInCheckOut_ID(int checkInCheckOut_ID) { CheckInCheckOut_ID = checkInCheckOut_ID; }

	public Guest getGuest() { return guest; }

	public void setGuest(Guest guest) { this.guest = guest; }

	public int getNumber_of_children() { return number_of_children; }

	public void setNumber_of_children(int number_of_children) { this.number_of_children = number_of_children; }

	public int getNumber_of_adults() { return number_of_adults; }

	public void setNumber_of_adults(int number_of_adults) { this.number_of_adults = number_of_adults; }

	public ArrayList<RoomStatus> getRoomStatus() { return roomStatus; }

	public void setRoomStatus(ArrayList<RoomStatus> roomStatus) { this.roomStatus = roomStatus; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }

	public BillPayment getBill() { return bill;}

	public void setBill(BillPayment bill) { this.bill = bill; }
}
