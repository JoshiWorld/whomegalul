package de.joshiworld.listeners;

import de.joshiworld.api.LevelAPI;
import de.joshiworld.main.Jobs;
import de.joshiworld.sql.JobsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class HolzListener implements Listener {
    private Jobs plugin;

    public HolzListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        JobsData jobsData = new JobsData(player.getName(), this.plugin);
        LevelAPI levelAPI = new LevelAPI(player, this.plugin);

        if(!jobsData.getJob().equalsIgnoreCase("holzfäller")) return;
        if(event.getBlock().hasMetadata("player")) return;

        switch(event.getBlock().getType()) {
            case ACACIA_LOG:
            case OAK_LOG:
            case BIRCH_LOG:
            case DARK_OAK_LOG:
            case JUNGLE_LOG:
            case SPRUCE_LOG:
            case STRIPPED_ACACIA_LOG:
            case STRIPPED_BIRCH_LOG:
            case STRIPPED_DARK_OAK_LOG:
            case STRIPPED_JUNGLE_LOG:
            case STRIPPED_OAK_LOG:
            case STRIPPED_SPRUCE_LOG:
                levelAPI.addXP(2.0);
                break;
            case CRIMSON_STEM:
            case STRIPPED_CRIMSON_STEM:
            case WARPED_STEM:
            case STRIPPED_WARPED_STEM:
                levelAPI.addXP(3.0);
                break;
        }
    }

}
