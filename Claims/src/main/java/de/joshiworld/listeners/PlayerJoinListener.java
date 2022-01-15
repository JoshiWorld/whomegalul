package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.Claims;
import de.joshiworld.sql.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private Claims plugin;
    private LuckPermsAPI luckperms;

    public PlayerJoinListener(Claims plugin) {
        this.plugin = plugin;
        this.luckperms = new LuckPermsAPI(this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        if(!playerData.playerExists()) playerData.createPlayer();

        String message = "§7[§a+§7] " + ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getName())) + player.getName();
        event.setJoinMessage(message);
    }

}
