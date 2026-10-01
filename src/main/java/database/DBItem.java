package database;

import application.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBItem {
    public static List<Item> getAll(Connection connection) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT itemid, name, price, description, amount FROM t_item";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Item item = new Item(
                        result.getInt("itemid"),
                        result.getString("name"),
                        result.getFloat("price"),
                        result.getString("description"),
                        result.getInt("amount")
                );
                items.add(item);
            }
        }
        return items;
    }

    public static Item getById(Connection connection, int itemId) throws SQLException {
        String sql = "SELECT itemid, name, price, description, amount FROM t_item WHERE itemid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Item(
                            result.getInt("itemid"),
                            result.getString("name"),
                            result.getFloat("price"),
                            result.getString("description"),
                            result.getInt("amount")
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


}
