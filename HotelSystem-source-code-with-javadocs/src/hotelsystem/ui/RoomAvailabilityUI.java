package hotelsystem.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import hotelsystem.controller.RoomController;
import hotelsystem.entity.Room;

/**
 * Description of Room Availability UI
 * Prints out room availability interface options
 * @since 17/04/2018
 * @version 1.0
 * @author Solberg Anna
 */
public class RoomAvailabilityUI {
	private static RoomAvailabilityUI instance = null;
    private Scanner sc;
    private Date today = new Date( );
    private Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
    
    /**
     * Set up scanner
     */
    private RoomAvailabilityUI() {
        sc = new Scanner(System.in);
    }
    
    /**
     * set Instance if instance is null
     * return instance
     */
    public static RoomAvailabilityUI getInstance() {
        if (instance == null) {
            instance = new RoomAvailabilityUI();
        }
        return instance;
    }
    
    /**
     * Printing of Reservation UI
     * calls corresponding functions based on input
     */
    public void displayOptions() {
    	boolean wifi = false;
    	boolean view = false;
    	boolean smoke = false;
    	boolean noSmoke = false;
    	boolean bedtype = false;
      	ArrayList<Room> matchingRoomList = getAllCurrentVacantRooms();
    	    int choice;
        do {
        	System.out.println("~~~~~~~~ ROOM AVAILABILITY MENU ~~~~~~~~~");
        	System.out.println("Add filter:");
        	
        	if(!wifi) {System.out.println("1.WIFI");}
            if(!view) {System.out.println("2.View");}
            if(!smoke) {System.out.println("3.Smoking");}
            if(!noSmoke) {System.out.println("4.No-smoking");}
            if(!bedtype) {System.out.println("5.Bedtype");}
            System.out.println("6.Refresh all filters");
            System.out.println("0.Back to previous level");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            choice = sc.nextInt();
            switch (choice) {
            	case 1:
                	    matchingRoomList = WIFIFilter(matchingRoomList);
                	    printRooms(matchingRoomList);
                	    wifi = true;
                		break;
                case 2:
                	    matchingRoomList = viewFilter(matchingRoomList);
            	        printRooms(matchingRoomList);
            	        view = true;
                    	break;
                case 3:
                  		matchingRoomList = smokingFilter(matchingRoomList);
            	        printRooms(matchingRoomList);
            	        smoke = true;
            	        break;
                case 4: 
                 		matchingRoomList = noSmokingFilter(matchingRoomList);
        	            printRooms(matchingRoomList);
        	            break;
                case 5:
                  		matchingRoomList = bedtypeFilter(matchingRoomList);
        	            printRooms(matchingRoomList);
        	            noSmoke = true;
        	            break;
                case 6:
	                	wifi = false;
	                	view = false;
	                	smoke = false;
	                	noSmoke = false;
	                	bedtype = false;
                	    matchingRoomList = getAllCurrentVacantRooms();
                	    System.out.println("You filters have been removed. You can add new filters.");
                	    break;
                case 0:
                    	break;
                default:
                    	System.out.println("Invalid input");
                    	break;
            }
        } while (choice > 0);
    }
    
    /**
     * Retrieve all vacant rooms from current day to the next day
     * return list of Rooms
     */
    private ArrayList<Room> getAllCurrentVacantRooms() {
	    	ArrayList<Room> vacantList = RoomController.getInstance().getAllRoom(today,tomorrow);
	    	return vacantList;
    }
    
    /**
     * Function to print list of vacant rooms
     */
    private void printRooms(ArrayList<Room> rList) {
    		System.out.println("~~~~~~~~~~~~~~~~~~~~ ROOM AVAILABILITY ~~~~~~~~~~~~~~~~~~~~");
    	    if (!rList.isEmpty()) {
    	    	System.out.println("Available rooms matching your search:");
    	     	char floor = rList.get(1).getRoomFloorNo().charAt(1);
    	     	System.out.print("Floor " + floor + ": ");
    	       	for (Room r : rList) {
    	       		if (r.getRoomFloorNo().charAt(1) >floor){
    	       			floor = r.getRoomFloorNo().charAt(1);
    	       			System.out.println();
    	       			System.out.print("Floor " + floor + ": ");
    	       		} 
    	       		System.out.print(r.getRoomFloorNo() + ", ");
    	       		
    	       	}
    	       	System.out.println();
    	       	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	       	System.out.println();
    	       	System.out.println("Add more filters or refresh your filters to do a new search.");
    	       	System.out.println();
    	    }
    	    else {
    	    	    System.out.println("No rooms matching your search.");
    	    	    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	    }
    }
    
    /**
     * Filter list of rooms for rooms with wifi
     * return list of filtered wifi rooms
     */
    private ArrayList<Room> WIFIFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isWifi()==true) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    /**
     * Filter list of rooms for smoking
     * return list of filtered smoking rooms
     */
    private ArrayList<Room> smokingFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isSmoking()==true) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    /**
     * Filter list of non-smoking rooms
     * return list of non-smoking rooms
     */
    private ArrayList<Room> noSmokingFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isSmoking()==false) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    /**
     * Filter list of rooms with scenary
     * return list of rooms with scenary
     */
    private ArrayList<Room> viewFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isView()==true) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    /**
     * Print menu for bed type filter
     * filter through bed type according to input
     * return filtered list of rooms
     */
    private ArrayList<Room> bedtypeFilter(ArrayList<Room> rList) {
    	   
    	System.out.println("What bedtype do you want?");
        System.out.println("1.Single");
        System.out.println("2.Double");
        System.out.println("3.Queen");
        System.out.println("4.King");
        System.out.println("0. Back to previous level");
        
    	int choice = sc.nextInt();
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	String bedtype = null;
      	
      	switch (choice) {
        case 1:
        	    bedtype = "Single";
        		break;
        case 2:
          	bedtype = "Double";
            	break;
        case 3:
         	bedtype = "Queen";
         	break;
        case 4:
         	bedtype = "Single";
         	break;
        case 0:
            	return rList;
        default:
            	System.out.println("Invalid input");
            	break;
    }
        
      	for (Room r : rList) {
       		if (r.getBedType().equals(bedtype)) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
}
    
