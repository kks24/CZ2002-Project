package hotelsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.RoomType;

/**
 * Description of Room Type Controller
 * Manages the Room Type and to save and load Room Type List to the Database
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
public class RoomTypeController extends DatabaseController{
	private static final String DB_PATH = "DB/RoomType.dat";
	private static RoomTypeController instance = null;
	private final ArrayList<RoomType> roomTypeList;
	
	/**
     * Constructor for Room Type Controller
     */
	private RoomTypeController() {
		roomTypeList = new ArrayList<>();
    }
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static RoomTypeController getInstance() {
        if (instance == null) {
            instance = new RoomTypeController();
        }
        return instance;
    }
	
	/**
     * Retrieve Room Type using Room Type ID
     * return Room Type
     */
	public RoomType getRoom(int roomTypeID) {
		for (RoomType rT : roomTypeList) {
            if (rT.getTypeSerial()==roomTypeID)
                return rT;
        }
        return null;
	}
	
	/**
     * Retrieve all Room Type available
     * return list of Room Types
     */
	public ArrayList<RoomType> getAllTypes() {
		ArrayList<RoomType> rTList = new ArrayList<>();
		for(RoomType rT : roomTypeList) {
			rTList.add(rT);
		}
		return rTList;
	}
	
	/**
     * Display all Room Types available from Room Type List
     */
	public void displayAllRoomType() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ ROOM TYPES ~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(String.format("%5s %15s %8s %18s", "RoomTypeID", "RoomType", "WeekdayPrice($)", "WeekendPrice($)"));
        for (RoomType rT : roomTypeList) {
             System.out.println(String.format("%5s %18s %10s %20s",rT.getTypeSerial(),rT.getRoomType(),rT.getWeekDayRate(),rT.getWeekEndRate()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
	
	/**
     * Display all Room Types available from Room Type List
     * return number of Room Types available
     */
	public int getAllRoomType() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ ROOM TYPES ~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(String.format("%5s %15s %8s %18s", "RoomTypeID", "RoomType", "WeekdayPrice($)", "WeekendPrice($)"));
        for (RoomType rT : roomTypeList) {
             System.out.println(String.format("%5s %18s %10s %20s",rT.getTypeSerial(),rT.getRoomType(),rT.getWeekDayRate(),rT.getWeekEndRate()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return roomTypeList.size();
    }
	
	/**
     * Loading of All Room Types from Database
     * returns error message if file not found
     */
	@Override
	public boolean LoadDB() {
		roomTypeList.clear();
        if (checkFileExist(DB_PATH)) {
            try {
                // read String from text file
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    // get individual 'fields' of the string separated by SEPARATOR
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);  //pass in the string to the string tokenizer using delimiter ","
                    int id = Integer.parseInt(token.nextToken().trim());         //ID                    
                    String roomType = token.nextToken().trim();
                    double weekdayRate = Double.parseDouble(token.nextToken().trim());
                    double weekendRate = Double.parseDouble(token.nextToken().trim());

                    // create Room object from file data
                    RoomType rT = new RoomType(id, roomType, weekdayRate, weekendRate);
                    // add to Room list
                    roomTypeList.add(rT);
                }

                System.out.printf("RoomTypeController: %,d Entries Loaded.\n", roomTypeList.size());
                return true;

            } catch (IOException | NumberFormatException ex) {
                System.out.println("[ERROR] Read Error! Database for RoomType is not loaded!");
                //Logger.getLogger(PromoController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } else {
            System.out.println("[ERROR] File not found! Database for RoomType is not loaded!");
            return false;
        }
	}

	/**
     *  Saving of Room Type into Database
     *  returns error message if file not found
     */
	@Override
	public void SaveDB() {
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            // Parse Content to Write
            for (RoomType rT : roomTypeList) {
                st.setLength(0); 					// Clear Buffer
                st.append(rT.getTypeSerial()); 		// ID
                st.append(SEPARATOR);
                st.append(rT.getRoomType()); 		// Room Floor No
                st.append(SEPARATOR);
                st.append(rT.getWeekDayRate()); 	
                st.append(SEPARATOR);
                st.append(rT.getWeekEndRate()); 		
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            // Attempt to save to file
            try {
                write(DB_PATH, output);
                //System.out.printf("RoomTypeController: %,d Entries Saved.\n",
                        //output.size());
            } catch (Exception ex) {
                System.out.println("[Error] Write Error! Changes not saved!");
            }
        } else {
            System.out.println("[ERROR] File not found! Changes not Saved!");
        }
	}
}
