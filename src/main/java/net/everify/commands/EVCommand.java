package net.everify.commands;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class EVCommand {

    private static List<EVCommand> evCommandList = new ArrayList<>();

    private String command;
    private String description;
    private boolean admin;

    public EVCommand(String command, String description, boolean admin) {
        this.command = command;
        this.description = description;
        this.admin = admin;
        evCommandList.add(this);
    }

    public final static List<EVCommand> getEvCommandList() {
        return evCommandList;
    }

    public abstract void onCommand(Player player, String[] args);

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAdmin() {
        return admin;
    }
}
