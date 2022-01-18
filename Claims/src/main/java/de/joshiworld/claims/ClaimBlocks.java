package de.joshiworld.claims;

import de.joshiworld.main.Claims;
import de.joshiworld.sql.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class ClaimBlocks implements Listener {
    private final Claims plugin;

    public ClaimBlocks(Claims plugin) {
        this.plugin = plugin;
    }

    // Cancel wenn Claimschaufel in Hand
    @EventHandler
    public void onClaim(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(checkClaimItem(player)) event.setCancelled(true);
    }

    // Cancel wenn Claimschaufel in Hand
    @EventHandler
    public void onClaim(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(checkClaimItem(player)) event.setCancelled(true);
    }

    // Check ob Claimschaufel in Hand
    private boolean checkClaimItem(Player player) {
        if(!(this.plugin.getClaimList().containsKey(player)) ||
                (player.getItemInHand().getType().equals(Material.AIR)) ||
                !(player.getItemInHand().getItemMeta().getCustomModelData() == 420)
        ) {
            return false;
        }
        return true;
    }

    // Check ob ClaimschaufelRemove in Hand
    private boolean checkClaimRemoveItem(Player player) {
        if(!(this.plugin.getClaimList().containsKey(player)) ||
                (player.getItemInHand().getType().equals(Material.AIR)) ||
                !(player.getItemInHand().getItemMeta().getCustomModelData() == 421)
        ) {
            return false;
        }
        return true;
    }

    // Item Click von Claimschaufel
    @EventHandler
    public void onClaim(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR)) && event.getHand().equals(EquipmentSlot.OFF_HAND))
            return;

        if(!(this.plugin.getClaimList().containsKey(player)) ||
                !(checkClaimItem(player)) ||
                event.getHand().equals(EquipmentSlot.OFF_HAND) ||
                event.getClickedBlock().getType().equals(Material.AIR)) return;

        if(new PlayerData(player.getName(), this.plugin).checkIfClaimed(event.getClickedBlock().getChunk().getChunkKey())) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Chunk gehört dir nicht!");
            return;
        }

        int set = 0;
        if(event.getAction().isLeftClick()) set = 1;

        List<Long> list = this.plugin.getClaimList().get(player);
        if(list.size() > 0) {
            list.set(set, event.getClickedBlock().getChunk().getChunkKey());
            player.sendMessage(this.plugin.getPrefix() + " §aWenn du dir sicher bist, dann bestätige mit: §e/claim create");
        } else {
            list.add(event.getClickedBlock().getChunk().getChunkKey());
            player.sendMessage(this.plugin.getPrefix() + " §aChunk gesetzt");
        }
        this.plugin.getClaimList().put(player, list);
    }

    // Item Click von ClaimschaufelRemove
    @EventHandler
    public void onClaimRemove(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR)) && event.getHand().equals(EquipmentSlot.OFF_HAND))
            return;

        if(!(this.plugin.getClaimList().containsKey(player)) ||
                !(checkClaimRemoveItem(player)) ||
                event.getHand().equals(EquipmentSlot.OFF_HAND) ||
                event.getClickedBlock().getType().equals(Material.AIR)) return;

        if(!new PlayerData(player.getName(), this.plugin).getClaims().contains(event.getClickedBlock().getChunk().getChunkKey())) {
            player.sendMessage(this.plugin.getPrefix() + " §cDieser Chunk gehört dir nicht!");
            return;
        }

        int set = 0;
        if(event.getAction().isLeftClick()) set = 1;

        List<Long> list = this.plugin.getClaimList().get(player);
        if(list.size() > 0) {
            list.set(set, event.getClickedBlock().getChunk().getChunkKey());
            player.sendMessage(this.plugin.getPrefix() + " §aWenn du dir sicher bist, dann bestätige mit: §e/claim remove");
        } else {
            list.add(event.getClickedBlock().getChunk().getChunkKey());
            player.sendMessage(this.plugin.getPrefix() + " §aChunk gesetzt");
        }
        this.plugin.getClaimList().put(player, list);
    }

}
