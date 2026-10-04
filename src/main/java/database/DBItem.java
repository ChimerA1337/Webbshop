package database;

import application.Item;
import application.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBItem {
    public static List<Item> getAll(Connection connection) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT itemid, name, price, description, amount, category FROM t_item";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Item item = new Item(
                        result.getInt("itemid"),
                        result.getString("name"),
                        result.getFloat("price"),
                        result.getString("description"),
                        result.getInt("amount"),
                        Category.valueOf(result.getString("category"))
                );
                items.add(item);
            }
        }
        return items;
    }

    public static Item getById(Connection connection, int itemId) throws SQLException {
        String sql = "SELECT itemid, name, price, description, amount, category FROM t_item WHERE itemid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Item(
                            result.getInt("itemid"),
                            result.getString("name"),
                            result.getFloat("price"),
                            result.getString("description"),
                            result.getInt("amount"),
                            Category.valueOf(result.getString("category"))
                    );
                }
            }
        }
        return null;
    }

    public static boolean decreaseStock(Connection connection, int itemId, int amount) throws SQLException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        String sql = "UPDATE t_item SET amount = amount - ? WHERE itemid = ? AND amount >= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setInt(2, itemId);
            statement.setInt(3, amount);
            return statement.executeUpdate() == 1;
        }
    }

    public static boolean create(Connection connection, String name, float price,
                                 String description, int amount, Category category) throws SQLException {
        String sql = "INSERT INTO t_item (name, price, description, amount, category) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setFloat(2, price);
            statement.setString(3, description);
            statement.setInt(4, amount);
            statement.setString(5, category.toString());
            return statement.executeUpdate() == 1;
        }
    }
    public static boolean delete(Connection connection, int itemId) throws SQLException {
        String sql = """
        DELETE FROM t_item
        WHERE itemid = ?
        AND NOT EXISTS (
            SELECT 1 FROM t_order_item WHERE itemid = t_item.itemid
        )
        """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            return statement.executeUpdate() == 1;
        }
    }
    public static boolean updateCategory(Connection connection, int itemId, Category category) throws SQLException {
        String sql = "UPDATE t_item SET category = ? WHERE itemid = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.name());
            statement.setInt(2, itemId);

            return statement.executeUpdate() == 1;
        }
    }

    public static boolean restock(Connection connection, int itemId, int amount) throws SQLException {
        String sql = "UPDATE t_item SET amount = amount + ? WHERE itemid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setInt(2, itemId);
            return statement.executeUpdate() == 1;
        }
    }

}

