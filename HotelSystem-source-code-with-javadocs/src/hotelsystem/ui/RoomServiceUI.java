package hotelsystem.ui;

import java.util.ArrayList;
import java.util.Scanner;

import hotelsystem.controller.FoodController;
import hotelsystem.controller.RoomServiceController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.entity.Food;
import hotelsystem.entity.RoomService;
import hotelsystem.entity.RoomStatus;

/**Description of Room Service UI
 * Prints out room service interface options
 * @since 17/04/2018
 * @version 1.0
 * @author Kenneth Yak Yong Seng
 */
public class RoomServiceUI {
	private static RoomServiceUI instance = null;
	private Scanner sc;
	
	/**
     * Set up scanner
     */
	private RoomServiceUI() {
		sc = new Scanner(System.in);
	}
	
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static RoomServiceUI getInstance() {
		if (instance == null) {
            instance = new RoomServiceUI();
        }
        return instance;
	}
	
	private static void creationComplete(RoomService rS) {
		System.out.print("RoomService :" + rS.getRoomServiceID() + "/");
		for (Food food : rS.getFoodList()) {
        	System.out.print(food.getfood_name() + " ");
        }
		System.out.print("/" + rS.getDesc() + "/" + rS.getStatus() + " has been CREATED.\n");  
    }
	
	public static void updateComplete(RoomService rS) {
    	System.out.println("RoomService :" + rS.getRoomServiceID() + " " 
				+ rS.getRoomStatusID() + " " + rS.getStatus()
				+ " has been UPDATED.");  
    }
	
	/**
     * Printing of Room Service UI
     * calls corresponding functions based on input
     */
	public void displayOptions() {
        int choice;
        do {
        	System.out.println("~~~~~~~~~ ROOM SERVICE MENU ~~~~~~~~~");
            System.out.println("1.Create Room Service");
            System.out.println("2.Update Room Service Information");
            System.out.println("0.Back");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            choice = sc.nextInt();
            switch (choice) {
                case 1:	createRoomService();
                		break;
                case 2:	updateRoomService();
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
	 * Input room number for checked-in room and prints food menu
	 * add new room service into Database
	 */
	private void createRoomService() {
		sc = new Scanner(System.in);
		double totalPrice = 0;
		Boolean check = null;
		ArrayList<Food> rsList = new ArrayList<>();
		
		System.out.print("Enter Room No: ");
		String roomNo = sc.next();
		
		RoomStatus rS = RoomStatusController.getInstance().getStatus(roomNo);
		if(rS != null) {
			System.out.println("Room Found!!");
		}
		else {
			System.out.println("Room Not Found!!");
			return;
		}
		
		do {
			displayFoodList();
			int foodChoice = sc.nextInt();
			Food f = FoodController.getInstance().getFood(foodChoice);
			rsList.add(f);
			System.out.print("Do you wish to add additional food (Y/N) :");
			char confirm = sc.next().charAt(0);
			sc.nextLine();
			if(confirm == 'Y' || confirm == 'y') {
				check = true;
			} 
			else {
				check = false;
			}
		}while(check);
		
		System.out.println("Do you wish to add any remarks? (Enter NIL if no)");
		String remarks = sc.nextLine();
		
		for(Food f : rsList) {
			totalPrice += f.getfood_price();
		}
		
		RoomService roomService = new RoomService(rsList, rS.getRoomBookings_ID(), remarks, totalPrice, "Confirmed");
		RoomServiceController.getInstance().addRoomService(roomService);
		creationComplete(roomService);
	}
	
	/**
	 * Function to get all available food list menu
	 * prints the list and input the food ID to add into the order
	 */
	private void displayFoodList() {
		ArrayList<Food> foodList = FoodController.getInstance().getAllFoodList();
		System.out.println("~~~~~~~~~~~~~~~~~~~ FOOD MENU ~~~~~~~~~~~~~~~~~~~");
		for(Food f : foodList) {
			System.out.println(f.getFood_ID() + "		" + f.getfood_name() + "		" + f.getfood_price() + "		" + f.getfood_description());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("*Please select the food ID you want to add*");
	}
	
	/**
	 * Input room number of occupied room
	 * prints out room service information
	 * update status accordingly
	 */
	private void updateRoomService() {
		sc = new Scanner(System.in);
		String status = null;
		
		System.out.print("Enter Room No: ");
		String roomNo = sc.next();
		RoomStatus rS = RoomStatusController.getInstance().getStatus(roomNo);
		RoomService rService = RoomServiceController.getInstance().getRoomService(rS.getRoomBookings_ID());
		if(rS != null) {
			System.out.println("Room Found!!");
		}
		System.out.println("~~~~~~~~~~~~ ROOM SERVICE DETAILS ~~~~~~~~~~~~");
		System.out.println("ROOM NO: " + rS.getRoomFloor_No());
		System.out.println("FOOD ORDERED: ");
		for(Food food : rService.getFoodList()) {
			System.out.print(food.getfood_name() + " ");
		}
		System.out.println("");
		System.out.println("REMARKS: " + rService.getDesc());
		System.out.println("TOTAL PRICE: " + rService.getTotalPrice());
		System.out.println("STATUS: " + rService.getStatus());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		System.out.println("Update Room Service Status");
		System.out.println("1. Preparing");
		System.out.println("2. Delivered");
		System.out.println("3. Cancelled");
		int choice = sc.nextInt();
		
		switch(choice) {
			case 1: status = "Preparing";
					break;
			case 2:	status = "Delivered";
					break;
			case 3:	status = "Cancelled";
					break;
			case 0:	break;
		}
		
		RoomServiceController.getInstance().updateRoomService(rS.getRoomBookings_ID(), status);
	}
}
