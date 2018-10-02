package hotelsystem.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description of Room Status
 * contains get & set methods required for Room Status
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class RoomStatus implements Serializable{
	private static int incID = 1;
	private int roomBookings_ID;
	private String roomFloor_No;
	private int guest_ID;
	private String status;
	private Date date_from;
	private Date date_to;
	
	public RoomStatus(int roomBookings_ID,String roomFloor_No,int guest_ID ,String status, Date date_from, Date date_to) {
		super();
		this.roomBookings_ID = roomBookings_ID;
		this.roomFloor_No = roomFloor_No;
		this.guest_ID = guest_ID;
		this.status = status;
		this.date_from = date_from;
		this.date_to = date_to;
	}
	
	public RoomStatus(String roomFloor_No,int guest_ID, String status, Date date_from, Date date_to) {
		super();
		this.roomBookings_ID = incID;
		this.roomFloor_No = roomFloor_No;
		this.guest_ID = guest_ID;
		this.status = status;
		this.date_from = date_from;
		this.date_to = date_to;
		incID++;
	}

	public static int getIncID() { return incID; }

	public static void setIncID(int incID) { RoomStatus.incID = incID; }

	public String getRoomFloor_No() { return roomFloor_No; }

	public void setRoomFloor_No(String roomFloor_No) { this.roomFloor_No = roomFloor_No; }

	public int getRoomBookings_ID() { return roomBookings_ID; }

	public void setRoomBookings_ID(int roomBookings_ID) { this.roomBookings_ID = roomBookings_ID; }

	public int getGuest_ID() { return guest_ID; }

	public void setGuest_ID(int guest_ID) { this.guest_ID = guest_ID; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }

	public Date getDate_from() { return date_from; }

	public void setDate_from(Date date_from) { this.date_from = date_from; }

	public Date getDate_to() { return date_to; }

	public void setDate_to(Date date_to) { this.date_to = date_to; }
	
}
