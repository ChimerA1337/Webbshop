package database;


import application.Order;
import application.Item;
import application.User;

import java.sql.*;
import java.util.*;

public class DBOrder {
    public static Order get(Connection connection, int id) throws SQLException {
        Order result = null;
        String query = "SELECT ordered, packed, shipped, userid FROM \"order\" WHERE orderid = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(true);
            statement.setInt(1, id);
        }
    }
}
