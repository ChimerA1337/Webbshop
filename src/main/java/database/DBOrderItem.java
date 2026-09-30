package database;

import java.sql.*;

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
}
