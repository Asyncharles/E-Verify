package net.everify.commands;

import net.everify.EVerify;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ForceVerifyCommand extends EVCommand {

    private List<String> illegalCharacter = Arrays.asList("/", "?", "!", "&", ",", "#");


    public ForceVerifyCommand() {
        super("force", "Forces verification for a player", true);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length != 3) {
            player.sendMessage("§c/ev force player email");
            return;
        }

        if(illegalCharacter.contains(args[1])) {
            player.sendMessage("§cIllegal character");
            return;
        }

        if(Bukkit.getOfflinePlayer(args[1]) == null) {
            player.sendMessage("§cPlayers do not exist");
            return;
        }

        if(args[2].contains("@") && args[2].contains(".")) {

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            String email = args[2];
            EVerify.getInstance().getDatabaseManager().insertEmail(target.getUniqueId(), email, 0000);
            player.sendMessage("§aVerified §b" + target.getName() + " §awith email §b"  + email);

        } else {
            player.sendMessage("§cPlease enter a valid email");
            return;
        }








    }


}
