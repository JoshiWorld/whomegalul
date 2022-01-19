package de.joshiworld.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClaimItems {

    public static ItemStack getClaimItem() {
        ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName("§eClaim-Schaufel");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getClaimRemoveItem() {
        ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName("§cClaim löschen");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getAdminClaimItem() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName("§cADMIN CLAIM");
        item.setItemMeta(meta);
        return item;
    }
}
