package net.everify.commands;

import net.everify.Constant;
import org.bukkit.entity.Player;

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
                if(AwaitingVerification.verify(player.getUniqueId(), code)) {
                    AwaitingVerification.removeID(player.getUniqueId());
                    player.sendMessage(Constant.getVerifiedMessage());
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
