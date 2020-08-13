package net.everify.commands;

import net.everify.Constant;
import net.everify.EVerify;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    public CommandHandler() {
        new EmailInsertCommand();
        new VerifyCommand();
        new FindCommand();
        new DropTablesCommand();
        new ReloadCommand();
        new ForceVerifyCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length < 1) {
                //player.sendMessage(Constant.getCommandHelpMessage());
                return true;
            }

            for(EVCommand evCommand : EVCommand.getEvCommandList()) {
                if(args[0].equalsIgnoreCase(evCommand.getCommand())) {
                    if(evCommand.isAdmin() && player.isOp()
                            || evCommand.isAdmin() && player.hasPermission(Constant.getAdminPermission())) {
                        evCommand.onCommand(player, args);
                        return true;
                    } else if(!evCommand.isAdmin()) {
                        evCommand.onCommand(player, args);
                        return true;
                    }
                }
            }

        } else {
            sender.sendMessage("§cYou need to be a player to execute this command");
            return true;
        }

        return false;
    }
}
