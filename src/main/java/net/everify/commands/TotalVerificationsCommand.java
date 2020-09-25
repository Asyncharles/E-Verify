package net.everify.commands;

import net.everify.EVerify;
import org.bukkit.entity.Player;

public class TotalVerificationsCommand extends EVCommand {

    public TotalVerificationsCommand() {
        super("total", "See the total verifications", true);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length != 1) {
            player.sendMessage("§cNo arguments needed");
            return;
        }

        EVerify.getInstance().getDatabaseManager().getTotalVerifications().whenComplete(((ints, throwable) -> {

            player.sendMessage(" ");
            player.sendMessage("§eTotal Verifications : §b" + ints[0] + ints[1]);
            player.sendMessage("§Normal Verifications : §b" + ints[0]);
            player.sendMessage("§Forced Verifications : §b" + ints[1]);

        }));



    }
}
