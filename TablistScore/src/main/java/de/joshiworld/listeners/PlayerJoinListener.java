package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import de.joshiworld.sql.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private TablistScore plugin;
    private LuckPermsAPI luckPerms;

    public PlayerJoinListener(TablistScore plugin) {
        this.plugin = plugin;
        this.luckPerms = new LuckPermsAPI(this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        if(!playerData.playerExists()) playerData.createPlayer();

        String message = "§7[§a+§7] " + ChatColor.translateAlternateColorCodes('&', this.luckPerms.getGroupPrefix(player.getName())) + player.getName();
        event.setJoinMessage(message);
    }

}
