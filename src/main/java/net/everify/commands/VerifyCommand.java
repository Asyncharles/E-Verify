package net.everify.commands;

import main.java.net.everify.Constant;
import main.java.net.everify.EVerify;
import net.everify.api.events.PlayerVerifyEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VerifyCommand extends EVCommand{

    public VerifyCommand() {
        super("verify", "Verify a user", false);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(AwaitingVerification.isAwaitingVerification(player.getUniqueId())) {
            if (args.length != 2) {
                player.sendMessage("§cPlease enter a code");
                return;
            }
            if (args[1].length() == 4) {
                int code = Integer.parseInt(args[1]);
                UUID id = player.getUniqueId();

                if(AwaitingVerification.verify(player.getUniqueId(), code)) {

                    String mail = AwaitingVerification.getMail(id);

                    PlayerVerifyEvent event = new PlayerVerifyEvent(player, mail, code);
                    Bukkit.getPluginManager().callEvent(event);

                    if(!event.isCancelled()) {
                        AwaitingVerification.removeID(player.getUniqueId());
                        player.sendMessage(Constant.getVerifiedMessage());
                        EVerify.getInstance().getDatabaseManager().insertEmail(id, mail, code);
                        return;
                    }

                } else if (AwaitingVerification.getAttempts(player.getUniqueId()) == Constant.getAttempts()){

                    player.kickPlayer(Constant.getAfterAttemptsMessage());
                    AwaitingVerification.removeID(player.getUniqueId());

                } else {

                    player.sendMessage("§cWrong code");
                    AwaitingVerification.addAttempts(player.getUniqueId());

                }

            } else {
                player.sendMessage("§cPlease enter a valid code");
                return;
            }
        }

    }
}
