package net.everify.mail;


import net.everify.Constant;
import net.everify.EVerify;
import org.bukkit.entity.Player;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.Executors;


public class JavaMail {

    /**
     * Opens a session to the senders a email
     * @param receiver the email of the user that will receive an email
     * @param code the code, that the user will use to verify
     * @param player the player that will verify
     */

    public final static void sendVerificationEmail(String receiver, int code, Player player) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");

        if(Constant.isGmail()) {
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
        } else {
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", Constant.smtpHost());
            properties.put("mail.smtp.port", Constant.smtpPort());
        }


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constant.getSenderEmail(), Constant.getSenderPassword());
            }
        });

        Message message = verificationMessage(session, Constant.getSenderEmail(), receiver, code, player);

        Executors.newCachedThreadPool().submit(() -> {
            try {
                Transport.send(message);
                EVerify.getInstance().log("Email sent to " + receiver + " with code " + code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    /**
     *
     * @param session The session to the senders email
     * @param senderEmail The senders email
     * @param receiver The users email that will receive an email
     * @param code The code use to verify
     * @param player The player
     * @return A message (HTML CODE)
     */

    private static Message verificationMessage(Session session, String senderEmail, String receiver, int code, Player player) {
        try {
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            msg.setSubject(Constant.getEmailSubject());
            msg.setContent(new MailHTMLCode(code, player.getName(), player.getUniqueId().toString()).getHTMLCode(), "text/html");
            return msg;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
