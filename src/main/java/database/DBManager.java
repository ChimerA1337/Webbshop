package database;

import java.sql.*;

public class DBManager {
    private static final String URL = "jdbc:postgresql://db.bjpawpwxkskfocseqzvc.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "MantuFirni#123";
    private static final String ConnectionSTring = "postgresql://postgres:[YOUR-PASSWORD]@db.bjpawpwxkskfocseqzvc.supabase.co:5432/postgres";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}
