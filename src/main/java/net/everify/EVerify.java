package net.everify;

import com.google.common.io.Resources;

import net.everify.api.MailManager;
import net.everify.commands.CommandHandler;
import net.everify.sql.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.logging.Logger;


public class EVerify extends JavaPlugin {

    private static EVerify INSTANCE;

    private MailManager mailManager;

    private Logger logger = getServer().getLogger();
    private DatabaseManager databaseManager;
    private File html, css;
    private File[] files;
    private File inputFiles;

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
            log("Database error");
            e.printStackTrace();
        }

        try {
            createDirectory();
            createFiles();

            files = new File[] {html, css};

            initializeMailCode();
        } catch (IOException exception) {
            log("File/Directory error");
            exception.printStackTrace();
        }

        getCommand("ev").setExecutor(new CommandHandler());
        if(!isConfigValid()) {
            log( "§cERROR : Config isn't valid");
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

    private void createDirectory() {

        File folder =  new File(this.getFile().getParentFile().getAbsolutePath() + "/EVerify/resources");
        File inputFoler = new File(this.getFile().getParentFile().getAbsolutePath() + "/EVerify/mail");

        if (!folder.exists() ) {

            folder.mkdirs();
            log("Created directory for resources [" + folder.getAbsolutePath() + "]");

        }

        if(!inputFoler.exists()) {

            folder.mkdirs();
            log("Created directory for resources [" + inputFoler.getAbsolutePath() + "]");

        }

    }

    private void createFiles() throws IOException {

        File htmlFile = new File(this.getFile().getParentFile().getAbsolutePath() + "/EVerify/resources/index.html");
        File cssFile = new File(this.getFile().getParentFile().getAbsolutePath() + "/EVerify/resources/style.css");


        if(!htmlFile.exists()) {
            htmlFile.createNewFile();
            log("Created html file for resources [" + htmlFile.getAbsolutePath() + "]");
        }

        if(!cssFile.exists()) {
            cssFile.createNewFile();
            log("Created css file for resources [" + cssFile.getAbsolutePath() + "]");
        }

        html = htmlFile;
        css = cssFile;

    }

    private void initializeMailCode() throws IOException {

        if(!getConfig().getBoolean("mails.mailcode.initialized")) {

            for(File file : files) {

                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

                bw.write(Resources.toString(getClass().getResource("mail/resources/" + file.getName()), StandardCharsets.UTF_8));
                bw.close();
            }

            getConfig().set("mails.mailcode.initialized", true);


        }


    }

    public URL getHTMLURL() throws IOException {

        return html.toURL();

    }

    public URL getCssURL() throws IOException {

        return css.toURL();

    }



}
