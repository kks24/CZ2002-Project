package hotelsystem.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Description of Reservation
 * contains get & set methods required for Reservation
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
@SuppressWarnings("serial")
public class Reservation implements Serializable{
	private static int incID = 1;
	private int reservation_ID;
	private String reservation_code;
	private Guest guest;
	private ArrayList<RoomStatus> statusList = new ArrayList<>();
	private int number_of_children;
	private int number_of_adults;
	private String status;
	
	public Reservation(Guest guest, ArrayList<RoomStatus> statusList, 
			int noChild, int noAdult, String status) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
    	LocalDate localDate = LocalDate.now();
    	
		this.reservation_ID = incID;
		this.reservation_code = "R" + incID + dtf.format(localDate);
		this.guest = guest;
		this.statusList = statusList;
		this.number_of_children = noChild;
		this.number_of_adults = noAdult;
		this.status = status;
		incID++;
	}
	
	public Reservation(Guest guest, int noChild, int noAdult, String status) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
    	LocalDate localDate = LocalDate.now();
    	
		this.reservation_ID = incID;
		this.reservation_code = "R" + incID + dtf.format(localDate);
		this.guest = guest;
		this.number_of_children = noChild;
		this.number_of_adults = noAdult;
		this.status = status;
		incID++;
	}
	
	public int getReservationID() { return this.reservation_ID; }
	
	public static int getIncID() { return Reservation.incID; }
	
	public static void setIncID(int ID) { Reservation.incID = ID; }
	
	public String getReservationCode() { return reservation_code; }
	
	public void setReservationCode(String reserveCode) { this.reservation_code = reserveCode; }
	
	public Guest getGuest() { return guest; }
	
	public ArrayList<RoomStatus> getStatusList(){ return statusList; }

	public int getNumberOfChildren() { return number_of_children; }

	public void setNumberOfChildren(int number_of_children) { this.number_of_children = number_of_children; }

	public int getNumberOfAdults() { return number_of_adults; }

	public void setNumberOfAdults(int number_of_adults) { this.number_of_adults = number_of_adults; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }
	
}
