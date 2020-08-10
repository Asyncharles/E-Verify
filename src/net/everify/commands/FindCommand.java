package net.everify.commands;

import net.everify.EVerify;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FindCommand extends EVCommand {



    public FindCommand() {
        super("find", "Find a players email", true);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length != 2) {
            player.sendMessage("§cPlease enter a player");
            return;
        }

        if(Bukkit.getOfflinePlayer(args[1]) == null) {
            player.sendMessage("§cPlayer does not exist");
            return;

        } else {
            UUID id = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
            
            EVerify.getInstance().getDatabaseManager().getPlayerInformation(id).whenComplete(((objects, ex) -> {

                boolean hasData = (boolean) objects[0];

                player.sendMessage(" ");

                if(hasData) {
                    player.sendMessage("§eName : §b" + player.getName());
                    player.sendMessage("§eVerified : §aYES");
                    player.sendMessage("§eEmail : §b" + objects[1]);
                    player.sendMessage("§eVerification code : §b" + objects[2]);

                } else {
                    player.sendMessage("§eName : §b" + player.getName());
                    player.sendMessage("§eVerified : §cNO");
                }

            }));

        }



    }
}