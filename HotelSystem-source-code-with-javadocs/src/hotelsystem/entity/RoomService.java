package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description of Room Service
 * contains get & set methods required for Room Service
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
@SuppressWarnings("serial")
public class RoomService implements Serializable{
	private static int incID = 1;
	private int room_service_id;
	private ArrayList<Food> foodList = new ArrayList<>();
	private int roomStatusID;
	private String desc;
	private double totalPrice;
	private String status;
	
	public RoomService() {}
	
	public RoomService(ArrayList<Food> foodList, int roomStatusID, String desc, double totalPrice, String status) {
		this.room_service_id = incID;
		this.foodList = foodList;
		this.roomStatusID = roomStatusID;
		this.desc = desc;
		this.totalPrice = totalPrice;
		this.status = status;
		incID++;
	}
	
	public static int getIncID() { return RoomService.incID;}
	
	public static void setIncID(int ID) { RoomService.incID = ID; }
	
	public int getRoomServiceID() { return room_service_id; }
	
	public void setRoomServiceID(int roomServiceID) { this.room_service_id = roomServiceID; }
	
	public ArrayList<Food> getFoodList() { return this.foodList; }
	
	public int getRoomStatusID() { return this.roomStatusID; }
	
	public void setRoomStatusID(int roomStatusID) { this.roomStatusID = roomStatusID; }
	
	public String getDesc() { return desc; }
	
	public void setDesc(String desc) { this.desc = desc; }
	
	public double getTotalPrice() { return this.totalPrice; }
	
	public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
	
	public String getStatus() { return status; }
	
	public void setStatus(String status) { this.status = status; }
}
