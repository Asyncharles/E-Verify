package net.everify.api;

import com.sun.istack.internal.NotNull;
import net.everify.EVerify;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class MailManager {

    private EVerify ev = EVerify.getInstance();

    public MailManager() {

    }


    /**
     *
     * @return The lisf of all the available emails
     */

    public CompletableFuture<List<String>> getAllMails() {

        CompletableFuture<List<String>> future = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            try {

                List<String> addressList = new ArrayList<>();
                String query = "SELECT mail FROM mails";
                Statement statement = ev.getDatabaseManager().getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()) {
                    addressList.add(resultSet.getString("id"));
                }

                future.complete(addressList);

            } catch (SQLException e) {
                ev.log("API ERROR : SQL EXCEPTION [getAllMails - MailManager]");
            }
        });

        return future;

    }

    /**
     *
     * @param id The players UUID
     * @param properties The mail properties
     * @param senderEmail The sender email
     * @param senderPassword The sender password
     * @param subject Email subject
     * @param content Email content (can be HTML & CSS)
     * @param contentType Content type
     */

    public void sendEmailToPlayer(@NotNull UUID id,
                                  Properties properties,
                                  String senderEmail,
                                  String senderPassword,
                                  String subject,
                                  Object content,
                                  String contentType) {

        final Message[] message = new Message[1];

         ev.getDatabaseManager().getPlayerInformation(id).whenComplete(((objects, throwable) -> {

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                message[0] = new MimeMessage(session);

                message[0].setRecipient(Message.RecipientType.TO, new InternetAddress((String) objects[1]));
                message[0].setSubject(subject);
                message[0].setFrom(new InternetAddress(senderEmail));
                message[0].setContent(content, contentType);

            } catch (MessagingException e) {
                e.printStackTrace();
            }


        })).thenAccept((objects) -> {
             try {
                 Transport.send(message[0]);
             } catch (MessagingException e) {
                 e.printStackTrace();
             }
         });



    }


}
