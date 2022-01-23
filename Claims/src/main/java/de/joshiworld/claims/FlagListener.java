package de.joshiworld.claims;

import de.joshiworld.main.Claims;
import de.joshiworld.sql.ChunkData;
import de.joshiworld.sql.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class FlagListener implements Listener {
    private final Claims plugin;

    public FlagListener(Claims plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamageClaim(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getEntity();
        PlayerData playerDataFromChunk = new PlayerData(new ChunkData(player.getLocation().getChunk().getChunkKey(), this.plugin).lookupChunkOwner(), this.plugin);

        if(playerDataFromChunk.getFlags().isEmpty() || !playerDataFromChunk.getFlags().contains("pvp")) return;
        if(!playerDataFromChunk.getClaims().contains(player.getLocation().getChunk().getChunkKey())) return;

        event.setCancelled(false);
    }

    @EventHandler
    public void onVillagerTrade(InventoryOpenEvent event) {
        if(!(event.getPlayer() instanceof Player) || !event.getInventory().getType().equals(InventoryType.MERCHANT)) return;
        event.setCancelled(true);

        Player player = (Player) event.getPlayer();
        PlayerData playerDataFromChunk = new PlayerData(new ChunkData(player.getLocation().getChunk().getChunkKey(), this.plugin).lookupChunkOwner(), this.plugin);

        if(new PlayerData(player.getName(), this.plugin).getClaims().contains(player.getLocation().getChunk().getChunkKey()) ||
            playerDataFromChunk.getOtherClaims().stream().anyMatch(other -> new PlayerData(other, this.plugin).getClaims().contains(player.getLocation().getChunk().getChunkKey()))) {
            event.setCancelled(false);
            return;
        }
        if(playerDataFromChunk.getFlags().isEmpty() || !playerDataFromChunk.getFlags().contains("trading")) return;
        if(!playerDataFromChunk.getClaims().contains(player.getLocation().getChunk().getChunkKey())) return;

        event.setCancelled(false);
    }

}
