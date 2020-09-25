package net.everify.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A simple class to make GUIs
 * @author GhostRider584 & modified by Aksyo
 * @version 1.1
 */
public class GUI implements Listener {

    private final Map<Integer, BiConsumer<Player, InventoryClickEvent>> actions = new HashMap<>();
    private BiConsumer<Player, InventoryCloseEvent> closeAction;

    private final Inventory inventory;

    private final List<Integer> lockedSlots = new ArrayList<>();
    private boolean isLocked = false;

    /**
     * Creates a new GUI instance
     * @param plugin The plugin instance used to register the events
     * @param title The title of the inventory
     * @param rows The number of rows of the inventory
     */
    public GUI(final Plugin plugin, final String title, final int rows) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        inventory = Bukkit.createInventory(null, rows*9, title);
    }

    /**
     * Copy constructor
     * @param gui GUI instance to copy
     */
    public GUI(final GUI gui) {
        actions.putAll(gui.actions);
        closeAction = gui.closeAction;
        lockedSlots.addAll(gui.lockedSlots);
        isLocked = gui.isLocked;
        inventory = Bukkit.createInventory(null, gui.inventory.getSize(), gui.inventory.getTitle());
    }

    /**
     * @param slot The slot to put the item in
     * @param item The ItemStack to put in the slot
     * @param consumer The consumer called when a player clicks on the item
     */
    public void setItem(final int slot, final ItemStack item, final BiConsumer<Player, InventoryClickEvent> consumer) {
        actions.put(slot, consumer);
        inventory.setItem(slot, item);
    }

    /**
     * Locks or unlocks the entire inventory
     * @param locked {@code true} if the inventory should be locked, {@code false} if not
     */
    public final void setLocked(final boolean locked) {
        isLocked = locked;
    }

    /**
     * Locks or unlocks a specific slot
     * @param slot The slot to lock or unlock
     * @param locked {@code true} if the inventory should be locked, {@code false} if not
     */
    public final void setLocked(final Integer slot, final boolean locked) {
        if(locked) {
            lockedSlots.add(slot);
        } else {
            lockedSlots.remove(slot);
        }
    }

    /**
     * Opens the inventory to the specified Player
     * @param player The player
     */
    public void open(final Player player) {
        player.openInventory(inventory);
    }

    /**
     * Sets the click action of a specific slot
     * @param slot The slot number
     * @param consumer The consumer called when a player clicks on the item
     */
    public void setAction(final int slot, final BiConsumer<Player, InventoryClickEvent> consumer) {
        actions.put(slot, consumer);
    }

    /**
     * Sets the inventory close action
     * @param consumer The consumer called when a player closes the inventory
     */
    public void setCloseAction(final BiConsumer<Player, InventoryCloseEvent> consumer) {
        closeAction = consumer;
    }

    /**
     * Returns the underlying inventory instance
     * @return The inventory instance
     */
    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    private void onClick(final InventoryClickEvent event) {
        if(!event.getInventory().equals(inventory)) {
            return;
        }
        final int slot = event.getSlot();
        event.setCancelled(isLocked || lockedSlots.contains(slot));
        final BiConsumer<Player, InventoryClickEvent> action;
        if(actions.containsKey(slot) && (action = actions.get(slot)) != null) {
            action.accept((Player) event.getWhoClicked(), event);
        }
    }

    @EventHandler
    private void onInventoryClose(final InventoryCloseEvent event) {
        if(!event.getInventory().equals(inventory)) {
            return;
        }
        if(closeAction != null) {
            closeAction.accept((Player) event.getPlayer(), event);
        }
    }


}
