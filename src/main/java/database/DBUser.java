package database;

import application.Item;
import application.PermissionLevel;
import application.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUser {
    public static User login(Connection connection, String username, String password) throws SQLException {
        User result = null;
        String query = "SELECT userid, permissionLevel FROM t_user WHERE username = ? AND password = ?";
        try (PreparedStatement userStatement = connection.prepareStatement(query)) {
            userStatement.setString(1, username);
            userStatement.setString(2, password);
            ResultSet resultSet = userStatement.executeQuery();
            if (resultSet.next()) {
                int userid = resultSet.getInt("userid");
                PermissionLevel permissionLevel = PermissionLevel.valueOf(resultSet.getString("permissionlevel"));
                result = new User(userid, username, permissionLevel);
            }
        }
        return result;
    }
}

