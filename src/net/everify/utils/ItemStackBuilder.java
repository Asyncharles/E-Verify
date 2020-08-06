package net.everify.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemStackBuilder(Material type) {
        this.itemStack = new ItemStack(type);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemStackBuilder(Material type, int amount) {
        this.itemStack = new ItemStack(type, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemStackBuilder(Material type, int amount, short damage) {
        this.itemStack = new ItemStack(type, amount, damage);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = new ItemStack(itemStack);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemStackBuilder name(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemStackBuilder lore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level, boolean bool) {
        itemMeta.addEnchant(enchantment, level, bool);
        return this;
    }

    public ItemStackBuilder removeEnchantment(Enchantment enchantment) {
        itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemStackBuilder itemFlags(ItemFlag... itemFlag) {
        itemMeta.removeItemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[] {}));
        itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public ItemStack create() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}

