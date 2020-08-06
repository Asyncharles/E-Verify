package net.everify.sql;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager(String host, int port, String dbName, String user, String password) {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://"
                    + host + ":" + port + "/" + dbName, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
