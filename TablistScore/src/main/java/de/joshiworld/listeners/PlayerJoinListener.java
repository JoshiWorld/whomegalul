package de.joshiworld.listeners;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.TablistScore;
import de.joshiworld.scoreboard.Score;
import de.joshiworld.sql.PlayerData;
import de.joshiworld.sql.WhiteList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

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

        Bukkit.getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                getInventory(player, playerData);
                putInMap(player);
            }
        }, 20L);

        Bukkit.getOnlinePlayers().forEach(all -> {
            Score score = new Score(all, this.plugin);
            score.updateScore();
            score.updateTab();
        });

        String message = "§7[§a+§7] " + ChatColor.translateAlternateColorCodes('&', this.luckPerms.getGroupPrefix(player.getName())) + player.getName();
        event.setJoinMessage(message);
    }

    @EventHandler
    public void onPreJoin(PlayerPreLoginEvent event) {
        String player = event.getName();
        WhiteList whiteList = new WhiteList(player, this.plugin);

        if(!whiteList.playerExists()) whiteList.createPlayer();
        if(!whiteList.getWhitelist()) {
            String msg = this.plugin.getPrefix() + " §cDu bist nicht auf der Whitelist!";
            event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, msg);
        }

        if(!this.luckPerms.hasPermissionGroup("claims.maintenance", event.getUniqueId())) {
            String msg = this.plugin.getPrefix() + " §cSERVER-WARTUNG!";
            event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, msg);
        }
    }

    private void getInventory(Player player, PlayerData playerData) {
        player.getInventory().setArmorContents(playerData.getInventory().getArmorContents());
        player.getInventory().setContents(playerData.getInventory().getContents());
        player.getInventory().setStorageContents(playerData.getInventory().getStorageContents());
    }

    private void putInMap(Player player) {
        plugin.getInventoryMap().put(player, Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                PlayerData playerData = new PlayerData(player.getName(), plugin);
                playerData.setInventory(player.getInventory());
            }
        }, 0, 20*10));
    }

}
