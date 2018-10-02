package hotelsystem.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.RoomStatus;

/**
 * Description of Room Status Controller
 * Controls individual Room Status
 * To display, saving, loading of Room Status List to the Database
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 * @author Kan Kah Seng
 */
public class RoomStatusController extends DatabaseController{
	private static final String DB_PATH = "DB/RoomStatus.dat";
	private static RoomStatusController instance = null;
	private final ArrayList<RoomStatus> roomStatusList;
	
	/**
     * Constructor for Room Status Controller
     */
	private RoomStatusController() {
		roomStatusList = new ArrayList<>();
    }
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static RoomStatusController getInstance() {
        if (instance == null) {
            instance = new RoomStatusController();
        }
        return instance;
    }
	
	/**
     * Update Room Status's status to Checked-In using Booking ID
     * save Room Status into Database
     */
	public void updateStatustoCheckedIn(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Checked-In");
            }
        }
        SaveDB();
    }
	
	/**
     * Update Room Status's status to Checked-Out using Booking ID
     * save Room Status into Database
     */
	public void updateStatustoCheckedOut(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Checked-Out");
            }
        }
        SaveDB();
    }
	
	/**
     * Update Room Status's status to Cancelled using Booking ID
     * save Room Status into Database
     */
	public void updateStatustoCancelled(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Cancelled");
            }
        }
        SaveDB();
	}
	
	/**
     * Update Room Status's status to Expired using Booking ID
     * save Room Status into Database
     */
	public void updateStatustoExpired(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Expired");
            }
        }
        SaveDB();
	}
	
	/**
     * Update Room Status's status under wait list to Confirmed
     * save Room Status into Database
     */
	public void updateWaitList(ArrayList<RoomStatus> rsList) {
		for (RoomStatus status : rsList) {
        	status.setStatus("Confirmed");
        }
        SaveDB();
	}
	
	/**
     * Validate whether if Room Status is Under-Maintenance, Reserved or Checked-In
     * return false if Room Status's status not under any of the statuses indicated
     */
	public Boolean checkRoomStatus(RoomStatus rs) {
		Boolean check = null;
		for (RoomStatus roomStatus : roomStatusList) {
			if(roomStatus.getDate_from().equals(rs.getDate_from()) && roomStatus.getDate_to().equals(rs.getDate_to())) {
				if (roomStatus.getStatus().equals("Under-Maintenance") || roomStatus.getStatus().equals("Reserved") || roomStatus.getStatus().equals("Checked-In")) 
					return check = false;
	            else
	            	return check = true;
			}
        }
		return check;
	}
	
	/**
     * Retrieve Room Status using room number
     * return list of Room Statuses if not empty
     */
	public ArrayList<RoomStatus> getRoomStatus(String roomFloorNo) {
		ArrayList<RoomStatus> result = new ArrayList<>();
        for (RoomStatus roomStatus : roomStatusList) {
            if (roomStatus.getRoomFloor_No().equals(roomFloorNo)) 
            	result.add(roomStatus);
        }
        if(!result.isEmpty())
        	return result;
        else 
        	return null;
        
    }
	
	/**
     * Retrieve Room Status using room number, status under Checked-In and current date before Room Status's date to
     * return Room Status
     */
	public RoomStatus getStatus(String roomFloorNo) {
		Date current = new Date();
        for (RoomStatus roomStatus : roomStatusList) {
            if (roomStatus.getRoomFloor_No().equals(roomFloorNo) && roomStatus.getStatus().equals("Checked-In") && current.before(roomStatus.getDate_to())) 
            	return roomStatus;
        }
        return null;
    }
	
	/**
     * Retrieve Room Statuses using start date and end date
     * return list of Room Statuses
     */
	public ArrayList<RoomStatus> getAllReserveRoom(Date start, Date end){
		ArrayList<RoomStatus> reserveList = new ArrayList<>();
		for (RoomStatus roomS : roomStatusList) {
				if(start.equals(roomS.getDate_from()) || end.equals(roomS.getDate_to())) {
					reserveList.add(roomS);
				}
		}
        return reserveList;
	}
	
	/**
     * Add new Room Status into Database
     */
	public void addRoomStatus(RoomStatus roomStatus) {
		roomStatusList.add(roomStatus);
    	SaveDB();
    }

	/**
     * Loading of Room Statuses from Database
     * returns error message if file not found
     */
	@Override
	public boolean LoadDB() {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		roomStatusList.clear();
        if (checkFileExist(DB_PATH)) {
            try {
                // read String from text file
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    // get individual 'fields' of the string separated by SEPARATOR
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);  //pass in the string to the string tokenizer using delimiter ","
                    @SuppressWarnings("unused")
					int id = Integer.parseInt(token.nextToken().trim());         //ID  
                    String room_ID = token.nextToken().trim();
                    int guest_ID = Integer.parseInt(token.nextToken().trim());
                    String status = token.nextToken().trim();
                    Date dateFrom = null;
                    Date dateTo = null;
					try {
						dateFrom = df.parse(token.nextToken().trim());
						dateTo = df.parse(token.nextToken().trim());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    // create Room object from file data
                    RoomStatus roomStatus = new RoomStatus(room_ID,guest_ID,status,dateFrom,dateTo);
                    // add to Room list
                    roomStatusList.add(roomStatus);
                }

                System.out.printf("RoomStatusController: %,d Entries Loaded.\n", roomStatusList.size());
                return true;

            } catch (IOException | NumberFormatException ex) {
                System.out.println("[ERROR] Read Error! Database for RoomStatus is not loaded!");
                //Logger.getLogger(PromoController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } else {
            System.out.println("[ERROR] File not found! Database for RoomStatus is not loaded!");
            return false;
        }
	}

	/**
     *  Saving of Room Statuses into Database
     *  returns error message if file not found
     */
	@Override
	public void SaveDB() {
		// TODO Auto-generated method stub
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            // Parse Content to Write
            for (RoomStatus roomStatus : roomStatusList) {
                st.setLength(0); 				// Clear Buffer
                st.append(roomStatus.getRoomBookings_ID()); 	// ID
                st.append(SEPARATOR);
                st.append(roomStatus.getRoomFloor_No()); 	// Room_ID
                st.append(SEPARATOR);
                st.append(roomStatus.getGuest_ID()); 	// Room_ID
                st.append(SEPARATOR);
                st.append(roomStatus.getStatus()); // Room Status
                st.append(SEPARATOR);
                st.append(roomStatus.getDate_from()); 	// Date From
                st.append(SEPARATOR);
                st.append(roomStatus.getDate_to()); 	// Date To
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            // Attempt to save to file
            try {
                write(DB_PATH, output);
                //System.out.printf("RoomStatusController: %,d Entries Saved.\n",
                        //output.size());
            } catch (Exception ex) {
                System.out.println("[Error] Write Error! Changes not saved!");
            }
        } else {
            System.out.println("[ERROR] File not found! Changes not Saved!");
        }
	}
}
