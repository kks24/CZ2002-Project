package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import hotelsystem.entity.Reservation;
import hotelsystem.entity.RoomStatus;

/**
 * Description of Reservation Controller
 * Controls and manages the flow of Reservations
 * Displaying, storing and loading of Reservation List to the Database
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
@SuppressWarnings("serial")
public class ReservationController implements Serializable{
	private static ReservationController instance = null;
	private final ArrayList<Reservation> reservationList = new ArrayList<>();
	
	/**
     * Constructor for Reservation Controller
     */
	private ReservationController() {}
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static ReservationController getInstance() {
		if (instance == null) {
            instance = new ReservationController();
        }
        return instance;
	}
	
	/**
	 *  Updating of Reservation Object from the Reservation List 
	 *  Store Reservation List into Database
	 */
	public void updateReservation(Reservation reservation) {
		reservationList.remove(reservation);
		reservationList.add(reservation);
		storeData();
	}
	
	/**
     *  Retrieve Reservation using Reservation Code
     *  return Reservation
     */
	public Reservation getReservation(String rCode) {
		for (Reservation reservation : reservationList) {
            if (reservation.getReservationCode().equals(rCode))
                return reservation;
        }
        return null;
	}
	
	/**
     *  Retrieve Reservation using ID
     *  return Reservation
     */
	public Reservation getReservationByID(int rID) {
		for (Reservation reservation : reservationList) {
            if (reservation.getReservationID() == rID)
                return reservation;
        }
        return null;
	}

	/**
     *  Adding new Reservation into Database
     */
	public void addReservation(Reservation reservation) {
		reservationList.add(reservation);
        storeData();
	}
	
	/**
     *  Retrieve Reservations under waitlist
     *  return list of Reservations
     */
	public ArrayList<Reservation> getWaitList() {
		ArrayList<Reservation> waitList = new ArrayList<>();
		for (Reservation r : reservationList) {
			if(r.getStatus().equals("WaitList"))
				waitList.add(r);
        }
		return waitList;
	}
	
	/**
     *  Check whether if Reservations are expired on current date
     *  update Reservation to expired if Reservations made are before current date
     */
	public void checkExpiredRoom(){
		Date current = new Date();
		for (Reservation r : reservationList) {
			ArrayList<RoomStatus> rsList = r.getStatusList();
			for (RoomStatus rs : rsList) {
				Date newDate = new Date(rs.getDate_from().getTime() + TimeUnit.HOURS.toMillis(1));
				if(current.after(newDate) && rs.getStatus().equals("Reserved")) {
					RoomStatusController.getInstance().updateStatustoExpired(rs);
					r.setStatus("Expired");
					updateReservation(r);
					System.out.println("Reservation " + r.getReservationCode() + " is Expired");
				}
			}
		}
	}
	
	/**
     *  Retrieve all Reservations that are not expired
     *  return List of Reservations
     */
	public ArrayList<Reservation> getAllReservation() {
		return reservationList;
	}
	
	/**
     * Storing of Reservations to Database
     * Returns error message if file not found
     */
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/Reservation.ser"));
            out.writeInt(reservationList.size());
            out.writeInt(Reservation.getIncID());
            for (Reservation reservation : reservationList)
                out.writeObject(reservation);
            //System.out.printf("ReservationController: %,d Entries Saved.\n", reservationList.size());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
	/**
     * Loading of Reservations from Database
     * Returns error message if file not found
     */
    public void loadData () {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
        	ois = new ObjectInputStream(new FileInputStream("DB/Reservation.ser"));

            int noOfOrdRecords = ois.readInt();
            Reservation.setIncID(ois.readInt());
            System.out.println("ReservationController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
            	reservationList.add((Reservation) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1 ) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
