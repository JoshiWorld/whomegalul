package de.joshiworld.claims;

import de.joshiworld.main.Claims;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ClaimBlocks implements Listener {
    private Claims plugin;

    public ClaimBlocks(Claims plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClaim(BlockBreakEvent event) {
        Player player = event.getPlayer();
    }



    @EventHandler
    public void onClaim(BlockPlaceEvent event) {
        Player player = event.getPlayer();
    }

}
