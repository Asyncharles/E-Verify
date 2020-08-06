package net.everify;

import org.bukkit.configuration.file.FileConfiguration;

public class Constant {

    private static FileConfiguration file = EVerify.getInstance().getConfig();

    public final static String getSenderEmail() {
        return file.getString("mails.user");
    }

    public final static String getSenderPassword() {
        return file.getString("mails.password");
    }

    public final static String getEmailSubject() {
        return file.getString("mails.messages.subject");
    }

    public final static String smtpHost() {
        return file.getString("mails.othermailconfig.smtphost");
    }

    public final static Integer smtpPort() {
        return file.getInt("mails.othermailconfig.smtpport");
    }

    public final static boolean isGmail() {
        return file.getBoolean("mails.gmail");
    }

    public final static Integer getAttempts() {
        return file.getInt("verification.attempts");
    }

    public final static String getVerifiedMessage() {
        return file.getString("verification.messages.verified").replace("&", "§");
    }

    public final static String getAfterAttemptsMessage() {
        return file.getString("verification.messages.afterattempts").replace("&", "§");
    }

    public final static String getAdminPermission() {
        return file.getString("command.adminpermission");
    }




}
