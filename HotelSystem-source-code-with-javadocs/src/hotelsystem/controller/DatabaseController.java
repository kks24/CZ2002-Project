package hotelsystem.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Description of DatabaseManager
 * A manager to manage database for writing and reading the database.
 * DatabaseManager is an abstract class that can be overwritten by other manager.
 * @since 17/4/2018
 * @version 1.0
 * @author Kan Kah Seng
 * @author Kenneth Yak Yong Seng
 * @author Koh Wei Hao
 * @author Solberg Anna
 */
public abstract class DatabaseController {
    protected static final String SEPARATOR = "|";
    protected static final String DATABASE_DIR = "DB/";
    private final List<String> data;
    private File f;

    /**
     * Check and creates the database linkage
     * <p/>
     * Prints error message if access to database is denied.
     */
    //Database Constructor
    public DatabaseController() {
        data = new ArrayList<>();

        f = new File(DATABASE_DIR);
        // if the directory does not exist, create it
        if (!f.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                f.mkdir();
            } catch (Exception ex) {
                //handle it
                System.out.println("[Error] You are not authorized to create a database. Please run as Administrator!");
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex); //Create Fatal Error to stop the program
            }
        }
    }

    //Abstract Methods for Child to Implement
    protected abstract boolean LoadDB();

    protected abstract void SaveDB();

    /**
     * Check if file exists and create one if it doesn't exist.
     */
    public boolean checkFileExist(String filepath) {
        f = new File(filepath);
        if (!f.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                f.createNewFile();
            } catch (Exception ex) {
                System.out.println("[Error] You are not authorized to create a database. Please run as Administrator!");
                //Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return f.exists();
    }

    /**
     * Creates a write to database(Saving info to database)
     *
     * @param fileName The file path to write to
     * @param data     data contains information to be saved.
     *                 Throws exception if fail to save to database.
     */
    public void write(String fileName, List<String> data) {
        try (PrintWriter outWriter = new PrintWriter(new FileWriter(fileName))) {
            for (String aData : data) {
                outWriter.println(aData);
            }
        } catch (Exception io) {
            System.out.println("[Error] Failed to write to file!");
        }
    }

    /**
     * Creates a read from database(Read info from database)
     *
     * @param fileName The file path to read to
     *                 Throws exception if fail to read from database.
     */
    public List<String> read(String fileName) throws IOException {
        data.clear();

        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
        return data;
    }

}
