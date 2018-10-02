package hotelsystem.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.PromoController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.Promo;

/**
 * Description of Promo UI
 * Prints out promo interface options
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
public class PromoUI {
	private static PromoUI instance = null;
	private final Scanner sc;

	/**
     * Set up scanner
     */
	private PromoUI() {
		sc = new Scanner(System.in);
	}

	/**
     * set Instance if instance is null
     * return instance
     */
	public static PromoUI getInstance() {
		if (instance == null) {
			instance = new PromoUI();
		}
		return instance;
	}

	/**
     * Printing of Promo UI
     * calls corresponding functions based on input
     */
	public void displayOptions() {
		int choice;
		try {
			do {
				System.out.println("1.Create Promo");
	            System.out.println("2.Remove Promo");
	            System.out.println("0.Back to previous level");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                    createPromo();
	                	break;
	                case 2:
	                	removePromo();
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
	 * Adding new promotion from a certain date to a certain date
	 * for guest that specially book a room and check in for the duration
	 */
	public void createPromo() {
		int roomtypeID;
		String dateFrom;
        String dateTo;
        String promo_desc;
        double disc;
        Date startDate = null;
        Date endDate = null;
		 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		 int roomtype = RoomTypeController.getInstance().getAllRoomType();
     	 System.out.println("Select The Room Type");
     	 try {
	         roomtypeID = sc.nextInt();
	         sc.nextLine();
	         if (roomtypeID>roomtype) {
	        	 System.out.println("Invaild Input! Please insert again.");
	         }
	         else {
	        	 System.out.println("Enter Promo Description: ");
	             promo_desc = sc.nextLine();
	             System.out.println("Enter % Discount: ");
	             disc = sc.nextDouble();
	             sc.nextLine();
	             System.out.println("Enter Date From (dd/MM/yyyy): ");
	             dateFrom = sc.next();
	             System.out.println("Enter Date To (dd/MM/yyyy): ");
	             dateTo = sc.next();
	             try {
	                 startDate = df.parse(dateFrom + " 00:00");
	             } catch (ParseException e) {
	                 e.printStackTrace();
	             }
	             try {
	                 endDate = df.parse(dateTo + " 00:00");
	             } catch (ParseException e) {
	                 e.printStackTrace();
	             }
	             
	             Promo promo = new Promo(roomtypeID,promo_desc,disc,startDate,endDate);
	             PromoController.getInstance().addPromo(promo);
			     System.out.println("Promo Code " + promo.getPromo_ID() +  " has been created.");  
	         }
     	 }
     	catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
	}
	
	/**
	 * Retrieve all ongoing promo and selecting a certain promo using ID to remove
	 */
	public void removePromo() {
		ArrayList<Promo> returnPromo = PromoController.getInstance().getPromoList();
		if (returnPromo.size()!=0) {
        	System.out.println("Promo ID	Room Type ID	Promo Desc	Promo Discount(in %)	Start Date	End Date");
        	for(Promo promo : returnPromo){
        		System.out.println(promo.getPromo_ID() +"		"+ promo.getRoomType_ID() +"		"+ promo.getPromo_desc() +"		"+ promo.getDiscount_amt()+"		"+ promo.getPromo_from()+"		"+ promo.getPromo_to());
        	}
			System.out.println("Select Promo ID");
	    	 try {
		         int promoID = sc.nextInt();
		         if (promoID>returnPromo.size()) {
		        	 System.out.println("Invaild Input! Please insert again.");
		         }
		         else {
			    	System.out.println("Are you sure you want to delete the " +returnPromo.get(promoID-1).getPromo_desc()+ " ? (Y-Yes, N-No)");
				    char reply = sc.next().charAt(0);
				    if (reply=='Y' || reply=='y') {
				    	Promo delete = PromoController.getInstance().getPromo(promoID);
					    PromoController.getInstance().removePromo(delete);
					    System.out.println("Promo Removed");
				    }
				    else
				    	System.out.println("Invaild Input! Please insert again.");
		         	}
		         }
	    	catch (InputMismatchException e) {
	        	System.out.println("Invaild Input! Please insert again.");
	        }
		}
    	
    }

}
