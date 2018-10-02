package hotelsystem.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import MainSystem.HotelApp;
import hotelsystem.controller.GuestController;
import hotelsystem.entity.Card;
import hotelsystem.entity.Guest;

/**
 * Description of Guest UI
 * Prints out guest interface options to add, retrieve, update or delete guest
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
public class GuestUI {
	private static GuestUI instance = null;
    private Scanner sc;

    /**
     * Set up scanner
     */
    private GuestUI() {
        sc = new Scanner(System.in);
    }
    
    /**
     * set Instance if instance is null
     * return instance
     */
    public static GuestUI getInstance() {
        if (instance == null) {
            instance = new GuestUI();
        }
        return instance;
    }

    /**
     * Printing of Guest UI
     * calls corresponding functions based on input
     */
    public void displayOptions() {
        int choice;
	        do {
	            System.out.println("_________________________ GUEST MENU ___________________________\n"
						 + "|                                                              |\n"
						 + "|  1. Create Guest                                             |\n"
						 + "|  2. Retrieve Guest Details                                   |\n"
						 + "|  3. Update Guest Details                                     |\n"
						 + "|  4. Remove Guest Particulars                                 |\n"
						 + "|  0. Back to previous level                                   |\n"
						 + "|______________________________________________________________|\n");
	            choice = sc.nextInt();
	            //sc.nextLine();
	            switch (choice) {
	                case 1:
	                    createGuestDetails();
	                	break;
	                case 2:
	                    retrieveGuestDetails();
	                    break;
	                case 3:
	                	updateGuestDetails();
	                    break;
	                case 4:
	                	removeGuestDetails();
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
	 * Creating of Guest Details
	 * create and store guest details into database
	 */
    private void createGuestDetails() {
        sc = new Scanner(System.in);
        String guestName;
        String identityNo;
        String address;
        long contactNo;
        String country;
        char gender;
        String nationality;
        String ccName = null,ccDate =null;
        long ccNum = 0;
        int ccCVV= 0;
        
	        System.out.println("Enter Guest Name: ");
	        guestName = sc.nextLine();
		    System.out.println("Enter Guest Identity Number: ");
		    identityNo = sc.nextLine();
		   if (checkExistingGuestByIC(identityNo)!=null) {
				System.out.println("Identity Numnber exist in the system. Please Try Again.");
				return;
		   }
		   else {
		        System.out.println("Enter Address:");
		        address = sc.nextLine();
		        System.out.println("Enter Contact Number:");
		        contactNo = sc.nextLong();
		        sc.nextLine();
		        System.out.println("Enter Country:");
		        country = sc.nextLine();
		        System.out.println("Enter Gender (M-Male, F-Female)");
		        gender = sc.nextLine().toUpperCase().charAt(0);
		        if (gender!='M') {
		        	if (gender!='F') {
		        		System.out.println("Invaild Input! Please insert again.");
		        		return;
		        	}
		       }
		        System.out.println("Enter Nationality:");
		        nationality = sc.nextLine();
		        
		        System.out.println("Do you want to add credit card details for guest? (Y-Yes, N-No)");
		        char reply = sc.next().charAt(0);
		        sc.nextLine();
		    	if (reply=='Y' || reply=='y') {
			        System.out.println("Enter Card Full Name:");
			        ccName = sc.nextLine();
			        System.out.println("Enter Card Number:");
			        ccNum = sc.nextLong();
			        sc.nextLine();
			        System.out.println("Enter Expiry Date (MM/YY):");
			        ccDate = sc.nextLine();
			        System.out.println("Enter CVV:");
			        ccCVV = sc.nextInt();
		    	}
		        
		        Card newcc = new Card(ccName,ccNum,ccDate,ccCVV);
		        Guest guest = new Guest(identityNo, guestName, address, contactNo, country, gender, nationality,newcc);
		        GuestController.getInstance().addGuest(guest);
		        System.out.println("Guest Name " + guest.getName() +  " has been created.");  
	        }
        
    }
    
    /**
	 * Creating of Guest Details for new customer when checking in or making new reservation
	 * return guest details in order to continue with the checking in or reservation process
	 */
    public Guest createnewGuest() {
        sc = new Scanner(System.in);
        String guestName;
        String identityNo;
        String address;
        long contactNo;
        String country;
        char gender;
        String nationality;
        String ccName = null,ccDate =null;
        long ccNum = 0;
        int ccCVV= 0;
        Guest rguest = null;
        
        try {
	        System.out.println("Enter Guest Name: ");
	        guestName = sc.nextLine();
		        System.out.println("Enter Guest Identity Number: ");
		        identityNo = sc.nextLine();
		   if (checkExistingGuestByIC(identityNo)!=null) {
		       System.out.println("Identity Numnber exist in the system. Please Try Again.");
		       rguest= null;
		   }
		   else {
		        System.out.println("Enter Address:");
		        address = sc.nextLine();
		        System.out.println("Enter Contact Number:");
		        contactNo = sc.nextLong();
		        sc.nextLine();
		        System.out.println("Enter Country:");
		        country = sc.nextLine();
		        System.out.println("Enter Gender (M-Male, F-Female)");
		        gender = sc.nextLine().toUpperCase().charAt(0);		       
		       if (gender!='M') {
		        	if (gender!='F') {
		        		System.out.println("Invaild Input! Please insert again.");
		        		rguest= null;
		        	}
		       }
		       else {
		        System.out.println("Enter Nationality:");
		        nationality = sc.nextLine();
		        
		        System.out.println("Do you want to add credit card details for guest? (Y-Yes, N-No)");
		        char reply = sc.next().charAt(0);
		        sc.nextLine();
		    	if (reply=='Y' || reply=='y') {
			        System.out.println("Enter Card Full Name:");
			        ccName = sc.nextLine();
			        System.out.println("Enter Card Number:");
			        ccNum = sc.nextLong();
			        sc.nextLine();
			        System.out.println("Enter Expiry Date (MM/YY):");
			        ccDate = sc.nextLine();
			        System.out.println("Enter CVV:");
			        ccCVV = sc.nextInt();
		    	}
		        
		        Card newcc = new Card(ccName,ccNum,ccDate,ccCVV);
		        Guest guest = new Guest(identityNo, guestName, address, contactNo, country, gender, nationality,newcc);
		        rguest = GuestController.getInstance().addGuestReturn(guest);
		        System.out.println("Guest Name " + guest.getName() +  " has been created.");  
		       }
	        }
        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
        return rguest;
    }
    
    /**
	 * Check for existing guest with the same identity number
	 * return guest if found
	 */
    private Guest checkExistingGuestByIC(String identity_No) {
        Guest rGuest = GuestController.getInstance().getGuestByIdenNo(identity_No);
        if (rGuest!=null) {
        	return rGuest;
        }
        else {
        	return null;
        }
    }
    
    /**
	 * Search guest based on keywords and show a list of similar names
	 * return guest details when user selected the correct user ID
	 */
    protected Guest searchGuest(String guestName) {
    	ArrayList<Guest> sGuest = new ArrayList<>();
    	sGuest = GuestController.getInstance().searchGuestList(guestName);
        if (sGuest.size()!=0) {
        	System.out.println(sGuest.size() + " Guest Found");
        	System.out.println("Guest ID	Guest Name	Guest Identity Number");
        	for(Guest guest : sGuest){
        		System.out.println(guest.getGuest_ID() +"		"+ guest.getName() +"		"+ guest.getIdentity_no());
        	}
        	System.out.println("Select a Guest (Enter Guest ID)");
        	try {
	        	sc = new Scanner(System.in);
	        	int sid = sc.nextInt();
	        	for(Guest guest : sGuest){
	        		if(guest.getGuest_ID() == sid) {
	        			return guest;
	        		}
	        	}
	        	System.out.println("Invaild Guest ID");
	        	return null;
        	}
        	catch (InputMismatchException e) {
            	System.out.println("Invaild Input! Please insert again.");
            	return null;
            }
        }
        else {
        	System.out.println("No such guest. Please try again.");
        	return null;
        }
    }
    
    /**
	 * Retrieve and print out all the guest's details
	 */
    private void retrieveGuestDetails(){
    	Guest guest = null;
    	String gender = null, cc = null;
    	
    	String guestName;
        sc = new Scanner(System.in);
        System.out.println("Enter Guest Name: ");
        guestName = sc.nextLine();
        guest = searchGuest(guestName);
		
		if (guest!=null) {
			char g = guest.getGender();
			if (g=='M') {
				gender ="Male";
			}
			else if (g=='F') {
				gender ="Female";
			}
			if (guest.getCardDetails().getCcname()==null) {
				cc="Not Found";
			}
			else {
				cc ="vaild";
			}
			System.out.println("Guest Details:\n"
					 + "Guest ID: " + guest.getGuest_ID() +"\n"
					 + "Name: " + guest.getName() +"\n"
					 + "Identity Number: " + guest.getIdentity_no() +"\n"
					 + "Address: " + guest.getAddress() +"\n"
					 + "Contact Number: " + guest.getContact_no() +"\n"
					 + "Country: " + guest.getCountry() +"\n"
					 + "Gender: " + gender +"\n"
					 + "Nationality: " + guest.getNationality() +"\n"
					 + "Credit Card: " + cc +"\n"
					 + "---------------------------------------");
		}
    }
    
    /**
	 * Update Guest Details and update database
	 */
    private void updateGuestDetails() {
    		int choice2;
    		Guest guest = null;
    		String guestName;
            sc = new Scanner(System.in);
            System.out.println("Enter Guest Name: ");
            guestName = sc.nextLine();
            guest = searchGuest(guestName);
        	
    		try {
	    		do {
	    			System.out.println("-Select Field to Update (Enter 0 to end)-\n"
	    							 + "1. Name\n"
	    							 + "2. Identity Number\n"
	    							 + "3. Address\n"
	    							 + "4. Contact Number\n"
	    							 + "5. Country\n"
	    							 + "6. Gender\n"
	    							 + "7. Nationality\n"
	    							 + "8. Credit Card Details\n"
	    							 + "---------------------------------------");
	    			
	    			choice2 = sc.nextInt();
	    			switch(choice2) {
	    				case 1:	
	    					System.out.println("Enter New Name: ");
	    					if (sc.nextLine() != null) {
	    						String name = sc.nextLine();
	    						guest.setName(name);
	    						System.out.println("Name Updated");
	    					}
	    						break;
	    				case 2:
	    					System.out.println("Enter New Identity Number: ");
	    					if (sc.nextLine() != null) {
	    						String idNo = sc.nextLine();
	    						 if (checkExistingGuestByIC(idNo)!=null) {
	    						       System.out.println("Identity Number exist in the system. Please Try Again.");
	    						       break;
	    						 }
	    						 else {
	    						guest.setIdentity_no(idNo);
	    						System.out.println("Identity Number Saved");
	    						 }
	    					}
	    					break;
	    				case 3:
	    					System.out.println("Enter New Address: ");
	    					if (sc.nextLine() != null) {
	    						String address = sc.nextLine();
	    						guest.setAddress(address);
	    						System.out.println("Address Saved");
	    					}
	    					break;
	    				case 4:
	    					System.out.println("Enter New Contact Number: ");
	    					int contactNo = sc.nextInt();
	    					guest.setContact_no(contactNo);
	    					System.out.println("Contact Number Saved");
	    					break;
	    				case 5:
	    					System.out.println("Enter New Country: ");
	    					if (sc.nextLine() != null) {
	    						String country = sc.nextLine();
	    						guest.setCountry(country);
	    						System.out.println("Country Saved");
	    					}
	    					break;
	    				case 6:
	    					System.out.println("Enter Gender (M-Male, F-Female)");
	    					char gender = sc.next().charAt(0);
	    					guest.setGender(gender);
	    					System.out.println("Gender Saved");
	    					break;
	    				case 7:
	    					System.out.println("Enter Nationality");
	    					if (sc.nextLine() != null) {
	    						String nationality = sc.nextLine();
	    						guest.setNationality(nationality);
	    						System.out.println("Nationality Saved");
	    					}
	    					break;
	    				case 8:
	    					System.out.println("Enter Card Full Name:");
	    			        String ccName = sc.nextLine();
	    			        sc.nextLine();
	    			        System.out.println("Enter Card Number:");
	    			        Long ccNum = sc.nextLong();
	    			        sc.nextLine();
	    			        System.out.println("Enter Expiry Date (MM/YY):");
	    			        String ccDate = sc.nextLine();
	    			        System.out.println("Enter CVV:");
	    			        int ccCVV = sc.nextInt();
	    			        Card newcc = new Card(ccName,ccNum,ccDate,ccCVV);
	    			        guest.setCardDetails(newcc);
	    			        System.out.println("Credit Card Details Saved");
	    					break;
	    				case 0:
	    					GuestController.getInstance().updateGuest(guest);
	    					System.out.println("Guest Details Updated!");
	    					break;
	    				}
	    			}while(choice2 > 0 && choice2 <= 8);
    		}
    		catch (InputMismatchException e) {
            	System.out.println("Invaild Input! Please insert again.");
            	displayOptions();
            }
    }
    
    /**
	 * Remove guest by searching the keyword to retrieve the list of guest and select the guest ID to delete
	 */
    public void removeGuestDetails() {
    	String guestName;
        sc = new Scanner(System.in);
        System.out.println("Enter Guest Name: ");
        guestName = sc.nextLine();
        Guest guest = searchGuest(guestName);
        if (guest != null) {
	    	System.out.println("Are you sure you want to delete the guest name " + guest.getName() + " ? (Y-Yes, N-No)");
	    	try{
		    	char reply = sc.next().charAt(0);
		    	if (reply=='Y' || reply=='y') {
			    	GuestController.getInstance().removeGuest(guest);
			    	System.out.println("Guest Removed");
		    	}
		    	else
		    		System.out.println("Invaild Input! Please insert again.");
		    	}
	    	catch (InputMismatchException e) {
	        	System.out.println("Invaild Input! Please insert again.");
	        	displayOptions();
	        }
        }
    }
}
