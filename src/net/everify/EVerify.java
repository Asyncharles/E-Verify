package net.everify;

import net.everify.commands.CommandHandler;
import net.everify.mail.JavaMail;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public class EVerify extends JavaPlugin {

    private static EVerify INSTANCE;
    private Logger logger = getServer().getLogger();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        getCommand("ev").setExecutor(new CommandHandler());
        if(!isConfigValid()) {
            logger.info(ChatColor.DARK_RED + "ERROR : Config isn't valid");
        }
    }

    @Override
    public void onDisable() {

    }

    public final static EVerify getInstance() {
        return INSTANCE;
    }

    private final boolean isConfigValid() {

        if(Constant.getSenderEmail().contains("exemple") || Constant.getSenderPassword().length() <= 4) {
            return false;
        }
        return true;

    }

    public void log(String... args) {
        for(String arg : args) {
            logger.info(arg);
        }
    }



}
