package hotelsystem.ui;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import hotelsystem.controller.CheckInCheckOutController;
import hotelsystem.controller.ReservationController;
import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Guest;
import hotelsystem.entity.Reservation;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomStatus;
import hotelsystem.entity.RoomType;

/**
 * Description of Checking In UI
 * Enable guest to check-in via walk in or by reservation
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
public class CheckInUI {
	private static CheckInUI instance = null;
    private Scanner sc;

    /**
     * Set up scanner
     */
    private CheckInUI() {
        sc = new Scanner(System.in);
    }
    
    /**
     * set Instance if instance is null
     * return instance
     */
    public static CheckInUI getInstance() {
        if (instance == null) {
            instance = new CheckInUI();
        }
        return instance;
    }

    /**
     * Printing of Checking In UI
     * calls corresponding functions based on input
     */
    public void displayOptions() {
        int choice;
        try {
	        do {
	            System.out.println("~~~~~~~~~~ CHECK-IN MENU ~~~~~~~~~~~");
	            System.out.println("1. Walk-In Check-In");
	            System.out.println("2. Reservation Check-In");
	            System.out.println("0. Back to previous menu");
	            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	            System.out.print("Pick a choice: ");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                    createWalkInCheckIn();
	                	break;
	                case 2:
	                    createReservationCheckIn();
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
	 * Walk in guest check in and check if it's existing or new guest
	 */
    public void createWalkInCheckIn() {
    	Guest guest = null;
    	int choice;
    	do {
    		System.out.println("Select an option:");
            System.out.println("1.New Customer");
            System.out.println("2.Existing Customer");
    		System.out.println("0.Back to previous menu");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                	guest = GuestUI.getInstance().createnewGuest();
	                	if(guest!=null)
	                	createWalkinCheckIn(guest);
	                	break;
	                case 2:
	         	       String guestName;
	         	       System.out.println("Enter Guest Name: ");
	         	       guestName = sc.next();
	         	       guest = GuestUI.getInstance().searchGuest(guestName);
	                	if(guest!=null)
	                		createWalkinCheckIn(guest);
	                    break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
    }
    
    /**
	 * Create check in for reservation
	 * requires user to provide reservation code to check in
	 */
    public void createReservationCheckIn() {
    	ArrayList<RoomStatus> statusList = new ArrayList<>();
    	sc = new Scanner(System.in);
    	System.out.println("Enter Reservation Code");
        String rcode = sc.nextLine();
        Reservation rdetails = ReservationController.getInstance().getReservation(rcode);
        if (rdetails != null) {
	        for (RoomStatus roomStatus : rdetails.getStatusList()) {
	        	RoomStatus roomS = new RoomStatus(roomStatus.getRoomBookings_ID(),roomStatus.getRoomFloor_No(), rdetails.getGuest().getGuest_ID() , "Checked-In", roomStatus.getDate_from(), roomStatus.getDate_to());
	        	RoomStatusController.getInstance().updateStatustoCheckedIn(roomS);
	        	statusList.add(roomS);
	        }
	        CheckInCheckOut cico = new CheckInCheckOut(rdetails.getGuest(),rdetails.getNumberOfChildren(),rdetails.getNumberOfAdults(),statusList,"Checked-In",null);
	        if(statusList != null) {
	        	 double total = 0;
	             SimpleDateFormat dfS = new SimpleDateFormat("EEE");
	             for(RoomStatus rS : statusList) {
	             	int days = (int) ((rS.getDate_to().getTime() - rS.getDate_from().getTime()) / (1000 * 60 * 60 * 24)-1);
	             	Date dateCheck = rS.getDate_from();
	             	for(int i = 0; i <= days ; i++) {
	                 	Room rm = RoomController.getInstance().getRoom(rS.getRoomFloor_No());
	                 	RoomType rT = RoomTypeController.getInstance().getRoom(rm.getRoomType());
	                 	if(dateCheck.equals(rS.getDate_from()) || dateCheck.equals(rS.getDate_to()) || (dateCheck.after(rS.getDate_from()) && dateCheck.before(rS.getDate_to()))) {
	                 		String dateCheckS = dfS.format(dateCheck);
	                 		if(dateCheckS.equals("Sat") || dateCheckS.equals("Sun"))
	                 			total += rT.getWeekEndRate();
	                 		else
	                 			total += rT.getWeekDayRate();
	                 	}
	                 	dateCheck = new Date(dateCheck.getTime() + TimeUnit.DAYS.toMillis(1));
	             	}
	             }
		        printConfirmation(cico, total);
		        CheckInCheckOutController.getInstance().addCheckIn(cico);
		        rdetails.setStatus("Checked-In");
		        ReservationController.getInstance().updateReservation(rdetails);
	        }
        }
        else {
        	System.out.println("Invaild Reservation Code. Please Try Again.");
        }
    }
    
    /**
	 * Create check in for walk in customers
	 */
    public void createWalkinCheckIn(Guest guest) {
    	sc = new Scanner(System.in);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	ArrayList<RoomStatus> statusList = new ArrayList<>();
        String dateTo;
        Date startDate = new Date();
        Date storeDate = new Date();
        String sD = df.format(startDate);
        try {
        	startDate = df.parse(sD);
        	storeDate = df2.parse(sD + " 12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
    	int noChild;
    	int noAdult;
    	int roomtype;
    	String wifi, smoke, view;
        
        
    	System.out.print("Enter Number of Adults: ");
        noAdult = sc.nextInt();
        System.out.print("Enter Number of Childrens: ");
        noChild = sc.nextInt();
        System.out.print("Enter Date To (dd/MM/yyyy): ");
        dateTo = sc.next();
        try {
            endDate = df.parse(dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startDate.equals(endDate)) {
        	System.out.println("Error: Minumum Duration is one night. Please Try Again.");
        	return;
        }
        else {
        	try {
                endDate = df2.parse(dateTo + " 12:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        Boolean addRoomB = null;
        ArrayList<String> roomNoList = new ArrayList<>();
        String roomNo = null;
        Boolean noroom = false;
        
        do {
        	ArrayList<Room> tempRoomList = new ArrayList<>();
             do {
            	 RoomTypeController.getInstance().displayAllRoomType();
            	 System.out.println("Which Room Do You Want? ");
                 roomtype = sc.nextInt();
                 RoomTypeController.getInstance().getRoom(roomtype);
                 
                tempRoomList = checkExisting(storeDate, endDate, roomtype, roomNoList);
                
                if(tempRoomList == null) { 
                	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ ROOMS AVAILABLE ~~~~~~~~~~~~~~~~~~~~~~~~");
                	System.out.println("No Available Rooms for for your chosen dates");
               		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
               		noroom=true;
                }
                else
                	noroom=false;
            } while(noroom);
            System.out.print("Do you want to filter? (Y/N) :");
            char filterChoice = sc.next().charAt(0);
            if(filterChoice=='Y' || filterChoice=='y') {
            	ArrayList<Room> filterList = filterRoom(tempRoomList, roomNoList, roomtype);
            	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ ROOMS AVAILABLE ~~~~~~~~~~~~~~~~~~~~~~~~");
        		System.out.println(String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi" , "Smoking Room", "Window View"));
            	if(!filterList.isEmpty()) {
        			for (Room room : filterList) {
        				if(!room.getRoomFloorNo().equals(roomNo)) {
        					RoomType rT = RoomTypeController.getInstance().getRoom(room.getRoomType());
        					if(room.isWifi() == true) {
        						wifi = "Yes";
        					}else {
        						wifi = "No";
        					}
        					if(room.isSmoking() == true) {
        						smoke = "Yes";
        					}else {
        						smoke = "No";
        					}
        					if(room.isView() == true) {
        						view = "Yes";
        					}else {
        						view = "No";
        					}
        					System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(), rT.getRoomType(), wifi, smoke, view));
        				}
        			}
        		}
               	else {
               		System.out.println("No Available Rooms for for your chosen dates");
               		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
               		return;
               	}
            		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
               }
            else if(filterChoice=='N' || filterChoice=='n') {}
            else {
            	System.out.println("Invalid Input!");
            }
            Boolean check = null;
            do {
	            System.out.print("Please Select a Room No(xx-xx): ");
	            Boolean proceed = null;
	            roomNo = sc.next();
	            for (Room tList : tempRoomList) {
	            	if(tList.getRoomFloorNo().equals(roomNo)) {
	            		proceed=true;
	            		check=true;
	            		break;
	            	}
	            	else {
	            		proceed=false;
	            	}
	            }
	            if(!proceed){
	            	System.out.println("Invalid Input! Please Try Again.");
	            	check=false;
	            }
            }while(!check);
            
            roomNoList.add(roomNo);
            RoomStatus roomStatus = new RoomStatus(roomNo, guest.getGuest_ID() , "Checked-In", storeDate, endDate);
            statusList.add(roomStatus);
            
        	System.out.print("Do you wish to add additional rooms? (Y/N): ");
        	char addRoom = sc.next().charAt(0);    	
        	if(addRoom == 'Y' || addRoom == 'y')
        		addRoomB = true;
        	else if(addRoom == 'N' || addRoom == 'n')
        		addRoomB = false;
        }while(addRoomB);
        
        if(statusList != null) {
       	 double total = 0;
            SimpleDateFormat dfS = new SimpleDateFormat("EEE");
            for(RoomStatus rS : statusList) {
            	int days = (int) ((rS.getDate_to().getTime() - rS.getDate_from().getTime()) / (1000 * 60 * 60 * 24)-1);
            	Date dateCheck = rS.getDate_from();
            	for(int i = 0; i <= days ; i++) {
                	Room rm = RoomController.getInstance().getRoom(rS.getRoomFloor_No());
                	RoomType rT = RoomTypeController.getInstance().getRoom(rm.getRoomType());
                	if(dateCheck.equals(rS.getDate_from()) || dateCheck.equals(rS.getDate_to()) || (dateCheck.after(rS.getDate_from()) && dateCheck.before(rS.getDate_to()))) {
                		String dateCheckS = dfS.format(dateCheck);
                		if(dateCheckS.equals("Sat") || dateCheckS.equals("Sun"))
                			total += rT.getWeekEndRate();
                		else
                			total += rT.getWeekDayRate();
                	}
                	dateCheck = new Date(dateCheck.getTime() + TimeUnit.DAYS.toMillis(1));
            	}
            }
        
	        CheckInCheckOut cico = new CheckInCheckOut(guest,noChild,noAdult,statusList,"Checked-In",null);
	        printConfirmation(cico, total);
	        CheckInCheckOutController.getInstance().addCheckIn(cico);
	        
	        for(RoomStatus s : statusList) {
	        	RoomStatusController.getInstance().addRoomStatus(s);
        	}
        }
    }
    
    /**
	 * Function to check for any vacant rooms according to indicated date from and to date to
	 * returns list of vacant rooms of different room type
	 */
    private ArrayList<Room> checkExisting(Date start, Date end, int roomtype, ArrayList<String> roomNo) {
		String wifi, smoke, view;
		ArrayList<Room> checkRoom = new ArrayList<>();
		ArrayList<Room> roomList = RoomController.getInstance().getAllRoom(start, end);
		if(!roomList.isEmpty()) {
			for (Room room : roomList) {
				if(!roomNo.contains(room.getRoomFloorNo())) {
					if(room.getRoomType() == roomtype) {
						checkRoom.add(room);
					}
				}
	        }
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ ROOMS AVAILABLE ~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi" , "Smoking Room", "Window View"));
		if(!checkRoom.isEmpty()) {
			for (Room room : checkRoom) {
				if(room.getRoomType() == roomtype) {
					RoomType rT = RoomTypeController.getInstance().getRoom(room.getRoomType());
					if(room.isWifi() == true) {
						wifi = "Yes";
					}else {
						wifi = "No";
					}
					if(room.isSmoking() == true) {
						smoke = "Yes";
					}else {
						smoke = "No";
					}
					if(room.isView() == true) {
						view = "Yes";
					}else {
						view = "No";
					}
					System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(), rT.getRoomType(), wifi, smoke, view));
				}
			}
		}
		else if(checkRoom.isEmpty()){
			System.out.println("No Available Rooms for for your chosen dates");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			return null;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return checkRoom;
    }
    
    /**
	 * Filters through list of vacant room
	 * from the choice of wifi, smoking and scenary view rooms
	 * returns list of filtered rooms
	 */
    private ArrayList<Room> filterRoom(ArrayList<Room> roomList, ArrayList<String> roomNo, int roomtype){
		ArrayList<Room> filterRoomList = new ArrayList<>();
		Boolean wifiB = null;
		Boolean smokeB = null;
		Boolean viewB = null;
    	System.out.print("Wifi (Y/N) :");
    	char wifiFilter = sc.next().charAt(0);
    	if(wifiFilter =='Y' || wifiFilter =='y')
    		wifiB = true;
    	else if(wifiFilter == 'N' || wifiFilter == 'n')
    		wifiB = false;
    	System.out.print("Smoking Room (Y/N) :");
    	char smokeFilter = sc.next().charAt(0);
    	if(smokeFilter =='Y' || smokeFilter =='y')
    		smokeB = true;
    	else if(smokeFilter == 'N' || smokeFilter == 'n')
    		smokeB = false;
    	System.out.print("Scenery View (Y/N) :");
    	char viewFilter = sc.next().charAt(0);
    	if(viewFilter =='Y' || viewFilter =='y')
    		viewB = true;
    	else if(viewFilter == 'N' || viewFilter == 'n')
    		viewB = false;
    	for(Room room : roomList) {
    		if(!roomNo.contains(room.getRoomFloorNo()) && room.getRoomType() == roomtype) {
    			if(wifiB==room.isWifi() && smokeB==room.isSmoking() && viewB==room.isView())
        			filterRoomList.add(room);
    		}
    	}
    	return filterRoomList;
	}
    
    /**
	 * Takes in Check In Check Out and prints out confirmation
	 */
    private void printConfirmation(CheckInCheckOut cico, double total) {
    	Date from = null;
    	Date to = null;
		System.out.println("~~~~~~~~~~~~~~~ CONFIRMATION ~~~~~~~~~~~~~~~");
		
		System.out.println("GUEST: " + cico.getGuest().getName());
		System.out.print("ROOMS CHECKED-IN: ");
        for (RoomStatus status : cico.getRoomStatus()) {
        	System.out.print("#"+status.getRoomFloor_No() + " ");
        	from = status.getDate_from();
        	to = status.getDate_to();
        }
        System.out.println("");
        System.out.println("DATE FROM: " + from);
        System.out.println("DATE END: " + to);
        System.out.println("NO OF CHILDRENS: " + cico.getNumber_of_children());
        System.out.println("NO OF ADULTS: " + cico.getNumber_of_adults());
        DecimalFormat df = new DecimalFormat("#.00"); 
        System.out.println("TOTAL CHARGE: $" + df.format(total));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
}
