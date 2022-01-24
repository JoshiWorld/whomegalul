package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.items.ClaimItems;
import de.joshiworld.main.Claims;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private Claims plugin;
    private LuckPermsAPI luckperms;

    public PlayerQuitListener(Claims plugin) {
        this.plugin = plugin;
        this.luckperms = new LuckPermsAPI(this.plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        unvanish(player);
        removePlayerFromAllLists(player);
        removeClaimItems(player);

        String message = "§7[§c-§7] " + ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getName())) + player.getName();
        //event.setQuitMessage(message);
    }



    // Un-Vanish on Quit
    private void unvanish(Player player) {
        if(this.plugin.getVanishList().contains(player))
            Bukkit.getOnlinePlayers().forEach((all) -> { all.showPlayer(player); });
    }

    // Remove from all Lists
    private void removePlayerFromAllLists(Player player) {
        if(this.plugin.getClaimList().containsKey(player)) this.plugin.getClaimList().remove(player);
        if(this.plugin.getVanishList().contains(player)) this.plugin.getVanishList().remove(player);
        if(this.plugin.getIgnoreList().contains(player)) this.plugin.getIgnoreList().remove(player);
    }

    // Remove Claim Items
    private void removeClaimItems(Player player) {
        if(player.getInventory().contains(ClaimItems.getClaimItem())) player.getInventory().remove(ClaimItems.getClaimItem());
        if(player.getInventory().contains(ClaimItems.getClaimRemoveItem())) player.getInventory().remove(ClaimItems.getClaimRemoveItem());
    }

}
