package net.everify.api;

import net.everify.EVerify;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import javax.mail.internet.InternetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MailManager {

    private EVerify ev;

    public MailManager(EVerify ev) {
        this.ev = ev;
    }


    public List<String> getAllMails() {

        List<String> addressList = new ArrayList<>();
        String query = "SELECT mail FROM mails";
        Statement statement;

        new BukkitRunnable() {
            @Override
            public void run() {

                try {

                    Statement statement = ev.getDatabaseManager().getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while(resultSet.next()) {
                        addressList.add(resultSet.getString("id"));
                    }

                } catch (SQLException e) {
                    ev.log("API ERROR : SQL EXCEPTION [getAllMails - MailManager]");
                }
            }

        }.runTaskAsynchronously(ev.getInstance());

        return addressList;
    }

}
