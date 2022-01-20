package de.joshiworld.listeners;

import de.joshiworld.main.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class BlockEventListener implements Listener {
    private Lobby plugin;

    public BlockEventListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(this.plugin.getBuildList().contains(player)) event.setCancelled(false);
        else event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(this.plugin.getBuildList().contains(player)) event.setCancelled(false);
        else event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDmg(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDmgByEntity(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

}
