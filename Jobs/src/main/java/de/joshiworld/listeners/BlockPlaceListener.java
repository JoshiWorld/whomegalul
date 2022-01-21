package de.joshiworld.listeners;

import de.joshiworld.main.Jobs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlaceListener implements Listener {
    private final Jobs plugin;

    public BlockPlaceListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(!event.getBlockPlaced().hasMetadata("player"))
            event.getBlockPlaced().setMetadata("player", new FixedMetadataValue(this.plugin, player.getName()));
    }

}
