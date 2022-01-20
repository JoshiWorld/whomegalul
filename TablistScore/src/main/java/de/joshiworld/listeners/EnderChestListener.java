package de.joshiworld.listeners;

import de.joshiworld.main.TablistScore;
import de.joshiworld.sql.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class EnderChestListener implements Listener {
    private TablistScore plugin;

    public EnderChestListener(TablistScore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnderChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getClickedBlock() == null || !event.getClickedBlock().getType().equals(Material.ENDER_CHEST) || !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        event.setCancelled(true);

        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        Inventory inventory = playerData.getEnderChest() != null ? playerData.getEnderChest() : createEnderChest(player);

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.0F, 1.0F);
    }

    @EventHandler
    public void onCloseEnder(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();

        if(!event.getView().getTitle().equals("§5Ender-Chest")) return;

        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        playerData.setEnderChest(event.getInventory());
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0F, 1.0F);
    }

    // Create Inventory
    private Inventory createEnderChest(Player player) {
        return Bukkit.createInventory(null, 27, "§5Ender-Chest");
    }

}
