package net.everify.sql;



import net.everify.EVerify;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class DatabaseManager {

    private Connection connection;
    private String host, dbName, user, password;
    private int port;

    public DatabaseManager(String host, int port, String dbName, String user, String password) {

        this.host = host;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
        this.port = port;

    }

    public void openConnection() throws SQLException {

        if(connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if(connection != null && !connection.isClosed()) {
                return;
            }
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.dbName,
                    this.user, this.password);
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS  `mails` " +
                    "(`id` char(36) NOT NULL DEFAULT '', " +
                    "`mail` char(36) NOT NULL DEFAULT '', " +
                    "`code` int(4) NOT NULL DEFAULT '0000'," +
                    "PRIMARY KEY (`id`)); ");
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void insertEmail(UUID id, String mail, int code) {

        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("INSERT INTO mails (id, mail, code) VALUES (" + id + ", " + mail + ", " + code + ");");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(EVerify.getInstance());
    }



}
