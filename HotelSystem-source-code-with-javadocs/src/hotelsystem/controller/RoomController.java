package hotelsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.Room;
import hotelsystem.entity.RoomStatus;
import hotelsystem.ui.RoomUI;

/**
 * Description of Room Controller
 * Saving and loading of Rooms to the Database
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
public class RoomController extends DatabaseController{
	private static final String DB_PATH = "DB/Room.dat";
	private static RoomController instance = null;
	private final ArrayList<Room> roomList;
	
	/**
     * Constructor of Room Controller
     */
	private RoomController() {
		roomList = new ArrayList<>();
    }
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static RoomController getInstance() {
        if (instance == null) {
            instance = new RoomController();
        }
        return instance;
    }
	
	/**
     * Update Room details and save onto Database
     */
	public void updateRoom(String roomFloorNo, String bedType, boolean wifi, boolean smoke, boolean view) {
		Room room = getRoom(roomFloorNo);
		room.setBedType(bedType);
		room.setWifi(wifi);
		room.setSmoking(smoke);
		room.setView(view);
		RoomUI.UpdateComplete(room);
		SaveDB();
	}
	
	/**
     * retrieve Room using floor number
     * return Room
     */
	public Room getRoom(String roomFloorNo) {
        for (Room room : roomList) {
            if (room.getRoomFloorNo().equals(roomFloorNo))
                return room;
        }
        return null;
    }
	
	/**
     * Retrieve all Rooms from Database
     * return list of Rooms
     */
	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> rList = new ArrayList<>();
		for(Room r : roomList) {
			rList.add(r);
		}
		return rList;
	}
	
	/**
     * Get the total number of room types
     * return int if found
     */
	public int getRoomTypeQty(int roomTypeID) {
		ArrayList<Room> rList = new ArrayList<>();
		for(Room r : roomList) {
			if (roomTypeID == r.getRoomType()) {
			rList.add(r); }
		}
		return rList.size();
	}
	
	/**
     * Retrieve all Rooms that are vacant using Start Date and End Date as parameters
     * return list of Rooms
     */
	public ArrayList<Room> getAllRoom(Date start, Date end) {
		ArrayList<Room> roomStatusList = new ArrayList<>();
		for(Room room: roomList) {
			String roomFNo = room.getRoomFloorNo();
			ArrayList<RoomStatus> rSList = RoomStatusController.getInstance().getRoomStatus(roomFNo);
			if(rSList!=null) {
				for (RoomStatus roomStatus : rSList) {
					if((start.before(roomStatus.getDate_from())&&end.before(roomStatus.getDate_from()))){
						roomStatusList.add(room);
					}
					else if((start.after(roomStatus.getDate_to())&&end.after(roomStatus.getDate_to()))){
						roomStatusList.add(room);
					}
					else if(roomStatus.getStatus().equals("Cancelled") || roomStatus.getStatus().equals("Expired") || roomStatus.getStatus().equals("Checked-Out")) {
						roomStatusList.add(room);
					}
					else if (roomStatus.getStatus().equals("Checked-In") || roomStatus.getStatus().equals("Reserved") || roomStatus.getStatus().equals("Under-Maintenance")) {
						int i =0;
						for(Room rSCheck : roomStatusList ) {
							if(rSCheck.getRoomFloorNo().equals(roomStatus.getRoomFloor_No())) {
								roomStatusList.remove(i);
								break;
							}
							i++;
						}
					}
					else break;
					
					Object[] st = roomStatusList.toArray();
				      for (Object s : st) {
				        if (roomStatusList.indexOf(s) != roomStatusList.lastIndexOf(s)) {
				        	roomStatusList.remove(roomStatusList.lastIndexOf(s));
				         }
				      }
				}
			}
			else {
				roomStatusList.add(room);
			}
		}
		Object[] st = roomStatusList.toArray();
	      for (Object s : st) {
	        if (roomStatusList.indexOf(s) != roomStatusList.lastIndexOf(s)) {
	        	roomStatusList.remove(roomStatusList.lastIndexOf(s));
	         }
	      }
        return roomStatusList;
    }
	
	/**
     * Retrieve all Rooms that is Under-Maintenance
     * return list of Rooms
     */
	public ArrayList<Room> getAllMaintenanceRoom() {
		ArrayList<Room> roomStatusList = new ArrayList<>();
		Date current = new Date();
		for(Room room: roomList) {
			String roomFNo = room.getRoomFloorNo();
			ArrayList<RoomStatus> rSList = RoomStatusController.getInstance().getRoomStatus(roomFNo);
			if(rSList!=null) {
				for (RoomStatus roomStatus : rSList) {
					if (roomStatus.getStatus().equals("Under-Maintenance")) {
						if(roomStatus.getDate_from().before(current) && roomStatus.getDate_to().after(current)){
							roomStatusList.add(room);
						
						}
						else if (roomStatus.getDate_from().equals(current) || roomStatus.getDate_to().equals(current)) {
							roomStatusList.add(room);
						}
					}
				}
			}
		}
		Object[] st = roomStatusList.toArray();
	      for (Object s : st) {
	        if (roomStatusList.indexOf(s) != roomStatusList.lastIndexOf(s)) {
	        	roomStatusList.remove(roomStatusList.lastIndexOf(s));
	         }
	      }
        return roomStatusList;
    }
	
	/**
     * Retrieve all rooms that has Room Status under wait list
     * return list of Rooms
     */
	public ArrayList<Room> getAllWaitListRoom(Date start, Date end) {
		ArrayList<Room> roomStatusList = new ArrayList<>();
		for(Room room: roomList) {
			String roomFNo = room.getRoomFloorNo();
			ArrayList<RoomStatus> rSList = RoomStatusController.getInstance().getRoomStatus(roomFNo);
			if(rSList!=null) {
				for (RoomStatus roomStatus : rSList) {
					if((start.equals(roomStatus.getDate_from()) || end.equals(roomStatus.getDate_to()) || roomStatus.getStatus().equals("Reserved"))){
						roomStatusList.add(room);
					}
					else break;
				}
			}
			else {
				roomStatusList.add(room);
			}
		}
		Object[] st = roomStatusList.toArray();
	      for (Object s : st) {
	        if (roomStatusList.indexOf(s) != roomStatusList.lastIndexOf(s)) {
	        	roomStatusList.remove(roomStatusList.lastIndexOf(s));
	         }
	      }
        return roomStatusList;
    }

    /**
     * Adding of new Room into Database
     */
    public void addRoom(Room room) {
    	roomList.add(room);
    	SaveDB();
    }

    /**
     * Loading of Room List from Database
     * returns error message if file not found
     */
	@Override
	public boolean LoadDB() {
		roomList.clear();
        if (checkFileExist(DB_PATH)) {
            try {
                // read String from text file
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    // get individual 'fields' of the string separated by SEPARATOR
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);  //pass in the string to the string tokenizer using delimiter ","
                    int id = Integer.parseInt(token.nextToken().trim());         //ID                    
                    String roomFloorNo = token.nextToken().trim();
                    int roomTypeID = Integer.parseInt(token.nextToken().trim());
                    String bedType = token.nextToken().trim();
                    boolean wifi = Boolean.parseBoolean(token.nextToken().trim());
                    boolean smoking = Boolean.parseBoolean(token.nextToken().trim());
                    boolean view = Boolean.parseBoolean(token.nextToken().trim());

                    // create Room object from file data
                    Room room = new Room(id, roomFloorNo, roomTypeID, bedType, wifi, smoking, view);
                    // add to Room list
                    roomList.add(room);
                }

                System.out.printf("RoomController: %,d Entries Loaded.\n", roomList.size());
                return true;

            } catch (IOException | NumberFormatException ex) {
                System.out.println("[ERROR] Read Error! Database for Room is not loaded!");
                //Logger.getLogger(PromoController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } else {
            System.out.println("[ERROR] File not found! Database for Room is not loaded!");
            return false;
        }
	}

	/**
     *  Saving of Rooms into Database
     *  returns error message if file not found
     */
	@Override
	public void SaveDB() {
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            // Parse Content to Write
            for (Room room : roomList) {
                st.setLength(0); 				// Clear Buffer
                st.append(room.getRoomID()); 	// ID
                st.append(SEPARATOR);
                st.append(room.getRoomFloorNo()); // Room Floor No
                st.append(SEPARATOR);
                st.append(room.getRoomType()); 	// Room Type
                st.append(SEPARATOR);
                st.append(room.getBedType()); 	// Bed Type
                st.append(SEPARATOR);
                st.append(room.isWifi()); 		//  Wifi
                st.append(SEPARATOR);
                st.append(room.isSmoking()); 	//  Smoking
                st.append(SEPARATOR);
                st.append(room.isView()); 		//  View
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            // Attempt to save to file
            try {
                write(DB_PATH, output);
                //System.out.printf("RoomController: %,d Entries Saved.\n",
                        //output.size());
            } catch (Exception ex) {
                System.out.println("[Error] Write Error! Changes not saved!");
            }
        } else {
            System.out.println("[ERROR] File not found! Changes not Saved!");
        }
	}
}