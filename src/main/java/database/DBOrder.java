package database;


import application.Order;
import application.Item;
import application.User;

import java.sql.*;
import java.util.*;

public class DBOrder {
    public static Order get(Connection connection, int id) throws SQLException {
        Order result = null;
        String query = "SELECT ordered, userid FROM t_order WHERE orderid = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
        }
        return result;
    }

    public static int create(Connection connection, int userId) throws SQLException {
        String sql = "INSERT INTO t_order (ordered, userid) VALUES (CURRENT_TIMESTAMP, ?) RETURNING orderid";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("orderid");
                }
                throw new SQLException("Could not retrieve the new order ID.");
            }
        }
    }
}
