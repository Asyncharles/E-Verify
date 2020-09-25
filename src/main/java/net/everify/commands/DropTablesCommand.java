package net.everify.commands;

import main.java.net.everify.EVerify;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class DropTablesCommand extends EVCommand {

    public DropTablesCommand() {
        super("drop", "Drop the table in database", true);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length != 1) {
            player.sendMessage("§cNo arguments needed");
            return;
        }

        EVerify.getInstance().getDatabaseManager().dropSQLTables();
        try {
            EVerify.getInstance().getDatabaseManager().getConnection().close();
            EVerify.getInstance().getDatabaseManager().openConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        player.sendMessage("§aCleared SQL TABLES | §cDropped table mails");

    }
}
