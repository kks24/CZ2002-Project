package hotelsystem.ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import hotelsystem.controller.FoodController;
import hotelsystem.entity.Food;

/**
 * @since 17/04/2018
 * @version 1.0
 * @author Koh Wei Hao
 */
public class FoodUI {
	private static FoodUI instance = null;
	private Scanner sc;

	/**
     * Set up scanner
     */
	private FoodUI() {
		sc = new Scanner(System.in);
	}

	/**
     * set Instance if instance is null
     * return instance
     */
	public static FoodUI getInstance() {
		if (instance == null) {
			instance = new FoodUI();
		}
		return instance;
	}

	/**
     * Printing of Food Menu UI
     * calls corresponding functions based on input
     */
	public void displayOptions() {
		int choice;
		try {
			do {
				System.out.println("1.Create Room Service Food Menu Items");
				System.out.println("2.Update Room Service Food Menu Items");
				System.out.println("3.Remove Room Service Food Menu Items");
				System.out.println("0.Back to previous level");
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					createFoodItems();
					break;
				case 2:
					updateFoodItems();
					break;
				case 3:
					removeFoodItems();
					break;
				case 0:
					break;
				default:
					System.out.println("Invalid Choice");
					break;
				}
			} while (choice > 0);
			FoodController.getInstance().SaveDB();
		}
		catch (InputMismatchException e) {
			System.out.println("Invaild Input! Please insert again.");
		}
	}

	/**
	 * Adding new Food Menu to the Database 
	 * return error message if exist
	 */
	private void createFoodItems() {
		sc = new Scanner(System.in);
		String foodName = null;
		Double foodPrice = 0.0;
		String foodDescription = null;

		try {
			System.out.println("Enter Food Name: ");
			foodName = sc.nextLine();
			if (checkExistingFood(foodName)!=null) {
				System.out.println("Food Exist in the system");
				return;
			}
			else {
				System.out.println("Enter Food Price:$ ");
				foodPrice = sc.nextDouble();
				sc.nextLine();
				System.out.println("Description Of How Food Is Prepared: ");
				foodDescription = sc.nextLine();
				Food food = new Food(foodName, foodPrice, foodDescription);
				FoodController.getInstance().addFood(food);
				System.out.println("Food Name " + food.getfood_name() +  " has been created."); 
			}
		}
		catch (InputMismatchException e) {
			System.out.println("Invaild Input! Please insert again.");
		}
	}

	/**
	 * Checks for Food if already exist in the Database using Food Name
	 * return null if non found
	 */
	private Food checkExistingFood(String foodName) {
		Food rFood = FoodController.getInstance().getFood(foodName);
		if (rFood!=null) {
			return rFood;
		}
		else {
			return null;
		}
	}

	/**
	 * Function to search for a particular food using Food Name
	 * return error message if food not found
	 */
	private Food searchFood() {
		String foodName;
		sc = new Scanner(System.in);
		System.out.println("Enter Food Name: ");
		foodName = sc.nextLine().toUpperCase();

		Food rFood = FoodController.getInstance().getFood(foodName);
		if (rFood!=null) {
			return rFood;
		}
		else {
			System.out.println("Food Not Found. Please Try Again.");
			return null;
		}
	}

	/**
	 * Retrieve all Food from the Database using searchFood() function
	 * update requested field and saves back into Database
	 */
	private void updateFoodItems(){
		int choice2;
		Food food = null;
		do
			food = searchFood();
		while (food == null);

		sc = new Scanner(System.in);
		String name = food.getfood_name();
		Double price = food.getfood_price();
		String description = food.getfood_description();

		try {
			do {
				System.out.println("-Select Field to Update (Enter 0 to end)-\n"
						+ "1. Name\n"
						+ "2. Price\n"
						+ "3. Description\n"
						+ "---------------------------------------");

				choice2 = sc.nextInt();
				switch(choice2) {
				case 1:	
					System.out.println("Enter New Name: ");
					if (sc.nextLine() != null) {
						name = sc.nextLine();
						if (checkExistingFood(name)!=null) {
							System.out.println("Name Exist in the system");
							break;
						}
						else {
							food.setfood_name(name);
							System.out.println("New Name Updated");
						}
					}
					break;
				case 2:
					System.out.println("Enter New Price: ");
					if (sc.nextLine() != null) {
						price = sc.nextDouble();
						food.setfood_price(price);
						System.out.println("New Price Saved");
					}
					break;
				case 3:
					System.out.println("Enter New Food Description: ");
					if (sc.nextLine() != null) {
						description = sc.nextLine();
						food.setfood_description(description);
						System.out.println("New Food Description Saved");
					}
					break;
				case 0:
					FoodController.getInstance().updateFood(food.getfood_name(), price, description);
					System.out.println("Food Details Updated!");
					break;
				}
			}while(choice2 > 0 && choice2 <= 3);
		}
		catch (InputMismatchException e) {
			System.out.println("Invaild Input! Please insert again.");
		}
	}

	/**
	 * Remove a Food from the menu from the Database using searchFood() function
	 */
	public void removeFoodItems() {
		Food food = null;
		do
			food = searchFood();
		while (food == null);
		System.out.println("Are you sure you want to delete the food " + food.getfood_name() + " ? (Y-Yes, N-No)");
		try{
			char reply = sc.next().charAt(0);
			if (reply=='Y' || reply=='y') {
				FoodController.getInstance().removeFood(food);
				System.out.println("Food Removed");
			}
			else
				System.out.println("Invaild Input! Please insert again.");
		}
		catch (InputMismatchException e) {
			System.out.println("Invaild Input! Please insert again.");
		}

	}
}
