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

    public static User register(Connection connection, String username, String password, PermissionLevel permissionlevel) throws SQLException {
        User result;
        String query = "INSERT INTO t_user (username, password, permissionlevel) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { // RETURN_GENERATED_KEYS tillåter dig att hitta userid
            connection.setAutoCommit(false); // Gör så att man inte automatiskt committar queryn till databasen
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
        String sql = "SELECT 1 FROM t_user WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("In controller: " + rs.toString());
                return rs.next();
            }
        }
    }
}

