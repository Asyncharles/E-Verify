package net.everify.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;



public class PlayerVerifyEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private String email;
    private int code;
    private boolean cancelled = false;

    public PlayerVerifyEvent(Player player, String email, int code) {
        this.player = player;
        this.email = email;
        this.code = code;
    }

    public Player getPlayer() {
        return player;
    }

    public String getEmail() {
        return email;
    }

    public int getCode() {
        return code;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
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
