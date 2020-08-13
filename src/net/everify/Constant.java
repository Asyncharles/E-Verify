package net.everify;

import net.everify.commands.EVCommand;
import org.bukkit.configuration.file.FileConfiguration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

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



    public final static byte[] idToBytes(UUID id) {
        return ByteBuffer.wrap(new byte[16])
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(id.getMostSignificantBits())
                .putLong(id.getLeastSignificantBits()).array();
    }

    public final static UUID bytesToId(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
        long idLong = buffer.getLong();
        long idLongS = buffer.getLong();
        return new UUID(idLong, idLongS);
    }

    public final static String getCommandHelpMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(" \n");

        for(EVCommand command : EVCommand.getEvCommandList()) {
            builder.append("§b/ev " + command.getCommand() + " §6- §e" + command.getDescription() + " §6- §3Is Admin : §d" + command.isAdmin() + "\n");
        }
        return builder.toString();
    }




}
