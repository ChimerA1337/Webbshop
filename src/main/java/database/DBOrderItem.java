package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import application.*;

public class DBOrderItem {
    public static void create(Connection connection, int orderId,
                              int itemId, int amount) throws SQLException {
        String sql = "INSERT INTO t_order_item (orderid, itemid, amount) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, itemId);
            statement.setInt(3, amount);
            statement.executeUpdate();
        }
    }

    public static List<OrderItem> getForOrder(Connection connection, int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT itemid, amount FROM t_order_item WHERE orderid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                items.add(new OrderItem(
                        resultSet.getInt("itemid"),
                        resultSet.getInt("amount")
                ));
            }
        }
        return items;
    }
}
