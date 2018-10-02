package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import hotelsystem.entity.Guest;

/**
 * Description of Check In Check Out Controller
 * To display, saving and loading of guest details to the Database
 * able to load and store data from database
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class GuestController implements Serializable{
	private static GuestController instance = null;
	private final ArrayList<Guest> guestList = new ArrayList<>();
	
	/**
	 * Constructor for Guest Controller
	 */
	private GuestController() {}
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static GuestController getInstance() {
        if (instance == null) {
            instance = new GuestController();
        }
        return instance;
    }
	
	/**
	 * Update guest details to database
	 */
	public void updateGuest(Guest guest) {
        guestList.remove(guest);
        guestList.add(guest);
        storeData();
    }
	
	/**
	 * Retrieve guest details with guest ID
	 * return guest if found
	 */
	public Guest getGuest(int guest_ID) {
        for (Guest guest : guestList) {
            if (guest.getGuest_ID() == guest_ID)
                return guest;
        }
        return null;
    }
	
	/**
	 * Retrieve guest details with guest name
	 * return guest if found
	 */
	public Guest getGuest(String name) {
		String checkName = name.toUpperCase();
        for (Guest guest : guestList) {
            if (guest.getName().toUpperCase().equals(checkName)){
                return guest;
            }
        }
        return null;
    }
	
	/**
	 * Retrieve guest details with identity number
	 * return guest if found
	 */
	public Guest getGuestByIdenNo(String identityID) {
        for (Guest guest : guestList) {
            if (guest.getIdentity_no().toUpperCase().equals(identityID)){
                return guest;
            }
        }
        return null;
    }
	
	/**
	 * Remove guest from database
	 */
    public void removeGuest(Guest guest) {
        guestList.remove(guest);
        storeData();
    }

    /**
	 * Add guest to database
	 */
    public void addGuest(Guest guest) {
        guestList.add(guest);
        storeData();
    }
    
    /**
	 * Add guest to database
	 * return guest
	 */
    public Guest addGuestReturn(Guest guest) {
        guestList.add(guest);
        storeData();
        return guest;
    }
    
    /**
	 * search guest by keyword with string contain function
	 * return ArrayList of guest
	 */
    public ArrayList<Guest> searchGuestList(String name) {
    	String checkName = name.toUpperCase();
    	ArrayList<Guest> result = new ArrayList<>();
    	for(Guest guest : guestList){
            if(guest.getName().toUpperCase() != null && guest.getName().toUpperCase().contains(checkName)) {
            	result.add(guest);
            }
        }
    	return result;
    }
    
    /**
     * Storing of Guest Informations to Database
     * Returns error message if file not found
     */
    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/Guest.ser"));
            out.writeInt(guestList.size());
            out.writeInt(Guest.getMaxID());
            for (Guest guest : guestList)
                out.writeObject(guest);
            //System.out.printf("GuestController: %,d Entries Saved.\n", guestList.size());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Loading of Guest Informations from Database
     * Returns error message if file not found
     */
    public void loadData () {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/Guest.ser"));

            int noOfOrdRecords = ois.readInt();
            Guest.setMaxID(ois.readInt());
            System.out.println("GuestController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                guestList.add((Guest) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
