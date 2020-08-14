package net.everify;

import net.everify.api.MailManager;
import net.everify.commands.CommandHandler;
import net.everify.mail.JavaMail;
import net.everify.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;


public class EVerify extends JavaPlugin {

    private static EVerify INSTANCE;

    private MailManager mailManager;

    private Logger logger = getServer().getLogger();
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        mailManager = new MailManager();

        databaseManager = new DatabaseManager(getConfig().getString("database.host"),
                getConfig().getInt("database.port"),
                getConfig().getString("database.databasename"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"));

        try {
            databaseManager.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getCommand("ev").setExecutor(new CommandHandler());
        if(!isConfigValid()) {
            logger.info(ChatColor.DARK_RED + "ERROR : Config isn't valid");
            this.getServer().getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {

    }

    public final static EVerify getInstance() {
        return INSTANCE;
    }

    private final boolean isConfigValid() {

        if(Constant.getSenderEmail().contains("exemple") || Constant.getSenderPassword().length() <= 4 ||
                getConfig().getString("database.databasename").equalsIgnoreCase(" ")
                || getConfig().getString("database.databasename").isEmpty()
                || getConfig().getString("database.username").equalsIgnoreCase(" ")
                || getConfig().getString("database.username").isEmpty()
                || getConfig().getString("database.password").equalsIgnoreCase(" ")
                || getConfig().getString("database.password").isEmpty()) {
            return false;
        }
        return true;

    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public MailManager getMailManager() { return mailManager; }

    public void log(String... args) {
        for(String arg : args) {
            logger.info(arg);
        }
    }



}
