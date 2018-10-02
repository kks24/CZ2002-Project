package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.BillPayment;

/**
 * Description of Bill Payment Controller
 * Add Bill payment to database
 * Able to store and load data from database
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class BillPaymentController implements Serializable{
	private static BillPaymentController instance = null;
	private final ArrayList<BillPayment> billList = new ArrayList<>();
	
	/**
	 * Constructor for Bill Payment Controller
	 */
	private BillPaymentController() {}
	
	/**
     * set Instance if instance is null
     * return instance
     */
	public static BillPaymentController getInstance() {
        if (instance == null) {
            instance = new BillPaymentController();
        }
        return instance;
    }
	
	/**
	 * Retrieve Bill Payment using ID
	 * return Bill Payment
	 */
	public BillPayment getBill(int ID) {
		for (BillPayment bill : billList) {
            if (bill.getBillPayment_ID() == ID)
                return bill;
        }
        return null;
    }

    /**
	 * Add new Bill Payment into Database
	 */
    public void addBillPayment(BillPayment bill) {
		billList.add(bill);
        storeData();
    }
	
    /**
     * Storing of Bill Payments to Database
     * Returns error message if file not found
     */
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/BillPayment.ser"));
            out.writeInt(billList.size());
            out.writeInt(BillPayment.getMaxID());
            for (BillPayment bill : billList)
                out.writeObject(bill);
            //System.out.printf("BillPaymentController: %,d Entries Saved.\n", billList.size());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
	/**
     * Loading of Bill Payment List from Database
     * Returns error message if file not found
     */
    public void loadData () {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/BillPayment.ser"));

            int noOfOrdRecords = ois.readInt();
            BillPayment.setMaxID(ois.readInt());
            System.out.println("BillPaymentController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                billList.add((BillPayment) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
