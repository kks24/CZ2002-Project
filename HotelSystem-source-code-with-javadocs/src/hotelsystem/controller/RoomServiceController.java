package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.RoomService;
import hotelsystem.ui.RoomServiceUI;

/**
 * Description of Room Service Controller
 * Control Room Service order List
 * Storing and loading Room Service order List to the Database
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
@SuppressWarnings("serial")
public class RoomServiceController implements Serializable{
	private static RoomServiceController instance = null;
	private final ArrayList<RoomService> roomServiceList;
	
	
	/**
     * Constructor for Room Service Controller
     */
	private RoomServiceController() {
		roomServiceList = new ArrayList<>();
    }
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static RoomServiceController getInstance() {
        if (instance == null) {
            instance = new RoomServiceController();
        }
        return instance;
    }
	
	/**
     * Adding of new Room Service into Database
     */
	public void addRoomService(RoomService roomService) {
		roomServiceList.add(roomService);
		storeData();
	}
	
	/**
     * Retrieve Room Service using Room Status ID
     * return Room Service
     */
	public RoomService getRoomService(int roomStatusID) {
		for (RoomService rS : roomServiceList) {
            if (rS.getRoomStatusID() == roomStatusID)
                return rS;
        }
        return null;
	}
	
	/**
     * Retrieve Room Service using Room Status ID
     * Update Room Service's Status
     */
	public void updateRoomService(int roomStatusID, String status) {
		RoomService rs = getRoomService(roomStatusID);
		rs.setStatus(status);
		RoomServiceUI.updateComplete(rs);
		storeData();
	}
	
	/**
     * Retrieve List Room Service using Room Status ID
     * return list of Room Services
     */
	public ArrayList<RoomService> getRSList(int roomStatusID) {
    	ArrayList<RoomService> result = new ArrayList<>();
    	for (RoomService rS : roomServiceList) {
    		if (rS.getRoomStatusID() == roomStatusID) {
    			result.add(rS);
            }
        }
    	return result;
    }
	
	/**
     * Storing of Room Services to Database
     * Returns error message if file not found
     */
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/RoomService.ser"));
            out.writeInt(roomServiceList.size());
            out.writeInt(RoomService.getIncID());
            for (RoomService roomService : roomServiceList)
                out.writeObject(roomService);
            //System.out.printf("RoomServiceController: %,d Entries Saved.\n", roomServiceList.size());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
	/**
     * Loading of Room Services from Database
     * Returns error message if file not found
     */
    public void loadData () {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/RoomService.ser"));

            int noOfOrdRecords = ois.readInt();
            RoomService.setIncID(ois.readInt());
            System.out.println("RoomServiceController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
            	roomServiceList.add((RoomService) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
