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
    private final Cart cart;

    public Model() {
        cart = new Cart();
    }

    public static List<ItemDTO> getAllItems() {
        List<ItemDTO> result = new ArrayList<>();
        try {
            List<Item> items = DBItem.getAll(dbManager.getConnection());
            for (Item item : items) {
                result.add(new ItemDTO(item.getItemId(), item.getName(),
                        item.getPrice(), item.getDescription()));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not fetch items.", exception);
        }
        return result;
    }

    public void addToCart(Item item) {
        cart.addItem(item);
    }
    public void removeFromCart(Item item) {
        cart.removeItem(item);
    }

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

    public static User register(String username, String password, PermissionLevel permissionlevel) {
        User user = null;
        try {
            user = DBUser.register(dbManager.getConnection(), username, password, permissionlevel);
        }
        catch(SQLException sqlException) {
            System.out.println("Failed to register." + sqlException.getMessage());
        }
        return user;
    }

    public static boolean usernameTaken(String username) {
        boolean result = true;
        try {
            result = DBUser.usernameTaken(dbManager.getConnection(), username);
        }
        catch(SQLException sqlException) {
            System.out.println("Failed to compare usernames." + sqlException.getMessage());
        }
        System.out.println("returning: " + result);
        return result;
    }
}
