package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import de.joshiworld.scoreboard.Score;
import de.joshiworld.sql.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private TablistScore plugin;
    private LuckPermsAPI luckperms;

    public PlayerQuitListener(TablistScore plugin) {
        this.plugin = plugin;
        this.luckperms = new LuckPermsAPI(this.plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        setInventory(player);
        updateTab();
        this.plugin.getInventoryMap().remove(player);

        String message = "§7[§c-§7] " + ChatColor.translateAlternateColorCodes('&', this.luckperms.getGroupPrefix(player.getName())) + player.getName();
        event.setQuitMessage(message);
    }

    private void setInventory(Player player) {
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        playerData.setInventory(player.getInventory());
    }

    private void updateTab() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(all -> {
                    Score score = new Score(all, plugin);
                    score.updateScore();
                    score.updateTab();
                });
            }
        }, 20L);
    }

}
