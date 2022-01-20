package de.joshiworld.listeners;

import de.joshiworld.main.Lobby;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private Lobby plugin;

    public PlayerMoveListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(!this.plugin.getBuildList().contains(player)) {
            if(player.getLocation().getY() < -50) player.teleport(new Location(player.getWorld(), 29, -22, 182));
        }
    }

}
