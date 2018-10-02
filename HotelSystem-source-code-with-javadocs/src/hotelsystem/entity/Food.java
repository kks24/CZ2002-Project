package hotelsystem.entity;

import java.io.Serializable;

/**
 * Description for Food
 * contains get & set methods required for Food
 * @since 17/04/2018
 * @version 1.0
 * @author Koh Wei Hao
 */
@SuppressWarnings("serial")
public class Food implements Serializable{
	private static int foodID = 1;
	private int food_id;
	private String food_name;
	private Double food_price;
	private String food_description;

	public Food(String food_name, Double food_price, String food_description) {
		this.food_id = foodID;
		this.food_name = food_name;
		this.food_price = food_price;
		this.food_description = food_description;
		foodID++;
	}

	public String getfood_name() { return food_name; }
	
	public static int getfoodID(){ return foodID; }
	
	public static void setfoodID(int ID) { foodID = ID; }
	
	public int getFood_ID() { return food_id; }
	
	public void setFood_ID(int food_id) { this.food_id = foodID; }

	public void setfood_name(String food_name) { this.food_name = food_name; }

	public Double getfood_price() { return food_price; }

	public void setfood_price(Double food_price) { this.food_price = food_price; }

	public String getfood_description() { return food_description; }

	public void setfood_description(String food_description) { this.food_description = food_description; }

	public String getName() { return null; }

	public static int getMaxID() { return 0; }

}