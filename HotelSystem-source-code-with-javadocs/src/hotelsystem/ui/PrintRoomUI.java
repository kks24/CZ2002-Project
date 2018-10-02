package hotelsystem.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomType;

/**
 * Description of Print Room UI
 * Prints out checking of room interface options
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 * @author Solberg Anna
 */
public class PrintRoomUI {
	private static PrintRoomUI instance = null;
	private final Scanner sc;
	private Date today = new Date( );
    private Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

    /**
     * Set up scanner
     */
    private PrintRoomUI() { 
        sc = new Scanner(System.in);
    }
    
    /**
     * set Instance if instance is null
     * return instance
     */
    public static PrintRoomUI getInstance() {
        if (instance == null) {
            instance = new PrintRoomUI();
        }
        return instance;
    }
    
    /**
     * Printing of checking Room Status UI
     * calls corresponding functions based on input
     */
    public void displayOptions() {
        int choice;
        try {
	        do {
	            System.out.println("Print Room Status statistic report by:");
	            System.out.println("  1.Room type occupancy rate");
	            System.out.println("  2.Room status");
	            System.out.println("  0.Back to previous level");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                	    printRoomTypeOccupancyRate();
	                	    break;
	                case 2:
	                    	printRoomStatus();
	                    	break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
    } 
    
    /**
     * Retrieve all rooms that is vacant for today and tomorrow and counts the total number
     * prints individual room and size of list
     */
    private void printRoomTypeOccupancyRate() {
    		    
	    ArrayList<RoomType> roomTList = RoomTypeController.getInstance().getAllTypes();
	    ArrayList<Room> roomList = RoomController.getInstance().getAllRoom(today,tomorrow);
	    ArrayList<Room> vacantRooms = new ArrayList<>();
	    
	    for (RoomType rtList : roomTList) {
	    	for (Room rList : roomList) {
	    		if (rList.getRoomType() == rtList.getTypeSerial()) {
	    			vacantRooms.add(rList);
	    		}
		    }
	    	int totalRoomNo = RoomController.getInstance().getRoomTypeQty(rtList.getTypeSerial());
	    	
	    	System.out.println(rtList.getRoomType() +" : Number : "+ vacantRooms.size()  +" out of " + totalRoomNo);
	    	System.out.print("           Rooms: ");
	    	for (Room printRList : vacantRooms) {
	    		System.out.print(printRList.getRoomFloorNo() + " ");
	    	}
	    	System.out.println("");
	    	
	    	vacantRooms.clear();
	    }
    }
    
    /**
     * Retrieve all rooms that is vacant for today and and tomorrow
     * Retrieve all rooms that is occupied currently
     * Retrieve all rooms that is currently on maintenance
     * prints out individual room number according to the status
     */
    private void printRoomStatus() {
	    System.out.println("Vacant:");
	    ArrayList<Room> vacantroomList = RoomController.getInstance().getAllRoom(today,tomorrow);
    	System.out.print("		Rooms: ");
    	int i = 0;
    	for (Room printRList : vacantroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println(printRList.getRoomFloorNo() + " ");
    			i=0;
    		}
    	}
	    System.out.println();
	    ArrayList<Room> occupiedroomList = RoomController.getInstance().getAllRooms();
	    ArrayList<Room> mroomList = RoomController.getInstance().getAllMaintenanceRoom();
	    System.out.println("Occupied:");
	    System.out.print("		Rooms:");
	    	for (Room vRList : vacantroomList) {
	    			occupiedroomList.remove(vRList);
	    	}
	    	for (Room mRList : mroomList) {
    			occupiedroomList.remove(mRList);
	    	}
	    
	    
	    for (Room printRList : occupiedroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println();
    			i=0;
    		}
    	}
	    System.out.println();
	    System.out.println("Maintenance:");
	    System.out.print("		Rooms:");
	    for (Room printRList : mroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println();
    			i=0;
    		}
    	}

	    System.out.println();
	    
	    vacantroomList.clear();
	    occupiedroomList.clear();
	    mroomList.clear();
    }
}