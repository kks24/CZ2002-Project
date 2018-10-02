package hotelsystem.entity;

import java.io.Serializable;

/**
 * Description of Room
 * contains get & set method for Room
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
@SuppressWarnings("serial")
public class Room implements Serializable{
	private static int incID = 1;
	private int room_ID;
	private String room_floor_no;
	private int roomTypeID;
	private String bed_type; //Need to include
	private boolean wifi;
	private boolean smoking;
	private boolean view;
	
	public Room(String roomFloorNo, int roomTypeID, String bedType, boolean wifi, boolean smoking, boolean view) {
		this.room_ID = incID;
		this.room_floor_no = roomFloorNo;
		this.roomTypeID = roomTypeID;
		this.bed_type = bedType;
		this.wifi = wifi;
		this.smoking = smoking;
		this.view = view;
		incID++;
	}
	
	public Room(int roomID, String roomFloorNo, int roomTypeID, String bedType, boolean wifi, boolean smoking, boolean view) {
		this.room_ID = incID;
		this.room_floor_no = roomFloorNo;
		this.roomTypeID = roomTypeID;
		this.bed_type = bedType;
		this.wifi = wifi;
		this.smoking = smoking;
		this.view = view;
		incID++;
	}
	
	public int getRoomID() { return this.room_ID; }
	
	public static int getIncID() { return Room.incID; }
	
	public static void setIncID(int ID) { Room.incID = ID; }

	public String getRoomFloorNo() { return room_floor_no; }
	
	public void setRoomFloorNo(String roomFloorNo) { this.room_floor_no = roomFloorNo; }

	public int getRoomType() { return roomTypeID; }

	public void setRoomType(int roomTypeID) { this.roomTypeID = roomTypeID; }
	
	public String getBedType() { return bed_type; }
	
	public void setBedType(String bedType) { this.bed_type = bedType; }

	public boolean isWifi() { return wifi; }

	public void setWifi(boolean wifi) { this.wifi = wifi; }

	public boolean isSmoking() { return smoking; }

	public void setSmoking(boolean smoking) { this.smoking = smoking; }

	public boolean isView() { return view; }

	public void setView(boolean view) { this.view = view;}

}
