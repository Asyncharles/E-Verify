package net.everify.commands;

import net.everify.EVerify;
import org.bukkit.entity.Player;

public class ReloadCommand extends EVCommand {

    public ReloadCommand() {
        super("reload", "Reload a paramater", true);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length > 2) {
            player.sendMessage("§cPlease enter parameter");
            return;
        }

        if(args[1].equalsIgnoreCase("config")) {
            EVerify.getInstance().reloadConfig();
            player.sendMessage("§aReloaded Config file!");
        }

    }
}
