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
        String query = "SELECT userid, permissionLevel FROM t_user WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("userid"),
                        username,
                        PermissionLevel.valueOf(resultSet.getString("permissionlevel"))
                );
            }
        }
        return null;
    }

    public static User register(Connection connection, String username, String password, PermissionLevel permissionlevel) throws SQLException {
        User result;
        String query = "INSERT INTO t_user (username, password, permissionlevel) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, permissionlevel.toString());
            statement.executeUpdate();

            int userid;
            var keysResultSet = statement.getGeneratedKeys();
            if (keysResultSet.next()) {
                userid = keysResultSet.getInt(1);
            } else throw new SQLException("Failed to fetch a new userid.");
            result = new User(userid, username, permissionlevel);
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            throw new SQLException(exception.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    public static boolean usernameTaken(Connection connection, String username) throws SQLException {
        String query = "SELECT 1 FROM t_user WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static List<User> getAllUsers(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT userid, username, permissionlevel FROM t_user ORDER BY username DESC";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                User user = new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        PermissionLevel.valueOf(resultSet.getString("permissionlevel"))
                );
                users.add(user);
            }
        }
        return users;
    }
}

