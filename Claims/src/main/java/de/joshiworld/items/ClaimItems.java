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
        meta.setCustomModelData(420);
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
        meta.setCustomModelData(421);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName("§cClaim löschen");
        item.setItemMeta(meta);
        return item;
    }
}
