package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Guest;
import hotelsystem.entity.RoomStatus;

/**
 * Description of Check In Check Out Controller
 * Retrieve check in and check out details with various methods by loading data from database
 * Save check in details by storing data to database
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class CheckInCheckOutController implements Serializable{
	private static CheckInCheckOutController instance = null;
	private final ArrayList<CheckInCheckOut> checkInList = new ArrayList<>();
	
	/**
	 * Constructor for Check In Check Out Controller
	 */
	private CheckInCheckOutController() {}
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static CheckInCheckOutController getInstance() {
        if (instance == null) {
            instance = new CheckInCheckOutController();
        }
        return instance;
    }
	
	/**
	 * Update check in check out details to database
	 */
	public void updateCheckInCheckOut(CheckInCheckOut cico) {
		checkInList.remove(cico);
		checkInList.add(cico);
        storeData();
    }
	
	/**
	 * retrieve check in check out details with guest ID
	 * return CheckInCheckOut if found
	 */
	public CheckInCheckOut getGuest(int ID) {
        for (CheckInCheckOut cico : checkInList) {
            if (cico.getCheckInCheckOut_ID() == ID)
                return cico;
        }
        return null;
    }
	
	/**
	 * Check if guest have checked-in
	 * return CheckInCheckOut if found
	 */
	public CheckInCheckOut getGuestOut(int ID) {
        for (CheckInCheckOut cico2 : checkInList) {
            if (cico2.getGuest().getGuest_ID() == ID) {
            	if(cico2.getStatus().equals("Checked-In")) {
            		return cico2; }
            }
        }
        return null;
    }
	
	/**
	 * Check if guest have any more rooms that is checked-in
	 * return boolean
	 */
	public boolean getGuestFullOut(int ID) {
		boolean check = false;
        for (CheckInCheckOut cico2 : checkInList) {
            if (cico2.getGuest().getGuest_ID() == ID) {
            	ArrayList<RoomStatus> rList = cico2.getRoomStatus();
            	for (RoomStatus rS : rList) {
            		if(rS.getStatus().equals("Checked-In")) {
            			check = true;
            			break;
            		}
            		else {
            			check = false;
            		}
            	}
            }
        }
        return check;
    }
	
	/**
	 * remove check in details from database
	 */
    public void removeCheckIn(CheckInCheckOut cico)  {
    	checkInList.remove(cico);
        storeData();
    }

    /**
	 * store check in details to database
	 */
    public void addCheckIn(CheckInCheckOut cico) {
    	checkInList.add(cico);
        storeData();
    }
    
    /**
     * Storing of Check In Check Out Informations to Database
     * Returns error message if file not found
     */
    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/CheckInCheckOut.ser"));
            out.writeInt(checkInList.size());
            out.writeInt(CheckInCheckOut.getMaxID());
            for (CheckInCheckOut cico : checkInList)
                out.writeObject(cico);
            //System.out.printf("CheckInController: %,d Entries Saved.\n", checkInList.size());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Loading of Check In and Check Out Informations from Database
     * Returns error message if file not found
     */
    public void loadData () {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/CheckInCheckOut.ser"));

            int noOfOrdRecords = ois.readInt();
            Guest.setMaxID(ois.readInt());
            System.out.println("CheckInController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                checkInList.add((CheckInCheckOut) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
