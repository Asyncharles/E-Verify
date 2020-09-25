package net.everify.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.mail.Message;
import javax.mail.Session;

public class AsyncMailEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private String senderEmail, receiverEmail;
    private Player player;
    private Session session;
    private Message message;
    private boolean cancelled = false;


    public AsyncMailEvent(Player player, Session session, String senderEmail, String receiverEmail, Message message) {
        this.player = player;
        this.session = session;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.message = message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public Player getPlayer() {
        return player;
    }

    public Session getSession() {
        return session;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

}
