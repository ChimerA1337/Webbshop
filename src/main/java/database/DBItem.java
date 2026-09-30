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
        String sql = "SELECT itemid, name, price, description FROM t_item";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Item item = new Item(
                        result.getInt("itemid"),
                        result.getString("name"),
                        result.getFloat("price"),
                        result.getString("description")
                );
                items.add(item);
            }
        }
        return items;
    }
}
