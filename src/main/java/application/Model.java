package application;

import database.*;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Model {
    static DBManager dbManager;

    public static boolean initialize() {
        dbManager = new DBManager();
        try {
            return dbManager.connect();
        }
        catch(SQLException sqlException) {
            System.out.println("Failed to connect to database." + sqlException.getMessage());
            return false;
        }
    }

    public static boolean shutdown() {
        try {
            dbManager.disconnect();
        }
        catch(SQLException | IOException exception) {
            System.out.println("Failed to disconnect." + exception.getMessage());
            return false;
        }
        return true;
    }

    public static User loginUser(String username, String password) {
        User user = null;
        try {
            user = DBUser.login(dbManager.getConnection(), username, password);
        }
        catch(SQLException sqlException) {
            System.out.println("Failed to login." + sqlException.getMessage());
        }
        return user;
    }
}
