package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
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

        String message = "§7[§c-§7] " + ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getName())) + player.getName();
        event.setQuitMessage(message);
    }



    // Un-Vanish on Quit
    private void unvanish(Player player) {
        if(this.plugin.getVanishList().contains(player))
            Bukkit.getOnlinePlayers().forEach((all) -> { all.showPlayer(player); });
    }

}
