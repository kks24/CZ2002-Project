package hotelsystem.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomStatus;

/**
 * Description of Room UI
 * Prints out room interface options
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
public class RoomUI {
	private static RoomUI instance = null;
    private Scanner sc;

    /**
     * Set up scanner
     */
    private RoomUI() {
        sc = new Scanner(System.in);
    }
    
    /**
     * set Instance if instance is null
     * return instance
     */
    public static RoomUI getInstance() {
        if (instance == null) {
            instance = new RoomUI();
        }
        return instance;
    }
    
    private static void creationComplete(Room room) {
    	System.out.println("Room :" + room.getRoomFloorNo() +  " has been CREATED.");  
    }

    public static void UpdateComplete(Room room) {
    	System.out.println("Room :" + room.getRoomFloorNo()  +  " has been UPDATED.");    
    }
    
    /**
     * Printing of Room UI
     * calls corresponding functions based on input
     */
    public void displayOptions() {
        int choice;
        do {
        	System.out.println("~~~~~~~~~~~ ROOM MENU ~~~~~~~~~~~");
            System.out.println("1.Create Room");
            System.out.println("2.Update Room Details");
            System.out.println("0.Back to previous level");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    	createRoomDetails();
                		break;
                case 2:
                    	updateRoomDetails();
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
	 * Input necessary information to create new room
	 * saves information into database
	 */
    private void createRoomDetails() {
        sc = new Scanner(System.in);
        String roomFloorNo;
        int typeSerial;
        String bedType;
        boolean wifi;
        String wifiS;
        boolean smoking;
        String smokeS;
        boolean view;
        String viewS;
        
        System.out.print("Enter Room Floor(xx-xx): ");
        roomFloorNo = sc.next();
        
        RoomTypeController.getInstance().displayAllRoomType();
        System.out.print("Enter Room Type ID: ");
        typeSerial = sc.nextInt();
        
        System.out.print("Bed Type: ");
        bedType = sc.next();
        
        System.out.print("Wifi (Y/N): ");
        wifiS = sc.next();
        if(wifiS.equals("Y") || wifiS.equals("y"))
        	wifi = true;
        else
        	wifi = false;

        System.out.print("Smoking Room (Y/N): ");
        smokeS = sc.next();
        if(smokeS.equals("Y") || smokeS.equals("y"))
        	smoking = true;
        else
        	smoking = false;

        System.out.print("View (Y/N): ");
        viewS = sc.next();
        if(viewS.equals("Y") || viewS.equals("y"))
        	view = true;
        else
        	view = false;

        
        Room room = new Room(roomFloorNo, typeSerial, bedType, wifi, smoking, view);
        RoomUI.creationComplete(room);
        RoomController.getInstance().addRoom(room);
    }
    
    /**
	 * Input room number to retrieve particular room
	 * update requested information and saves into Database
	 */
	private void updateRoomDetails() {
    	sc = new Scanner(System.in);
    	String roomFloorNo;
    	
    	System.out.print("Enter Room No:");
    	roomFloorNo = sc.next();
    	
    	Room room = RoomController.getInstance().getRoom(roomFloorNo);
    	
    	if(room != null) {
    		ArrayList<RoomStatus> roomStatusList = new ArrayList<>();
    		String bedType = room.getBedType();
    		boolean wifi = room.isWifi();
        	boolean smoke = room.isSmoking();
        	boolean view = room.isView();
    		System.out.println("Room " + room.getRoomFloorNo() + " found! Which field to be updated?");
    		System.out.println("1. Bed Type\n"
    						+  "2. Wifi\n"
    						+  "3. Smoking Room\n"
    						+  "4. View\n"
    						+  "5. Room Status");
    		int choice = sc.nextInt();
    		switch(choice) {
    			case 1: System.out.println("Please Choose Bed Type.\n"
    									+  "1. Single\n"
    									+  "2. Double\n"
    									+  "3. Queen\n"
    									+  "4. King\n"
    									+  "0. Back");
    					int bedTypeId = sc.nextInt();
    					switch(bedTypeId) {
    						case 1: bedType = "Single";
    								break;
    						case 2: bedType = "Double";
    								break;
    						case 3: bedType = "Queen";
    								break;
    						case 4: bedType = "King";
    								break;
    						case 0:	break;
    					}
    					break;
    					
    			case 2:	System.out.print("Wifi (Y/N): ");
    					char wifiS = sc.next().charAt(0);
    					if(wifiS=='Y' || wifiS =='y')
    						wifi = true;
    					else
    						wifi = false;
    					break;
    			
    			case 3:	System.out.print("Smoking Room (Y/N): ");
    					String smokeS = sc.next();
						if(smokeS.equals("Y") || smokeS.equals("y"))
							smoke = true;
						else
							smoke = false;
    					break;
    					
    			case 4:	System.out.print("View (Y/N): ");
						String viewS = sc.next();
						if(viewS.equals("Y") || viewS.equals("y")) 
							view = true;
						else 
							view = false;
    					break;
    					
    			case 5:	roomStatusList = RoomStatusController.getInstance().getRoomStatus(roomFloorNo);
			        	if(roomStatusList != null) {
			        		System.out.println("Room Status ID	Status	Start Date	End Date");
			        		for(RoomStatus roomStatus : roomStatusList){
			        			System.out.println(roomStatus.getRoomBookings_ID() +"		"+ roomStatus.getStatus() +"		"+ roomStatus.getDate_from() +"		"+ roomStatus.getDate_to());
			        		}
			        	}
			        	else {
			        		System.out.println("Room is Vacant");
			        	}
						System.out.println("Select.\n"
						+  "1. Add New Status\n"
						+  "0. Back");
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						int status2Id = sc.nextInt();
						String dateFrom;
				        String dateTo;
				        Date startDate = null;
				        Date endDate = null;
						switch(status2Id) {
							case 1: String statusName = null;
									System.out.println("Please Choose Status.\n"
												+  "1. Under-Maintenance\n"
												+  "0. Back");
							        int status1 = sc.nextInt();
							        switch(status1) {
								        case 1: statusName = "Under-Maintenance";
										        System.out.print("Enter Date From (Format - dd/MM/yyyy): ");
										        dateFrom = sc.next();
										        System.out.print("Enter Date To (Format - dd/MM/yyyy): ");
										        dateTo = sc.next();
										        try {
										            startDate = df.parse(dateFrom + " 12:00");
										        } catch (ParseException e) {
										            e.printStackTrace();
										        }
										        try {
										            endDate = df.parse(dateTo + " 12:00");
										        } catch (ParseException e) {
										            e.printStackTrace();
										        }
										        RoomStatus roomS = new RoomStatus(roomFloorNo,0,statusName,startDate,endDate);
										        RoomStatusController.getInstance().addRoomStatus(roomS);
								        case 0:	break;
							        }
							case 0: break;
						}
							break;
    		}
    		RoomController.getInstance().updateRoom(room.getRoomFloorNo(), bedType, wifi, smoke, view);
    	}else {
    		System.out.println("Room not found. Please enter available room");
    	}
    }
}
