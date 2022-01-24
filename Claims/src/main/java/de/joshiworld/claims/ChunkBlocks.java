package de.joshiworld.claims;

import de.joshiworld.api.LuckPermsAPI;
import de.joshiworld.main.Claims;
import de.joshiworld.sql.ChunkData;
import de.joshiworld.sql.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

public class ChunkBlocks implements Listener {
    private final Claims plugin;
    private final LuckPermsAPI luckperms;

    public ChunkBlocks(Claims plugin) {
        this.plugin = plugin;
        luckperms = new LuckPermsAPI(this.plugin);
    }

    // Block break on claimed chunk
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Long chunk = event.getBlock().getChunk().getChunkKey();
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        if(this.plugin.getIgnoreList().contains(player)) {
            event.setCancelled(false);
            return;
        }

        if(!checkBlock(player, chunk, playerData)) event.setCancelled(true);
    }


    // Block place on claimed chunk
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Long chunk = event.getBlock().getChunk().getChunkKey();
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        if(this.plugin.getIgnoreList().contains(player)) {
            event.setCancelled(false);
            return;
        }

        if(!checkBlock(player, chunk, playerData)) event.setCancelled(true);
    }

    // No Interact
    @EventHandler
    public void onClaimInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) return;
        Player player = event.getPlayer();

        if(this.plugin.getIgnoreList().contains(player)) {
            event.setCancelled(false);
            return;
        }

        Long chunk = event.getClickedBlock().getChunk().getChunkKey();
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);

        if(player.getItemInHand().getType().equals(Material.OAK_BOAT) || player.getItemInHand().getType().equals(Material.BIRCH_BOAT) ||
                player.getItemInHand().getType().equals(Material.ACACIA_BOAT) || player.getItemInHand().getType().equals(Material.JUNGLE_BOAT) ||
                player.getItemInHand().getType().equals(Material.DARK_OAK_BOAT) || player.getItemInHand().getType().equals(Material.SPRUCE_BOAT)) {
            event.setCancelled(false);
            return;
        }
        if(!checkBlock(player, chunk, playerData)) event.setCancelled(true);
    }

    // Check if chunk is claimed or trusted
    private boolean checkBlock(Player player, Long chunk, PlayerData playerData) {
        boolean isTrusted = playerData.getOtherClaims().stream().anyMatch(trusted -> {
            PlayerData trustedData = new PlayerData(trusted, this.plugin);
            return trustedData.getClaims().contains(chunk);
        });

        if(playerData.playerExists() && !playerData.getClaims().contains(chunk) && !isTrusted) {
            if(!this.plugin.getClaimList().containsKey(player)) {
                if(playerData.checkIfClaimed(chunk)) player.sendMessage(this.plugin.getPrefix() + " §cDieser Chunk gehört dir nicht!");
                else player.sendMessage(this.plugin.getPrefix() + " §cDieser Chunk wurde noch nicht geclaimed!");
            }
            return false;
        }

        return true;
    }

    // Prevent Pistons
    @EventHandler
    public void onPistonExtClaim(BlockPistonExtendEvent event) {
        event.getBlocks().forEach((block) -> {
            if(preventPistons(block)) event.setCancelled(true);
        });
    }

    // Prevent Pistons
    @EventHandler
    public void onPistonRetClaim(BlockPistonRetractEvent event) {
        event.getBlocks().forEach((block) -> {
            if(preventPistons(block)) event.setCancelled(true);
        });
    }

    // Prevent pistons
    private boolean preventPistons(Block block) {
        if(!checkClaimForPiston(block.getChunk().getChunkKey())) return false;

        Optional<Player> playerOpt = (Optional<Player>) Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getLocation().getChunk().getChunkKey() == block.getChunk().getChunkKey())
                .findFirst();

        if(!playerOpt.isPresent()) return false;

        Player player = playerOpt.get();
        PlayerData playerData = new PlayerData(player.getName(), this.plugin);
        return playerData.getClaims().contains(block.getChunk().getChunkKey());
    }

    // Check Chunk
    private boolean checkClaimForPiston(Long chunk) {
        return new ChunkData(chunk, this.plugin).checkBlockForChunk();
    }

}
