package application;

import database.*;

import java.io.IOException;
import java.sql.*;
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

    public static UserDTO loginUser(String username, String password) {
        User user = null;
        try {
            user = DBUser.login(dbManager.getConnection(), username, password);
        }
        catch(SQLException sqlException) {
            System.out.println("Failed to login." + sqlException.getMessage());
        }
        return toDTO(user);
    }

    public static UserDTO register(String username, String password, PermissionLevel permissionlevel) {
        User user = null;
        try {
            user = DBUser.register(dbManager.getConnection(), username, password, permissionlevel);
        }
        catch(SQLException sqlException) {
            System.out.println("Failed to register." + sqlException.getMessage());
        }
        return toDTO(user);
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

    public static List<UserDTO> getAllUsers() {
        List<UserDTO> result = new ArrayList<>();
        try {
            List<User> users = DBUser.getAllUsers(dbManager.getConnection());
            for(User user : users) {
                result.add(toDTO(user));
            }
        }
        catch(SQLException exception) {
            throw new IllegalStateException("Could not fetch users.", exception);
        }
        return List.copyOf(result);
    }

    public static List<ItemDTO> getAllItems() {
        List<ItemDTO> result = new ArrayList<>();
        try {
            List<Item> items = DBItem.getAll(dbManager.getConnection());
            for (Item item : items) {
                result.add(toDTO(item));
            }
        }
        catch (SQLException exception) {
            throw new IllegalStateException("Could not fetch items.", exception);
        }
        return List.copyOf(result);
    }

    public List<CartLineDTO> getCartItems() {
        Map<Integer, Integer> quantities = new LinkedHashMap<>();
        Map<Integer, Item> itemsById = new LinkedHashMap<>();

        for (Item item : cart.getItems()) {
            quantities.merge(item.getItemid(), 1, Integer::sum);
            itemsById.putIfAbsent(item.getItemid(), item);
        }

        List<CartLineDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {
            Item item = itemsById.get(entry.getKey());
            result.add(new CartLineDTO(toDTO(item), entry.getValue()));
        }
        return List.copyOf(result);
    }

    public float getCartTotal() {
        float total = 0;
        for (Item item : cart.getItems()) {
            total += item.getPrice();
        }
        return total;
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

    public void removeFromCart(int itemId) {
        for (Item item : cart.getItems()) {
            if (item.getItemid() == itemId) {
                cart.removeItem(item);
                return;
            }
        }
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

    public static List<OrderDTO> getAllOrders() {
        try (Connection connection = DBManager.openConnection()) {
            List<OrderDTO> result = new ArrayList<>();
            for (Order order : DBOrder.getAll(connection)) {
                result.add(new OrderDTO(order.getOrderid(), order.getOrdered(),
                        order.getPacked(), order.getUserid()));
            }
            return List.copyOf(result);
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not fetch orders.", exception);
        }
    }

    private static ItemDTO toDTO(Item item) {
        return new ItemDTO(item.getItemid(), item.getName(), item.getPrice(),
                item.getDescription(), item.getAmount(), item.getCategory());
    }

    private static UserDTO toDTO(User user) {
        return user == null ? null : new UserDTO(user.getUserid(), user.getUsername(),
                user.getPermissionlevel());
    }

    public static boolean addItem(String name, float price, String description, int amount, Category category) {
        if (name == null || name.isBlank() || !Float.isFinite(price)
                || price < 0 || amount < 0) {
            return false;
        }
        try (Connection connection = DBManager.openConnection()) {
            return DBItem.create(connection, name.trim(), price, description, amount, category);
        } catch (SQLException exception) {
            System.out.println("Could not create item: " + exception.getMessage());
            return false;
        }
    }
    public static boolean deleteItem(int itemId) {
        try (Connection connection = DBManager.openConnection()) {
            return DBItem.delete(connection, itemId);
        } catch (SQLException exception) {
            System.out.println("Could not delete item: " + exception.getMessage());
            return false;
        }
    }
    public static boolean updateItemCategory(int itemId, Category category) {
        if (itemId <= 0 || category == null) {
            return false;
        }

        try (Connection connection = DBManager.openConnection()) {
            return DBItem.updateCategory(connection, itemId, category);

        } catch (SQLException exception) {
            System.out.println(
                    "Could not update category: " + exception.getMessage()
            );
            return false;
        }
    }

    public static boolean deleteUser(int userId) {
        try (Connection connection = DBManager.openConnection()) {
            return DBUser.delete(connection, userId);
        } catch (SQLException exception) {
            System.out.println("Could not delete user: " + exception.getMessage());
            return false;
        }
    }

    public static boolean packOrder(int orderId) {
        try (Connection connection = DBManager.openConnection()) {
            return DBOrder.pack(connection, orderId);
        } catch (SQLException exception) {
            System.out.println("Could not pack order: " + exception.getMessage());
            return false;
        }
    }

    public static boolean restockItem(int itemId, int amount) {
        if (amount <= 0) {
            return false;
        }
        try (Connection connection = DBManager.openConnection()) {
            return DBItem.restock(connection, itemId, amount);
        } catch (SQLException exception) {
            System.out.println("Could not restock item: " + exception.getMessage());
            return false;
        }
    }
}

