package database;

import application.Order;
import application.OrderItem;
import application.Item;
import application.User;

import java.sql.*;
import java.util.*;

public class DBOrder {
    public static Order get(Connection connection, int id) throws SQLException {
        String query = "SELECT ordered, packed, userid FROM t_order WHERE orderid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                List<OrderItem> items = DBOrderItem.getForOrder(connection, id);
                return new Order(
                        id,
                        resultSet.getTimestamp("ordered"),
                        resultSet.getTimestamp("packed"),
                        resultSet.getInt("userid"),
                        items
                );
            }
        }
        return null;
    }

    public static List<Order> getAll(Connection connection) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT orderid, ordered, userid, packed FROM t_order ORDER BY orderid DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderid");
                List<OrderItem> items = DBOrderItem.getForOrder(connection, orderId);
                Order order = new Order(
                        orderId,
                        resultSet.getTimestamp("ordered"),
                        resultSet.getTimestamp("packed"),
                        resultSet.getInt("userid"),
                        items
                );
                orders.add(order);
            }
        }
        return orders;
    }

    public static int create(Connection connection, int userid) throws SQLException {
        String query = "INSERT INTO t_order (ordered, userid) VALUES (CURRENT_TIMESTAMP, ?) RETURNING orderid";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("orderid");
            }
            throw new SQLException("Could not retrieve the new order ID.");
        }
    }
}