package hotelsystem.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.CheckInCheckOutController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Guest;
import hotelsystem.entity.RoomStatus;

/**
 * Description of Checking Out UI
 * Full check out process with generating bill payment
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
public class CheckOutUI {
	private static CheckOutUI instance = null;
    private Scanner sc;

    /**
     * Set up scanner
     */
    private CheckOutUI() {
        sc = new Scanner(System.in);
    }
    
    /**
     * set Instance if instance is null
     * return instance
     */
    public static CheckOutUI getInstance() {
        if (instance == null) {
            instance = new CheckOutUI();
        }
        return instance;
    }
	
    /**
     * Printing of Checking Out UI
     * Retrieve room details based on guest name and calls corresponding functions based on input
     */
	public void displayOptions() {
        int choice;
        try {
	        Guest guest = null;
	        String guestName;
	        sc = new Scanner(System.in);
	        do {
		        System.out.println("Enter Guest Name: ");
		        guestName = sc.nextLine();
		        guest = GuestUI.getInstance().searchGuest(guestName);
	        }
	        while (guest==null);
	        
	        
	        CheckInCheckOut cico = CheckInCheckOutController.getInstance().getGuestOut(guest.getGuest_ID());
	        if (cico!=null) {
	        	System.out.println(cico.getRoomStatus().size() + " Rooms Found");
	        	System.out.println("Room Booking ID		Room Number	Status			Checked-In Date			Check Out Date");
	        	ArrayList<RoomStatus> rSlist = cico.getRoomStatus();
	        	for (RoomStatus rL : rSlist) {
	        		System.out.println(rL.getRoomBookings_ID() +"			"+ rL.getRoomFloor_No() +"		"+ rL.getStatus() +"		"+ rL.getDate_from() +"	"+ rL.getDate_to());
	             }
	        	
	        	do {
	        	System.out.println("Select an option: ");
	        	System.out.println("1. Check out for all rooms and Print Bill Invoice");
	        	System.out.println("2. Check out for selected rooms");
	        	choice = sc.nextInt();
	            sc.nextLine();
		            switch (choice) {
		                case 1:
		                    checkOutAll(cico);
		                	return;
		                case 2:
		                    checkOutSelectedRooms(cico);
		                    return;
		                case 0:
		                    break;
		                default:
		                    System.out.println("Invalid Choice");
		                    break;
		            }
	        	} while (choice > 0);
	        }
	        else {
	        	System.out.println("No Rooms Found. Please Try Again.");
	        }

        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
    }
	
	/**
	 * Check-out all rooms and and generate bill for guest
	 */
	private void checkOutAll(CheckInCheckOut cico) {
		ArrayList<RoomStatus> updateList = new ArrayList<RoomStatus>();
		
		for (RoomStatus roomStatus : cico.getRoomStatus()) {
			if(roomStatus.getStatus().equals("Checked-In")) {
				RoomStatus roomS = new RoomStatus(roomStatus.getRoomBookings_ID(),roomStatus.getRoomFloor_No(), roomStatus.getGuest_ID() , "Checked-Out", roomStatus.getDate_from(), roomStatus.getDate_to());
				RoomStatusController.getInstance().updateStatustoCheckedOut(roomS);
				updateList.add(roomS);
			}
			else {
        		updateList.add(roomStatus);
        	}
        }
		cico.setRoomStatus(updateList);
		cico.setStatus("Checked-Out");
		CheckInCheckOutController.getInstance().updateCheckInCheckOut(cico);
		System.out.println("All rooms has been checked-out successfully. Generating Bill Invoice...");
		BillPaymentUI.getInstance().generateBill(cico);
	}
	
	/**
	 * Check-out selected room by user input on room number
	 * validate if it's the last room checked-out it will proceed to generate bill for guest 
	 */
	private void checkOutSelectedRooms(CheckInCheckOut cico) {
		ArrayList<RoomStatus> updateList = new ArrayList<RoomStatus>();
		System.out.println("Please Select a Room No(xx-xx): ");
        String roomNo = sc.next();
        for (RoomStatus roomStatus : cico.getRoomStatus()) {
        	if(roomStatus.getRoomFloor_No().equals(roomNo)) {
        		RoomStatus roomS = new RoomStatus(roomStatus.getRoomBookings_ID(),roomStatus.getRoomFloor_No(), roomStatus.getGuest_ID() , "Checked-Out", roomStatus.getDate_from(), roomStatus.getDate_to());
            	RoomStatusController.getInstance().updateStatustoCheckedOut(roomS);
            	updateList.add(roomS);
        	}
        	else {
        		updateList.add(roomStatus);
        	}
        }
        cico.setRoomStatus(updateList);
		System.out.println("Room Number: " + roomNo + " has been checked-out successfully.");
		boolean check = CheckInCheckOutController.getInstance().getGuestFullOut(cico.getGuest().getGuest_ID());
		if(check) {
				CheckInCheckOutController.getInstance().updateCheckInCheckOut(cico);
		}
		else {
			cico.setStatus("Checked-Out");
			CheckInCheckOutController.getInstance().updateCheckInCheckOut(cico);
			System.out.println("All rooms has been checked-out successfully. Generating Bill Invoice...");
			BillPaymentUI.getInstance().generateBill(cico);
		}
	}
}
