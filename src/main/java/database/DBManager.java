package database;

import java.io.IOException;
import java.sql.*;

public class DBManager {
    private static final String URL = "jdbc:postgresql://aws-1-eu-west-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USERNAME = "postgres.bjpawpwxkskfocseqzvc";
    private static final String PASSWORD = "MantuFirni#123";

    //private static final String ConnectionString = "postgresql://postgres:MantuFirni#123@db.bjpawpwxkskfocseqzvc.supabase.co:5432/postgres";

    private Connection dbConnection;

    public boolean connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch(ClassNotFoundException exception) {
            System.out.println("Can't connect for some reason: " + dbConnection + exception.getMessage());
            return false;
        }
        return true;
    }

    public void disconnect() throws IOException, SQLException {
        dbConnection.close();
    }

    public Connection getConnection() {
        return dbConnection;
    }


}
