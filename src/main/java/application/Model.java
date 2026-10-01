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
        } catch (SQLException | IOException exception) {
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

    public static List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            List<User> users = DBUser.getAllUsers(dbManager.getConnection());
            // Apparently might not be necessary?
            for(User user : users) {
                result.add(new User(
                        user.getUserid(),
                        user.getUsername(),
                        user.getPermissionlevel()
                ));
            }
        }
        catch(SQLException exception) {
            throw new IllegalStateException("Could not fetch users.", exception);
        }
        return result;
    }

    public static List<Item> getAllItems() {
        List<Item> result = new ArrayList<>();
        try {
            List<Item> items = DBItem.getAll(dbManager.getConnection());
            for (Item item : items) {
                result.add(new Item(
                        item.getItemid(),
                        item.getName(),
                        item.getPrice(),
                        item.getDescription(),
                        item.getAmount())
                );
            }
        }
        catch (SQLException exception) {
            throw new IllegalStateException("Could not fetch items.", exception);
        }
        return result;
    }

    public List<CartLine> getCartItems() {
        Map<Integer, Integer> quantities = new LinkedHashMap<>();   // preserves insertion order
        Map<Integer, Item> itemsById = new LinkedHashMap<>();

        for (Item item : cart.getItems()) {
            quantities.merge(item.getItemid(), 1, Integer::sum);
            itemsById.putIfAbsent(item.getItemid(), item);
        }

        List<CartLine> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {
            Item item = itemsById.get(entry.getKey());
            result.add(new CartLine(item, entry.getValue()));
        }
        return result;
    }

    public float getCartTotal() {
        float total = 0;
        for (Item item : cart.getItems()) {
            total += item.getPrice();
        }
        return total;
    }

    public void addToCart(Item item) {
        cart.addItem(item);
    }

    public boolean addToCart(int itemId) {
        try {
            Item item = DBItem.getById(dbManager.getConnection(), itemId);
            if (item == null) {
                return false;
            }
            cart.addItem(item);
            return true;
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not fetch product.", exception);
        }
    }

    public void removeFromCart(Item item) {
        cart.removeItem(item);
    }

    public boolean placeOrder(int userId) {
        if (cart.getItems().isEmpty()) {
            return false;
        }
        Map<Integer, Integer> amounts = new TreeMap<>();
        for (Item item : cart.getItems()) {
            amounts.merge(item.getItemid(), 1, Integer::sum);
        }
        try (Connection connection = DBManager.openConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Map.Entry<Integer, Integer> entry : amounts.entrySet()) {
                    if (!DBItem.decreaseStock(connection, entry.getKey(), entry.getValue())) {
                        connection.rollback();
                        return false;
                    }
                }
                int orderId = DBOrder.create(connection, userId);
                for (Map.Entry<Integer, Integer> entry : amounts.entrySet()) {
                    DBOrderItem.create(connection, orderId,
                            entry.getKey(), entry.getValue());
                }
                connection.commit();
                cart.clearList();
                return true;
            } catch (SQLException exception) {
                connection.rollback();
                System.out.println("Could not place order: " + exception.getMessage());
                return false;
            }
        } catch (SQLException exception) {
            System.out.println("Database error: " + exception.getMessage());
            return false;
        }
    }

    public List<Order> getOrders(int userId) {
        List<Order> result = null;
        try {
            result = DBOrder.getAll(dbManager.getConnection());
        }
        catch(SQLException exception) {
            System.out.println("Could not get all orders of this userId." + exception.getMessage());
        }
        return result;
    }
}

