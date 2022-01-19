package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private TablistScore plugin;
    private LuckPermsAPI luckperms;

    public PlayerChatListener(TablistScore plugin) {
        this.plugin = plugin;
        this.luckperms = new LuckPermsAPI(this.plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(this.luckperms.getGroup(player.getUniqueId()).getName().equalsIgnoreCase("default"))
            event.setFormat("§8§l[§7§lPleb§8§l] §7" + player.getName() + "§8: §f" + event.getMessage());
        else
            event.setFormat("§7§l[" + ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getUniqueId())) + "§l" + this.luckperms.getGroup(player.getUniqueId()).getName().substring(0, 1).toUpperCase() + this.luckperms.getGroup(player.getUniqueId()).getName().substring(1) + "§7§l] " +
                    ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getUniqueId())) + player.getName() + "§7: §f" + event.getMessage());
    }
}
