package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.Claims;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private Claims plugin;
    private LuckPermsAPI luckperms;

    public PlayerChatListener(Claims plugin) {
        this.plugin = plugin;
        this.luckperms = new LuckPermsAPI(this.plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        //e.setFormat("§8§l[§7§lPleb§8§l] §7" + p.getName() + "§8: §f" + e.getMessage());

        //e.setFormat("§7§l[" + ChatColor.translateAlternateColorCodes('&', color) + "§l" + group.substring(0, 1).toUpperCase() + group.substring(1) + "§7§l] " +
        //        ChatColor.translateAlternateColorCodes('&', color) + p.getName() + "§7: §f" + e.getMessage());

        Bukkit.getServer().broadcast(new TextComponent("NIGGA"));

        TextComponent text = new TextComponent("NIGGA");

        if(this.luckperms.getGroup(player.getUniqueId()).getName().equalsIgnoreCase("default"))
            event.setFormat("§8§l[§7§lPleb§8§l] §7" + player.getName() + "§8: §f" + event.getMessage());
        else
            event.setFormat("§7§l[" + ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getUniqueId())) + "§l" + this.luckperms.getGroup(player.getUniqueId()).getName().substring(0, 1).toUpperCase() + this.luckperms.getGroup(player.getUniqueId()).getName().substring(1) + "§7§l] " +
                    ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getUniqueId())) + player.getName() + "§7: §f" + event.getMessage());
    }
}
