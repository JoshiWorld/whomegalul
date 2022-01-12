package de.joshiworld.claims;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.Claims;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ChunkBlocks implements Listener {
    private Claims plugin;
    private LuckPermsAPI luckperms;

    public ChunkBlocks(Claims plugin) {
        this.plugin = plugin;
        luckperms = new LuckPermsAPI(this.plugin);
    }

    // Block break on claimed chunk
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Long chunk = event.getBlock().getChunk().getChunkKey();

        if(!this.plugin.getClaimedChunks().containsKey(player) || !this.plugin.getClaimedChunks().get(player).contains(chunk)) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Chunk gehört dir nicht!");
            event.setCancelled(true);
            return;
        }

        this.plugin.getClaimedChunks().get(player).forEach((li) -> {
            player.sendMessage("§a" + li);
        });
    }


    // Block place on claimed chunk
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Long chunk = event.getBlock().getChunk().getChunkKey();

        if(!this.plugin.getClaimedChunks().containsKey(player) || !this.plugin.getClaimedChunks().get(player).contains(chunk)) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Chunk gehört dir nicht!");
            event.setCancelled(true);
            return;
        }
    }

}
