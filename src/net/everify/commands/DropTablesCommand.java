package net.everify.commands;

import net.everify.EVerify;
import org.bukkit.entity.Player;

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

        player.sendMessage("§aCleared SQL TABLES | §cDropped table mails");

    }
}
