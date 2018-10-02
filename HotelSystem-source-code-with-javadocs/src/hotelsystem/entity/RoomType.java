package hotelsystem.entity;

import java.io.Serializable;

/**
 * Description of Room Type
 * contains get & set method required for Room Type
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
@SuppressWarnings("serial")
public class RoomType implements Serializable{
	private int type_serial;
	private String room_type;
	private double weekday_rate;
	private double weekend_rate;
	
	public RoomType() {}
	
	public RoomType(int typeSerial, String roomType, double weekday_rate, double weekend_rate) {
		this.type_serial = typeSerial;
		this.room_type = roomType;
		this.weekday_rate = weekday_rate;
		this.weekend_rate = weekend_rate;
	}
	
	public int getTypeSerial() { return this.type_serial; }
	
	public String getRoomType() { return room_type; }
	
	public void setRoomType(String roomType) { this.room_type = roomType; }
	
	public double getWeekDayRate() { return weekday_rate; }
	
	public void setWeekDayRate(double weekday_rate) { this.weekday_rate = weekday_rate; }
	
	public double getWeekEndRate() { return weekend_rate; }
	
	public void setWeekEndRate(double weekend_rate) { this.weekend_rate = weekend_rate; }
}
